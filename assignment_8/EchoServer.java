package assignment_8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
	public static void main(String[] args) {
		try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); Selector s = Selector.open()){
			
			//.socket ritorna un il serversocket di serverSocketChannel
			//tramite bind, lego il server socket alla porta a cui si connette il client
			serverSocketChannel.socket().bind(new InetSocketAddress(5000));
			
			//configuro il server come non bloccante
			serverSocketChannel.configureBlocking(false);
			
			//registro il canale del server all'interno del selettore
			//come operazione di interesse in questo caso si utilizza solo OP_ACCEPT
			serverSocketChannel.register(s, SelectionKey.OP_ACCEPT);
			System.out.println("Server: waiting for connection");
			
			while(true) {
				s.selectedKeys().clear();
				
				//seleziono le chiavi corrispondenti ai canali pronti su operazioni di I/O
				System.out.println("Server: now selecting");
				s.select();
				Set<SelectionKey> selectedKeys = s.selectedKeys();
				
				//l'iteratore mi servirà per tenere aggiornate le chiavi
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				
				while(iter.hasNext()) {
					SelectionKey key = iter.next();
					
					//rimuovo le chiavi dal selected set ma non dal registed set
					//questo per fare in modo che le chiavi vengano aggiornate alla prossima iterazione
					iter.remove();
					
					//se l'operazione di interesse pronta per quella chiave è una ACCEPT
					if(key.isAcceptable()) acceptClient(s,key);
					
					//se l'operazioen di interesse pronta per quella chiave è una READ
					if(key.isReadable()) read(s, key);
					
					//se l'operazione di interesse pronta per quella chiave è una WRITE
					if(key.isWritable()) write(key);

				}
			}
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	private static void read(Selector s, SelectionKey key) throws IOException {
		System.out.println("Server: reading the client message.");
		
		//estraggo il socketchannel del canale pronto per l'operazione di scrittura
		SocketChannel client = (SocketChannel) key.channel();
		
		//estraggo il messaggio incapsulato precedentemente durante l'accetazione della connessione
		ByteBuffer message = (ByteBuffer)key.attachment();
		
		//eseguo la lettura del messaggio dal client e provvedo con il trasferimento sul bytebuffer message
		long res = client.read(message);
		while(res != 0 && res != -1) res = client.read(message);
		
		//passo alla modalità scrittura
		message.flip();
		
		//registro il client sul selettore cambiando l'operazione di interesse
		client.register(s, SelectionKey.OP_WRITE,message);
	}

	private static void write(SelectionKey key) throws IOException {
		System.out.println("Server: sending the aswer to client channel.");
		//estraggo il socketchannel del canale pronto per l'operazione di scrittura
		SocketChannel client = (SocketChannel) key.channel();
		
		//estraggo il messaggio incapsulato precedentemente durante l'accetazione della connessione
		ByteBuffer message = (ByteBuffer)key.attachment();
		
		//scrivo il messaggio sul canale del client
		client.write(message);
		
		//se il messaggio è stato scritto per la sua interezza
		//chiudo il canale e cancello la chiave
		if(!message.hasRemaining()) {
			client.close();
			key.cancel();
		}
	}

	private static void acceptClient(Selector s, SelectionKey key) throws IOException {
		//estraggo il serversocketchannel relativo alla chiave
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		
		//creo un socket channel per il client
		SocketChannel client = server.accept();
		System.out.println("New client "+ client.getRemoteAddress());
		client.configureBlocking(false);
		
		//alloco un buffer che conterrà il messaggio del client
		ByteBuffer message = ByteBuffer.allocate(1024);
		
		//registro il client nel selettore con operazione di interesse READ
		//allego al client il messaggio
		client.register(s, SelectionKey.OP_READ, message);
	}
}
