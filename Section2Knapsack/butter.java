/*
NAME: leejasp1
LANG: JAVA
PROG: butter
*/

package USACOTraining.Section2Knapsack;

import java.io.*;
import java.util.*;

public class butter {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("butter.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numCows = Integer.parseInt(st.nextToken());
		boolean test = (numCows == 35);
		int numPasts = Integer.parseInt(st.nextToken());
		int numPaths = Integer.parseInt(st.nextToken());
		int[] pastCows = new int[numPasts];
		for (int i = 0; i < numCows; i++) {
			pastCows[Integer.parseInt(reader.readLine())-1]++;
		}
		ArrayList<Integer>[] paths = new ArrayList[numPasts];
		ArrayList<Integer>[] pathLens = new ArrayList[numPasts];
		for (int i = 0; i < numPasts; i++) {
			paths[i] = new ArrayList<>();
			pathLens[i] = new ArrayList<>();
		}
		for (int i = 0; i < numPaths; i++) {
			st = new StringTokenizer(reader.readLine());
			int pastA = Integer.parseInt(st.nextToken())-1;
			int pastB = Integer.parseInt(st.nextToken())-1;
			int pathLen = Integer.parseInt(st.nextToken());
			paths[pastA].add(pastB);
			paths[pastB].add(pastA);
			pathLens[pastA].add(pathLen);
			pathLens[pastB].add(pathLen);
		}
		reader.close();
		
		int[][] adjMat = new int[numPasts][numPasts];
		for (int i = 0; i < numPasts - 1; i++) {
			dijkstras(i, paths, pathLens, adjMat);
		}
		
//		for (int[] row : adjMat) {
//			writer.println(Arrays.toString(row));
//		}
		
		int minLen = Integer.MAX_VALUE;
		for (int i = 0; i < numPasts; i++) {
			int totalLen = 0;
			for (int j = 0; j < numPasts; j++) {
				totalLen += pastCows[j] * adjMat[j][i];
			}
			if (totalLen < minLen) {
				minLen = totalLen;
			}
		}
		writer.println(minLen);
		writer.close();
	}
	
	private static void dijkstras(int past, ArrayList<Integer>[] paths, ArrayList<Integer>[] pathLens, int[][] adjMat) {
		int numPasts = paths.length;
		boolean[] visited = new boolean[numPasts];
		int[] dist = new int[numPasts];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[past] = 0;
		TreeSet<Integer> pasts = new TreeSet<>(new PastComp(dist));
		
		int currPast = past;
		while (true) {
			if (currPast > past) {
				adjMat[past][currPast] = dist[currPast];
				adjMat[currPast][past] = dist[currPast];
			}
			visited[currPast] = true;
			
			for (int i = 0; i < paths[currPast].size(); i++) {
				int adjPast = paths[currPast].get(i);
				int pathLen = pathLens[currPast].get(i);
				if (!visited[adjPast] && dist[currPast] + pathLen < dist[adjPast]) {
					pasts.remove(adjPast);
					dist[adjPast] = dist[currPast] + pathLen;
					pasts.add(adjPast);
				}
			}
			
			if (pasts.size() == 0) {
				break;
			}
			currPast = pasts.pollFirst();
		}
	}
	
	private static class PastComp implements Comparator<Integer> {
		public int[] dist;
		
		public PastComp(int[] d) {
			dist = d;
		}
		
		public int compare(Integer a, Integer b) {
			if (a == b) {
				return 0;
			}
			if (dist[a] == dist[b]) {
				return a - b;
			}
			return dist[a] - dist[b];
		}
	}
}