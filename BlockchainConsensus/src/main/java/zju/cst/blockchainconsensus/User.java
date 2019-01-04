package zju.cst.blockchainconsensus;

public class User {
    private String ID;
    private String nodeId;
    private double chargeAccount;

    public User(String ID, String nodeId, double chargeAccount) {
        this.ID = ID;
        this.nodeId = nodeId;
        this.chargeAccount = chargeAccount;
    }

    public String getID() {
        return ID;
    }

    public String getNodeId() {
        return nodeId;
    }

    public double getChargeAccount() {
        return chargeAccount;
    }

    @Override
    public String toString() {
        return "User [ID=" + ID + ", nodeId=" + nodeId + ", chargeAccount=" + chargeAccount + "]";
    }

}
