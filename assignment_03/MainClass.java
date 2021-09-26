package assignment_03;

public class MainClass {
	public static void main(String[] ags) {
		int k = (int)(Math.random()*5 + 1);
		Tutor tutorLab = new Tutor(20, k, k);
		Student students[] = new Student[20];	//20 sono il numero di studenti
		Undergraduate unders[] = new Undergraduate[10];	//10 sono il numero di undergraduates
		Professor professors[] = new Professor[5];	//5 sono il numero di professori
		
		/* inizializzo gli array di thread */
		for(int i = 0; i < students.length; i++) {
			students[i] = new Student(tutorLab,i,k);
			students[i].setPriority(Thread.MIN_PRIORITY);
		}
		for(int i = 0; i < unders.length; i++) {
			unders[i] = new Undergraduate(tutorLab,i,k);
			unders[i].setPriority(Thread.NORM_PRIORITY);
		}
		for(int i = 0; i < professors.length; i++) {
			professors[i] = new Professor(tutorLab,i,k);
			professors[i].setPriority(Thread.MAX_PRIORITY);
		}
		
		/* mando in esecuzione i thread */
		for(int i = 0; i < unders.length; i++) unders[i].start();
		for(int i = 0; i < students.length; i++) students[i].start();
		for(int i = 0; i < professors.length; i++) professors[i].start();
		
		/* join */
		for(int i = 0; i < students.length; i++) {
			try {
				students[i].join();
			} catch (InterruptedException e) {}
		}
		for(int i = 0; i < unders.length; i++) {
			try {
				unders[i].join();
			} catch (InterruptedException e) {}
		}
		for(int i = 0; i < professors.length; i++) {
			try {
				professors[i].join();
			} catch (InterruptedException e) {}
		}
		System.out.println("Esecuzione terminata.");
	}
}
