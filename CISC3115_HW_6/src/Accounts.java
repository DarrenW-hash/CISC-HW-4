import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class Accounts {

	private Depositors depositor;
	private int AccountNumber;
	private String accountType;
	private String accountStatus;
	private double balance;
	private ArrayList<TransactionReceipt> transactionReceipts = new ArrayList<>();
	private Check check;
	private Calendar date;
	private TransactionTicket ticket;
	
	public Accounts(Depositors depositor, int AccountNumber, String accountType, double balance, String accountStatus)	{
		this.depositor = depositor;
		this.AccountNumber = AccountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountStatus = accountStatus;
	}
	
	public Accounts(Depositors depositor, int AccountNumber, String accountType, double balance, String accountStatus, Calendar date) {
		this.depositor = depositor;
		this.AccountNumber = AccountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountStatus = accountStatus;
		this.date = date;
	}
	
	//copy constructors
	public Accounts(Accounts other)	{
		this.depositor = other.depositor;
		this.AccountNumber = other.AccountNumber;
		this.balance = other.balance;
		this.accountStatus = other.accountStatus;
		
		if(other.date != null) {
			this.date = other.date;
		}else {
			this.date = null;
		}
	}
	//getters
	public Depositors getdepositor() {
		return depositor;
	}
	public int getAccountNumber() {
		return AccountNumber;
	}
	public String getaccountType() {
		return accountType;
	}
	public ArrayList<TransactionReceipt> gettransactionRecipts() {
		return transactionReceipts;
	}
	public Check getCheck() {
		return check;
	}
	public double getbalance() {
		return balance;
	}
	public Calendar getDate() {
		return date;
	}
	
	//setters
	private void setAccountStatus(String _accountStatus) {
		accountStatus = _accountStatus;
	}
	private void setbalance(double _balance)	{
		balance = _balance;
	}
	private void addtransactionReceipt(TransactionReceipt receipt)	{
		transactionReceipts.add(receipt);
	}
	private void setcheck(Check _check)	{
		check = _check;
	}
	private void setCalendar(Calendar _date) {
		date = _date;
	}
	
	//toString()
	@Override
	public String toString()	{
		return depositor.toString() + " " + AccountNumber + " " + accountType + " " + check.toString() + balance + " " + date ;
	}
}
