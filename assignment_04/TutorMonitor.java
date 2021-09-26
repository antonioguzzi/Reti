package assignment_04;

import java.util.*;

public class TutorMonitor {
	private Vector<Integer> computers;
	private int size;
	private int busy;
	
	public TutorMonitor(int sizeLab) {
		this.size = sizeLab;
		this.computers = new Vector<Integer>(size);
		Collections.fill(computers, 0);
		busy = 0;
	}
	
	public  synchronized int entranceReqStud(int id, int i, int index) { //index deve essere 0
		System.out.println("Sono lo studete " + id + " provo a occupare un computer.");
		while(busy == size) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		System.out.println("Sono lo studete " + id + " eseguo la richiesta "+i);
		while(computers.get(index) == 1) index++;
		computers.set(index, 1);
		busy++;
		return index;
	}
	public synchronized void endReq(int index) {
		computers.set(index, 0);
		busy--;
		notifyAll();
		return;
	}
	
	public  synchronized int entranceReqUnder(int id, int i, int index) { //index casuale da 0 a 10
		System.out.println("Sono il tesista " + id + " provo a occupare un computer.");
		while(computers.get(index) == 1) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		System.out.println("Sono il tesista " + id + " eseguo la richiesta "+i);
		computers.set(index, 1);
		busy++;
		return index;
	}
	
	public  synchronized void entraceRequestProf(int id, int i) {
		System.out.println("Sono il professore " + id + " provo a occupare il laboratorio.");
		while(busy != 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		System.out.println("Sono il professore " + id + " eseguo la richiesta " + i);
		Collections.fill(computers, 1);
		busy = size;
		return;
	}
	
	public synchronized void endReqProf() {
		Collections.fill(computers, 0);
		busy = 0;
		notifyAll();
		return;
	}
	
}
