import java.util.Calendar;
public class TransactionTicket {
	private int accountNumber;
	private Calendar dateofTransaction;
	private String typeofTransaction;
	private double amountofTransaction;
	private int termofCD;
	
	public TransactionTicket(int accountNumber, Calendar dateofTransaction, String typeofTransaction, double amountofTransaction, int termofCD) {
		this.accountNumber = accountNumber;
		this.dateofTransaction = dateofTransaction;
		this.typeofTransaction = typeofTransaction;
		this.amountofTransaction = amountofTransaction;
		this.termofCD = termofCD;
	}
	
	public Calendar getDateofTransaction() {
		return dateofTransaction;
	}
	public String getTransaction() {
		return typeofTransaction;
	}
	public int gettermofCD() {
		return termofCD;
	}
	public int getAccountnumber() {
		return accountNumber;
	}
	public double getTransactionAmount() {
		return amountofTransaction;
	}
	//toString
	@Override
	public String toString()	{
		return accountNumber + " " + dateofTransaction + " " + typeofTransaction + " " + amountofTransaction + " " + termofCD;
	}
}
