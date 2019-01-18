package zju.cst.blockchainconsensus;

public class User {
    private String ID;
    public Node node;
    private double chargeAccount;

    public User(String ID, Node node, double chargeAccount) {
        this.ID = ID;
        this.node = node;
        this.chargeAccount = chargeAccount;
    }

    public void minusMoney(double money) {
		this.chargeAccount -= money;
	}
    
    public void addMoney(double money) {
    	this.chargeAccount += money;
    }
    
    public String getID() {
        return ID;
    }

    //get my node
    public Node getNode() {
        return this.node;
    }

    //get my node ID
    public String getNodeId() {
        return this.node.getID();
    }

    //get my fragment
    public Fragmentation getFragmentation(){
        return this.node.getFragmentation();
    }

    //get my fragment ID
    public String getFragmentationID(){
        return this.getFragmentation().getID();
    }

    //change node
    public void setNode(Node node){
    	this.node = node;
    }
    
    public double getChargeAccount() {
        return chargeAccount;
    }

    @Override
    public String toString() {
        return "User [ID=" + ID + ", nodeId=" + this.getNodeId() + ", chargeAccount=" + chargeAccount + "]";
    }

}
