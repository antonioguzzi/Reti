package assignment_10;

import java.net.UnknownHostException;

public class MainClassClient {
	public static void main(String[] args) {
		String multicastAddr = args[0]; //"239.255.1.3"
		int port = 30000;
		
		try {
			TimeClient client = new TimeClient(multicastAddr, port);
			client.startClient();
		} 
		catch (UnknownHostException e) {e.printStackTrace();}
		catch (IllegalArgumentException e) {e.printStackTrace();}
	}
}
