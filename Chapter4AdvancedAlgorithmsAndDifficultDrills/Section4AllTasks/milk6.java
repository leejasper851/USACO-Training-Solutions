/*
NAME: leejasp1
LANG: JAVA
PROG: milk6
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section4AllTasks;

import java.io.*;
import java.util.*;

public class milk6 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("milk6.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("milk6.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numNodes = Integer.parseInt(st.nextToken());
		int numEdges = Integer.parseInt(st.nextToken());
		HashSet<Edge> edges = new HashSet<>();
		PriorityQueue<Edge> edgeSort = new PriorityQueue<>(new CostComp());
		for (int i = 0; i < numEdges; i++) {
			st = new StringTokenizer(reader.readLine());
			int from = Integer.parseInt(st.nextToken())-1;
			int to = Integer.parseInt(st.nextToken())-1;
			int cost = Integer.parseInt(st.nextToken());
			Edge edge = new Edge(from, to, cost, i);
			edges.add(edge);
			edgeSort.add(edge);
		}
		reader.close();
		
		ArrayList<Edge> rems = new ArrayList<>();
		int totalCost = 0;
		int currFlow = calcFlow(edges, numNodes);
		while (!edgeSort.isEmpty()) {
			Edge currEdge = edgeSort.remove();
			int from = currEdge.from;
			int cost = currEdge.cost;
			edges.remove(currEdge);
			int newFlow = calcFlow(edges, numNodes);
			if (newFlow < currFlow) {
				currFlow = newFlow;
				rems.add(currEdge);
				totalCost += cost;
				if (currFlow == 0) {
					break;
				}
				continue;
			}
			edges.add(currEdge);
		}
		
		Collections.sort(rems, new IdComp());
		writer.println(totalCost + " " + rems.size());
		for (Edge rem : rems) {
			writer.println(rem.id+1);
		}
		writer.close();
	}
	
	private static class Edge {
		public int from;
		public int to;
		public int cost;
		public int id;
		
		public Edge(int f, int t, int c, int i) {
			from = f;
			to = t;
			cost = c;
			id = i;
		}
		
		public boolean equals(Edge other) {
			return (id == other.id);
		}
	}
	
	private static class CostComp implements Comparator<Edge> {
		public int compare(Edge a, Edge b) {
			if (a.cost != b.cost) {
				return a.cost - b.cost;
			}
			return a.id - b.id;
		}
	}
	
	private static class IdComp implements Comparator<Edge> {
		public int compare(Edge a, Edge b) {
			return a.id - b.id;
		}
	}
	
	private static int calcFlow(HashSet<Edge> edges, int numNodes) {
		HashMap<Integer, Integer>[] testEdges = new HashMap[numNodes];
		for (int i = 0; i < numNodes; i++) {
			testEdges[i] = new HashMap<>();
		}
		for (Edge edge : edges) {
			int from = edge.from;
			int to = edge.to;
			if (testEdges[from].containsKey(to)) {
				testEdges[from].put(to, testEdges[from].get(to) + 1);
			} else {
				testEdges[from].put(to, 1);
			}
		}
		int totalFlow = 0;
		
		while (true) {
			boolean[] visited = new boolean[numNodes];
			int[] flow = new int[numNodes];
			int[] par = new int[numNodes];
			TreeSet<Integer> flowSort = new TreeSet(new FlowComp(flow));
			flow[0] = Integer.MAX_VALUE;
			flowSort.add(0);
			
			while (!flowSort.isEmpty()) {
				int curr = flowSort.pollLast();
				if (flow[curr] == 0 || curr == numNodes-1) {
					break;
				}
				visited[curr] = true;
				
				for (int adj : testEdges[curr].keySet()) {
					if (visited[adj]) {
						continue;
					}
					int adjFlow = Math.min(flow[curr], testEdges[curr].get(adj));
					if (adjFlow > flow[adj]) {
						flow[adj] = adjFlow;
						par[adj] = curr;
						flowSort.remove(adj);
						flowSort.add(adj);
					}
				}
			}
			
			int currFlow = flow[numNodes-1];
			if (currFlow == 0) {
				break;
			}
			totalFlow += currFlow;
			int curr = numNodes-1;
			while (true) {
				if (curr == 0) {
					break;
				}
				int prev = par[curr];
				testEdges[prev].put(curr, testEdges[prev].get(curr) - currFlow);
				if (testEdges[prev].get(curr) == 0) {
					testEdges[prev].remove(curr);
				}
				if (testEdges[curr].containsKey(prev)) {
					testEdges[curr].put(prev, testEdges[curr].get(prev) + currFlow);
				} else {
					testEdges[curr].put(prev, currFlow);
				}
				curr = prev;
			}
		}
		return totalFlow;
	}
	
	private static class FlowComp implements Comparator<Integer> {
		public int[] flow;
		
		public FlowComp(int[] f) {
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