/*
NAME: leejasp1
LANG: JAVA
PROG: fence6
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section1Optimization;

import java.io.*;
import java.util.*;

public class fence6 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fence6.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("fence6.out")));
		
		int numSegs = Integer.parseInt(reader.readLine());
		HashMap<HashSet<Integer>, Integer> nodes = new HashMap<>();
		int nodeInd = 0;
		HashSet<int[]> segs = new HashSet<>();
		
		for (int i = 0; i < numSegs; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int segNum = Integer.parseInt(st.nextToken());
			int segLen = Integer.parseInt(st.nextToken());
			int numCons1 = Integer.parseInt(st.nextToken());
			int numCons2 = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(reader.readLine());
			HashSet<Integer> cons1 = new HashSet<>();
			for (int j = 0; j < numCons1; j++) {
				cons1.add(Integer.parseInt(st.nextToken()));
			}
			cons1.add(segNum);
			int node1 = 0;
			if (nodes.containsKey(cons1)) {
				node1 = nodes.get(cons1);
			} else {
				nodes.put(cons1, nodeInd);
				node1 = nodeInd;
				nodeInd++;
			}
			
			st = new StringTokenizer(reader.readLine());
			HashSet<Integer> cons2 = new HashSet<>();
			for (int j = 0; j < numCons2; j++) {
				cons2.add(Integer.parseInt(st.nextToken()));
			}
			cons2.add(segNum);
			int node2 = 0;
			if (nodes.containsKey(cons2)) {
				node2 = nodes.get(cons2);
			} else {
				nodes.put(cons2, nodeInd);
				node2 = nodeInd;
				nodeInd++;
			}
			
			segs.add(new int[] {node1, node2, segLen});
		}
		reader.close();
		
		int[][] adjMat = new int[nodeInd][nodeInd];
		int[][] calcMat = new int[nodeInd][nodeInd];
		for (int i = 0; i < nodeInd; i++) {
			Arrays.fill(adjMat[i], 1000000);
			Arrays.fill(calcMat[i], 1000000);
		}
		for (int[] seg : segs) {
			int node1 = seg[0];
			int node2 = seg[1];
			int segLen = seg[2];
			if (segLen < adjMat[node1][node2]) {
				adjMat[node1][node2] = segLen;
				adjMat[node2][node1] = segLen;
				calcMat[node1][node2] = segLen;
				calcMat[node2][node1] = segLen;
			}
		}
		
		int minLoop = 1000000;
		for (int k = 0; k < nodeInd; k++) {
			for (int i = 0; i < nodeInd; i++) {
				for (int j = 0; j < nodeInd; j++) {
					if (i != j && j != k && i != k) {
						minLoop = Math.min(minLoop, calcMat[i][j] + adjMat[k][i] + adjMat[k][j]);
					}
				}
			}
			
			for (int i = 0; i < nodeInd; i++) {
				for (int j = 0; j < nodeInd; j++) {
					if (calcMat[i][k] + calcMat[k][j] < calcMat[i][j]) {
						calcMat[i][j] = calcMat[i][k] + calcMat[k][j];
					}
				}
			}
		}
		
		writer.println(minLoop);
		writer.close();
	}
}