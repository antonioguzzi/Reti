package assignment_05;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NamesQueue {
	List<String> names;
	ArrayList<String> tmp;
	final Lock queueLock;
	final Condition full;
	final Condition empty;
	private int capacity;
	private int extract;
	
	public NamesQueue(int cap) {
		this.names = (List<String>) Collections.synchronizedList(new ArrayList<String>());
		this.tmp = new ArrayList<String>();
		this.queueLock = new ReentrantLock();
		this.full = queueLock.newCondition();
		this.empty = queueLock.newCondition();
		this.capacity = cap;
		this.extract = -1;
	}
	
	public void produceWrite(int index) {
		queueLock.lock();
		try {
			while(names.size() >= capacity) {
				try {
					full.await();
				} catch (InterruptedException e) {}
			}
			names.add(tmp.get(index));
			extract++;
			System.out.println("String Produced: " + names.get(extract));
			empty.signalAll();
		}finally {queueLock.unlock();}
		return;
	}
	
	public void consumeRead(int consumerId) {
		queueLock.lock();
		try {
			while(names.isEmpty()) {
				try {
					empty.await();
				} catch (InterruptedException e) {}
			}
			System.out.println("String Consumed by "+consumerId+" : "+names.get(extract));
			names.remove(extract);
			extract--;
			full.signal();
		}finally {queueLock.unlock();}
		return;
	}
	
	public int recursiveFileCrawler(File dir) {
		/* visita ricorsiva della directory */
		File[] files = dir.listFiles();
		for(File file : files) {
			if(file.isDirectory()) {
				tmp.add(file.getName());
				recursiveFileCrawler(file);
			}else tmp.add(file.getName());
		}
		/* fine visita ricorsiva */
		return tmp.size();
	}
	
	public int getNameSize() {
		return names.size();
	}
	
}
