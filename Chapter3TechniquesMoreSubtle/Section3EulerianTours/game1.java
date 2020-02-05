/*
NAME: leejasp1
LANG: JAVA
PROG: game1
*/

package Chapter3TechniquesMoreSubtle.Section3EulerianTours;

import java.io.*;
import java.util.*;

public class game1 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("game1.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("game1.out")));
		
		int size = Integer.parseInt(reader.readLine());
		int[] board = new int[size];
		StringTokenizer st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < size; i++) {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(reader.readLine());
			}
			board[i] = Integer.parseInt(st.nextToken());
		}
		reader.close();
		
		int[][] maxScore = new int[size][size];
		int[][] otherScore = new int[size][size];
		for (int i = 0; i < size; i++) {
			maxScore[i][i] = board[i];
			otherScore[i][i] = 0;
		}
		for (int range = 2; range <= size; range++) {
			for (int startInd = 0; startInd < size - range + 1; startInd++) {
				int beg = startInd;
				int end = startInd + range - 1;
				int maxLeft = maxScore[beg][end - 1];
				int maxRight = maxScore[beg + 1][end];
				if (maxLeft > maxRight) {
					maxScore[beg][end] = board[beg] + otherScore[beg + 1][end];
					otherScore[beg][end] = maxScore[beg + 1][end];
				} else {
					maxScore[beg][end] = board[end] + otherScore[beg][end - 1];
					otherScore[beg][end] = maxScore[beg][end - 1];
				}
			}
		}
		writer.println(maxScore[0][size-1] + " " + otherScore[0][size-1]);
		writer.close();
	}
}