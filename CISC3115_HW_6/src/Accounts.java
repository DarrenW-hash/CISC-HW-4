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
		this.accountType = other.accountType;
		
		if(other.date != null) {
			 this.date = (Calendar) other.date.clone();
		}else {
			this.date = null;
		}
	}
	//getters
	public Depositors getdepositor() {
		return new Depositors(depositor);
	}
	public int getAccountNumber() {
		return AccountNumber;
	}
	public String getaccountType() {
		return accountType;
	}
	public ArrayList<TransactionReceipt> gettransactionRecipts() {
		return new ArrayList<>(transactionReceipts);
	}
	public Check getCheck() {
		return new Check(check);
	}
	public double getbalance() {
		return balance;
	}
	public Calendar getDate() {
		return (Calendar) date.clone();
	}
	public String getStatus()	{
		return accountStatus;
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
		return depositor.toString() + " " + AccountNumber + " " + accountType + " " + balance + " " + date ;
	}
	
	//equals method
	@Override
	public boolean equals(Object obj)	{
		if(this == obj) {
			return true;
		}
		if(obj == null)	{
			return false;
		}
		Accounts other = (Accounts) obj;
		
		return  this.depositor.equals(other.depositor)
	            && this.AccountNumber == other.AccountNumber
	            && this.balance == other.balance;
	}
	
	public TransactionReceipt makeWithDrawal(TransactionTicket ticket, Scanner userinput)	{
		double preTransaction = getbalance();
		Calendar currentDate = Calendar.getInstance();
		TransactionReceipt receipt;
		
		//check if th3e account is closed
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number " + ticket.getAccountnumber() + " is CLOSED";
			receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate);
			addtransactionReceipt(receipt);
			return receipt;
		}else {
			if(getaccountType().equals("CD")) {
				if(currentDate.before(getDate())) {
					Calendar maturity = getDate();
					int month = maturity.get(Calendar.MONTH);
					int day = maturity.get(Calendar.DAY_OF_MONTH);
					int year = maturity.get(Calendar.YEAR);
					String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
					receipt = new TransactionReceipt(ticket, false, reason, getbalance(), getbalance(), getDate());
					addtransactionReceipt(receipt);
					return receipt;
				}else {
					//process successful CD withdrawal
					double newBalance = preTransaction - ticket.getTransactionAmount();
					System.out.println("Select new Maturity period in months: 6, 12, 18, or 24");
					int month = userinput.nextInt();
					if(month == 6 || month == 12 || month == 18 || month == 24){
						Calendar newMaturity = (Calendar) currentDate.clone();
						newMaturity.add(Calendar.MONTH, month);
						setCalendar(newMaturity);
					}else {
						System.out.println("Invalid input. Maturity date not updated.");
					}
					//successful withdrawal 
					setbalance(newBalance);
					receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate());
					addtransactionReceipt(receipt);
					return receipt;
				}
			}else {
				if(ticket.getTransactionAmount() < 0) {
					String reason = String.format("Error: Invalid Withdrawal input: %.2f",ticket.getTransactionAmount());
					receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate);
					addtransactionReceipt(receipt);
					return receipt;
				}else if(preTransaction < ticket.getTransactionAmount()) {
					String reason = String.format("Error: Insufficient funds. Withdrawal Amount: %.2f. Current Balance: %.2f", ticket.getTransactionAmount(), getbalance());
					receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate);
					addtransactionReceipt(receipt);
					return receipt;
				}else {
					//successful withdrawal
					double newBalance = preTransaction - ticket.getTransactionAmount();
					setbalance(newBalance);
					receipt =  new TransactionReceipt(ticket, true, preTransaction,newBalance,currentDate);
					addtransactionReceipt(receipt);
					return receipt;
				}
			}
		}
		
	}
}
