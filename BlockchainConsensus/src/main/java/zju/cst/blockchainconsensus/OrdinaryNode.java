package zju.cst.blockchainconsensus;

import java.util.ArrayList;

public class OrdinaryNode extends Node {
	@Override
	public ArrayList getUserExtraList() {
		return userExtraList;
	}
	@Override
	public void setUserExtraList(ArrayList userExtraList) {
		this.userExtraList = userExtraList;
	}
	@Override
	public ArrayList<Trading> packageBlock() {
		return null;
	}
	@Override
	public String simulationExecution() {
		return null;
	}
	@Override
	public String broadcast() {
		return null;
	}
	@Override
	public void chargeAccount() {
	}
	@Override
	public void Management() {
	}
}
