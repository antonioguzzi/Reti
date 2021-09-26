package assignment_8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

public class EchoClient {
	public static void main(String[] args) {
		SocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 5000);
		//utilizzo un canale in sola lettura per lo standard in
		try(ReadableByteChannel input = Channels.newChannel(System.in); SocketChannel clientSocketChannel = SocketChannel.open(serverAddress)){
			ByteBuffer message = ByteBuffer.allocate(1024);
			System.out.println("insert the message for server:");
			
			//trasferisco il messaggio da tastiera al bytebuffer
			input.read(message);
			
			//passo alla modalità scrittura del bytebuffer
			message.flip();
			
			//scrivo il contenuto del bytebuffer sul buffer in uscita del client
			clientSocketChannel.write(message);
			
			//passo alla modalita lettura del butebuffer resettando le posizioni
			message.clear();
			
			//leggo il contenuto del messaggio sul buffer di entrata del client
			clientSocketChannel.read(message);
			System.out.print("Your Message: " + new String(message.array(),0,message.limit()));
		} catch (IOException e) {e.printStackTrace();}
	}
}
