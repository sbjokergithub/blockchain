package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;

public class MasterNode extends Node {
	private ArrayList<Node> masterNode;
    public MasterNode(String ID, Fragmentation fragmentation) {
        super(ID, fragmentation);
    }

    @Override
    public Map<String, Double> getUserExtraList() {
        return userExtraList;
    }

    @Override
    public void setUserExtraList(Map<String, Double> userExtraList) {
        this.userExtraList = userExtraList;
    }

    //Package blocks from the trading pool
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

    //Simulates executing transactions on blocks
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

    //Broadcasts the packaged blocks to all representative nodes
    @Override
    public String broadcast() {
    	//How do I get other master node information
    	masterNode=null;
    	int i;
    	for(i=0;i<masterNode.size();i++){
    		masterNode.get(i).simulationExecution();//将区块传递过去
    	}
        return null;
    }

    //Simulates executing transactions on blocks
    @Override
    public void chargeAccount() {
    	userExtraList=null;
    	int number=0;
    	int i;
    	for(i=0;i<masterNode.size();i++){
    		if(userExtraList.equals(masterNode.get(i).userExtraList)){
    			number++;
    		}
    	}
    	if(number>masterNode.size()/3*2){
    		
    	}
    }

    //
    @Override
    public void Management() {
    }
}
