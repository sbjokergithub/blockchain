package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;

public class OrdinaryNode extends Node {
	private ArrayList<Node> ordinaryNode;
	private ArrayList<Node> masterNode;
    public OrdinaryNode(String ID, double coinNumber, Fragmentation fragmentation, int load) {
        super(ID, coinNumber, fragmentation, load);
    }

    @Override
    public Map<String, Double> getUserExtraList() {
        return userExtraList;
    }

    @Override
    public void setUserExtraList(Map<String, Double> userExtraList) {
        this.userExtraList = userExtraList;
    }

    //Package data from the transaction pool and return the packaged block
    @Override
    public ArrayList<Trading> packageBlock() {
        // trading pits
        //How do I get transaction pool data?
        ArrayList<Trading> tradingPits = null;
        //Whether a new block class is needed?
        ArrayList<Trading> block = new ArrayList<Trading>();
        int tradingNumber = 10;
        for (int i = 0; i < tradingPits.size() && i < tradingNumber; i++) {
            block.add(tradingPits.get(i));
        }
        return block;
    }

    //Simulates executing the transaction and returns the execution result
    @Override
    public String simulationExecution() {
        //How do I get the user balance table?
        userExtraList = null;
        //Packaged block
        ArrayList<Trading> block = null;
        int i;
        int tradingNumber = 10;
        for (i = 0; i < tradingNumber; i++) {
            Trading trading = block.get(i);
            String sender = trading.getTransactionSender();
            double account = trading.getTransactionAmount();
            String receiver = trading.getTransactionReceiver();
            double senderExtra = userExtraList.get(sender);
            double receiverExtra = userExtraList.get(receiver);
            if (senderExtra < account) {
                System.out.println("Invalid deal：" + trading.toString());
                continue;
            }
            userExtraList.put(sender, senderExtra - account);
            userExtraList.put(receiver, receiverExtra - account);
        }
        return null;
    }

    //Broadcast the results of simulation execution to all nodes in the sharding and all representative nodes
    @Override
    public String broadcast() {
    	masterNode=null;
    	int i;
    	for(i=0;i<masterNode.size();i++){
    		masterNode.get(i).simulationExecution();//将区块传递过去
    	}
    	ordinaryNode=null;
    	for(i=0;i<ordinaryNode.size();i++){
    		ordinaryNode.get(i).simulationExecution();//将区块传递过去
    	}
        return null;
    }

    //Connect blocks to the block chain and update the local user balance table
    @Override
    public void chargeAccount() {
    	userExtraList=null;
    	int number=0;
    	int i;
    	for(i=0;i<ordinaryNode.size();i++){
    		if(userExtraList.equals(ordinaryNode.get(i).userExtraList)){
    			number++;
    		}
    	}
    	if(number>ordinaryNode.size()/3*2){
    		
    	}
    }

    //User dynamic adjustment
    @Override
    public void Management() {
    }
}
