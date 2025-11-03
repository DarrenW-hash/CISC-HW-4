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
	public ArrayList<Accounts> getbankAccounts()	{
		ArrayList<Accounts> copylist = new ArrayList<>();
		for(Accounts a : bankAccounts) {
			//System.out.println(a);
			copylist.add(new Accounts(a));
		}
		return copylist;
	}
	public void setindex(int i)	{
		index = i;
	}
	public void addAccounts(Accounts Acc)	{
		System.out.println("Added Account");
		bankAccounts.add(Acc);
	}
	private int findAcct(int accNum)	{
		int index = 0;
		for(int i = 0; i < bankAccounts.size(); i++)	{
			if(bankAccounts.get(i).getAccountNumber() == accNum)	{
				setindex(i);
				return i;
			}
		}
		index =-1;
		setindex(index);
		return index;
	}
	
	public int getindex() {
		return index;
	}
	
	/* Processes a withdrawal request for a specific account.
	 * Finds the account by account number, and if found, delegates the withdrawal
	 * to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt makeWithdrawal(TransactionTicket ticket, int accountNumber, Scanner userinput) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonForFailure  = "Error Account Number:" + accountNumber +" not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonForFailure, 0.0, 0.0, Calendar.getInstance());
			return Receipt;
		}else {
			return bankAccounts.get(index).makeWithDrawal(ticket, userinput);
		}
	}
	/* Processes a deposit request for a specific account.
	 * Finds the account by account number, and if found, delegates the deposit
	 * to the Account object. If the account is not found, returns a failure receipt.	
	 */
	public TransactionReceipt makedeposit(TransactionTicket ticket, int accountNumber, Scanner userinput) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance());
			return Receipt;
		}else {
			return bankAccounts.get(index).makedeposit(ticket ,userinput );
		}
	}
	/* Retrieves the current balance of a specific account.
	 * Finds the account by account number, and if found, delegates the balance
	 * retrieval to the Account object. If the account is not found, returns a failure receipt.	
	 */
	public TransactionReceipt getBalance(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number" + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance());
			return Receipt;
		}else {
			return bankAccounts.get(index).getBalance(ticket);
		}
	}
	/* Processes a check clearing transaction for a specific account.
	 * Finds the account by account number, and if found, delegates the check clearing
	 * to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt clearCheck(TransactionTicket ticket, int accountNumber, Calendar checkDate) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number" + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance());
			return Receipt;
		}else {
			return bankAccounts.get(index).clearCheck(ticket,checkDate);
		}
	}
}
