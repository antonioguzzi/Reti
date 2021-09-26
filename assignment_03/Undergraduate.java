package assignment_03;

public class Undergraduate extends Thread {
	private int id;
	private Tutor request = null;
	private int k;
	
	public Undergraduate(Tutor request, int id, int k) {
		this.id = id;
		this.request = request;
		this.k = k;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < k; i++) {
			request.entranceRequestUndergrad((int)Math.random()*10,id,i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
		System.out.println("Sono il tesista " + id + " ho terminato le mie operazioni.");
	}

}
