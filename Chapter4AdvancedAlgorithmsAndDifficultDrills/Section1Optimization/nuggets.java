/*
NAME: leejasp1
LANG: JAVA
PROG: nuggets
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section1Optimization;

import java.io.*;
import java.util.*;

public class nuggets {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("nuggets.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("nuggets.out")));
		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numPacks = Integer.parseInt(reader.readLine());
		ArrayList<Integer> packs = new ArrayList<>();
		for (int i = 0; i < numPacks; i++) {
			int currPack = Integer.parseInt(reader.readLine());
			boolean putPack = true;
			for (int j = packs.size()-1; j >= 0; j--) {
				if (currPack % packs.get(j) == 0) {
					putPack = false;
				} else if (packs.get(j) % currPack == 0) {
					packs.remove(j);
				}
			}
			if (putPack) {
				packs.add(currPack);
			}
		}
		reader.close();
		
		if (packs.get(0) == 1) {
			writer.println(0);
			writer.close();
			return;
		}
		
		int minPack = Collections.min(packs);
		boolean noBound = false;
		for (int i = 2; i <= minPack; i++) {
			boolean allDiv = true;
			for (int j = 0; j < packs.size(); j++) {
				if (packs.get(j) % i != 0) {
					allDiv = false;
					break;
				}
			}
			if (allDiv) {
				noBound = true;
				break;
			}
		}
		if (noBound) {
			writer.println(0);
			writer.close();
			return;
		}
		
		int upperBound = Collections.max(packs) * Collections.max(packs) * 2;
		boolean[] vals = new boolean[upperBound+1];
		vals[0] = true;
		for (int i = 0; i <= upperBound; i++) {
			for (int pack : packs) {
				if (i - pack >= 0 && vals[i - pack]) {
					vals[i] = true;
					break;
				}
			}
		}
		
		for (int i = upperBound; i >= 0; i--) {
			if (!vals[i]) {
				writer.println(i);
				break;
			}
		}
		writer.close();
	}
}