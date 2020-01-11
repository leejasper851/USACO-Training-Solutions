/*
NAME: leejasp1
LANG: JAVA
PROG: spin
*/

package USACOTraining.Section2Knapsack;

import java.io.*;
import java.util.*;

public class spin {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("spin.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
		
		int[] speed = new int[5];
		int[] numWedges = new int[5];
		int[][] wedgeStarts = new int[5][];
		int[][] wedgeExtents = new int[5][];
		for (int i = 0; i < 5; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			speed[i] = Integer.parseInt(st.nextToken());
			int currWedges = Integer.parseInt(st.nextToken());
			numWedges[i] = currWedges;
			wedgeStarts[i] = new int[currWedges];
			wedgeExtents[i] = new int[currWedges];
			for (int j = 0; j < currWedges; j++) {
				wedgeStarts[i][j] = Integer.parseInt(st.nextToken());
				wedgeExtents[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		reader.close();
		
		boolean lighted = false;
		for (int time = 0; time < 360; time++) {
			for (int deg = 0; deg < 360; deg++) {
				boolean currLight = true;
				for (int wheel = 0; wheel < 5; wheel++) {
					if (!wheelLight(wheel, time, deg, speed, numWedges, wedgeStarts, wedgeExtents)) {
						currLight = false;
						break;
					}
				}
				if (currLight) {
					writer.println(time);
					lighted = true;
					break;
				}
			}
			if (lighted) {
				break;
			}
		}
		if (!lighted) {
			writer.println("none");
		}
		writer.close();
	}
	
	private static boolean wheelLight(int wheel, int time, int deg, int[] speed, int[] numWedges, int[][] wedgeStarts, int[][] wedgeExtents) {
		for (int i = 0; i < numWedges[wheel]; i++) {
			int start = (speed[wheel] * time + wedgeStarts[wheel][i]) % 360;
			int end = (start + wedgeExtents[wheel][i]) % 360;
			if (start < end) {
				if (deg >= start && deg <= end) {
					return true;
				}
			} else {
				if (deg >= start || deg <= end) {
					return true;
				}
			}
		}
		return false;
	}
}