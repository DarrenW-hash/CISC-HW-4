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
	//copy constructor
	public TransactionReceipt(TransactionReceipt other) {
	    this.ticket = new TransactionTicket(other.ticket); 
	    this.TransactionSuccessIndicatorFlag = other.TransactionSuccessIndicatorFlag;
	    this.ReasonForFailure = other.ReasonForFailure;
	    this.PreTransactionBalance = other.PreTransactionBalance;
	    this.PostTransactionBalance = other.PostTransactionBalance;
	    this.postTransactionMaturityDate = (Calendar) other.postTransactionMaturityDate.clone();
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
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(TransactionSuccessIndicatorFlag == false) {
			sb.append("Transaction Failed \n");
			sb.append("Account Number " + ticket.getAccountnumber() + "\n");
			sb.append("Reason : " + ReasonForFailure + "\n");
		}else {
			switch(ticket.getTransaction()) {
				case("WITHDRAWAL"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
				case("DEPOSIT"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
				case("Clear Check"):
					sb.append(ticket.toString());
					sb.append(String.format("Old Balance : %.2f%nNew Balance : %.2f",
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
				case("Close Account"):
					sb.append(ticket.toString());
					sb.append("Account Status : Now Closed");
					break;
				case("Open Account"):
					sb.append(ticket.toString());
					sb.append("Account Status : Now Open");
					break;
				case("Delete Account"):
					sb.append("Account Number " +ticket.getAccountnumber() + " has been DELETED \n");
					
			}
		}
		return sb.toString();
	}
}
