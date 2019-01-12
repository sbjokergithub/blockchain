package zju.cst.blockchainconsensus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static javax.swing.UIManager.put;

public class BlockChain {
    private static Map<String, Fragmentation> fragmentationList;
    private static Map<String, Node> nodeList;
    private static Map<String, User> userList;
    private static ArrayList<Trading> transaction;
    private static Timestamp blockTime;

    //Randomly generate validation groups£¿
    public ArrayList<String> generateValidationGroups() {

        return null;
    }

    //The reserved interface
    public void fragmentationManagement() {

    }

    //How is shading managed£¿
    public Boolean adjust(String str) {
        return false;
    }
    
    //generate a block
    public static void generateBlock() {
    	
    }
    
    //judge which is large, nowTime or blockTime 
    public static boolean timeOver(Timestamp nowTime, Timestamp blockTime) {
    	if(nowTime.after(blockTime)) return true;
    	return false;
    }
    
    //add node, add user, add trading
    public static void checkNum(StringTokenizer st) {
        st.hasMoreElements();
        String checkNum = (String) st.nextElement();
        //add user
        if (checkNum.equals("0")) {
            String[] userInfo = new String[5];
            for (int j = 0; j < 5; j++) {
                st.hasMoreElements();
                userInfo[j] = (String) st.nextElement();
            }
            Timestamp nowTime = Timestamp.valueOf(userInfo[3] + " " + userInfo[4]);
            if (timeOver(nowTime,blockTime)) generateBlock();
            Fragmentation fragmentation = fragmentationList.get(userInfo[1]);
            OrdinaryNode node = (OrdinaryNode) fragmentation.bestNode();
            User user = new User(userInfo[0], node, Double.parseDouble(userInfo[2]));
            userList.put(user.getID(), user);
            node.addUser(user);
            fragmentation.addUser(user);
        }
        //add trading
        if (checkNum.equals("1")) {
            String[] tradeInfo = new String[5];
            for (int k = 0; k < 5; k++) {
                st.hasMoreElements();
                tradeInfo[k] = (String) st.nextElement();
            }
            Timestamp nowTime = Timestamp.valueOf(tradeInfo[3] + " " + tradeInfo[4]);
            Trading trading = new Trading(tradeInfo[0], Double.parseDouble(tradeInfo[1]), tradeInfo[2], nowTime);
            transaction.add(trading);
        }
        //add node
        if (checkNum.equals("2")) {
            String[] nodeInfo = new String[6];
            for (int j = 0; j < 6; j++) {
                st.hasMoreElements();
                nodeInfo[j] = (String) st.nextElement();
            }
            Timestamp nowTime = Timestamp.valueOf(nodeInfo[4] + " " + nodeInfo[5]);
            Fragmentation fragmentation = fragmentationList.get(nodeInfo[2]);
            OrdinaryNode node = new OrdinaryNode(nodeInfo[0], Double.parseDouble(nodeInfo[1]), fragmentation, Integer.parseInt(nodeInfo[3]));
            nodeList.put(node.getID(), node);
            fragmentation.addNode(node);
        }
    }

    //
    public static void main(String[] args) {
        fragmentationList = new HashMap<String, Fragmentation>();
        nodeList = new HashMap<String, Node>();
        userList = new HashMap<String, User>();
        blockTime = Timestamp.valueOf("2018-12-10 00:00:00");

        Fragmentation fragmentation = new Fragmentation("F000");
        fragmentationList.put(fragmentation.getID(), fragmentation);

        //Reads transaction information from a text document
        ArrayList<String> arrayList = new ArrayList<String>();
        transaction = new ArrayList<Trading>();
        try {
            FileReader fr = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int length = arrayList.size();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            StringTokenizer st = new StringTokenizer(s, ",!' '.;");

            checkNum(st);

        }

        for (int i = 0; i < transaction.size(); i++) {

            System.out.println(transaction.get(i));
        }

    }
}
