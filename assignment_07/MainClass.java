
import java.io.*;
import java.net.*;

public class MainClass {
	public static void main (String args[]) throws Exception {
		int myPort = 6791;
		
		@SuppressWarnings("resource")
		ServerSocket listenSocket = new ServerSocket (myPort);
		while (true) {
			Socket connectionSocket = listenSocket.accept();
			//leggo il contenuto della richiesta(GET)
			//bufferizzo la lettura per una maggiore efficienza
			BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			//leggo una linea alla volta e stampo a schermo le stringhe inviate dal client sul receive buffer
			String line = reader.readLine();
			//estraggo il path del file richiesto
			String[] parseLine = line.split(" ");
			if(!parseLine[1].equals("/favicon.ico")) {
				System.out.println("Richiesta di connessione accettata");
				while(!line.isEmpty()) {
					System.out.println(line);
					line = reader.readLine();
				}
				System.out.println();
				String fileName = parseLine[1].substring(1);
				
				//mando una risposta al client
				File file = new File(fileName);
				int numOfBytes = (int) file.length();
				byte[] buffer = new byte[numOfBytes];
				
				//bufferizzo la lettura per una maggiore efficienza
				BufferedInputStream readFile = new BufferedInputStream(new FileInputStream(fileName));
				//bufferizo la scrittur sul buffer di output del socket (quello che leggerà il client) per una maggiore efficienza
				BufferedOutputStream writeFile = new BufferedOutputStream(connectionSocket.getOutputStream());
				//costruisco il primo messaggio di invio dal server al client facendo attenzione al line break
				
				writeFile.write(("HTTP/1.1 200 OK\r\n\r\n").getBytes());
				//leggendo il file inserisco i byte corrispondenti a quest'ultimo dentro al buffer
				while(readFile.read(buffer) != -1) writeFile.write(buffer);
				writeFile.write("\r\n\r\n".getBytes());
				writeFile.flush();
				
				reader.close();
				readFile.close();
				connectionSocket.close();
			}
		}
	}
}
