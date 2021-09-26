package assignment_06;

import java.util.concurrent.*;

public class ThreadPool {
	private ThreadPoolExecutor executor;
	
	public ThreadPool() {
		this.executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}
	
	public void executeTask(Task t) {
		System.out.println("Pool: A new task has arrived");
		executor.execute(t);
		System.out.println("Pool: task completed");
		
	}
	
	public void closePool(long wait) {
		executor.shutdown();
		while(!executor.isTerminated())
			try {
				executor.awaitTermination(wait, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				System.out.println("awaitTermination interrupted");
			}
	}
}
