package zju.cst.blockchainconsensus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.Iterator;

import static javax.swing.UIManager.put;

//一个用户作为发送方所涉及的所有交易及其交易次数
class UserTx {
	public String userId;
	// 跨分片交易
	public Map<String, Integer> outTx; // 分片ID，交易次数
	public String outFragId; // 接收方所在交易分片ID
	public int maxTimes; // 最大跨片交易次数
	// 片内交易
	public String inFragId; // 发送方所在交易分片ID
	public int times; // 片内交易次数
	// public double proportion; //maxTimes/times
};

public class BlockChain {
	public static Map<String, Fragmentation> fragmentationList;
	private static Map<String, Node> nodeList;
	private static Map<String, User> userList;
	private static ArrayList<Trading> transaction;
	private static Map<String, Double> userAccount;
	public static Map<String, Double> pbftUserAccount;
	public static int[][] dis;
	private static Node pbftMasterNode;
	private static ArrayList<Trading> finalTransaction;// 主链最后交易池
	private static long timePOS, timePBFT;
	private static Map<String, String> pbftHashMap;
	public static Map<String, String> tradeHashMap;
	static int commit;
	public static Block masterBlock;
	private static Fragmentation pbftAnswerFragment;
	private static int[][] graph;

	// Randomly generate validation groups？
	public ArrayList<String> generateValidationGroups() {

		return null;
	}

	// The reserved interface
	public void fragmentationManagement() {

	}

	// How is shading managed？
	public Boolean adjust(String str) {
		return false;
	}

	// find the number of new fragmentation
	public static int getNewFragmentationNumber() {
		Integer p = 0;
		while (true) {
			String pString = p.toString();
			while (pString.length() < 3) pString = "0" + pString;
			if (fragmentationList.get(pString) == null)
				return p.intValue();
			else
				p = p + 1;
		}

	}

	// choose a fragment to split
	public static int chooseFragmentation() {
		int n = 0, m = 0;
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			int i = Integer.parseInt(entry.getKey());
			Fragmentation fragment = entry.getValue();
			int TPSpos = 50;
			int s = fragment.tryFragment(TPSpos);
			if (s > n) {
				n = s;
				m = i;
			}
		}
		if (n > 0)
			return m;
		else
			return -1;
	}

	// generate a block
	public static void generateBlock() {

		// after generate a block, a fragmentation adjustment must be down
//    	adjustFragment();
	}

	// if the system can split fragment
	public static boolean splitFragment() {
		if (timePOS > timePBFT)
			return true;
		else
			return false;
	}

	// if the system should merge fragment
	public static boolean mergeFragment() {
		if (timePOS < timePBFT)
			return true;
		else
			return false;
	}

	// adjust fragment
	public static void adjustFragment() {
		Integer k;
		if (splitFragment()) {
			k = chooseFragmentation();
			if (k.intValue() != -1) {
				int l = getNewFragmentationNumber();
				Fragmentation newfragmentation = new Fragmentation(String.valueOf(l));
				fragmentationList.put(newfragmentation.getID(), newfragmentation);
				String kString = k.toString();
				while (kString.length() < 3) kString = "0" + kString;
				Fragmentation fragmentation = fragmentationList.get(kString);

				ArrayList<Trading> newTransation = fragmentation.implementation(newfragmentation);
				for (int i = 0; i < newTransation.size(); ++i)
					transaction.add(newTransation.get(i));
			}
		} else if (mergeFragment()) {
			graph = new int[fragmentationList.size()][fragmentationList.size()];
			ArrayList<Trading> temptrade = masterBlock.getTransaction();
			for (int t = 0; t < temptrade.size(); ++t) {
				Trading trade = temptrade.get(t);
				int t1 = Integer.valueOf(userList.get(trade.getTransactionSender()).getFragmentationID()).intValue();
				int t2 = Integer.valueOf(userList.get(trade.getTransactionReceiver()).getFragmentationID()).intValue();
				graph[t1][t2] += 1;
				graph[t2][t1] += 1;
			}
			int max = 0;
			int maxi = -1;
			int maxj = -1;
			for (int i = 0; i < graph.length; i++) {
				for (int j = i + 1; j < graph.length; j++) {
					if (graph[i][j] > max) {
						max = graph[i][j];
						maxi = i;
						maxj = j;
					}
				}
			}
			String frag1, frag2;
			frag1 = String.valueOf(maxi);
			frag2 = String.valueOf(maxj);
			while (frag1.length() < 3) {
				frag1 = "0" + frag1;
			}
			while (frag2.length() < 3) {
				frag2 = "0" + frag2;
			}

			Fragmentation fragmentation = fragmentationList.get(frag1);
			fragmentation.merge(fragmentationList.get(frag2));

		}
	}

	// judge which is large, nowTime or blockTime
	public static boolean timeOver(Timestamp nowTime, Timestamp blockTime) {
		if (nowTime.after(blockTime))
			return true;
		return false;
	}

	public static void updateLocalUserAccount() {
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			fragmentation.setLocalAccount();
		}
	}

	public static void generateMasterChain() {
		ArrayList<String> keys = new ArrayList<>();
		ArrayList<Integer> times = new ArrayList<>();
		for (Map.Entry<String, String> entry : tradeHashMap.entrySet()) {
			String key = entry.getKey();
			if (!keys.contains(key)) {
				keys.add(key);
				times.add(1);
			} else {
				int index = keys.indexOf(key);
				times.set(index, times.get(index) + 1);
			}
		}
		int max = 0;
		for (int i = 0; i < times.size(); i++) {
			if (times.get(i) > max)
				max = times.get(i);
		}
		Node pbftAnswerNode = nodeList.get(keys.get(times.indexOf(max)));
		pbftAnswerFragment = pbftAnswerNode.getFragmentation();

		masterBlock = new Block(pbftAnswerFragment.pbftTransaction);
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragment = entry.getValue();
			masterBlock.addTransaction(fragment.transaction);
		}

		ArrayList<Trading> trades = masterBlock.getTransaction();
		for (int i = 0; i < trades.size(); ++i) {
			Trading trade = trades.get(i);
			User sender = userList.get(trade.getTransactionSender());
			User receiver = userList.get(trade.getTransactionReceiver());
			sender.minusMoney(trade.getTransactionAmount());
			receiver.addMoney(trade.getTransactionAmount());
		}

		for (Map.Entry<String, Double> entry : userAccount.entrySet()) {
			User user = userList.get(entry.getKey());
			entry.setValue(user.getChargeAccount());
		}

	}

	public static void generateLocalChain() {
		for (int i = 0; i < pbftAnswerFragment.pbftTransaction.size(); i++) {
			Trading trade = pbftAnswerFragment.pbftTransaction.get(i);
			User user = userList.get(trade.getTransactionSender());
			Fragmentation fragmentation = user.getFragmentation();
			fragmentation.transaction.add(trade);
			user = userList.get(trade.getTransactionReceiver());
			fragmentation = user.getFragmentation();
			fragmentation.transaction.add(trade);
		}

		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragment = entry.getValue();
			fragment.generateBlcok();
		}

	}

	public static void main(String[] args) {
		fragmentationList = new HashMap<String, Fragmentation>();
		nodeList = new HashMap<String, Node>();
		userList = new HashMap<String, User>();
		transaction = new ArrayList<Trading>();
		userAccount = new HashMap<String, Double>();
		pbftUserAccount = new HashMap<String, Double>();
		
		Initialize();
		System.out.println("1");
		dis = floydWarshall(dis);
		System.out.println("2");

		dropWrongTrade();
		updateLocalUserAccount();
		dropDoubleTrade();
		System.out.println("3");

		consense();
		System.out.println("4");

		generateMasterChain();
		generateLocalChain();
		System.out.println("5");

		adjustFragment();
		System.out.println("6");
		
		print1();

	}

	public static void print1() {
		masterBlock.print();

	}

	public static int[][] floydWarshall(int[][] road) {
		int n = road.length;

		for (int i = 0; i < road.length; i++) {
			for (int j = i + 1; j < n; j++) {
				if (road[i][j] == 0) {
					road[i][j] = 1000;
					road[j][i] = 1000;
				}
			}
		}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					road[i][j] = road[j][i] = Math.min(road[i][j], road[i][k] + road[k][j]);
				}
			}
		}
		return road;
	}

	public static void dropDoubleTrade() {
		ArrayList<Trading> tempTransaction = new ArrayList<Trading>();
		for (int i = 0; i < transaction.size(); ++i) {
			Trading trade = transaction.get(i);
			User sender = userList.get(trade.getTransactionSender());
			User receiver = userList.get(trade.getTransactionReceiver());
			if (sender.getFragmentationID().equals(receiver.getFragmentationID())) {
				Fragmentation fragmention = sender.getFragmentation();
				fragmention.addTransaction(trade);
				tempTransaction.add(trade);
				transaction.remove(i--);
			}
		}

		for (int i = 0; i < tempTransaction.size(); ++i) {
			Trading trade = tempTransaction.get(i);
			User sender = userList.get(trade.getTransactionSender());
			Double money = userAccount.get(sender.getID());
			if (money >= trade.getTransactionAmount()) {
				userAccount.put(sender.getID(), money - trade.getTransactionAmount());
			}
		}

		for (int i = 0; i < transaction.size(); ++i) {
			Trading trade = transaction.get(i);
			User sender = userList.get(trade.getTransactionSender());
			Double money = userAccount.get(sender.getID());
			if (money >= trade.getTransactionAmount()) {
				userAccount.put(sender.getID(), money - trade.getTransactionAmount());
			} else {
				transaction.remove(i--);
			}
		}

	}

	public static void dropWrongTrade() {
		for (int i = transaction.size() - 1; i >= 0; i--) {
			Trading trade = transaction.get(i);
			if (!userList.containsKey(trade.getTransactionSender())
					|| !userList.containsKey(trade.getTransactionReceiver())) {
				transaction.remove(i);
			} else {
				if (trade.getTransactionAmount() > userAccount.get(trade.getTransactionSender()))
					transaction.remove(i);
			}
		}

	}

	public static Node nextPbftMasterNode() {
		String nextPbftMaxterID = pbftMasterNode.getID();
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			if (Integer.valueOf(fragmentation.getMasterNodeID()).intValue() > Integer.valueOf(nextPbftMaxterID)
					.intValue()) {
				nextPbftMaxterID = fragmentation.getID();
				break;
			}
		}
		if (nextPbftMaxterID == pbftMasterNode.getID()) {
			nextPbftMaxterID = "-001";
			for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
				Fragmentation fragmentation = entry.getValue();
				if (Integer.valueOf(fragmentation.getMasterNodeID()).intValue() > Integer.valueOf(nextPbftMaxterID)
						.intValue()) {
					nextPbftMaxterID = fragmentation.getID();
					break;
				}
			}
		}
		return nodeList.get(nextPbftMaxterID);
	}

	public static boolean isPbftMasterTrust() {
		ArrayList<String> values = new ArrayList<>();
		ArrayList<Integer> times = new ArrayList<>();
		for (Map.Entry<String, String> entry : pbftHashMap.entrySet()) {
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
		int n = (2 * fragmentationList.size()) / 3;
		for (int i = 0; i < times.size(); i++) {
			max = Math.max(times.get(i), max);
		}
		return max > n;
	}

	public static void virtualSendTrade() {
		pbftHashMap = new HashMap<>();
		pbftMasterNode = nodeList.get("0000");
		int answer = 0;
		int senderDisID = Integer.valueOf(pbftMasterNode.getID()).intValue();
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			int receiverDisID = Integer.valueOf(fragmentation.getMasterNodeID()).intValue();
			answer = answer + dis[senderDisID][receiverDisID];
			pbftHashMap.put(fragmentation.getMasterNodeID(), fragmentation.transactionHash());
		}
		try {
			Thread.sleep(answer * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sendPbftTrade() {
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			for (int i = 0; i < transaction.size(); i++) {
				fragmentation.pbftTransaction.add(transaction.get(i));
			}
			fragmentation.tradingProve();
		}
	}

	public static void pbftVote() {
		tradeHashMap = new HashMap<>();
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			tradeHashMap.put(fragmentation.getMasterNodeID(), fragmentation.tradingProve());
		}
	}

	public static boolean commitVote() {
		commit = 0;
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			if (fragmentation.pbftCommit()) {
				commit++;
			}
		}
		int n = (2 * fragmentationList.size()) / 3;
		return commit > n;
	}

	public static void posCommit() {
		for (Map.Entry<String, Fragmentation> entry : fragmentationList.entrySet()) {
			Fragmentation fragmentation = entry.getValue();
			fragmentation.selectBillingNode();
		}
	}

	public static void consense() {
		long startTime = System.currentTimeMillis();
		sendPbftTrade();
		virtualSendTrade();
		while (!isPbftMasterTrust()) {
			pbftMasterNode = nextPbftMasterNode();
			virtualSendTrade();
		}

		pbftVote();
		commitVote();
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		timePBFT = endTime - startTime;

		posCommit();
	}

	public void adjustUsers() {
		HashMap<String, String> usersNeedChange = new HashMap<String, String>(); // 需要动态加入或退出的用户 <用户ID，需要加入的分片ID>
		ArrayList<UserTx> table = new ArrayList<UserTx>();
		for (int i = 0; i < finalTransaction.size(); i++) {
			int flag = 0;
			String sender = finalTransaction.get(i).getTransactionSender(); // 记录当前交易的发送者ID
			for (int j = 0; j < table.size(); j++) // 遍历用户交易表，判断此用户是否已经存在
			{
				if (table.get(j).userId.equals(sender)) // 用户已存在
				{
					flag = 1;
					String fragmentationID_sender = userList.get(sender).getFragmentationID(); // 发送方所在分片ID
					String fragmentationID_receiver = userList.get(finalTransaction.get(i).getTransactionReceiver())
							.getFragmentationID(); // 接收方所在分片ID
					if (fragmentationID_sender.equals(fragmentationID_receiver)) // 片内交易
					{
						table.get(j).times++;// 片内交易次数加1
					}
					// 片外交易
					else {
						String outFragId = fragmentationID_receiver; // 记录分片ID
						if (table.get(j).outTx.get(outFragId) == null) {
							table.get(j).outTx.put(outFragId, 1); // 不存在此分片，交易次数记为1
						} else {
							table.get(j).outTx.put(outFragId, table.get(j).outTx.get(outFragId) + 1);// 已存在此分片，交易次数加1
						}
					}
					break;
				}
			}
			if (flag == 0) // 不存在此用户
			{
				UserTx temp = new UserTx(); // 新建一个用户
				temp.userId = finalTransaction.get(i).getTransactionSender(); // 记录用户ID
				String fragmentationID_sender = userList.get(temp.userId).getFragmentationID(); // 发送方所在分片ID
				String fragmentationID_receiver = userList.get(finalTransaction.get(i).getTransactionReceiver())
						.getFragmentationID(); // 接收方所在分片ID
				temp.inFragId = fragmentationID_sender; // 记录用户所在分片
				if (fragmentationID_sender.equals(fragmentationID_receiver)) // 如果是片内交易
				{
					temp.times++; // 片内交易次数+1
				} else // 片外交易
				{
					String outFragId = fragmentationID_receiver; // 记录分片ID
					temp.outTx.put(outFragId, 1);// 片外交易次数初始化为1
				}
				table.add(temp);// 将初始化好的用户加入表中
			}
		}
		for (int i = 0; i < table.size(); i++) // 遍历用户表
		{
			int max_times = 0;
			String max_ID = null;
			for (Map.Entry<String, Integer> entry : table.get(i).outTx.entrySet()) // 遍历跨分片map
			{
				if (entry.getValue() > max_times) {
					max_times = entry.getValue(); // 记录最大跨分片交易次数
					max_ID = entry.getKey();// 记录跨分片ID
				}
			}
			table.get(i).outFragId = max_ID;
			table.get(i).maxTimes = max_times;
			// table.get(i).proportion = table.get(i).maxTimes/table.get(i).times;
			if (table.get(i).maxTimes > table.get(i).times) // 如果一个用户的跨片交易多于片内交易，则把其放入需要列表中
			{
				usersNeedChange.put(table.get(i).userId, table.get(i).outFragId);
			}
		}
		for (Map.Entry<String, String> entry : usersNeedChange.entrySet()) // 遍历需要调整的用户列表
		{
			Fragmentation fragmentation = fragmentationList.get(entry.getValue());
			int min = fragmentation.nodeList.get(0).getUserNumber();
			int index = 0;
			for (int i = 1; i < fragmentation.nodeList.size(); i++) // 利用负载均衡，用户选择分片内的一个节点加入
			{
				if (fragmentation.nodeList.get(i).getUserNumber() < min) {
					min = fragmentation.nodeList.get(i).getUserNumber();
					index = i;
				}
			}
			userList.get(entry.getKey()).node = fragmentation.nodeList.get(index); // 改变用户所属的节点
			fragmentation.nodeList.get(index).addUser(userList.get(entry.getKey())); // 节点加入此用户
			fragmentation.nodeList.get(index).deleteUser(userList.get(entry.getKey())); // 节点删除此用户
			fragmentation.addUser(userList.get(entry.getKey()));// 分片加入此用户
			fragmentation.deleteUser(userList.get(entry.getKey()));// 分片删除此用户
		}
//		for (int i = 0; i < fragmentationList.size(); i++) // 打印出所有分片有哪些用户
//		{
//			System.out.println(fragmentationList.get(i));
//			for (int j = 0; j < fragmentationList.get(i).userList.size(); j++) {
//				System.out.println(fragmentationList.get(i).userList.get(j));
//			}
//		}
	}

	public static void Initialize() {
		ArrayList<String> arrayList0 = new ArrayList<String>();
		ArrayList<String> arrayList1 = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		ArrayList<String> arrayList3 = new ArrayList<String>();
		ArrayList<String> arrayList4 = new ArrayList<String>();
		try {
			FileReader fr0 = new FileReader("C:/Users/73162/git/blockchain/data/Table0.txt");
			BufferedReader bf0 = new BufferedReader(fr0);
			String str0;
			while ((str0 = bf0.readLine()) != null)
				arrayList0.add(str0);
			bf0.close();
			fr0.close();

			FileReader fr1 = new FileReader("C:/Users/73162/git/blockchain/data/Table1.txt");
			BufferedReader bf1 = new BufferedReader(fr1);
			String str1;
			while ((str1 = bf1.readLine()) != null)
				arrayList1.add(str1);
			bf1.close();
			fr1.close();

			FileReader fr2 = new FileReader("C:/Users/73162/git/blockchain/data/Table2.txt");
			BufferedReader bf2 = new BufferedReader(fr2);
			String str2;
			while ((str2 = bf2.readLine()) != null)
				arrayList2.add(str2);
			bf2.close();
			fr2.close();

			FileReader fr3 = new FileReader("C:/Users/73162/git/blockchain/data/Table3.txt");
			BufferedReader bf3 = new BufferedReader(fr3);
			String str3;
			while ((str3 = bf3.readLine()) != null)
				arrayList3.add(str3);
			bf3.close();
			fr3.close();

			FileReader fr4 = new FileReader("C:/Users/73162/git/blockchain/data/trading.txt");
			BufferedReader bf4 = new BufferedReader(fr4);
			String str4;
			while ((str4 = bf4.readLine()) != null)
				arrayList4.add(str4);
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
			Fragmentation fragmentation = new Fragmentation(myInfo[0], myInfo[1]);
			fragmentationList.put(myInfo[0], fragmentation);
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
				nodeList.put(myInfo[0], node);
				fragmentation.addNode(node);
			} else {
				OrdinaryNode node = new OrdinaryNode(myInfo[0], fragmentation);
				nodeList.put(myInfo[0], node);
				fragmentation.addNode(node);
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
			User user = new User(myInfo[0], node, Double.parseDouble(myInfo[2]));
			userList.put(myInfo[0], user);
			userAccount.put(myInfo[0], Double.parseDouble(myInfo[2]));
			pbftUserAccount.put(myInfo[0], Double.parseDouble(myInfo[2]));
			node.addUser(user);
			Fragmentation fragmentation = node.getFragmentation();
			fragmentation.addUser(user);
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

}
