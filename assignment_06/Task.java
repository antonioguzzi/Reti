package assignment_06;

public class Task implements Runnable{
	private Occurrences occ;
	private BankAccount account;
	
	public Task(Occurrences occ, BankAccount account) {
		this.occ = occ;
		this.account = account;
	}
	
	@Override
	public void run() {
		try {
			for(int i = 0; i < account.getMovements().size(); i++) {
				if (account.getMovements().get(i).getCausal().equals("Bonifico")) occ.incrBonifico();
				else if(account.getMovements().get(i).getCausal().equals("Accredito")) occ.incrAccredito();
				else if(account.getMovements().get(i).getCausal().equals("Bollettino")) occ.incrBollettino();
				else if(account.getMovements().get(i).getCausal().equals("F24")) occ.incrF24();
				else occ.incrPagoBancomat();
			}
			Thread.sleep((long)(Math.random()*1000)); // tempo per il quale il thread viene occupato
		} catch(InterruptedException e) {e.printStackTrace();}
	}
}
