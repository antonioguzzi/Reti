package assignment_09;

public class MainServer {
	public static void main(String[] args) {
		// localhost 10002
		
		String nameServer =  "localhost";
		int port = 10002;
		
		if(!nameServer.equals("localhost")) { 
			System.out.println("ERR - arg 1");
			return;
		}
		if(port < 0) {
			System.out.println("ERR - arg 2"); 
			return; 
		}
		
		PingServer server = new PingServer(port, nameServer);
		server.startServer();		
	}
}

