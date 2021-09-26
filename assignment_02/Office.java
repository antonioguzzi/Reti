package assignment_02;

import java.util.concurrent.*;

public class Office {
	private ThreadPoolExecutor pool;
	private int numServedCustomers;
	
	public Office(int numFrontOffice, int waitingQueueSize) {
		this.pool = new ThreadPoolExecutor(numFrontOffice, numFrontOffice, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(waitingQueueSize)); 
		this.numServedCustomers = 0;
	}
	
	public void serveCustomer(Customer cust) {
		try {
			pool.execute(cust);
		}
		catch(RejectedExecutionException e) {
			throw new RejectedExecutionException();
		}
		numServedCustomers++;
	}
	
	public void closeOffice(long wait) {
		pool.shutdown();
		while(!pool.isTerminated())
			try {
				pool.awaitTermination(wait, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				System.out.println("awaitTermination interrotta");
			}
	}
	
	public int getServed() { return this.numServedCustomers; }
}
