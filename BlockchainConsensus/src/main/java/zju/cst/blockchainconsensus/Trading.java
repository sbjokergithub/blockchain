package zju.cst.blockchainconsensus;

import java.sql.Timestamp;

public class Trading {
    private String transactionSender;
    private double transactionAmount;
    private String transactionReceiver;
    private Timestamp time;
    private User sender;
    private User receiver;

    public Trading(String transactionSender, double transactionAmount, String transactionReceiver, Timestamp time) {
        this.transactionSender = transactionSender;
        this.transactionAmount = transactionAmount;
        this.transactionReceiver = transactionReceiver;
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
    	return sender;
    }
    
    public User userGetReceiver() {
    	return receiver;
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
