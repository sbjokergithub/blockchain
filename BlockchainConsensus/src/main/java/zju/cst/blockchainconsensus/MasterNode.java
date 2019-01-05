package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;

public class MasterNode extends Node {
    public MasterNode(String ID, double coinNumber, String fragmentationId, int load) {
        super(ID, coinNumber, fragmentationId, load);
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
        return null;
    }

    //Simulates executing transactions on blocks
    @Override
    public String simulationExecution() {
        return null;
    }

    //Broadcasts the packaged blocks to all representative nodes
    @Override
    public String broadcast() {
        return null;
    }

    //Simulates executing transactions on blocks
    @Override
    public void chargeAccount() {
    }

    //
    @Override
    public void Management() {
    }
}
