package assignment_03;

import java.util.*;
import java.util.concurrent.locks.*;

public class Tutor {
	private int [] computers;
	private int size;
	private Lock lockComputers;
	private Condition occupiedStud;
	private Condition occupiedUnder;
	private Condition occupiedProf;
	private int busy;
	private int underNum, profNum;
	
	public Tutor(int sizeLab,int underReqNum, int proReqNum) {
		this.size = sizeLab;
		this.underNum = underReqNum;
		this.profNum = proReqNum;
		this.computers = new int[size];
		Arrays.fill(computers,0);
		lockComputers = new ReentrantLock();
		occupiedStud = lockComputers.newCondition();
		occupiedUnder = lockComputers.newCondition();
		occupiedProf = lockComputers.newCondition();
		busy = 0;
	}
	
	public void entranceReqStud(int index, int id, int i) { //index deve essere 0
		lockComputers.lock();
		try {
			System.out.println("Sono lo studete " + id + " provo a occupare un computer.");
			try {
				while(busy == size || underNum > 0 || profNum > 0) occupiedStud.await();
			} catch (InterruptedException e) {}
			System.out.println("Sono lo studete " + id + " eseguo la richiesta "+i);
			while(computers[index] == 1) index++;
			computers[index] = 1;
			busy++;
		}finally {lockComputers.unlock();}
		
		/* vado in sleep per simulare l'operazione al computer*/
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		/* termino la sleep */
		
		lockComputers.lock();
		try {
			computers[index] = 0;
			busy--;
			
			/* eseguo le signal */ 
			occupiedStud.signalAll(); 
			occupiedUnder.signalAll();
			occupiedProf.signalAll();
		}finally {lockComputers.unlock();}
	}
	
	public void entranceRequestUndergrad(int index, int id, int i) { //casuale da 1 a 10
		lockComputers.lock();
		try {
			System.out.println("Sono il tesista " + id + " provo a occupare un computer.");
			try {
				while(computers[index] == 1 || profNum > 0) occupiedUnder.await();
			} catch (InterruptedException e) {}
			System.out.println("Sono il tesista " + id + " eseguo la richiesta "+i);
			computers[index] = 1;
			busy++;
		}finally {lockComputers.unlock();}
		
		/* vado in sleep per simulare l'operazione al computer*/
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		/* termino la sleep */
		
		lockComputers.lock();
		try {
			computers[index] = 0;
			busy--;
			underNum--;
			
			/* eseguo le signal */
			occupiedStud.signalAll(); 
			occupiedUnder.signalAll();
			occupiedProf.signal();
		}finally {lockComputers.unlock();}
	}
	
	public void entraceRequestProf(int id, int i) {
		lockComputers.lock();
		try {
			System.out.println("Sono il professore " + id + " provo a occupare il laboratorio.");
			try {
				while(busy > 0) occupiedProf.await();
			} catch (InterruptedException e) {}
			System.out.println("Sono il professore " + id + " eseguo la richiesta " + i);
			Arrays.fill(computers,1);
			busy = size;
		}finally {lockComputers.unlock();}
		
		/* vado in sleep per simulare l'operazione al computer*/
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		/* termino la sleep */
		
		lockComputers.lock();
		try {
			Arrays.fill(computers,0);
			busy = 0;
			profNum--;
			
			/* eseguo le signal */
			occupiedStud.signalAll();
			occupiedUnder.signalAll();
			occupiedProf.signal();
		}finally {lockComputers.unlock();}
	}
	
	public void printTerminals() {
		System.out.println(computers);
	}
}
