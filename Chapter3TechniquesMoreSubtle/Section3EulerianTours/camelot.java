/*
NAME: leejasp1
LANG: JAVA
PROG: camelot
*/

package Chapter3TechniquesMoreSubtle.Section3EulerianTours;

import java.io.*;
import java.util.*;

public class camelot {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("camelot.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("camelot.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		int numSqrs = numRows * numCols;
		HashMap<ArrayList<Integer>, Integer> sqrHash = new HashMap<>();
		ArrayList<Integer>[] sqrHashRev = new ArrayList[numSqrs];
		int hashVal = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				sqrHash.put(sqrFact(i, j), hashVal);
				sqrHashRev[hashVal] = sqrFact(i, j);
				hashVal++;
			}
		}
		st = new StringTokenizer(reader.readLine());
		int kingCol = st.nextToken().charAt(0) - 65;
		int kingRow = Integer.parseInt(st.nextToken())-1;
		int king = sqrHash.get(sqrFact(kingRow, kingCol));
		ArrayList<Integer> knts = new ArrayList<>();
		String line = reader.readLine();
		while (line != null && line.length() > 0) {
			st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				int kntCol = st.nextToken().charAt(0) - 65;
				int kntRow = Integer.parseInt(st.nextToken())-1;
				knts.add(sqrHash.get(sqrFact(kntRow, kntCol)));
			}
			line = reader.readLine();
		}
		reader.close();
		
		ArrayList<Integer>[] moves = new ArrayList[numSqrs];
		for (int i = 0; i < numSqrs; i++) {
			moves[i] = getMoves(sqrHashRev[i], sqrHash, numRows, numCols);
		}
		
		int[] kntCosts = new int[numSqrs];
		int[] kingCost = new int[numSqrs];
		Arrays.fill(kingCost, Integer.MAX_VALUE);
		for (int knt : knts) {
			kntBfs(knt, sqrHashRev, moves, king, kntCosts, kingCost);
		}
		
		if (knts.isEmpty()) {
			writer.println(0);
		} else {
			int minMoves = Integer.MAX_VALUE;
			for (int i = 0; i < numSqrs; i++) {
				minMoves = Math.min(minMoves, kntCosts[i] + kingCost[i]);
			}
			writer.println(minMoves);
		}
		writer.close();
	}
	
	private static ArrayList<Integer> sqrFact(int row, int col) {
		ArrayList<Integer> sqr = new ArrayList<Integer>();
		sqr.add(row);
		sqr.add(col);
		return sqr;
	}
	
	private static void kntBfs(int sqr, ArrayList<Integer>[] sqrHashRev, ArrayList<Integer>[] moves, int king, int[] kntCosts, int[] kingCost) {
		int numSqrs = sqrHashRev.length;
		int[][] dist = new int[numSqrs][2];
		for (int i = 0; i < numSqrs; i++) {
			dist[i][0] = Integer.MAX_VALUE;
			dist[i][1] = Integer.MAX_VALUE;
		}
		dist[sqr][0] = 0;
		dist[sqr][1] = kingMoves(king, sqr, sqrHashRev);
		LinkedList<Integer> queue = new LinkedList<>();
		LinkedList<Integer> kingQueue = new LinkedList<>();
		queue.add(sqr);
		kingQueue.add(0);
		queue.add(sqr);
		kingQueue.add(1);
		
		while (!queue.isEmpty()) {
			int currSqr = queue.remove();
			int hasKing = kingQueue.remove();
			
			for (int move : moves[currSqr]) {
				if (dist[currSqr][hasKing] + 1 < dist[move][hasKing]) {
					dist[move][hasKing] = dist[currSqr][hasKing] + 1;
					queue.add(move);
					kingQueue.add(hasKing);
				}
			}
			
			if (hasKing == 0) {
				int kingDist = dist[currSqr][0] + kingMoves(king, currSqr, sqrHashRev);
				if (kingDist < dist[currSqr][1]) {
					dist[currSqr][1] = kingDist;
					
					for (int move : moves[currSqr]) {
						if (dist[currSqr][1] + 1 < dist[move][1]) {
							dist[move][1] = dist[currSqr][1] + 1;
							queue.add(move);
							kingQueue.add(1);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < numSqrs; i++) {
			kingCost[i] = Math.min(kingCost[i], dist[i][1] - dist[i][0]);
			if (dist[i][0] == Integer.MAX_VALUE || kntCosts[i] == Integer.MAX_VALUE) {
				kntCosts[i] = Integer.MAX_VALUE;
			} else {
				kntCosts[i] += dist[i][0];
			}
		}
	}
	
	private static ArrayList<Integer> getMoves(ArrayList<Integer> sqr, HashMap<ArrayList<Integer>, Integer> sqrHash, int numRows, int numCols) {
		ArrayList<Integer> moves = new ArrayList<>();
		int[][] adjs = { {-1,2}, {1,2}, {-1,-2}, {1,-2}, {-2,1}, {-2,-1}, {2,1}, {2,-1} };
		for (int i = 0; i < adjs.length; i++) {
			int newRow = sqr.get(0) + adjs[i][0];
			int newCol = sqr.get(1) + adjs[i][1];
			if (newRow < 0 || newRow >= numRows || newCol < 0 || newCol >= numCols) {
				continue;
			}
			moves.add(sqrHash.get(sqrFact(newRow, newCol)));
		}
		return moves;
	}
	
	private static int kingMoves(int king, int goal, ArrayList<Integer>[] sqrHashRev) {
		ArrayList<Integer> kingArr = sqrHashRev[king];
		ArrayList<Integer> goalArr = sqrHashRev[goal];
		return Math.max(Math.abs(kingArr.get(0) - goalArr.get(0)), Math.abs(kingArr.get(1) - goalArr.get(1)));
	}
}