package assignment_05;

public class Consumer extends Thread {
	NamesQueue names;
	int id;
	
	public Consumer(NamesQueue names, int consumerId) {
		this.names = names;
		this.id = consumerId;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			names.consumeRead(id);
		}
	}
}
