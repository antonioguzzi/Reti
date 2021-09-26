package assignment_06;

import java.util.ArrayList;

public class ArrayListBankAccount {
	ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
	
	public ArrayListBankAccount() {}
	
	public void setArrayListBankAccount(ArrayList<BankAccount> a) {
		this.bankAccounts = a;
	}
	
	public ArrayList<BankAccount> getArrayListBankAccount(){
		return this.bankAccounts;
	}
}
