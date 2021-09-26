package assingment_01_pigreco;

public class PiThread implements Runnable {
	private int i;
	private int div;
	private double pi;
	private double accuracy;
	
	public PiThread(double acc) {
		this.i = 0;
		this.div = 1;
		this.pi = 4;
		this.accuracy = acc;
	}
	
	public double getPi() {
		return this.pi;
	}
	
	@Override
	public void run() {
		//fino a quando il thread non viene interrotto o non raggiungo l'accuratezza desiderata, calcolo il valore di Pi
		while(!Thread.interrupted() && Math.abs(pi - Math.PI) >= accuracy) {
			div += 2;
			if(i % 2 == 0) pi = pi - (double)4/div;
			else pi = pi + (double)4/div;
			i++;
		}
		if(pi - Math.PI < accuracy)	System.out.println("Accuracy " + accuracy + " Reached:" + Math.abs(pi - Math.PI));
		return;
	}
	
}
