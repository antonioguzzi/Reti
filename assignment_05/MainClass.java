package assignment_05;
import java.io.*;

public class MainClass {
	public static void main(String[] args) {
		NamesQueue names = new NamesQueue(10);
		File currentDir = new File(".");
		Consumer consumers[] = new Consumer[3];
		for(int i = 0; i < consumers.length; i++) consumers[i] = new Consumer(names,i);
		
		new Producer(names,currentDir).start();
		for(int i = 0; i < consumers.length; i++) consumers[i].start();
	}
}
