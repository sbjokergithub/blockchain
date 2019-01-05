package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public class Fragmentation {
    private String ID;
    private ArrayList<Node> nodeList;
    private ArrayList<User> userList;
    private ArrayList<Trading> transaction;
    private String masterNode;

    public Fragmentation(String ID) {
        this.ID = ID;
        this.userList = new ArrayList<User>();
        this.nodeList = new ArrayList<Node>();
        this.transaction = new ArrayList<Trading>();
    }

    public String getID() {
        return ID;
    }

    //add user
    public void addUser(User user) {
        this.userList.add(user);
    }

    //add node
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    //add trading
    public void addTransaction(Trading trading) {
        this.transaction.add(trading);
    }

    //chose a best ordinary node to store a new user
    public Node bestNode()
    {
    	nodeList=null;
    	int min=nodeList.get(0).userExtraList.size();
    	int index=0;
    	for(int i=1;i<nodeList.size();i++){
    		if(nodeList.get(i).userExtraList.size()<min){
    			min=nodeList.get(i).userExtraList.size();
    			index=i;
    		}
    	}
    	
		return nodeList.get(index);

    }

    //How to select the representative nodes?
    public String chooseMasterNode() {
        return null;
    }

    //Select the billing node and return the ID of the billing node
    public String selectBillingNode() {
        int target = 123;
        int i;
        while (true) {
            for (i = 0; i < nodeList.size(); i++) {
                int random = (int) (1 + Math.random() * (100));
                if (random < target * nodeList.get(i).getCoinNumber()) {
                    break;
                }
            }
            if (i < nodeList.size()) {
                break;
            }
        }
        return nodeList.get(i).getID();
    }

}
