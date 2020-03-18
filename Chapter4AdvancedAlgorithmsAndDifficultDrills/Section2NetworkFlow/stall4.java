/*
NAME: leejasp1
LANG: JAVA
PROG: stall4
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section2NetworkFlow;

import java.io.*;
import java.util.*;

public class stall4 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("stall4.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("stall4.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numCows = Integer.parseInt(st.nextToken());
		int numStalls = Integer.parseInt(st.nextToken());
		int numNodes = numCows + numStalls + 2;
		HashMap<Integer, Integer>[] edges = new HashMap[numNodes];
		for (int i = 0; i < numNodes; i++) {
			edges[i] = new HashMap<>();
		}
		for (int i = 1; i <= numCows; i++) {
			edges[0].put(i, 1);
		}
		for (int i = numCows + 1; i < numNodes - 1; i++) {
			edges[i].put(numNodes-1, 1);
		}
		for (int i = 1; i <= numCows; i++) {
			st = new StringTokenizer(reader.readLine());
			int currStalls = Integer.parseInt(st.nextToken());
			for (int j = 0; j < currStalls; j++) {
				int stall = numCows + Integer.parseInt(st.nextToken());
				edges[i].put(stall, 1);
			}
		}
		reader.close();
		
		int totalFlow = 0;
		while (true) {
			boolean[] visited = new boolean[numNodes];
			int[] flow = new int[numNodes];
			int[] par = new int[numNodes];
			TreeSet<Integer> nodeSort = new TreeSet<>(new NodeComp(flow));
			flow[0] = Integer.MAX_VALUE;
			par[0] = -1;
			nodeSort.add(0);
			while (!nodeSort.isEmpty()) {
				int curr = nodeSort.pollLast();
				if (flow[curr] == 0) {
					break;
				}
				visited[curr] = true;
				
				for (int adj : edges[curr].keySet()) {
					if (visited[adj]) {
						continue;
					}
					int currFlow = Math.min(flow[curr], edges[curr].get(adj));
					if (currFlow > flow[adj]) {
						flow[adj] = currFlow;
						par[adj] = curr;
						nodeSort.remove(adj);
						nodeSort.add(adj);
					}
				}
			}
			
			int minFlow = flow[numNodes-1];
			if (minFlow == 0) {
				break;
			}
			totalFlow += minFlow;
			int curr = numNodes-1;
			while (true) {
				int prev = par[curr];
				if (prev == -1) {
					break;
				}
				edges[prev].put(curr, edges[prev].get(curr) - minFlow);
				if (edges[prev].get(curr) == 0) {
					edges[prev].remove(curr);
				}
				if (edges[curr].containsKey(prev)) {
					edges[curr].put(prev, edges[curr].get(prev) + minFlow);
				} else {
					edges[curr].put(prev, minFlow);
				}
				curr = prev;
			}
		}
		writer.println(totalFlow);
		writer.close();
	}
	
	private static class NodeComp implements Comparator<Integer> {
		public int[] flow;
		
		public NodeComp(int[] f) {
			flow = f;
		}
		
		public int compare(Integer a, Integer b) {
			if (flow[a] != flow[b]) {
				return flow[a] - flow[b];
			}
			return a - b;
		}
	}
}