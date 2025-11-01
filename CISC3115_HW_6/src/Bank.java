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
	
	public void addAccounts(Accounts Acc)	{
		System.out.println("Added Account");
		bankAccounts.add(Acc);
	}
}
