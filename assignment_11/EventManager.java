package assignment_11;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * implementa l'interfaccia che permette di donare i servizi al client
 * 
 * @author Antonio Guzzi
 *
 */
@SuppressWarnings("serial")
public class EventManager extends RemoteServer implements EventManagerInterface {
	
	ArrayList<Hashtable<String, SpeakerList>> schedules; 
	public EventManager() {
		this.schedules = new ArrayList<Hashtable<String, SpeakerList>>(3);
		Hashtable<String, SpeakerList> CongressSchedule1 = new Hashtable<String, SpeakerList>();
		CongressSchedule1.put("S1", new SpeakerList());
		CongressSchedule1.put("S2", new SpeakerList());
		CongressSchedule1.put("S3", new SpeakerList());
		CongressSchedule1.put("S4", new SpeakerList());
		CongressSchedule1.put("S5", new SpeakerList());
		CongressSchedule1.put("S6", new SpeakerList());
		CongressSchedule1.put("S7", new SpeakerList());
		CongressSchedule1.put("S8", new SpeakerList());
		CongressSchedule1.put("S9", new SpeakerList());
		CongressSchedule1.put("S10", new SpeakerList());
		CongressSchedule1.put("S11", new SpeakerList());
		CongressSchedule1.put("S12", new SpeakerList());
		schedules.add(CongressSchedule1);
		Hashtable<String, SpeakerList> CongressSchedule2 = new Hashtable<String, SpeakerList>();
		CongressSchedule2.put("S1", new SpeakerList());
		CongressSchedule2.put("S2", new SpeakerList());
		CongressSchedule2.put("S3", new SpeakerList());
		CongressSchedule2.put("S4", new SpeakerList());
		CongressSchedule2.put("S5", new SpeakerList());
		CongressSchedule2.put("S6", new SpeakerList());
		CongressSchedule2.put("S7", new SpeakerList());
		CongressSchedule2.put("S8", new SpeakerList());
		CongressSchedule2.put("S9", new SpeakerList());
		CongressSchedule2.put("S10", new SpeakerList());
		CongressSchedule2.put("S11", new SpeakerList());
		CongressSchedule2.put("S12", new SpeakerList());
		schedules.add(CongressSchedule2);
		Hashtable<String, SpeakerList> CongressSchedule3 = new Hashtable<String, SpeakerList>();
		CongressSchedule3.put("S1", new SpeakerList());
		CongressSchedule3.put("S2", new SpeakerList());
		CongressSchedule3.put("S3", new SpeakerList());
		CongressSchedule3.put("S4", new SpeakerList());
		CongressSchedule3.put("S5", new SpeakerList());
		CongressSchedule3.put("S6", new SpeakerList());
		CongressSchedule3.put("S7", new SpeakerList());
		CongressSchedule3.put("S8", new SpeakerList());
		CongressSchedule3.put("S9", new SpeakerList());
		CongressSchedule3.put("S10", new SpeakerList());
		CongressSchedule3.put("S11", new SpeakerList());
		CongressSchedule3.put("S12", new SpeakerList());
		schedules.add(CongressSchedule3);
	}
	
	@Override
	public synchronized boolean registerSpeaker(String session, String name, int day) throws RemoteException, IllegalArgumentException {
		if(session == null || name == null || day < 0 || day >= 3) throw new IllegalArgumentException("Parametri errati.");
		Hashtable<String, SpeakerList> CongressSchedule = this.schedules.get(day);
		if(!CongressSchedule.containsKey(session)) throw new IllegalArgumentException("Sessione inesitente.");
		if(!CongressSchedule.get(session).isIn(name)) {
			CongressSchedule.get(session).appendSpeaker(name);
			return true; //la registrazione è andata a buon fine
		}
		return false; //lo speaker si è già registrato nella sessione "session"
	}

	@Override
	public synchronized boolean getCongressSchedule(int day) throws RemoteException, IllegalArgumentException{
		if(day < 0 || day >= 3) throw new IllegalArgumentException("Parametri errati.");
		Hashtable<String, SpeakerList> CongressSchedule = this.schedules.get(day);
		if(CongressSchedule.isEmpty()) return false;
		/*Set<String> sessions = CongressSchedule.keySet();
		for(String s : sessions) {
			System.out.print(s + ": ");
			for(int i = 0; i < CongressSchedule.get(s).getSize(); i++) {
				System.out.print(CongressSchedule.get(s).getSpeaker(i) + " ");
			}
			System.out.println();
		}*/
		for (Entry<String, SpeakerList> entry : CongressSchedule.entrySet()) {
		    System.out.print(entry.getKey() + ": ");
		    entry.getValue().printList();
		    System.out.println();
		}
		return true;
	}

}
