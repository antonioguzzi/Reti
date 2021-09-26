package assignment_06;
import java.util.ArrayList;

public class BankAccount {
	private String name;
	private ArrayList<Movement> movements = new ArrayList<Movement>();
	
	public BankAccount() {}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Movement> getMovements(){
		return this.movements;
	}
	
	public Movement getMovement(int index) {
		return movements.get(index);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addMovement(Movement mov) {
		this.movements.add(mov);
	}
}
