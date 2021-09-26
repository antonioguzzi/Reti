package assignment_06;

import java.util.concurrent.atomic.AtomicInteger;

public class Occurrences {
	private AtomicInteger occBonifico = new AtomicInteger();
	private AtomicInteger occAccredito = new AtomicInteger();
	private AtomicInteger occBollettino = new AtomicInteger();
	private AtomicInteger occF24 = new AtomicInteger();
	private AtomicInteger occPagoBancomat = new AtomicInteger();
	
	public void incrBonifico() {occBonifico.getAndIncrement();}
	public void incrAccredito() {occAccredito.getAndIncrement();}
	public void incrBollettino() {occBollettino.getAndIncrement();}
	public void incrF24() {occF24.getAndIncrement();}
	public void incrPagoBancomat(){occPagoBancomat.getAndIncrement();}
	
	public int getOccBonifico() {return occBonifico.intValue();}
	public int getOccAccredito() {return occAccredito.intValue();}
	public int getOccBollettino() {return occBollettino.intValue();}
	public int getOccF24() {return occF24.intValue();}
	public int getOccPagoBancomat() {return occPagoBancomat.intValue();}
}
