package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;

public class OrdinaryNode extends Node {
	public OrdinaryNode(String ID, double coinNumber, String fragmentationId, int load) {
		super(ID, coinNumber, fragmentationId, load);
	}

	@Override
	public Map<String,Double> getUserExtraList() {
		return userExtraList;
	}
	@Override
	public void setUserExtraList(Map<String,Double> userExtraList) {
		this.userExtraList = userExtraList;
	}
	//Package data from the transaction pool and return the packaged block
	@Override
	public ArrayList<Trading> packageBlock() {
		// trading pits
		//How do I get transaction pool data?
		ArrayList<Trading> tradingPits = null;
		//Whether a new block class is needed?
		ArrayList<Trading> block=new ArrayList<Trading>();
		int i;
		int tradingNumber=10;
		for(i=0;i<tradingPits.size()&&i<tradingNumber;i++){
			block.add(tradingPits.get(i));
		}
		return block;
	}
	//Simulates executing the transaction and returns the execution result
	@Override
	public String simulationExecution() {
		//How do I get the user balance table?
		userExtraList=null;
		//Packaged block
		ArrayList<Trading> block=null;
		int i;
		int tradingNumber=10;
		for(i=0;i<tradingNumber;i++){
			Trading trading=block.get(i);
			String sender=trading.getTransactionSender();
			double account=trading.getTransactionAmount();
			String receiver=trading.getTransactionReceiver();
			double senderExtra=userExtraList.get(sender);
			double receiverExtra=userExtraList.get(receiver);
			if(senderExtra<account){
				System.out.println("Invalid deal£º"+trading.toString());
				continue;
			}
			userExtraList.put(sender, senderExtra-account);
			userExtraList.put(receiver, receiverExtra-account);
		}
		return null;
	}
	//Broadcast the results of simulation execution to all nodes in the sharding and all representative nodes
	@Override
	public String broadcast() {
		return null;
	}
	//Connect blocks to the block chain and update the local user balance table
	@Override
	public void chargeAccount() {
	}
	//User dynamic adjustment
	@Override
	public void Management() {
	}
}
