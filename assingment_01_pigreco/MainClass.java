package assingment_01_pigreco;
import java.util.*;
public class MainClass {
	public static void main (String[] args) {
		double accuracy =  Double.valueOf(args[0]).doubleValue();
		double timeMax = Double.valueOf(args[1]).doubleValue();
		
		System.out.println("Accuracy: " + accuracy);
		System.out.println("Maximum Execution Time: " + timeMax);
		PiThread pt = new PiThread(accuracy);
		Thread t = new Thread(pt);
		t.start();
		
		Date startTime = Calendar.getInstance().getTime(); //memorizzo il tempo di inizio esecuzione
		//fino a quando il thread non viene interrotto o non raggiungo l'accuratezza desiderata, calcolo il tempo corrente
		while(!t.isInterrupted() && Math.abs(pt.getPi() - Math.PI) >= accuracy) { 
			Date currentTime = Calendar.getInstance().getTime();
			long time = currentTime.getTime() - startTime.getTime();
			//una volta che il tempo limite viene raggiunto interrompo il thread (più sarà l'accuratezza, più tempo ci vorrà per terminare il thread)
			if((time >= timeMax*1000)) {
				System.out.println("End of Execution Time");
				t.interrupt();
				return;
			}
		}
		return;
	}
}
