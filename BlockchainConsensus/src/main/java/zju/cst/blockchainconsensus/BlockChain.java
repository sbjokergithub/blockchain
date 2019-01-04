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
    private static ArrayList<Trading> transaction;

    //Randomly generate validation groups£¿
    public ArrayList<String> generateValidationGroups() {

        return null;
    }

    //The reserved interface
    public void fragmentationManagement() {

    }

    //How is sharding managed£¿
    public Boolean adjust(String str) {
        return false;
    }

    //
    public static void main(String[] args) {

        Fragmentation fragmentation = new Fragmentation("F000");
        fragmentationList = new HashMap<String, Fragmentation>() {
            {
                put(fragmentation.getID(),fragmentation);
            }
        };

        //Reads transaction information from a text document
        ArrayList<String> arrayList = new ArrayList<String>();
        transaction = new ArrayList<Trading>();
        try {
            FileReader fr = new FileReader("C:/Users/73162/IdeaProjects/blockchain/tradingpool.txt");
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

            st.hasMoreElements();
            String checkNum = (String) st.nextElement();
            if (checkNum.equals("0")) {
                String[] userInfo = new String[5];
                for (int j = 0; j < 5; j++) {
                    st.hasMoreElements();
                    userInfo[j] = (String) st.nextElement();
                }
                User user = new User(userInfo[0], userInfo[1], Double.parseDouble(userInfo[2]));
            }
            if (checkNum.equals("1")) {
                String[] tradeInfo = new String[5];
                for (int j = 0; j < 5; j++) {
                    st.hasMoreElements();
                    tradeInfo[j] = (String) st.nextElement();
                }
                Timestamp tradeTime;
                tradeTime = Timestamp.valueOf(tradeInfo[3] + " " + tradeInfo[4]);
                Trading trading = new Trading(tradeInfo[0], Double.parseDouble(tradeInfo[1]), tradeInfo[2], tradeTime);
                transaction.add(trading);
            }
            if (checkNum.equals("2")) {
                String[] nodeInfo = new String[6];
                for (int j = 0; j < 6; j++) {
                    st.hasMoreElements();
                    nodeInfo[j] = (String) st.nextElement();
                }
                OrdinaryNode node = new OrdinaryNode(nodeInfo[0], Double.parseDouble(nodeInfo[1]), nodeInfo[2], Integer.parseInt(nodeInfo[3]));
            }
        }
        for (int i = 0; i < transaction.size(); i++) {
            System.out.println(transaction.get(i));
        }

    }
}
