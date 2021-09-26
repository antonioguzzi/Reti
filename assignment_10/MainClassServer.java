package assignment_10;

import java.net.UnknownHostException;

public class MainClassServer {
	public static void main(String[] args) {
		String multicastAddr = args[0]; //"239.255.1.3"
		int port = 30000;
		
		try {
			TimeServer server = new TimeServer(multicastAddr,port);
			server.start();
		} 
		catch (UnknownHostException e) {e.printStackTrace();}
		catch (IllegalArgumentException e) {e.printStackTrace();}
	}
}
