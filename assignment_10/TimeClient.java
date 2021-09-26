package assignment_10;

import java.io.IOException;
import java.net.*;

public class TimeClient {
	private InetAddress multicastDateGroup;
	private int port;
	
	public TimeClient(String addr, int port) throws UnknownHostException {
		this.multicastDateGroup = InetAddress.getByName(addr);
		 //se l'indirizzo non è multicast lancio un'eccezione
		 if(!this.multicastDateGroup.isMulticastAddress()) throw new IllegalArgumentException();
		 this.port = port;
	}
	
	/**
	 * avvia il client
	 */
	public void startClient() {
		try (MulticastSocket dateGroup = new MulticastSocket(this.port)){
			dateGroup.joinGroup(multicastDateGroup);
			for(int i = 0; i < 10; i++) {
				 DatagramPacket receivedPacket = new DatagramPacket(new byte[1024], 1024);
				 dateGroup.receive(receivedPacket);
				 System.out.printf("Client received: %s\n", new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength()));
			}
		} catch (IOException e) {e.printStackTrace();}
	}
}
