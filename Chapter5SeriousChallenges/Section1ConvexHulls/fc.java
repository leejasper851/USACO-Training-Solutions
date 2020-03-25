/*
NAME: leejasp1
LANG: JAVA
PROG: fc
*/

package Chapter5SeriousChallenges.Section1ConvexHulls;

import java.io.*;
import java.util.*;

public class fc {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fc.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("fc.out")));
		
		int numSpots = Integer.parseInt(reader.readLine());
		double[][] spots = new double[numSpots][2];
		double[] midSpot = new double[2];
		for (int i = 0; i < numSpots; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			spots[i][0] = x;
			spots[i][1] = y;
			midSpot[0] += x;
			midSpot[1] += y;
		}
		midSpot[0] /= numSpots;
		midSpot[1] /= numSpots;
		reader.close();
		
		Arrays.sort(spots, new SpotComp(midSpot));
		ArrayList<Integer> fence = new ArrayList<>();
		fence.add(0);
		fence.add(1);
		for (int i = 2; i < numSpots - 1; i++) {
			fence.add(i);
			double[] spot1 = spots[fence.get(fence.size() - 3)];
			double[] spot2 = spots[fence.get(fence.size() - 2)];
			double[] spot3 = spots[fence.get(fence.size()-1)];
			while (over180(spot1, spot2, spot3)) {
				fence.remove(fence.size() - 2);
				if (fence.size() < 3) {
					break;
				}
				spot1 = spots[fence.get(fence.size() - 3)];
				spot2 = spots[fence.get(fence.size() - 2)];
				spot3 = spots[fence.get(fence.size()-1)];
			}
		}
		
		fence.add(numSpots-1);
		boolean under180 = true;
		do {
			under180 = true;
			double[] spot1 = spots[fence.get(fence.size() - 3)];
			double[] spot2 = spots[fence.get(fence.size() - 2)];
			double[] spot3 = spots[fence.get(fence.size()-1)];
			double[] spot4 = spots[fence.get(0)];
			double[] spot5 = spots[fence.get(1)];
			while (over180(spot1, spot2, spot3)) {
				fence.remove(fence.size() - 2);
				under180 = false;
				spot1 = spots[fence.get(fence.size() - 3)];
				spot2 = spots[fence.get(fence.size() - 2)];
				spot3 = spots[fence.get(fence.size()-1)];
				spot4 = spots[fence.get(0)];
				spot5 = spots[fence.get(1)];
			}
			while (over180(spot2, spot3, spot4)) {
				fence.remove(fence.size()-1);
				under180 = false;
				spot1 = spots[fence.get(fence.size() - 3)];
				spot2 = spots[fence.get(fence.size() - 2)];
				spot3 = spots[fence.get(fence.size()-1)];
				spot4 = spots[fence.get(0)];
				spot5 = spots[fence.get(1)];
			}
			while (over180(spot3, spot4, spot5)) {
				fence.remove(0);
				under180 = false;
				spot1 = spots[fence.get(fence.size() - 3)];
				spot2 = spots[fence.get(fence.size() - 2)];
				spot3 = spots[fence.get(fence.size()-1)];
				spot4 = spots[fence.get(0)];
				spot5 = spots[fence.get(1)];
			}
		} while (!under180);
		
		double totalLen = 0;
		for (int i = 0; i < fence.size() - 1; i++) {
			double[] spot1 = spots[fence.get(i)];
			double[] spot2 = spots[fence.get(i + 1)];
			totalLen += dist(spot1, spot2);
		}
		double[] spot1 = spots[fence.get(fence.size()-1)];
		double[] spot2 = spots[fence.get(0)];
		totalLen += dist(spot1, spot2);
		writer.printf("%.2f", totalLen);
		writer.println();
		writer.close();
	}
	
	private static class SpotComp implements Comparator<double[]> {
		double[] midSpot;
		
		public SpotComp(double[] ms) {
			midSpot = ms;
		}
		
		public int compare(double[] a, double[] b) {
			double atanA = Math.atan2(a[1] - midSpot[1], a[0] - midSpot[0]);
			double atanB = Math.atan2(b[1] - midSpot[1], b[0] - midSpot[0]);
			if (atanA > atanB) {
				return 1;
			}
			return -1;
		}
	}
	
	public static boolean over180(double[] spot1, double[] spot2, double[] spot3) {
		double[] vect1 = {spot1[0] - spot2[0], spot1[1] - spot2[1]};
		double[] vect2 = {spot3[0] - spot2[0], spot3[1] - spot2[1]};
		double crossProd = vect1[0] * vect2[1] - vect1[1] * vect2[0];
		return (crossProd >= 0);
	}
	
	public static double dist(double[] spot1, double[] spot2) {
		return Math.sqrt(Math.pow(spot1[0] - spot2[0], 2) + Math.pow(spot1[1] - spot2[1], 2));
	}
}