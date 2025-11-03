import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.PrintWriter;

public class Main_Class {

	public static void main (String args []	) throws IOException {
		//mac files link
		File inputFile = new File("/Users/darrenweng/git/CISC-HW-6/CISC3115_HW_6/src/input.txt");
		PrintWriter outputWriter = new PrintWriter("/Users/darrenweng/git/CISC-HW-6/CISC3115_HW_6/src/output.txt");
		//File inputFile = new File("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\input.txt");
		//PrintWriter outputWriter = new PrintWriter("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\output.txt");
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
				case("D"):
				case("d"):
					deposit(bank, userinput, outputWriter);
					break;
				case("B"):
				case("b"):
					balance(bank, userinput, outputWriter);
					break;
				case("C"):
				case("c"):
					clearCheck(bank, userinput, outputWriter);
					break;
//				case("N"):
//				case("n"):
//					outputWriter.println("Transaction: New Account");
//					newAccount(bank, userinput, outputWriter);
//					break;
//				case("I"):
//				case("i"):
//					outputWriter.println("Transaction: Account Info");
//					acctInfo(bank, userinput, outputWriter);
//					break;
//				case("H"):
//				case("h"):
//					outputWriter.println("Transaction: Account Info With Transaction History");
//					acctInfoHistory(bank, userinput, outputWriter);
//					break;
//				case("S"):
//				case("s"):
//					outputWriter.println("Transaction: Close Account");
//					closeAcct(bank, userinput, outputWriter);
//					break;
//				case("R"):
//				case("r"):
//					outputWriter.println("Transaction: Reopen Account");
//					reopenAcct(bank, userinput, outputWriter);
//					break;
//				case("X"):
//				case("x"):
//					outputWriter.println("Transaction: Delete Account");
//					deleteAcct(bank, userinput, outputWriter);
//					break;
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
		//mac file 
		File bankAccounts = new File("/Users/darrenweng/git/CISC-HW-6/CISC3115_HW_6/src/BankAccounts.txt");
		//windows file
		//File bankAccounts = new File("C:\\Users\\dweng\\git\\CISC-HW-4\\CISC3115_HW_6\\src\\BankAccounts.txt");
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
	/* * Handles a deposit transaction for a specific bank account.
	 * Prompts the user for an account number and deposit amount,
	 * creates a TransactionTicket, and processes the deposit through the Bank object.
	 */
	public static void deposit(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		ArrayList<Accounts> accounts = bank.getbankAccounts();			
		outputWriter.println("Transaction: Deposit");
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		System.out.println("Enter Deposit Amount: ");
		double amount = userInput.nextDouble();
		Calendar time = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, time, "DEPOSIT", amount, 0);		
		TransactionReceipt receipt = bank.makedeposit(ticket, accountNumber, userInput);
		int index = bank.getindex();
		printTransactionReceipt(bank,outputWriter,receipt);
	}
	/* Handles a balance inquiry for a specific bank account.
	 * Prompts the user for an account number, creates a TransactionTicket,
	 * retrieves the current balance through the Bank object, and outputs the result.
	 */
	public static void balance(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		ArrayList<Accounts>accounts = bank.getbankAccounts();
		outputWriter.println("Transaction: Balance");
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		Calendar time = Calendar.getInstance();
		TransactionTicket ticket = new TransactionTicket(accountNumber, time, "BALANCE", 0.0 , 0);	
		TransactionReceipt receipt = bank.getBalance(ticket,accountNumber);
		int index = bank.getindex();			if(index != -1) {
			outputWriter.printf("Account Number : %d%nAccount Type : %s%nCurrent Balance : %.2f%n",
					ticket.getAccountnumber(),
					accounts.get(index).getaccountType(),
					accounts.get(index).getbalance());
			outputWriter.println();
		}else {
			outputWriter.println("Transaction Failed");
		    outputWriter.println("ERROR Account not Found " + accountNumber);
		    outputWriter.println("--------------------");
		    outputWriter.println();
		}	
	}
	/* Processes a check-clearing transaction for a specific bank account.
	 * Prompts the user for account number, check details (amount and date),
	 * creates a Check and TransactionTicket object, and requests the Bank
	 * to validate and clear the check.
	 */
	public static void clearCheck(Bank bank, Scanner userInput, PrintWriter outputWriter) {
		ArrayList<Accounts>accounts = bank.getbankAccounts();
		outputWriter.println("Transaction: Clear Check");
		Calendar time = Calendar.getInstance();
		// Prompt for account number
		System.out.println("Enter Account Number : ");
		int accountNumber = userInput.nextInt();
		// Prompt for the check amount
		System.out.println("Enter Check Amount : ");
		double amount = userInput.nextDouble();
		// Prompt for the check’s date information (month, day, year)
	    System.out.print("Enter check month (1-12): ");
		int month = userInput.nextInt();
		System.out.print("Enter check day: ");
		int day = userInput.nextInt();
		System.out.print("Enter check year: ");
	    int year = userInput.nextInt();
	    Calendar checkDate = Calendar.getInstance();
	    checkDate.set(year, month - 1, day);
	    Check check = new Check(accountNumber, amount, checkDate);
	   	TransactionTicket ticket = new TransactionTicket(accountNumber, time, "Clear Check", amount,0);			TransactionReceipt receipt = bank.clearCheck(ticket, accountNumber, checkDate);
		printTransactionReceipt(bank,outputWriter,receipt);
	}
}
