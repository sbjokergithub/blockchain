package zju.cst.blockchainconsensus;

public class minRode {
	// 用来记录路径值
	static int[][] road;

	// 随机产生路径值，后面设计路径也在这里
	public static int[][] randomRoad(int n) {
		road = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int rand = (int) (24 * Math.random()) + 1;
				road[i][j] = rand;
				road[j][i] = rand;
			}
		}
		return null;
	}

	// 打印邻接矩阵
	public static void printRoad(int[][] road) {
		for (int i = 0; i < road.length; i++) {
			for (int j = 0; j < road.length; j++) {
				System.out.print(road[i][j] + ",");
			}
			System.out.println();
		}
		System.out.println();
	}

	// 计算最短路径的三重循环
	public static int[][] floydWarshall(int[][] road) {
		int n = road.length;
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					// 这里采取的是加入中间点K的做法，因此不用一次循环
					road[i][j] = road[j][i] = Math.min(road[i][j], road[i][k] + road[k][j]);
				}
			}
			printRoad(road);
		}
		return road;
	}

	public static void main(String[] args) {
		int n = 5;
		randomRoad(n);
		printRoad(road);
		floydWarshall(road);
	}
}
