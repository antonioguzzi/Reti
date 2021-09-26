package assignment_11;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainClassServer {
	private static final int PORT_DEFAULT = 5000;
	public static void main(String[] args){
		int port = PORT_DEFAULT;
		try {
            EventManager eventManager = new EventManager();
           
            // esporta l'oggetto
            EventManagerInterface stub = (EventManagerInterface) UnicastRemoteObject.exportObject(eventManager, port);

            // crea il registro
            LocateRegistry.createRegistry(port);
            Registry register = LocateRegistry.getRegistry(port);

            // binding
            register.rebind("EVENT_MANAGER", stub);
            System.out.println("Server è pronto");
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
	}
}
