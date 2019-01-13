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
    private static int consenseNum;
    private static int[][] dis;

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
    
    //find the number of new fragmentation
    public static int getNewFragmentationNumber() {
    	int j = 0;
    	Integer p;
    	while (true) {
    		p = j;
    		if (fragmentationList.get(p) == null) return j;
    		else j++;
    	}

    }
    
    //choose a fragment to split
//    public static int chooseFragmentation() {
//    	int n = 0, m = 0;
//    	for (Map.Entry<Integer, Fragmentation> entry : fragmentationList.entrySet()) { 
//    		int i = entry.getKey().intValue();
//    		Fragmentation fragment = entry.getValue();
//    		int TPSpos = 500;
//    		int s = fragment.tryFragment(TPSpos); 
//    		if (s > n) {n = s; m = i;}
//    	}
//    	if (n > 0) return m;
//    	else return -1;
//    }
    
    //generate a block
    public static void generateBlock() {
    	
    	
    	
    	//after generate a block, a fragmentation adjustment must be down
//    	adjustFragment();
    }
    
    //if the system can split fragment
    public static boolean splitFragment() {
    	return true;
    }
    
    //if the system should merge fragment
    public static boolean mergeFragment() {
    	return true;
    }
    
    //adjust fragment
//    public static void adjustFragment() {
//    	if (consenseNum == 10) {
//    		//fragmentation splitting and merging
//    		if (splitFragment()) {
//    			Integer k = chooseFragmentation();
//    			if (k.intValue() != -1) 
//    			{
//    			    int l = getNewFragmentationNumber();
//    	            Fragmentation newfragmentation = new Fragmentation(l);
//    	            fragmentationList.put(newfragmentation.getID(), newfragmentation);  
//    	        	Fragmentation fragmentation = fragmentationList.get(k);
//    	        	
//    	            ArrayList<Trading> newTransation = fragmentation.implementation(newfragmentation);
//    	            for (int i=0; i<newTransation.size(); ++i) transaction.add(newTransation.get(i));
//    	        }
//    		}
//    		else if (mergeFragment()) {
//    			
//    		}
//    		consenseNum = 0;
//    	}
//    	else {
//    		//Node join and quit
//    		
//    		consenseNum++;
//    	}
//    }
    
    //judge which is large, nowTime or blockTime 
    public static boolean timeOver(Timestamp nowTime, Timestamp blockTime) {
    	if(nowTime.after(blockTime)) return true;
    	return false;
    }
    
//    //add node, add user, add trading
//    public static void checkNum(StringTokenizer st) {
//        st.hasMoreElements();
//        String checkNum = (String) st.nextElement();
//        //add user
//        if (checkNum.equals("0")) {
//            String[] userInfo = new String[5];
//            for (int j = 0; j < 5; j++) {
//                st.hasMoreElements();
//                userInfo[j] = (String) st.nextElement();
//            }
//            Timestamp nowTime = Timestamp.valueOf(userInfo[3] + " " + userInfo[4]);
//            if (timeOver(nowTime,blockTime)) generateBlock();
//            
//            Fragmentation fragmentation = fragmentationList.get(Integer.parseInt(userInfo[1]));
//            OrdinaryNode node = (OrdinaryNode) fragmentation.bestNode();
//            User user = new User(userInfo[0], node, Double.parseDouble(userInfo[2]));
//            userList.put(user.getID(), user);
//            node.addUser(user);
//            fragmentation.addUser(user);
//        }
//        //add trading
//        if (checkNum.equals("1")) {
//            String[] tradeInfo = new String[5];
//            for (int k = 0; k < 5; k++) {
//                st.hasMoreElements();
//                tradeInfo[k] = (String) st.nextElement();
//            }
//            Timestamp nowTime = Timestamp.valueOf(tradeInfo[3] + " " + tradeInfo[4]);
//            if (timeOver(nowTime,blockTime)) generateBlock();
//            
//            Trading trading = new Trading(tradeInfo[0], Double.parseDouble(tradeInfo[1]), tradeInfo[2], nowTime);
//            transaction.add(trading);
//        }
//        //add node
//        if (checkNum.equals("2")) {
//            String[] nodeInfo = new String[6];
//            for (int j = 0; j < 6; j++) {
//                st.hasMoreElements();
//                nodeInfo[j] = (String) st.nextElement();
//            }
//            Timestamp nowTime = Timestamp.valueOf(nodeInfo[4] + " " + nodeInfo[5]);
//            if (timeOver(nowTime,blockTime)) generateBlock();
//            
//            Fragmentation fragmentation = fragmentationList.get(Integer.parseInt(nodeInfo[2]));
////            OrdinaryNode node = new OrdinaryNode(nodeInfo[0], Double.parseDouble(nodeInfo[1]), fragmentation, Integer.parseInt(nodeInfo[3]));
////            nodeList.put(node.getID(), node);
////            fragmentation.addNode(node);
//        }
//    }
    
  public static void Initialize() {
	  ArrayList<String> arrayList0 = new ArrayList<String>();
      ArrayList<String> arrayList1 = new ArrayList<String>();
      ArrayList<String> arrayList2 = new ArrayList<String>();
      ArrayList<String> arrayList3 = new ArrayList<String>();
      ArrayList<String> arrayList4 = new ArrayList<String>();
      try {
          FileReader fr0 = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
          BufferedReader bf0 = new BufferedReader(fr0);
          String str0;
          while ((str0 = bf0.readLine()) != null) arrayList0.add(str0);
          bf0.close();
          fr0.close();
          
          FileReader fr1 = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
          BufferedReader bf1 = new BufferedReader(fr1);
          String str1;
          while ((str1 = bf1.readLine()) != null) arrayList1.add(str1);
          bf1.close();
          fr1.close();
          
          FileReader fr2 = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
          BufferedReader bf2 = new BufferedReader(fr2);
          String str2;
          while ((str2 = bf2.readLine()) != null) arrayList2.add(str2);
          bf2.close();
          fr2.close();
          
          FileReader fr3 = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
          BufferedReader bf3 = new BufferedReader(fr3);
          String str3;
          while ((str3 = bf3.readLine()) != null) arrayList3.add(str3);
          bf3.close();
          fr3.close();
          
          FileReader fr4 = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
          BufferedReader bf4 = new BufferedReader(fr4);
          String str4;
          while ((str4 = bf4.readLine()) != null) arrayList4.add(str4);
          bf4.close();
          fr4.close();
          
      } catch (IOException e) {
          e.printStackTrace();
      }     
      
      for (int i = 0; i < arrayList0.size(); i++) {
          String s = arrayList0.get(i);
          StringTokenizer st = new StringTokenizer(s, ",!' '.;");
          String[] myInfo = new String[2];
          for (int j = 0; j < 2; j++) {
              st.hasMoreElements();
              myInfo[j] = (String) st.nextElement();
          }         
          Fragmentation fragmentation = new Fragmentation(myInfo[0],myInfo[1]);
          fragmentationList.put(myInfo[0],fragmentation);
      }

      for (int i = 0; i < arrayList1.size(); i++) {
          String s = arrayList1.get(i);
          StringTokenizer st = new StringTokenizer(s, ",!' '.;");
          String[] myInfo = new String[2];
          for (int j = 0; j < 2; j++) {
              st.hasMoreElements();
              myInfo[j] = (String) st.nextElement();
          }         
          Fragmentation fragmentation = fragmentationList.get(myInfo[1]);
		  if (myInfo[0].equals(fragmentation.getMasterNodeID())) {
        	  MasterNode node = new MasterNode(myInfo[0], fragmentation);
        	  fragmentation.setMasterNode(node);
        	  nodeList.put(myInfo[0],node);
          }
          else {
        	  OrdinaryNode node = new OrdinaryNode(myInfo[0], fragmentation);
        	  nodeList.put(myInfo[0],node);
          }
      }     
      
      for (int i = 0; i < arrayList2.size(); i++) {
          String s = arrayList2.get(i);
          StringTokenizer st = new StringTokenizer(s, ",!' '.;");
          String[] myInfo = new String[3];
          for (int j = 0; j < 3; j++) {
              st.hasMoreElements();
              myInfo[j] = (String) st.nextElement();
          }         
          Node node = nodeList.get(myInfo[1]);
          User user = new User(myInfo[0], node, Integer.parseInt(myInfo[2]));
          userList.put(myInfo[0], user);
      }    
      
      for (int i = 0; i < arrayList3.size(); i++) {
          String s = arrayList3.get(i);
          StringTokenizer st = new StringTokenizer(s, ",!' '.;");
          String[] myInfo = new String[3];
          for (int j = 0; j < 3; j++) {
              st.hasMoreElements();
              myInfo[j] = (String) st.nextElement();
          }         
          dis = new int[nodeList.size()][nodeList.size()];
          int k0 = Integer.parseInt(myInfo[0]);
          int k1 = Integer.parseInt(myInfo[1]);
          int k2 = Integer.parseInt(myInfo[2]);
          dis[k0][k1] = k2;
          dis[k1][k0] = k2;
      }         
      
      for (int i = 0; i < arrayList4.size(); i++) {
          String s = arrayList4.get(i);
          StringTokenizer st = new StringTokenizer(s, ",!' '.;");
          String[] myInfo = new String[5];
          for (int j = 0; j < 5; j++) {
              st.hasMoreElements();
              myInfo[j] = (String) st.nextElement();
          }         
          int money = Integer.parseInt(myInfo[2]);
          Timestamp time = Timestamp.valueOf(myInfo[3] + " " + myInfo[4]);
          Trading trade = new Trading(myInfo[0], money, myInfo[1], time);
          transaction.add(trade);
      }      
  }
    
    
  public static void main(String[] args) {  
      fragmentationList = new HashMap<String, Fragmentation>();
      nodeList = new HashMap<String, Node>();
      userList = new HashMap<String, User>();
      transaction = new ArrayList<Trading>();

      Initialize();
      
      
      
      
      
      

  }
  
    //
//    public static void main(String[] args) {
//        fragmentationList = new HashMap<Integer, Fragmentation>();
//        nodeList = new HashMap<String, Node>();
//        userList = new HashMap<String, User>();
//        blockTime = Timestamp.valueOf("2018-12-10 00:00:00");
//        consenseNum = 0;
//
//        Fragmentation fragmentation = new Fragmentation(0);
//        fragmentationList.put(fragmentation.getID(), fragmentation);
//
//        //Reads transaction information from a text document
//        ArrayList<String> arrayList = new ArrayList<String>();
//        transaction = new ArrayList<Trading>();
//        try {
//            FileReader fr = new FileReader("E:/blockchainconsensus/blockchain/tradingpool.txt");
//            BufferedReader bf = new BufferedReader(fr);
//            String str;
//            while ((str = bf.readLine()) != null) {
//                arrayList.add(str);
//            }
//            bf.close();
//            fr.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int length = arrayList.size();
//        for (int i = 0; i < length; i++) {
//            String s = arrayList.get(i);
//            StringTokenizer st = new StringTokenizer(s, ",!' '.;");
//
//            checkNum(st);
//
//        }
//
//        for (int i = 0; i < transaction.size(); i++) {
//
//            System.out.println(transaction.get(i));
//        }
//
//    }
}
