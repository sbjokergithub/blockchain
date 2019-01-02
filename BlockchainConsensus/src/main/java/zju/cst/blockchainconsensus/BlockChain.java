package zju.cst.blockchainconsensus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

public class BlockChain {
	private Map<String, Fragmentation> fragmentationList;
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
			Trading trading = new Trading();
			for (int j = 0; j < 4; j++) {
				st.hasMoreElements();

				if (j == 0) {
					String sender = (String) st.nextElement();
					trading.setTransactionSender(sender);
				} else if (j == 1) {
					String acount = (String) st.nextElement();
					trading.setTransactionAmount(Double.parseDouble(acount));
				} else if (j == 2) {
					String receiver = (String) st.nextElement();
					trading.setTransactionReceiver(receiver);
				} else if (j == 3) {
					String time = (String) st.nextElement();
					st.hasMoreElements();
					time += " ";
					time += (String) st.nextElement();
					trading.setTime(Timestamp.valueOf(time));
				}
			}
			transaction.add(trading);
		}
		for (int i = 0; i < transaction.size(); i++) {
			System.out.println(transaction.get(i));
		}

	}
}
