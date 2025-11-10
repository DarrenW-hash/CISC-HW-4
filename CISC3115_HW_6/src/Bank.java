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
	private static double totalAmountInCDAccts;
	private static double totalAmountInAllAccts;
	
	
	Bank()	{
		bankAccounts = new ArrayList<>();
	}
	
	
	//getter 
	public ArrayList<Accounts> getbankAccounts()	{
		ArrayList<Accounts> copyList = new ArrayList<>();

	    for (Accounts a : bankAccounts) {
	        copyList.add(new Accounts(a));  // ✅ Calls the copy constructor in Accounts
	    }

	    return copyList;
	}
	
	// getters for totals
	public static double getTotalSavings() {
		return totalAmountInSavingAccts; }
	
	public static double getTotalChecking() {
		return totalAmountInCheckingAccts; }
	
	public static double getTotalCD() { 
		return totalAmountInCDAccts; }
	
	public static double getTotalAll() { 
		return totalAmountInAllAccts; }
	
	public void setindex(int i)	{
		index = i;
	}
	
	public void addAccounts(Accounts Acc)	{
		//System.out.println("Added Account");
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
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonForFailure, 0.0, 0.0, Calendar.getInstance(),null, null);
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts().get(index).getaccountType() + " " + ticket.getTransactionAmount());
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
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts().get(index).getaccountType() + " " + ticket.getTransactionAmount());
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
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(), null,null);
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
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),getbankAccounts().get(index).getStatus(),getbankAccounts().get(index).getaccountType());
			return Receipt;
		}else {
			System.out.println("Debug: " + getbankAccounts().get(index).getaccountType() + " " + ticket.getTransactionAmount());
			return bankAccounts.get(index).clearCheck(ticket,checkDate);
		}
	}
	/* Creates a new account in the bank if it passes all validations.
	 * Performs checks for duplicate account numbers, valid account types,
	 * valid SSN, and account number ranges. Returns a TransactionReceipt
	 * indicating success or failure.
	 */	
	public TransactionReceipt makeNewAcct(Accounts account) {
		int accountNumber = account.getAccountNumber();
		Calendar time = account.getDate();
		int index = findAcct(accountNumber);
		int SSnumber = Integer.parseInt(account.getdepositor().getSSnumber());
			
		// Check if account number already exists
		if(index != -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " Exist.";
			TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
			TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
			return Receipt;
		}else {
			// Validate account type
			if(account.getaccountType().equals("Saving") || account.getaccountType().equals("Checking") || account.getaccountType().equals("CD"))  {
				// Validate account number range (6-digit numbers)
				if(account.getAccountNumber() >= 100000 && account.getAccountNumber() <= 999999) {
					// Validate SSN (9-digit numbers)
					if(SSnumber >= 100000000 && SSnumber <= 999999999) {
						bankAccounts.add(account);
						TransactionTicket ticket = new TransactionTicket(accountNumber, time,"New Account", 0,0) ;
						TransactionReceipt Receipt = new TransactionReceipt(ticket, true, 0.0,0.0,time,getbankAccounts().get(bankAccounts.size()-1).getStatus(), getbankAccounts().get(bankAccounts.size()-1).getaccountType());
						return Receipt;
					}else {
						// Invalid SSN
						String ReasonforFailure = "Error INVALID SSN : " + account.getdepositor().getSSnumber();
						TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
						TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
						return Receipt;
					}
				}else {
					// Invalid account number range
					String ReasonforFailure = "Error INVALID ACCOUNT NUMBER RANGE : " + account.getAccountNumber();
					TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
					TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
					return Receipt;
				}
						
			}else {
				// Invalid account type
				String ReasonforFailure = "Error INVALID ACCOUNT TYPE of : " + account.getaccountType();
				TransactionTicket ticket = new TransactionTicket(accountNumber,time,"New Account",0.0,0);
				TransactionReceipt Receipt = new TransactionReceipt(ticket,false,ReasonforFailure,0.0,0.0,Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
				return Receipt;
			}
		}
	}
	/* Closes a specific bank account based on the account number provided.
	 * Finds the account by account number, and if found, delegates the closing
	 * operation to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
				}else {
					return bankAccounts.get(index).closeAcct(ticket);
		}
	}
	/* Reopens a previously closed bank account based on the account number provided.
	 * Finds the account by account number, and if found, delegates the reopening
	 * operation to the Account object. If the account is not found, returns a failure receipt.
	 */
	public TransactionReceipt openAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0.0, 0.0, Calendar.getInstance(),null,null);
			return Receipt;
				}else {
					return bankAccounts.get(index).openAcct(ticket);
			}	
		}
	/* Deletes a bank account if it exists and has a zero balance.
	 * Finds the account by account number, validates balance, and removes it from the bank's list.
	 * Returns a TransactionReceipt indicating success or failure.
	 */
	public TransactionReceipt deleteAcct(TransactionTicket ticket, int accountNumber) {
		int index = findAcct(accountNumber);
		if(index == -1) {
			String ReasonforFailure = "Error Account Number " + accountNumber + " not found.";
			TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0, 0.0, Calendar.getInstance(),null, null);
			return Receipt;
			}else {
				if(bankAccounts.get(index).getbalance() > 0) {
					String ReasonforFailure = "Error Account Number " + accountNumber + " has a balance of " + bankAccounts.get(index).getbalance();
					TransactionReceipt Receipt = new TransactionReceipt(ticket, false, ReasonforFailure, 0, 0.0, Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
					return Receipt;
				}else {
					bankAccounts.remove(index);
					TransactionReceipt Receipt = new TransactionReceipt(ticket, true, 0.0,0.0,Calendar.getInstance(),getbankAccounts().get(index).getStatus(), getbankAccounts().get(index).getaccountType());
					return Receipt;
			}
		}
	}
	
	public static void recalcTotals(ArrayList<Accounts> accounts) {
	    for (Accounts acc : accounts) {
	        double balance = acc.getbalance();
	        String type = acc.getaccountType();

	        switch(type) {
	            case "Savings":
	                totalAmountInSavingAccts += balance;
	                break;
	            case "Checking":
	                totalAmountInCheckingAccts += balance;
	                break;
	            case "CD":
	                totalAmountInCDAccts += balance;
	                break;
	        }
	        totalAmountInAllAccts += balance;
	    }
	    System.out.printf("%.2f",totalAmountInAllAccts);
	}
	
}
