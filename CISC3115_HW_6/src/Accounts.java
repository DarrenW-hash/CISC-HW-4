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
		this.date = Calendar.getInstance();
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
		this.depositor = new Depositors(other.depositor);
		this.AccountNumber = other.AccountNumber;
		this.balance = other.balance;
		this.accountStatus = other.accountStatus;
		this.accountType = other.accountType;
		
		if(other.date != null) {
			 this.date = (Calendar) other.date.clone();
		}else {
			this.date = null;
		}
		
		// deep copy transaction receipts
	    this.transactionReceipts = new ArrayList<>();
	    for (TransactionReceipt tr : other.transactionReceipts) {
	        this.transactionReceipts.add(new TransactionReceipt(tr)); 
	    }

	    // copy check if needed
	    if (other.check != null) {
	        this.check = new Check(other.check); 
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
		if (this.date == null) {
			return Calendar.getInstance(); 
		    	}
	 return (Calendar) this.date.clone();
		
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
		System.out.println("DEBUG: Added transaction to account " + getAccountNumber() + ", list size now: " + transactionReceipts.size());
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
		StringBuilder sb = new StringBuilder();
		if(getaccountType().equals("CD")) {
			sb.append(String.format("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f %12tD",
					getdepositor().getNames().getFirstName(),
					getdepositor().getNames().getLastName(),
					getdepositor().getSSnumber(),
					getAccountNumber(),
					getaccountType(),
					getStatus(),
					getbalance(),
					getDate()));
		}else {
			sb.append(String.format("%-12s %-12s %-12s %-10d %-12s %-12s %-10.2f", 
					getdepositor().getNames().getFirstName(),
					getdepositor().getNames().getLastName(),
					getdepositor().getSSnumber(),
					getAccountNumber(),
					getaccountType(),
					getStatus(),
					getbalance()));
		}
		return sb.toString();
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
			receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
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
					receipt = new TransactionReceipt(ticket, false, reason, getbalance(), getbalance(), getDate(), getStatus(),getaccountType());
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
					receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(), getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
				}
			}else {
				if(ticket.getTransactionAmount() < 0) {
					String reason = String.format("Error: Invalid Withdrawal input: %.2f",ticket.getTransactionAmount());
					receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(), getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
				}else if(preTransaction < ticket.getTransactionAmount()) {
					String reason = String.format("Error: Insufficient funds. Withdrawal Amount: %.2f. Current Balance: %.2f", ticket.getTransactionAmount(), getbalance());
					receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(), getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
				}else {
					//successful withdrawal
					double newBalance = preTransaction - ticket.getTransactionAmount();
					setbalance(newBalance);
					receipt =  new TransactionReceipt(ticket, true, preTransaction,newBalance,currentDate,getStatus(), getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
				}
			}
		}
		
	}
	
	/* Processes a deposit into the account.
	 * Handles standard accounts and CD accounts with maturity date restrictions.
	 * Validates account status and deposit amount.
	 * Updates balances and maturity date as needed.
	 */
		public TransactionReceipt makedeposit(TransactionTicket ticket, Scanner userinput) {
			double preTransaction = getbalance();
			Calendar currentDate = Calendar.getInstance();
			TransactionReceipt receipt;
			
			 // Check if the account is closed
			if(getStatus().equals("Closed")) {
				String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
				receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate, getStatus(),getaccountType());
				addtransactionReceipt(receipt); 
				return receipt;
			}else {
				 // Handle CD accounts separately
				if(getaccountType().equals("CD")) {
					if(currentDate.before(getDate())) {
						Calendar maturity = getDate();
						int month = maturity.get(Calendar.MONTH) + 1; // months are 0-based
						int day = maturity.get(Calendar.DAY_OF_MONTH);
						int year = maturity.get(Calendar.YEAR);
						String reason = "Error: Maturity Date " + month + "/" + day + "/" + year + " not reached ";
						receipt = new TransactionReceipt(ticket, false, reason, getbalance(),getbalance(), getDate(), getStatus(),getaccountType());
						addtransactionReceipt(receipt); 
						return receipt;
					}else {
						double newBalance = preTransaction + ticket.getTransactionAmount();
						// Prompt user to select new maturity period for CD
						System.out.println("Select new maturity period in months: 6, 12, 18, or 24");
						int months = userinput.nextInt();
						if (months == 6 || months == 12 || months == 18 || months == 24) {
			                Calendar newMaturity = (Calendar) currentDate.clone();
			                newMaturity.add(Calendar.MONTH, months);
			                setCalendar(newMaturity);
						}else {
							System.out.println("Invalid input. Maturity date not updated.");
						}
						// Successful deposit
						setbalance(newBalance);
						receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, getDate(), getStatus(),getaccountType());
						addtransactionReceipt(receipt); 
						return receipt;
					}
				}else {
					 // Handle standard accounts (Checking, Savings)
					if(ticket.getTransactionAmount() < 0) {
						String reason = String.format(
				        	    "Error: Invalid Deposit input: %.2f",
				        	    ticket.getTransactionAmount()
				        	);
						receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
						addtransactionReceipt(receipt); 
				        return receipt;
					}else {
						// Successful deposit
						double newBalance = preTransaction + ticket.getTransactionAmount();
						setbalance(newBalance);
						receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
						addtransactionReceipt(receipt); 
						return receipt;
					}
				}
			}
		}
	/* Retrieves the current balance of the account.
	* Creates a TransactionReceipt to record the balance inquiry.
	* 	
	*/
	public TransactionReceipt getBalance(TransactionTicket ticket){
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
		receipt = new TransactionReceipt(ticket,true,getbalance(),0,currentDate, getStatus(), getaccountType());
		addtransactionReceipt(receipt);
		return receipt;
	}
	/* Processes clearing of a check for a checking account.
	 * Validates account status, check date (post-dated or stale), and available funds.
	 * Applies a $2.50 fee for insufficient funds (bounced check) and updates the account balance.
	 */
	public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar checkDate) {
		double preTransaction = getbalance();
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
		Calendar sixMonthsAgo = (Calendar)currentDate.clone();
		sixMonthsAgo.add(Calendar.MONTH, -6);
		// Create a Check object representing the check being cleared
		Check check = new Check(ticket.getAccountnumber(), ticket.getTransactionAmount(), checkDate);	
		 // Check if the account is closed
//		if(getAccountStatus().equals("Closed")) {
//		String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
//		receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate);
//		return receipt;
//		}else {
			 // Only Checking accounts can clear checks
		if(getaccountType().equals("Checking")) {
			// Get readable date parts for display
		    int curMonth = currentDate.get(Calendar.MONTH) + 1;
		    int curDay = currentDate.get(Calendar.DAY_OF_MONTH);
		    int curYear = currentDate.get(Calendar.YEAR);
		    int checkMonth = check.getDateofCheck().get(Calendar.MONTH) + 1;
		    int checkDay = check.getDateofCheck().get(Calendar.DAY_OF_MONTH);
		    int checkYear = check.getDateofCheck().get(Calendar.YEAR);
		    int staleMonth = sixMonthsAgo.get(Calendar.MONTH) + 1;
		    int staleDay = sixMonthsAgo.get(Calendar.DAY_OF_MONTH);
		    int staleYear = sixMonthsAgo.get(Calendar.YEAR);        
		    if(getStatus().equals("Closed")) {
		    	String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is CLOSED";
		    		receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
		    		addtransactionReceipt(receipt); 
		    		return receipt;
		    	}else {
					 // Check if the check is post-dated
					if(check.getDateofCheck().after(currentDate)) {
						String reason = "Error:Check not cleared - Post-dated check: " + checkMonth + "/" + checkDay + "/" + checkYear;
					  	receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
					    addtransactionReceipt(receipt); 
					    return receipt; 
						}else {
						// Check if the check is stale (older than six months)
						if(check.getDateofCheck().before(sixMonthsAgo)) {
							String reason = "Error: Cannot clear stale check (older than 6 months). "
		                           + "Check Date: " + checkMonth + "/" + checkDay + "/" + checkYear + ". "
		                           + "Oldest Acceptable Date: " + staleMonth + "/" + staleDay + "/" + staleYear;
						      receipt = new TransactionReceipt(ticket, false, reason, preTransaction, preTransaction, currentDate,getStatus(),getaccountType());
						      addtransactionReceipt(receipt); 
						      return receipt;
						}
						else {
							if(preTransaction < check.getCheckAmount()) {
								// Check for insufficient funds
								double newBalance = preTransaction - 2.50;
								setbalance(newBalance);
								String reason = "Error: Insufficient funds. $2.50 service fee applied for bounced check - " 
										+ "Old Balance : " + preTransaction+" | " + "New Balance : " + newBalance;
							    receipt = new TransactionReceipt(ticket, false, reason, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
							    addtransactionReceipt(receipt);
							    return receipt;
							}else {
								double newBalance = preTransaction - check.getCheckAmount();
								setbalance(newBalance);
								receipt = new TransactionReceipt(ticket, true, preTransaction, newBalance, currentDate, getStatus(),getaccountType());
								addtransactionReceipt(receipt);
								return receipt;
							}
						}
					}
				}
			}else {
				// Reject non-Checking accounts
				String reason = "Error only Clear Checking Accounts. " + "Account Type : " + getaccountType();
				receipt = new TransactionReceipt(ticket,false,reason,0,0,currentDate, getStatus(),getaccountType());
				addtransactionReceipt(receipt); 
				return receipt;
		}
	}
	/* Closes the account if it is currently open.
	 * Validates the current account status and updates it to "Closed" if allowed.
	 * Records the transaction in the account’s transaction history.
	 */
	public TransactionReceipt closeAcct(TransactionTicket ticket) {
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
			
		if(getStatus().equals("Closed")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is Already CLOSED";
			receipt = new TransactionReceipt(ticket, false, reason, 0, 0, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
				}else {
					setAccountStatus("Closed");
					receipt = new TransactionReceipt(ticket, true,0,0, currentDate, getStatus(),getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
				
			}
		}
	/* Reopens the account if it is currently closed.
	 * Validates the current account status and updates it to "Open" if allowed.
	 * Records the transaction in the account’s transaction history.
	 */
	public TransactionReceipt openAcct(TransactionTicket ticket) {
		TransactionReceipt receipt;
		Calendar currentDate = Calendar.getInstance();
			
		if(getStatus().equals("Open")) {
			String reason = "Error: Account Number : " + ticket.getAccountnumber()+ " is Already Open";
			receipt = new TransactionReceipt(ticket, false, reason, 0, 0, currentDate, getStatus(),getaccountType());
			addtransactionReceipt(receipt); 
			return receipt;
				}else {
					setAccountStatus("Open");
					receipt = new TransactionReceipt(ticket, true,0,0, currentDate, getStatus(),getaccountType());
					addtransactionReceipt(receipt);
					return receipt;
		}
	}
}
