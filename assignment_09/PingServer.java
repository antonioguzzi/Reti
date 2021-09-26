package assignment_09;

import java.io.*;
import java.net.*;
import java.util.Random;


public class PingServer {
	private int serverPort;
	private String serverName;
	
	/**
    * @param port porta su cui è in ascolto il server
    * @param serverName hostname del server
    */
	public PingServer(int port, String serverName) {
		this.serverPort = port;
		this.serverName = serverName;
	}
	
	/**
	 * avvia il server
	 */
	public void startServer() {
		 try (DatagramSocket server = new DatagramSocket(serverPort)){
			 byte[] receiveBuffer = new byte [1024];
			 DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			 Random rand = new Random(); 
			 
			 for(int i = 0; i < 10; i++) {
				 server.receive(receivedPacket);
				 ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(receivedPacket.getData(), 0, receivedPacket.getLength());
				 DataInputStream dataIn = new DataInputStream(byteArrayIn);
				 
				 //lettura dati
				 String ping = dataIn.readUTF();
				 int sign = dataIn.readInt();
				 long sendAtTime = dataIn.readLong(); 

				 boolean lost = rand.nextInt(4) == 0; //probabilità del 25% di essere vera
				 if(lost) {
					//simulazione della perdita
					 System.out.printf("%s:%d> %s %d %d ACTION: not sent\n",receivedPacket.getAddress(),receivedPacket.getPort(),ping, sign, (int)sendAtTime);
				 }else {
					 //ritrasmissione del pacchetto
					 double delay = Math.random() * 500;
					 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
					 DataOutputStream dataOut = new DataOutputStream (byteArrayOut);
					 
					 dataOut.writeUTF(ping);
					 dataOut.writeInt(sign);
					 dataOut.writeLong(sendAtTime);
					 byte[] sendBuffer = byteArrayOut.toByteArray();
					 DatagramPacket packetToSend = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(this.serverName), receivedPacket.getPort());
					 
					 //aggiungo un ritardo di ritrasmissione artificiale
					 System.out.printf("%s:%d> %s %d %d ACTION: delayed %d ms\n",receivedPacket.getAddress(),receivedPacket.getPort(),ping, sign, (int)sendAtTime, (int)delay);
					 Thread.sleep((long) delay);
					 server.send(packetToSend);
				 }
			 }
		 } 
		 catch (BindException e){ System.out.println("Porta già occupata"); }
		 catch (IOException e) { e.printStackTrace(); }
		 catch (InterruptedException e) { e.printStackTrace(); }
	}
}
