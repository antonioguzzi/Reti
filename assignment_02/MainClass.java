package assignment_02;

import java.util.ArrayList;
import java.util.concurrent.*;

public class MainClass {
	public static void main(String[] args) {
		int k =  Integer.valueOf(args[0]).intValue();
		Office postOffice = new Office(4, k);
		ArrayList<Customer> customerQueue = new ArrayList<Customer>();
		
		for(int i = 0; i < 50; i++) customerQueue.add(new Customer(i)); //i clienti vengono inizialmente inseriti tutti nella prima coda
		for(int i = 0; i < customerQueue.size(); i++) {
			try {
				postOffice.serveCustomer(customerQueue.get(i));
			}catch (RejectedExecutionException e) {
				System.out.printf("Customer %d: sala d'attesa piena\n", customerQueue.get(i).getId());
			}
			try {
				Thread.sleep(50); // il rate con cui i clienti vengono serviti è stabilito da questa sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		postOffice.closeOffice(5);
		System.out.printf("Numero di Customers serviti: %d\n",postOffice.getServed());
	}
}
