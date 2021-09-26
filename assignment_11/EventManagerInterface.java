package assignment_11;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * EventManagerInterface rappresenta le azioni disponibili al client
 * 
 * @author Antonio Guzzi
 *
 */
public interface EventManagerInterface extends Remote{
	
	/**
	 * @param name è il nome dell'utente che si vuole registrare nella sessione "session"
	 * @return true se la registrazione va a buon fine, false altrimenti
	 * @throws IllegalArgumentException se l'argomento passato risulta invalido
	 * @throws RemoteException se si verificano, durante l'esecuzione della chiamata remota, dei problemi
	 */
	boolean registerSpeaker(String session, String name, int day) throws RemoteException, IllegalArgumentException;
	
	/**
	 * @return true se la stampa va a buon fine, false altrimenti
	 * @throws RemoteException se si verificano, durante l'esecuzione della chiamata remota, dei problemi
	 */
	boolean getCongressSchedule(int day) throws RemoteException;
}
