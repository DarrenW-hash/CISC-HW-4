import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.PrintWriter;

public class Main_Class {

	public static void main (String args []	) throws IOException {
		File inputFile = new File("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\input.txt");
		PrintWriter outputWriter = new PrintWriter("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\output.txt");
		String userchoice; 
		Scanner userinput = new Scanner(inputFile);
		Bank bank = new Bank();
		readaccts(bank);
		
		printAccts(bank,outputWriter);

		do {
			menu();
			System.out.println("Enter choice : ");
			userchoice = userinput.next();
			switch(userchoice) {
				case("w"):
				case("W"):
					withdrawalMethod(bank, outputWriter, userinput);
					break;
				case("Q"):
				case("q"):
					outputWriter.println();
					outputWriter.println("Ending Program");
					outputWriter.println("Updated Bank Account");
					// Print account info
					printAccts(bank,outputWriter);
					break;
			
			
			default:
			System.out.println("Not a choice ");
			break;
			}
		}while(!userchoice.equalsIgnoreCase("Q"));
		outputWriter.flush();
		
	}
	public static int readaccts(Bank bank) throws IOException{
		int currentAccount = 0;
		File bankAccounts = new File("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\BankAccounts.txt");
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
		System.out.println("Select one of the Following : ");
		System.out.println("W - Withdrawal");
		System.out.println("D - Deposit");
		System.out.println("C - Clear Account");
		System.out.println("N - New Account");
		System.out.println("B - Balance");
		System.out.println("I - Account Info)");
		System.out.println("H - Account Transaction History");
		System.out.println("S - Close Account");
		System.out.println("R - Reopen Account");
		System.out.println("X - Delete Account");
		System.out.println("Q - Quit");
	}
	
	public static void printAccts(Bank bank, PrintWriter outputWriter)	{
		ArrayList<Accounts> acc = bank.getbankAccounts();
		outputWriter.printf("%-12s %-12s %-12s %-10s %-12s %-12s %-12s %-15s%n",
				"First Name", "Last Name", "SSN", "ACCT NUM","ACCT TYPE", "STATUS", "BALANCE","MATURITY DATE");
		
		for(Accounts a : acc) {
			if(a.getaccountType().equals("CD")) {
				outputWriter.printf("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f %-15tD%n", 
						a.getdepositor().getNames().getFirstName(),
						a.getdepositor().getNames().getLastName(),
						a.getdepositor().getSSnumber(),
						a.getAccountNumber(),
						a.getaccountType(),
						a.getStatus(),
						a.getbalance(),
						a.getDate());
			}else{
				outputWriter.printf("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f%n", 
						a.getdepositor().getNames().getFirstName(),
						a.getdepositor().getNames().getLastName(),
						a.getdepositor().getSSnumber(),
						a.getAccountNumber(),
						a.getaccountType(),
						a.getStatus(),
						a.getbalance());
			}
		}
		
		outputWriter.println();
		outputWriter.flush();
	}
	
	public static void printTransactionReceipt(Bank bank, PrintWriter outputWriter, TransactionReceipt Receipt) {
		ArrayList<Accounts> accounts = bank.getbankAccounts();
		
		int index = bank.getindex();
		System.out.println(index);
		if(index == -1) {
			outputWriter.println("Transaction Failed");
	    	outputWriter.println(Receipt. getReasonForFailure());
	    	outputWriter.println("--------------------");
	    	outputWriter.println();
		}else {
			if(Receipt.getTransactionSuccessIndicatorFlag() == false) {
				outputWriter.println("Transaction Failed");
				outputWriter.println("Account Type : " + accounts.get(index).getaccountType());
				outputWriter.printf("Account Number:  %d%nReason: %s%n", accounts.get(index).getAccountNumber(), Receipt.getReasonForFailure());
				outputWriter.println("--------------------");
				outputWriter.println();
			}else {
					if(accounts.get(index).getaccountType().equalsIgnoreCase("CD")) {
						if(Receipt.getTransactionTicket().getTransaction().equals("WITHDRAWAL")) {
							outputWriter.printf("Account Number : %d%nAccount Type : %s%nWithdrawl Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f%nCD New Maturity Date: %tD%nAccount Status : %s%n",
								bank.getbankAccounts().get(index).getAccountNumber(),
								bank.getbankAccounts().get(index).getaccountType(),
								Receipt.getTransactionTicket().getTransactionAmount(),
								Receipt.getPreTransactionBalance(),
								Receipt.getPostTransactionBalance(),
								accounts.get(index).getDate(),
								accounts.get(index).getStatus());
							outputWriter.println();
						}else if(Receipt.getTransactionTicket().getTransaction().equals("DEPOSIT")) {
							outputWriter.printf("Account Number : %d%nAccount Type : %s%nDeposit Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f%nCD New Maturity Date: %tD%nAccount Status : %s%n",
								bank.getbankAccounts().get(index).getAccountNumber(),
								bank.getbankAccounts().get(index).getaccountType(),
								Receipt.getTransactionTicket().getTransactionAmount(),
								Receipt.getPreTransactionBalance(),
								Receipt.getPostTransactionBalance(),
								accounts.get(index).getDate(),
								accounts.get(index).getStatus());
							outputWriter.println();
						}
					}else {
						if(Receipt.getTransactionTicket().getTransaction().equals("WITHDRAWAL")) {
							outputWriter.printf("Account Number : %d%nAccount Type : %s%nWithdrawl Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f%nAccount Status : %s%n",
								bank.getbankAccounts().get(index).getAccountNumber(),
								bank.getbankAccounts().get(index).getaccountType(),
								Receipt.getTransactionTicket().getTransactionAmount(),
								Receipt.getPreTransactionBalance(),
								Receipt.getPostTransactionBalance(),
								accounts.get(index).getStatus());
							outputWriter.println();
						}else if(Receipt.getTransactionTicket().getTransaction().equals("DEPOSIT")) {
							outputWriter.printf("Account Number : %d%nAccount Type : %s%nDeposit Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f%nAccount Status : %s%n",
								bank.getbankAccounts().get(index).getAccountNumber(),
								bank.getbankAccounts().get(index).getaccountType(),
								Receipt.getTransactionTicket().getTransactionAmount(),
								Receipt.getPreTransactionBalance(),
								Receipt.getPostTransactionBalance(),
								accounts.get(index).getStatus());
							outputWriter.println();
						}else if(Receipt.getTransactionTicket().getTransaction().equals("Clear Check")) {
							outputWriter.printf("Account Number : %d%nAccount Type : %s%nAmount of Check:: %.2f%nOld Balance : %.2f%nNew Balance : %.2f%nAccount Status : %s%n",
									bank.getbankAccounts().get(index).getAccountNumber(),
									bank.getbankAccounts().get(index).getaccountType(),
									Receipt.getTransactionTicket().getTransactionAmount(),
									Receipt.getPreTransactionBalance(),
									Receipt.getPostTransactionBalance(),
									accounts.get(index).getStatus());
								outputWriter.println();
						}
					}
			}
		}
		outputWriter.flush();
	}
	
	public static void withdrawalMethod(Bank bank, PrintWriter outputWriter, Scanner userinput)	{
		ArrayList<Accounts> acc = bank.getbankAccounts();
		outputWriter.println("Transaction Type : Withdrawal ");
		System.out.println("Enter Account Number : ");
		int accountNumber = userinput.nextInt();
		System.out.println("Withdrawal Amount : ");
		double withdrawalAmount = userinput.nextDouble();
		Calendar withdrawalTime = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, withdrawalTime, "WITHDRAWAL", withdrawalAmount, 0);
		TransactionReceipt receipt = bank.makeWithdrawal(ticket, accountNumber, userinput);
		printTransactionReceipt(bank,outputWriter,receipt);
	}
}
