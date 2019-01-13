package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;

public abstract class Node {
    public String ID;
    public ArrayList<Trading> block;
    public double coinNumber;
    public Fragmentation fragmentation;
    public int load;
    public Map<String, Double> userExtraList;
    public ArrayList<User> myUser;

    public Node(String ID, Fragmentation fragmentation) {
        this.ID = ID;
        this.fragmentation = fragmentation;
        this.myUser = new ArrayList<User>();
    }

    public String getID() {
        return ID;
    }

    public ArrayList<Trading> getBlock() {
        return this.block;
    }

    public void addUser(User user) {
        this.myUser.add(user);
    }

    //the user number in this node
    public int getUserNumber() {
    	return this.myUser.size();
    }
    
    public double getCoinNumber() {
        return coinNumber;
    }

    public Fragmentation getFragmentation() {
        return this.fragmentation;
    }

    public String getFragmentationID() {
        return this.fragmentation.getID();
    }
    
    public void setFragment(Fragmentation newfragment) {
    	this.fragmentation = newfragment;
    }

    public int getLoad() {
        return load;
    }

    public Map<String, Double> getUserExtraList() {
        return userExtraList;
    }

    public void setUserExtraList(Map<String, Double> userExtraList) {this.userExtraList=userExtraList;}

    public ArrayList<Trading> packageBlock() {
        return null;
    }

    public String simulationExecution() {
        return null;
    }

    public String broadcast() {
        return null;
    }

    public void chargeAccount() {
    }

    public void Management() {
    }

    @Override
    public String toString() {
        return "Node [ID=" + ID + ", block=" + block + ", coinNumber=" + coinNumber + ", fragmentationId="
                + this.getFragmentationID() + ", load=" + load + ", userExtraList=" + userExtraList + "]";
    }

}
