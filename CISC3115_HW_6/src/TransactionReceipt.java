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
	
	public TransactionReceipt(TransactionTicket ticket, boolean TranactionSuccessIndicatorFlag,String ReasonForFailure, double PreTransactionBalance, double PostTransactionBalance, Calendar postTransactionMaturityDate) {
		this.ticket = ticket;
		this.TransactionSuccessIndicatorFlag = TranactionSuccessIndicatorFlag;
		this.PreTransactionBalance = PreTransactionBalance;
		this.PostTransactionBalance = PostTransactionBalance;
		this.ReasonForFailure = ReasonForFailure;
		this.postTransactionMaturityDate = postTransactionMaturityDate;
	}
	
	//getters 
	public TransactionTicket getTransactionTicket() {
		return ticket;
	}
	public boolean getTransactionSuccessIndicatorFlag() {
		return TransactionSuccessIndicatorFlag;
	}
	public String getReasonForFailure() {
		return ReasonForFailure;
	}
	public double getPostTransactionBalance() {
		return PostTransactionBalance;
	}
	public double getPreTransactionBalance() {
		return PreTransactionBalance;
	}
	public Calendar getPostTransactionMaturityDate() {
		return postTransactionMaturityDate;
	}
	
	//toString 
	@Override
	public String toString()	{
		if(postTransactionMaturityDate != null) {
			return ticket.toString() +  " " + TransactionSuccessIndicatorFlag + " " + ReasonForFailure + " " + PreTransactionBalance + " " + PostTransactionBalance + " " + postTransactionMaturityDate;
		}else {
			return ticket.toString() +  " " + TransactionSuccessIndicatorFlag + " " + ReasonForFailure + " " + PreTransactionBalance + " " + PostTransactionBalance;
		}
	}
}
