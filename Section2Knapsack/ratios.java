/*
NAME: leejasp1
LANG: JAVA
PROG: ratios
*/

package USACOTraining.Section2Knapsack;

import java.io.*;
import java.util.*;

public class ratios {
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("ratios.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
		/*
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		*/
		int[] goal = new int[3];
		StringTokenizer st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 3; i++) {
			goal[i] = Integer.parseInt(st.nextToken());
		}
		int[][] mixes = new int[3][3];
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 3; j++) {
				mixes[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		reader.close();
		
		int minSum = Integer.MAX_VALUE;
		int[] units = new int[4];
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				for (int k = 0; k < 100; k++) {
					if (i == 0 && j == 0 && k == 0) {
						continue;
					}
					if (multEqual(i, j, k, mixes, goal) && i + j + k < minSum) {
						minSum = i + j + k;
						units[0] = i;
						units[1] = j;
						units[2] = k;
						units[3] = (mixes[0][0] * i + mixes[1][0] * j + mixes[2][0] * k) / goal[0];
					}
				}
			}
		}
		
		if (minSum == Integer.MAX_VALUE) {
			writer.println("NONE");
		} else {
			writer.println(units[0] + " " + units[1] + " " + units[2] + " " + units[3]);
		}
		writer.close();
	}
	
	private static boolean multEqual(int i, int j, int k, int[][] mixes, int[] goal) {
		int[] goalMult = new int[3];
		for (int l = 0; l < 3; l++) {
			goalMult[l] = mixes[0][l] * i + mixes[1][l] * j + mixes[2][l] * k;
		}
		if (goalMult[0] % goal[0] != 0) {
			return false;
		}
		int mult = goalMult[0] / goal[0];
		return (goal[1] * mult == goalMult[1] && goal[2] * mult == goalMult[2]);
	}
}