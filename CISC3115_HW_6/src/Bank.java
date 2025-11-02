import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.io.PrintWriter;


public class Bank {

	private ArrayList<Accounts>bankAccounts = new ArrayList<>();
	private int index;
	private static double totalAmountInSavingAccts;
	private static double totalAmountInCheckingAccts;
	private static double tatalAmountInCDAccts;
	private static double totalAmountInAllAccts;
	
	
	Bank()	{
		bankAccounts = new ArrayList<>();
	}
	
	//copy constructor
	Bank(Bank other)	{
		this.bankAccounts = new ArrayList<>();
		for(Accounts a : other.bankAccounts) {
			this.bankAccounts.add(new Accounts(a));
		}
	}
	
	//getter 
	public ArrayList getbankAccounts()	{
		ArrayList<Accounts> copylist = new ArrayList<>();
		for(Accounts a : bankAccounts) {
			//System.out.println(a);
			copylist.add(new Accounts(a));
		}
		return copylist;
	}
	
	public void addAccounts(Accounts Acc)	{
		System.out.println("Added Account");
		bankAccounts.add(Acc);
	}
	private int findAcct(int accNum)	{
		int index = 0;
		for(int i = 0; i < bankAccounts.size(); i++)	{
			if(bankAccounts.get(i).getAccountNumber() == accNum)	{
				index = i;
				return i;
			}
		}
		index =-1;
		return index;
	}
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket, int accountNumber, Scanner userinput) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonForFailure  = "Error Account Number:" + accountNumber +" not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonForFailure, 0.0, 0.0, Calendar.getInstance());
			return Receipt;
		}else {
			
		}
	}
}
