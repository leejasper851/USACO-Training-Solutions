/*
NAME: leejasp1
LANG: JAVA
PROG: kimbits
*/

package USACOTraining.Chapter3TechniquesMoreSubtle.Section2Knapsack;

import java.io.*;
import java.util.*;

public class kimbits {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("kimbits.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numBits = Integer.parseInt(st.nextToken());
		int max1s = Integer.parseInt(st.nextToken());
		long elemNum = Long.parseLong(st.nextToken());
		reader.close();
		
		checkMax1s(numBits, max1s, elemNum, writer);
		writer.println();
		writer.close();
	}
	
	private static void checkMax1s(int numBits, int max1s, long elemNum, PrintWriter writer) {
		long[] strSums = new long[numBits+1];
		strSums[0] = 1;
		for (int i = 1; i <= numBits; i++) {
			long numPos = 0;
			for (int j = 0; j < max1s; j++) {
				numPos += ncr(i - 1, j);
			}
			strSums[i] = strSums[i - 1] + numPos;
		}
		
		if (elemNum > strSums[numBits - 1]) {
			writer.print(1);
			max1s--;
			elemNum -= strSums[numBits - 1];
		} else {
			writer.print(0);
		}
		
		if (numBits > 1) {
			checkMax1s(numBits - 1, max1s, elemNum, writer);
		}
	}
	
	private static long ncr(int n, int r) {
		if (r > n) {
			return 0;
		}
		if (r > n / 2) {
			r = n - r;
		}
		long rFact = 1;
		for (int i = 2; i <= r; i++) {
			rFact *= i;
		}
		
		double res = 1;
		boolean useR = false;
		for (int i = n - r + 1; i <= n; i++) {
			res *= i;
			if (res >= rFact && !useR) {
				res /= rFact;
				useR = true;
			}
		}
		return Math.round(res);
	}
}