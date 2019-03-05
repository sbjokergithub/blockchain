package zju.cst.blockchainconsensus;

import java.sql.Timestamp;

public class Trading {
    private String transactionSender;
    private double transactionAmount;
    private String transactionReceiver;
    private Timestamp time;
    private User sender;
    private User receiver;

    public Trading(String transactionSender, double transactionAmount, String transactionReceiver, Timestamp time,User sender, User receiver) {
        this.transactionSender = transactionSender;
        this.transactionAmount = transactionAmount;
        this.transactionReceiver = transactionReceiver;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }

    public String print() {
        return "Trading [transactionSender=" + transactionSender + ", transactionAmount=" + transactionAmount
                + ", transactionReceiver=" + transactionReceiver + ", time=" + time + "]";		
	}
    
    public String getTransactionSender() {
        return transactionSender;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionReceiver() {
        return transactionReceiver;
    }
    
    public User userGetSender() {
    	return this.sender;
    }
    
    public User userGetReceiver() {
    	return this.receiver;
    }

    public Timestamp getTime() {
        return time;
    }


    @Override
    public String toString() {
        return "Trading [transactionSender=" + transactionSender + ", transactionAmount=" + transactionAmount
                + ", transactionReceiver=" + transactionReceiver + ", time=" + time + "]";
    }

}
