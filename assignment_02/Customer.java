package assignment_02;

public class Customer implements Runnable {
	private int id;
	
	public Customer(int id) {
		this.id = id;
	}
	
	public int getId() { return this.id; }
	
	@Override
	public void run() {
		System.out.printf("Customer %d: sto eseguendo l'operazione allo sportello %d\n", id, Thread.currentThread().getId());
		try {
			Thread.sleep((long)(Math.random()*1000)); // tempo per il quale il thread viene occupato
		} catch (InterruptedException e) {
			System.out.println("Cliente" + id + "interrotto mentre eseguva la sleep");
		}
		System.out.printf("Customer %d: ho terminato le operazioni allo sportello %d\n", id, Thread.currentThread().getId());
	}

}
