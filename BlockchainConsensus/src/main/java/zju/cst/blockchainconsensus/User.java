package zju.cst.blockchainconsensus;

public class User {
	private String ID;
	private String nodeId;
	private double chargeAccount;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public double getChargeAccount() {
		return chargeAccount;
	}
	public void setChargeAccount(double chargeAccount) {
		this.chargeAccount = chargeAccount;
	}
	@Override
	public String toString() {
		return "User [ID=" + ID + ", nodeId=" + nodeId + ", chargeAccount=" + chargeAccount + "]";
	}
	
}
