/*
NAME: leejasp1
LANG: JAVA
PROG: buylow
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section3Bignums;

import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class buylow {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("buylow.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("buylow.out")));
		
		int numDays = Integer.parseInt(reader.readLine());
		int[] prices = new int[numDays];
		int index = 0;
		while (index < numDays) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			while (st.hasMoreTokens()) {
				prices[index] = Integer.parseInt(st.nextToken());
				index++;
			}
		}
		reader.close();
		
		int[] seq = new int[numDays];
		Arrays.fill(seq, 1);
		BigInteger[] count = new BigInteger[numDays];
		for (int i = 0; i < numDays; i++) {
			count[i] = BigInteger.ONE;
		}
		for (int i = 1; i < numDays; i++) {
			HashSet<Integer> prevs = new HashSet<>();
			for (int j = i - 1; j >= 0; j--) {
				if (prices[j] > prices[i]) {
					if (seq[j] + 1 > seq[i]) {
						seq[i] = seq[j] + 1;
						count[i] = count[j];
						prevs.add(prices[j]);
					} else if (seq[j] + 1 == seq[i] && !prevs.contains(prices[j])) {
						count[i] = count[i].add(count[j]);
						prevs.add(prices[j]);
					}
				}
			}
		}
		
		int longSeq = 0;
		BigInteger totalCount = BigInteger.ONE;
		HashSet<Integer> prevs = new HashSet<>();
		for (int i = numDays-1; i >= 0; i--) {
			if (seq[i] > longSeq) {
				longSeq = seq[i];
				totalCount = count[i];
				prevs.add(prices[i]);
			} else if (seq[i] == longSeq && !prevs.contains(prices[i])) {
				totalCount = totalCount.add(count[i]);
				prevs.add(prices[i]);
			}
		}
		writer.println(longSeq + " " + totalCount);
		writer.close();
	}
}