package assignment_10;

import java.io.IOException;
import java.net.*;

public class TimeServer {
	 private InetAddress multicastDateGroup;
	 private int port;
	 
	 public TimeServer(String addr, int port) throws UnknownHostException {
		 this.multicastDateGroup = InetAddress.getByName(addr);
		 //se l'indirizzo non è multicast lancio un'eccezione
		 if(!this.multicastDateGroup.isMulticastAddress()) throw new IllegalArgumentException();
		 this.port = port;
	 }
	 
	 /**
	  * avvia il server
	  */
	 public void start() {
		 //il server non necessita di una socket multicast
		 try (DatagramSocket serverSocket = new DatagramSocket()){
			 while(true) {
				 String date = (java.time.LocalDateTime.now()).toString();
				 DatagramPacket packetToSend = new DatagramPacket(date.getBytes(),date.length(),this.multicastDateGroup,this.port);
				 serverSocket.send(packetToSend);
				 System.out.printf("Server send: %s\n", new String(packetToSend.getData(), packetToSend.getOffset(), packetToSend.getLength()));
				 Thread.sleep(1000);
			 }
		 } catch (IOException | InterruptedException e) {e.printStackTrace();}
	 }
}
