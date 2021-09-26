package assignment_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClassClient {
	private static final int PORT_DEFAULT = 5000;
	public static void main(String[] args){
		int port = PORT_DEFAULT;
		
		try (BufferedReader in  = new BufferedReader(new InputStreamReader(System.in))) {
			
			// ottiene una reference per il registro
            Registry reg = LocateRegistry.getRegistry(port);
            // ottiene una reference all'event manager
            EventManagerInterface remoteEventManager = (EventManagerInterface) reg.lookup("EVENT_MANAGER"); 
            
            while(true) {
            	System.out.println("Insert speaker name (or type 'exit'):");
                String name = in.readLine();
                if(name.equals("exit")) return;
                
                System.out.println("Insert day:");
                int day = Integer.parseInt(in.readLine());
                
                System.out.println("Insert session:");
                String session = in.readLine();   
                
                try {
                	if(remoteEventManager.registerSpeaker(session, name, day)) {
                    	System.out.println("Speaker " + name + " successfully registered in session " + session + ".");
                    }else {
                    	System.out.println("Speaker " + name + " already registered in session " + session + ".");
                    }
                	
                	if(!remoteEventManager.getCongressSchedule(day)) {
                		System.out.println("Schedule is empty.");
                	}
    
                }catch(RemoteException | IllegalArgumentException e) {e.printStackTrace();}
                
            }
            
		}
		catch(IOException | NotBoundException e){e.printStackTrace();}
	}
}
