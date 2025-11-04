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
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if(TransactionSuccessIndicatorFlag == false) {
			sb.append("Account Number " + ticket.getAccountnumber() + "\n");
			sb.append("Reason : " + ReasonForFailure + "\n");
		}else {
			switch(ticket.getTransaction()) {
				case("WITHDRAWAL"):
					sb.append(String.format("Account Number : %d%nWithdrawl Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f",
							ticket.getAccountnumber(),
							ticket.getTransactionAmount(),
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
				case("DEPOSIT"):
					sb.append(String.format("Account Number : %d%nDeposit Amount : %.2f%nOld Balance : %.2f%nNew Balance : %.2f",
							ticket.getAccountnumber(),
							ticket.getTransactionAmount(),
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
				case("Clear Check"):
					sb.append(String.format("Account Number : %d%nAmount of Check:: %.2f%nOld Balance : %.2f%nNew Balance : %.2f",
							ticket.getAccountnumber(),
							ticket.getTransactionAmount(),
							getPreTransactionBalance(),
							getPostTransactionBalance()));
					break;
			}
		}
		return sb.toString();
	}
}
