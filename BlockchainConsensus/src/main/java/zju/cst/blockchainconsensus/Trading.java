package zju.cst.blockchainconsensus;

import java.sql.Timestamp;

public class Trading {
	private String transactionSender;
	private double transactionAmount;
	private String transactionReceiver;
	private Timestamp time;
	public String getTransactionSender() {
		return transactionSender;
	}
	public void setTransactionSender(String transactionSender) {
		this.transactionSender = transactionSender;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionReceiver() {
		return transactionReceiver;
	}
	public void setTransactionReceiver(String transactionReceiver) {
		this.transactionReceiver = transactionReceiver;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Trading [transactionSender=" + transactionSender + ", transactionAmount=" + transactionAmount
				+ ", transactionReceiver=" + transactionReceiver + ", time=" + time + "]";
	}
	
}
