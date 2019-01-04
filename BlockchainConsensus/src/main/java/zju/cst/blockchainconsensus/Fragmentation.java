package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public class Fragmentation {
	private String ID;
	//private ArrayList<String> nodeList;
	private ArrayList<Node> nodeList;
	private ArrayList<Trading> transaction;
	private String masterNode;
	//How to select the representative nodes?
	public String chooseMasterNode(){
		return null;
	}
	//Select the billing node and return the ID of the billing node
	public String selectBillingNode(){
		int target=123;
		int i;
		while(true){
			for(i=0;i<nodeList.size();i++){
				int random=(int)(1+Math.random()*(100));
				if(random<target*nodeList.get(i).getCoinNumber()){
					break;
				}
			}
			if(i<nodeList.size()){
				break;
			}
		}
		return nodeList.get(i).getID();
	}
//	public static void main(String[] args) {
//		int random=(int)(1+Math.random()*(100));
//		System.out.println(random);
//	}
	
}
