package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public class Block {
	private Block fatherBlock;
	private ArrayList<Trading> transactionList;
	private Integer index;
	private String indexID;
	
	public Block(ArrayList<Trading> tradeList){
		this.transactionList = tradeList;
	}
	
	public void addTransaction(ArrayList<Trading> tradeList) {
		for (int i=0; i<tradeList.size(); ++i) {
			this.transactionList.add(tradeList.get(i));
		}
	}
	
	public ArrayList<Trading> getTransaction(){
		return this.transactionList;
	}
	
}
