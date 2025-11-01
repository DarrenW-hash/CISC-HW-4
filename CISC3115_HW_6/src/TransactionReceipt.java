import java.util.Calendar;


public class TransactionReceipt {
	private TransactionTicket ticket;
	private boolean TransactionSuccessIndicatorFlag;
	private String ReasonForFailure;
	private double PreTransactionBalance;
	private double PostTransactionBalance;
	private Calendar postTransactionMaturityDate;
	
	public TransactionReceipt(TransactionTicket ticket, boolean TranactionSuccessIndicatorFlag, double PreTransactionBalance, double PostTransactionBalance, Calendar postTransactionMaturityDate ) {
		this.ticket = ticket;
		this.TransactionSuccessIndicatorFlag = TranactionSuccessIndicatorFlag;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
		this.postTransactionMaturityDate = postTransactionMaturityDate;
	}
	
	public TransactionReceipt(TransactionTicket ticket, boolean TranactionSuccessIndicatorFlag, double PreTransactionBalance, double PostTransactionBalance) {
		this.ticket = ticket;
		this.TransactionSuccessIndicatorFlag = TranactionSuccessIndicatorFlag;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
	}
	
	//getters 
	public TransactionTicket getTransactionTicket() {
		return ticket;
	}
	public boolean getTransactionSuccessIndicatorFlag() {
		return TransactionSuccessIndicatorFlag;
	}
	public String ReasonForFailure() {
		return ReasonForFailure;
	}
	public double getPreTransactionFailureReason() {
		return PreTransactionBalance;
	}
	public double getPostTransactionBalance() {
		return PostTransactionBalance;
	}
	public Calendar getPostTransactionMaturityDate() {
		return postTransactionMaturityDate;
	}
}
