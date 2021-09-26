package assignment_05;

import java.io.*;

public class Producer extends Thread {
	NamesQueue names;
	File dir;
	
	public Producer(NamesQueue names, File dir) {
		this.names = names;
		this.dir = dir;
	}
	
	public void run() {
		int tmpSize = names.recursiveFileCrawler(dir);
		for (int i = 0; i < tmpSize; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			names.produceWrite(i);
		}
		System.out.println("Producer end his messages");
	}
}
