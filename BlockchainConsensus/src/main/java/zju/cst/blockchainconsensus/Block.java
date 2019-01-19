package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public class Block {
	private Block fatherBlock;
	private ArrayList<Trading> transactionList;
	private Integer index;
	private String indexID;
	
	public Block(ArrayList<Trading> tradeList){
		this.transactionList = new ArrayList<Trading>();
		for (int i=0; i<tradeList.size(); ++i) {
			this.transactionList.add(tradeList.get(i));
		}
	}
	
	public void addTransaction(ArrayList<Trading> tradeList) {
		for (int i=0; i<tradeList.size(); ++i) {
			this.transactionList.add(tradeList.get(i));
		}
	}
	
	public ArrayList<Trading> getTransaction(){
		return this.transactionList;
	}
	
	public void print() {
		for (int i = 0; i<this.transactionList.size(); ++i) {
			System.out.println(this.transactionList.get(i).print());
		}
	}
	
}
