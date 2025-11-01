import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Calendar;

public class Main_Class {

	public static void main (String args []	) throws IOException {
		//File inputFile = new File("");
		Bank bank = new Bank();
		readaccts(bank);
		
		
		
		
	}
	public static int readaccts(Bank bank) throws IOException{
		int currentAccount = 0;
		File bankAccounts = new File("\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\BankAccounts.txt");
		Scanner fileReader = new Scanner(bankAccounts);
		
		while(fileReader.hasNext()) {
			String [] line = fileReader.nextLine().split(" ");
			String firstName = line[0];
			String lastName = line[1];
			String SSnumber = line[2];
			int acctnum = Integer.parseInt(line[3]);
			String acctType = line[4];
			String Status = line[5];
			double balance = Double.parseDouble(line[6]);
			
			Name name = new Name(firstName,lastName);
			Depositors depositor = new Depositors(name, SSnumber);
			Accounts account;
			
			if(acctType.equals("CD"))	{
				String [] date = line[7].split("/");
				Calendar maturityDate = Calendar.getInstance();
				maturityDate.set(Integer.parseInt(date[2]), Integer.parseInt(date[0]) -1, Integer.parseInt(date[1]));
				account = new Accounts(depositor, acctnum, acctType, balance, Status, maturityDate);
			}else {
				account = new Accounts(depositor, acctnum, acctType, balance, Status);
			}
			currentAccount++;
			bank.addAccounts(account);
		}
		return currentAccount;
	}
	
	public static void menu()	{
		
	}
}
