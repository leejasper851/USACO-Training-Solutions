/*
NAME: leejasp1
LANG: JAVA
PROG: ditch
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section2NetworkFlow;

import java.io.*;
import java.util.*;

public class ditch {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("ditch.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numDitchs = Integer.parseInt(st.nextToken());
		int numInters = Integer.parseInt(st.nextToken());
		HashMap<Integer, Integer>[] ditchs = new HashMap[numInters];
		for (int i = 0; i < numInters; i++) {
			ditchs[i] = new HashMap<>();
		}
		for (int i = 0; i < numDitchs; i++) {
			st = new StringTokenizer(reader.readLine());
			int from = Integer.parseInt(st.nextToken())-1;
			int to = Integer.parseInt(st.nextToken())-1;
			int flow = Integer.parseInt(st.nextToken());
			if (ditchs[from].containsKey(to)) {
				ditchs[from].put(to, ditchs[from].get(to) + flow);
			} else {
				ditchs[from].put(to, flow);
			}
		}
		reader.close();
		
		int totalFlow = 0;
		while (true) {
			boolean[] visited = new boolean[numInters];
			int[] flow = new int[numInters];
			int[] par = new int[numInters];
			TreeSet<Integer> interSort = new TreeSet<>(new InterComp(flow));
			flow[0] = Integer.MAX_VALUE;
			par[0] = -1;
			interSort.add(0);
			while (!interSort.isEmpty()) {
				int curr = interSort.pollLast();
				if (flow[curr] == 0) {
					break;
				}
				visited[curr] = true;
				for (int adj : ditchs[curr].keySet()) {
					if (visited[adj]) {
						continue;
					}
					int currFlow = Math.min(flow[curr], ditchs[curr].get(adj));
					if (currFlow > flow[adj]) {
						flow[adj] = currFlow;
						par[adj] = curr;
						interSort.remove(adj);
						interSort.add(adj);
					}
				}
			}
			
			int minFlow = flow[numInters-1];
			if (minFlow == 0) {
				break;
			}
			totalFlow += minFlow;
			int curr = numInters-1;
			while (true) {
				int prev = par[curr];
				if (prev == -1) {
					break;
				}
				ditchs[prev].put(curr, ditchs[prev].get(curr) - minFlow);
				if (ditchs[prev].get(curr) == 0) {
					ditchs[prev].remove(curr);
				}
				if (ditchs[curr].containsKey(prev)) {
					ditchs[curr].put(prev, ditchs[curr].get(prev) + minFlow);
				} else {
					ditchs[curr].put(prev, minFlow);
				}
				curr = prev;
			}
		}
		writer.println(totalFlow);
		writer.close();
	}
	
	private static class InterComp implements Comparator<Integer> {
		public int[] flow;
		
		public InterComp(int[] f) {
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