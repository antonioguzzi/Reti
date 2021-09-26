package assignment_03;

public class Student extends Thread {
	private int id;
	private Tutor request = null;
	private int k;
	
	public Student(Tutor request, int id, int k) {
		this.id = id;
		this.request = request;
		this.k = k;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < k; i++) {
			request.entranceReqStud(0,id,i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
		System.out.println("Sono lo studete " + id + " ho terminato le mie operazioni.");
	}
	
}
