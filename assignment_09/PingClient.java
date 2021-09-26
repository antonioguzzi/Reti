package assignment_09;

import java.io.*;
import java.net.*;
import java.util.*;

public class PingClient {
	private int serverPort;
	private int timeOut;
	private String serverName;
	
	/**
    * @param serverPort porta del server
    * @param serverName nome del server
    * @param timeout attesa per la ricezione di una risposta (ms)
    */
   public PingClient(int serverPort, String serverName, int timeout){
       this.timeOut = timeout;
       this.serverPort = serverPort;
       this.serverName = serverName;
   }
   
   /**
    * avvia il client
    */
   public void startClient() {
	   try(DatagramSocket client = new DatagramSocket()){
		   ArrayList <Integer> times = new  ArrayList <Integer>();
		   int received = 0;
		   
		   for(int i = 0; i < 10; i++) {
			   //inizializzazione
			   ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			   DataOutputStream dataOut = new DataOutputStream (byteArrayOut);
			   dataOut.writeUTF("PING");
			   dataOut.writeInt(i);
			   long sendAtTime = System.currentTimeMillis();
			   dataOut.writeLong(sendAtTime);
			   byte[] sendBuffer = byteArrayOut.toByteArray();
			   DatagramPacket packetToSend = new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(this.serverName), this.serverPort);
			   
			   //send
			   client.send(packetToSend);
			   client.setSoTimeout(timeOut);
			   
			   //ricezione
			   byte[] receiveBuffer = new byte [sendBuffer.length];
			   DatagramPacket  receivedPacket = new DatagramPacket(receiveBuffer,receiveBuffer.length);
			   try {
				   client.receive(receivedPacket);
			   }catch(SocketTimeoutException e) {
				   System.out.printf("PING %d %d RTT: *\n",i, (int)sendAtTime);
				   continue;
			   }
			   received++;
			   ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(receivedPacket.getData(), 0, receivedPacket.getLength());
			   DataInputStream dataIn = new DataInputStream(byteArrayIn);
			  
			   //lettura dati
			   String ping = dataIn.readUTF();
			   int sign = dataIn.readInt();
			   sendAtTime = dataIn.readLong();
			   times.add((int)(System.currentTimeMillis()-sendAtTime));
			   
			   //scrittura in System.out
			   System.out.printf("%s %d %d RTT: %d ms\n", ping, sign, (int)sendAtTime, times.get(times.size()-1));
		   }
		   
		   System.out.println("---- PING Statistics ----");
		   System.out.printf("%d packets transmitted, %d packets received, %d packet loss\n", 10, received, (10-received)*10);
		   int max = this.max(times);
		   int min = this.min(times);
		   float avg = this.avg(times);
		   System.out.printf("round-trip (ms) min/avg/max = %d/%.2f/%d\n",min,avg,max);
	   } 
	   catch (BindException e){ System.out.println("Porta già occupata."); }
	   catch (IOException e) { e.printStackTrace(); }
   }
   
   private int max(ArrayList <Integer> a) {
	   if(a.isEmpty()) return 0;
	   int temp = Integer.MIN_VALUE;
	   for(int i = 0; i < a.size(); i++) {
		   if(temp < a.get(i)) temp = a.get(i);
	   }
	   return temp;
   }
   
   private int min(ArrayList <Integer> a) {
	   if(a.isEmpty()) return 0;
	   int temp = Integer.MAX_VALUE;
	   for(int i = 0; i < a.size(); i++) {
		   if(temp > a.get(i)) temp = a.get(i);
	   }
	   return temp;
   }
   
   private float avg(ArrayList <Integer> a) {
	   if(a.isEmpty()) return 0;
	   int sum = 0;
	   for(int i = 0; i < a.size(); i++) {
		   sum += a.get(i);
	   }
	   return sum/a.size();
   }
   
   
}
