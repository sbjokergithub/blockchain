package zju.cst.blockchainconsensus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Fragmentation {
	private String ID;
	public ArrayList<Node> nodeList;
	public ArrayList<User> userList;
	public ArrayList<Trading> transaction;
	public ArrayList<Trading> pbftTransaction;
	private Map<String, Integer> pos;
	private Node masterNode;
	private String masterNodeID;
	private Map<String, Double> localUserAccount;
	private Map<String, String> pbftTradeMap;
	private Map<String, Double> localPbftUserAccount;
	private Block localBlock;
	private int[][] graph;

	public Fragmentation(String ID, String nodeID) {
		this.ID = ID;
		this.masterNodeID = nodeID;
		this.userList = new ArrayList<User>();
		this.nodeList = new ArrayList<Node>();
		this.transaction = new ArrayList<Trading>();
		this.pbftTransaction = new ArrayList<Trading>();
		this.pos = new HashMap<String, Integer>();
		this.localUserAccount = new HashMap<String, Double>();
	}

	public Fragmentation(String ID) {
		this.ID = ID;
		this.masterNodeID = null;
		this.userList = new ArrayList<User>();
		this.nodeList = new ArrayList<Node>();
		this.transaction = new ArrayList<Trading>();
		this.pbftTransaction = new ArrayList<Trading>();
		this.pos = new HashMap<String, Integer>();
		this.localUserAccount = new HashMap<String, Double>();
	}

	public void generateBlcok() {
		this.localBlock = new Block(this.transaction);
	}

	public void merge(Fragmentation fragmentation) {
		ArrayList<Node> oldNode = fragmentation.nodeList;
		for (int i = 0; i < oldNode.size(); ++i) {
			Node node = oldNode.get(i);
			node.setFragment(this);
		}
		
		fragmentation = null;
	}

	public boolean pbftCommit() {
		ArrayList<String> values = new ArrayList<>();
		ArrayList<Integer> times = new ArrayList<>();
		for (Map.Entry<String, String> entry : pbftTradeMap.entrySet()) {
			String value = entry.getValue();
			if (!values.contains(value)) {
				values.add(value);
				times.add(1);
			} else {
				int index = values.indexOf(value);
				times.set(index, times.get(index) + 1);
			}
		}
		int max = 0;
		int n = (2 * BlockChain.fragmentationList.size()) / 3;
		for (int i = 0; i < times.size(); i++) {
			max = Math.max(times.get(i), max);
		}
		return max > n;
	}

	public String tradingProve() {
		String answer = "";
		this.pbftTradeMap = new HashMap<>();
		for (Map.Entry<String, Double> entry : BlockChain.pbftUserAccount.entrySet()) {
			this.localPbftUserAccount.put(entry.getKey(), entry.getValue());
		}
		for (int i = 0; i < pbftTransaction.size(); i++) {
			Trading trade = transaction.get(i);
			String sender = trade.getTransactionSender();
			double money = trade.getTransactionAmount();
			if (localPbftUserAccount.get(sender) < money) {
				pbftTransaction.remove(i--);
			} else {
				localPbftUserAccount.put(sender, localPbftUserAccount.get(sender) - money);
			}
		}
		answer = transactionHash();
		int senderDisID = Integer.valueOf(masterNodeID).intValue();
		for (Map.Entry<String, Fragmentation> entry : BlockChain.fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			fragmentation.pbftTradeMap.put(masterNodeID, answer);
			int recevierDisID = Integer.valueOf(fragmentation.getMasterNodeID()).intValue();
			try {
				Thread.sleep(BlockChain.dis[senderDisID][recevierDisID] * 2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return answer;
	}

	public String transactionHash() {
		String string = SHA(this.pbftTransaction.toString(), "SHA-256");
		return string;
	}

	private static String SHA(final String strText, final String strType) {
		String strResult = null;
		if (strText != null && strText.length() > 0) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				messageDigest.update(strText.getBytes());
				byte byteBuffer[] = messageDigest.digest();
				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}

	public void printUserAccount() {
		System.out.println(this.ID);
		for (Map.Entry<String, Double> entry : this.localUserAccount.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	public void setLocalAccount() {
		for (int i = 0; i < userList.size(); ++i) {
			User user = userList.get(i);
			this.localUserAccount.put(user.getID(), user.getChargeAccount());
		}
	}

	public void userConfirm() {
		for (int i = userList.size() - 1; i >= 0; --i) {
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

	// add user
	public void addUser(User user) {
		this.userList.add(user);

	}

	// add user
	public void deleteUser(User user) {
		this.userList.remove(user);

	}

	// add node
	public void addNode(Node node) {
		this.nodeList.add(node);
	}

	// add trading
	public void addTransaction(Trading trading) {
		this.transaction.add(trading);
	}

	// chose a best ordinary node to store a new user
	public Node bestNode() {
//    	nodeList=null;
		int min = nodeList.get(0).getUserNumber();
		int index = 0;
		for (int i = 1; i < nodeList.size(); i++) {
			if (nodeList.get(i).getUserNumber() < min) {
				min = nodeList.get(i).getUserNumber();
				index = i;
			}
		}

		return nodeList.get(index);
	}

	// How to select the representative nodes?
	public Node chooseMasterNode() {
		if (this.masterNode == null) {
			return nodeList.get(0);
		} else {
			for (int i = 0; i < nodeList.size(); ++i) {
				Node node = nodeList.get(i);
				if (masterNodeID.equals(node.getID()))
					return nodeList.get((i + 1) % nodeList.size());
			}
			return nodeList.get(0);
		}
	}

	// Select the billing node and return the ID of the billing node
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
		for (int k = 0; k < transaction.size(); k++) {
			Trading trade = transaction.get(k);
			String sender = trade.getTransactionSender();
			double money = trade.getTransactionAmount();
			if (localUserAccount.get(sender) < money) {
				transaction.remove(k--);
			} else {
				localUserAccount.put(sender, localUserAccount.get(sender) - money);
			}
		}
		return nodeList.get(i).getID();
	}

	// a fragmentation wants to fragmented
	public int tryFragment(int TPSpos) {
		if (nodeList.size() >= 6 && transaction.size() > TPSpos)
			return transaction.size();
		else
			return 0;
	}

	// implement fragment
	public ArrayList<Trading> implementation(Fragmentation newFragment) {
		int[] v = new int[userList.size()];
		boolean[] visited = new boolean[userList.size()];
		int[] wage = new int[userList.size()];
		int[] squ = new int[userList.size()];
		int minCut = 1000000;
		int r = 0;
		int index = 0;

		graph = new int[userList.size()][userList.size()];
		this.pos.clear();
		for (int t = 0; t < userList.size(); ++t) {
			User user = userList.get(t);
			this.pos.put(user.getID(), t);
		}
		for (int t1 = 0; t1 < userList.size(); ++t1)
			for (int t2 = 0; t2 < userList.size(); ++t2)
				graph[t1][t2] = 0;
		for (int t = 0; t < transaction.size(); ++t) {
			Trading trade = transaction.get(t);
			int t1 = this.pos.get(trade.getTransactionSender()).intValue();
			int t2 = this.pos.get(trade.getTransactionReceiver()).intValue();
			graph[t1][t2] += 1;
			graph[t2][t1] += 1;
		}
		for (int t = 0; t < userList.size(); ++t)
			v[t] = t;

		int n = userList.size();
		while (n > 1) {
			int pre = 0;
			for (int t = 0; t < n; ++t)
				visited[t] = false;
			for (int t = 0; t < n; ++t)
				wage[t] = 0;

			for (int i = 1; i < n; ++i) {
				int k = -1;
				for (int j = 1; j < n; ++j) {
					if (!visited[v[j]])
						wage[v[j]] += graph[v[pre]][v[j]];
					if (k == -1 || wage[v[k]] < wage[v[j]])
						k = j;
				}

				visited[v[k]] = true;
				if (i == n - 1) {
					int s = v[pre];
					int t = v[k];
					squ[r++] = t;
					if (wage[t] < minCut) {
						minCut = wage[t];
						index = r;
					}
					for (int j = 0; j < n; ++j) {
						graph[s][v[j]] += graph[v[j]][t];
						graph[v[j]][s] += graph[v[j]][t];
					}
					v[k] = v[--n];
				}

				pre = k;
			}
		}

		boolean[] userChange = new boolean[1000];
		for (int i = 0; i < userList.size(); ++i)
			userChange[i] = false;
		for (int i = index; i < userList.size(); ++i)
			userChange[squ[i]] = true;

		for (int i = this.nodeList.size() - 1; i > this.nodeList.size() / 2; --i) {
			Node node = this.nodeList.get(i);
			this.nodeList.remove(i);
			newFragment.addNode(node);
			node.setFragment(newFragment);
		}
		newFragment.setMasterNode(newFragment.chooseMasterNode());

		for (int i = userList.size() - 1; i >= 0; i--)
			if (userChange[i]) {
				User user = this.userList.get(i);
				this.userList.remove(i);
				newFragment.addUser(user);
				user.setNode(newFragment.bestNode());
			}

		ArrayList<Trading> outTrading = new ArrayList<Trading>();

		for (int i = this.transaction.size() - 1; i >= 0; ++i) {
			Trading trade = this.transaction.get(i);
			User user1 = trade.userGetSender();
			User user2 = trade.userGetReceiver();

			if (user1.getFragmentationID() == user2.getFragmentationID()) {
				if (user1.getFragmentationID() != this.ID) {
					this.transaction.remove(i);
					newFragment.addTransaction(trade);
				}
			} else {
				this.transaction.remove(i);
				outTrading.add(trade);
			}

		}

		return outTrading;
	}

}
