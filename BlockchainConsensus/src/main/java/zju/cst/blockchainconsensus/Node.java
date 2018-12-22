package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public abstract class Node {
	public String ID;
	public ArrayList<Trading> block;
	public double coinNumber;
	public String fragmentationId;
	public int load;
	public ArrayList userExtraList;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public ArrayList<Trading> getBlock() {
		return block;
	}
	public void setBlock(ArrayList<Trading> block) {
		this.block = block;
	}
	public double getCoinNumber() {
		return coinNumber;
	}
	public void setCoinNumber(double coinNumber) {
		this.coinNumber = coinNumber;
	}
	public String getFragmentationId() {
		return fragmentationId;
	}
	public void setFragmentationId(String fragmentationId) {
		this.fragmentationId = fragmentationId;
	}
	public int getLoad() {
		return load;
	}
	public void setLoad(int load) {
		this.load = load;
	}
	public ArrayList getUserExtraList() {
		return userExtraList;
	}
	public void setUserExtraList(ArrayList userExtraList) {
		this.userExtraList = userExtraList;
	}
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
				+ fragmentationId + ", load=" + load + ", userExtraList=" + userExtraList + "]";
	}
	
}
