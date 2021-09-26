package assignment_06;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainClass {
	public static void main(String[] args) {
		ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		ObjectMapper objectMapper1 = new ObjectMapper();
		
		bankAccounts.add(0, new BankAccount());
		bankAccounts.get(0).setName("nome 1");
		bankAccounts.get(0).addMovement(new Movement("01/11/11","Bonifico"));
		bankAccounts.get(0).addMovement(new Movement("03/12/13","Accredito"));
		bankAccounts.get(0).addMovement(new Movement("21/12/12","Bollettino"));
		bankAccounts.get(0).addMovement(new Movement("08/08/11","F24"));
		bankAccounts.get(0).addMovement(new Movement("26/04/13","PagoBancomat"));
		
		bankAccounts.add(1, new BankAccount());
		bankAccounts.get(1).setName("nome 2");
		bankAccounts.get(1).addMovement(new Movement("02/12/11","Bonifico"));
		bankAccounts.get(1).addMovement(new Movement("04/01/13","Accredito"));
		bankAccounts.get(1).addMovement(new Movement("22/02/12","F24"));
		bankAccounts.get(1).addMovement(new Movement("09/09/11","F24"));
		bankAccounts.get(1).addMovement(new Movement("27/05/13","F24"));
		
		bankAccounts.add(2, new BankAccount());
		bankAccounts.get(2).setName("nome 3");
		bankAccounts.get(2).addMovement(new Movement("03/11/11","Bonifico"));
		bankAccounts.get(2).addMovement(new Movement("05/12/13","Bonifico"));
		bankAccounts.get(2).addMovement(new Movement("23/12/12","Accredito"));
		bankAccounts.get(2).addMovement(new Movement("10/10/11","Bollettino"));
		bankAccounts.get(2).addMovement(new Movement("28/06/13","Bollettino"));
		
		ArrayListBankAccount BA = new ArrayListBankAccount();
		BA.setArrayListBankAccount(bankAccounts);
		try {
			File file = new File("BankAccountFileJackson.json");
			file.createNewFile();
			objectMapper1.writeValue(file, BA);
		}catch(IOException e) {e.printStackTrace();}
		
		try {
			readFile();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private static void readFile() throws IOException {
        ObjectMapper objectMapper2 = new ObjectMapper();
        ArrayListBankAccount BA = new ArrayListBankAccount();
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        FileInputStream file = new FileInputStream("BankAccountFileJackson.json");
        Occurrences occ = new Occurrences();
        ThreadPool executor = new ThreadPool();
        
        FileChannel channel = file.getChannel();
        System.out.println("File size is: " + channel.size());
        ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
        channel.read(buffer);
        BA = objectMapper2.readValue(buffer.array(), ArrayListBankAccount.class);
        bankAccounts = BA.getArrayListBankAccount();
        for(int i = 0; i < bankAccounts.size(); i++) {
        	System.out.println(bankAccounts.get(i).getName());
        	for(int j = 0; j < bankAccounts.get(i).getMovements().size(); j++) System.out.print(bankAccounts.get(i).getMovements().get(j).getCausal() + " ");
        	System.out.println();
        	Task task = new Task(occ, bankAccounts.get(i));
        	executor.executeTask(task);
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();}
        	System.out.println();
        }
        
        System.out.print(occ.getOccBonifico() + " " + occ.getOccAccredito() + " " + occ.getOccBollettino() + " " + occ.getOccF24() + " " + occ.getOccPagoBancomat());
        channel.close();
        file.close();
        executor.closePool(5);
    }
}
