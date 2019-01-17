package zju.cst.blockchainconsensus;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Fragmentation {
    private String ID;
    private ArrayList<Node> nodeList;
    private ArrayList<User> userList;
    private ArrayList<Trading> transaction;
    private Map<String, Integer> pos;
    private Node masterNode;
    private String masterNodeID;
<<<<<<< HEAD
    private Node posNode ;
=======
	private Map<String, Double> localUserAccount;
>>>>>>> c92d71ee602d82054007dacd34d10ad668f63268

    public Fragmentation(String ID, String nodeID) {
        this.ID = ID;
        this.masterNodeID = nodeID;
        this.userList = new ArrayList<User>();
        this.nodeList = new ArrayList<Node>();
        this.transaction = new ArrayList<Trading>();
        this.pos = new HashMap<String, Integer>();
        this.localUserAccount = new HashMap<String, Double>();
    }

    public void printUserAccount(){
    	System.out.println(this.ID);	
		for (Map.Entry<String, Double> entry : this.localUserAccount.entrySet()) { 
			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			}
    }
    
    public void setLocalAccount() {
    	for (int i=0; i<userList.size(); ++i) {
    		User user = userList.get(i);
    		this.localUserAccount.put(user.getID(), user.getChargeAccount());
    	}
    }
    
    public void userConfirm() {
    	for (int i = userList.size()-1; i>=0; --i) {
    		User user = userList.get(i);
    		if (user.getFragmentationID() != this.ID) {
    			userList.remove(i);
    		}
    	}
    }
    
    public String getMasterNodeID() {
    	return masterNodeID;
    }
    
    public void setMasterNode(Node node) {
    	this.masterNode = node;
    }
    
    public String getID() {
        return ID;
    }

    //add user
    public void addUser(User user) {
        this.userList.add(user);
        
    }

    //add node
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    //add trading
    public void addTransaction(Trading trading) {
        this.transaction.add(trading);
    }

    //chose a best ordinary node to store a new user
    public Node bestNode()
    {
//    	nodeList=null;
    	int min=nodeList.get(0).getUserNumber();
    	int index=0;
    	for(int i=1;i<nodeList.size();i++){
    		if(nodeList.get(i).getUserNumber()<min){
    			min=nodeList.get(i).getUserNumber();
    			index=i;
    		}
    	}
    	
		return nodeList.get(index);
    }

    //How to select the representative nodes?
    public Node chooseMasterNode() {
        return null;
    }

    //Select the billing node and return the ID of the billing node
    public String selectBillingNode() {
        int target = 123;
        int i;
        while (true) {
            for (i = 0; i < nodeList.size(); i++) {
                int random = (int) (1 + Math.random() * (100));
                if (random < target * nodeList.get(i).getCoinNumber()) {
                    break;
                }
            }
            if (i < nodeList.size()) {
                break;
            }
        }
        return nodeList.get(i).getID();
    }
    
    //a fragmentation wants to fragmented 
    public int tryFragment(int TPSpos) {
    	if (nodeList.size()>=6 && transaction.size()>TPSpos)
    		return transaction.size();
    	else return 0;
    }
    
    //implement fragment
    public ArrayList<Trading> implementation(Fragmentation newFragment) {
    	int[] v = new int[1000];
    	boolean[] visited = new boolean[1000];
    	int[][] graph = new int[1000][1000];
    	int[] wage = new int[1000];
    	int[] squ = new int[1000];
    	int minCut = 1000000;
    	int r = 0;
    	int index = 0;
    	
    	this.pos.clear();
    	for (int t = 0; t < userList.size(); ++t) {
    		User user = userList.get(t);
    		this.pos.put(user.getID(), t);
    	}
    	for (int t1 = 0; t1 < userList.size(); ++t1)
    		for (int t2 = 0; t2 < userList.size(); ++t2)
    			graph[t1][t2] = 0;
    	for (int t = 0; t < transaction.size(); ++t){
    		Trading trade = transaction.get(t);
    		int t1 = this.pos.get(trade.getTransactionSender()).intValue();
    		int t2 = this.pos.get(trade.getTransactionReceiver()).intValue();
    		graph[t1][t2] += 1;
    		graph[t2][t1] += 1;
    	}
        for (int t = 0; t < userList.size(); ++t) v[t] = t;        
        
    	int n = userList.size();
        while (n>1)
        {
        	int pre = 0;
        	for (int t = 0; t < n; ++t) visited[t] = false;
        	for (int t = 0; t < n; ++t) wage[t] = 0;
        	
        	for (int i = 1; i < n; ++i) {
        		int k = -1;
        		for (int j = 1; j < n; ++j) {
        			if (!visited[v[j]]) wage[v[j]] += graph[v[pre]][v[j]];
                    if (k == -1 || wage[v[k]] < wage[v[j]]) k = j;        			
        		}
        		
        		visited[v[k]] = true;
        		if (i == n - 1) 
        		{
        			int s = v[pre];
        			int t = v[k];
        			squ[r++]=t;
                    if(wage[t]<minCut)
                    {
                        minCut=wage[t];
                        index=r;
                    }
                    for (int j = 0; j < n; ++j)      
                    {
                        graph[s][v[j]] += graph[v[j]][t];
                        graph[v[j]][s] += graph[v[j]][t];
                    }
                    v[k] = v[--n]; 	
        		}
        		
        		pre = k;       		
        	}       	
        }
        
        boolean[] userChange = new boolean[1000];
    	for (int i = 0; i < userList.size(); ++i) userChange[i] = false;
        for (int i = index; i < userList.size(); ++i) userChange[squ[i]] = true;
        
        Node node = this.nodeList.get(0);
        this.nodeList.remove(0);
        newFragment.addNode(node);
        node.setFragment(newFragment);
        
        for (int i=userList.size()-1; i>=0; i--) 
        if (userChange[i])
        {
        	User user = this.userList.get(i);        	
        	this.userList.remove(i);
        	newFragment.addUser(user);
        	user.setNode(newFragment.bestNode());
        }
        
        ArrayList<Trading> outTrading = new ArrayList<Trading>();
        
        for (int i=this.transaction.size()-1; i>=0; ++i) {
        	Trading trade = this.transaction.get(i);
        	User user1 = trade.userGetSender();
        	User user2 = trade.userGetReceiver();
        	
        	if (user1.getFragmentationID() == user2.getFragmentationID()) {
        		if (user1.getFragmentationID() != this.ID){
        			this.transaction.remove(i);
        			newFragment.addTransaction(trade);
        		}        		
        	}
        	else {
        		this.transaction.remove(i);
        		outTrading.add(trade);
        	}
        	
        }
        
        return outTrading;
    }

}
