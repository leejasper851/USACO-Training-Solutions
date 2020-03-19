/*
NAME: leejasp1
LANG: JAVA
PROG: lgame
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section3Bignums;

import java.io.*;
import java.util.*;

public class lgame {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("lgame.in"));
		BufferedReader dictReader = new BufferedReader(new FileReader("lgame.dict"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("lgame.out")));
		
		HashMap<Character, Integer> vals = new HashMap<>();
		vals.put('q', 7); vals.put('w', 6); vals.put('e', 1); vals.put('r', 2); vals.put('t', 2); vals.put('y', 5); vals.put('u', 4); vals.put('i', 1); vals.put('o', 3); vals.put('p', 5);
		vals.put('a', 2); vals.put('s', 1); vals.put('d', 4); vals.put('f', 6); vals.put('g', 5); vals.put('h', 5); vals.put('j', 7); vals.put('k', 6); vals.put('l', 3);
		vals.put('z', 7); vals.put('x', 7); vals.put('c', 4); vals.put('v', 6); vals.put('b', 5); vals.put('n', 2); vals.put('m', 5);
		
		HashSet<String> dict = new HashSet<>();
		while (true) {
			String line = dictReader.readLine();
			if (line.equals(".")) {
				break;
			}
			dict.add(line);
		}
		dictReader.close();
		
		String lets = reader.readLine();
		reader.close();
		
		int[] maxVal = {0};
		HashSet<String> maxWords = new HashSet<>();
		generateCombs("", lets, maxVal, maxWords, vals, dict);
		
		TreeSet<String> sortWords = new TreeSet<>(new WordComp());
		for (String word : maxWords) {
			sortWords.add(word);
		}
		
		writer.println(maxVal[0]);
		for (String word : sortWords) {
			writer.println(word);
		}
		writer.close();
	}
	
	private static class WordComp implements Comparator<String> {
		public int compare(String a, String b) {
			String[] aSplit = a.split(" ");
			String[] bSplit = b.split(" ");
			if (aSplit[0].compareTo(bSplit[0]) != 0) {
				return aSplit[0].compareTo(bSplit[0]);
			}
			if (aSplit.length < 2 || bSplit.length < 2) {
				return 0;
			}
			return aSplit[1].compareTo(bSplit[1]);
		}
	}
	
	private static void generateCombs(String currComb, String availLets, int[] maxVal, HashSet<String> maxWords, HashMap<Character, Integer> vals, HashSet<String> dict) {
		if (currComb.length() >= 3) {
			testComb(currComb, maxVal, maxWords, vals, dict);
		}
		if (availLets.length() == 0) {
			return;
		}
		for (int i = 0; i < availLets.length(); i++) {
			String newComb = currComb + availLets.charAt(i);
			String newAvail = availLets.substring(0, i) + availLets.substring(i + 1);
			generateCombs(newComb, newAvail, maxVal, maxWords, vals, dict);
		}
	}
	
	private static void testComb(String comb, int[] maxVal, HashSet<String> maxWords, HashMap<Character, Integer> vals, HashSet<String> dict) {
		int val = getVal(comb, vals, dict);
		if (val > maxVal[0]) {
			maxVal[0] = val;
			maxWords.clear();
			maxWords.add(comb);
		} else if (val == maxVal[0]) {
			maxWords.add(comb);
		}
		
		if (comb.length() == 6) {
			String comb1 = comb.substring(0, 3);
			String comb2 = comb.substring(3);
			val = get2Val(comb1, comb2, vals, dict);
			if (val > maxVal[0]) {
				maxVal[0] = val;
				maxWords.clear();
				maxWords.add(comb1 + " " + comb2);
			} else if (val == maxVal[0]) {
				maxWords.add(comb1 + " " + comb2);
			}
		}
		
		if (comb.length() == 7) {
			String comb1 = comb.substring(0, 3);
			String comb2 = comb.substring(3);
			val = get2Val(comb1, comb2, vals, dict);
			if (val > maxVal[0]) {
				maxVal[0] = val;
				maxWords.clear();
				maxWords.add(comb1 + " " + comb2);
			} else if (val == maxVal[0]) {
				maxWords.add(comb1 + " " + comb2);
			}
			
			comb1 = comb.substring(0, 4);
			comb2 = comb.substring(4);
			val = get2Val(comb1, comb2, vals, dict);
			if (val > maxVal[0]) {
				maxVal[0] = val;
				maxWords.clear();
				maxWords.add(comb1 + " " + comb2);
			} else if (val == maxVal[0]) {
				maxWords.add(comb1 + " " + comb2);
			}
		}
	}
	
	private static int getVal(String comb, HashMap<Character, Integer> vals, HashSet<String> dict) {
		if (!dict.contains(comb)) {
			return 0;
		}
		int val = 0;
		for (int i = 0; i < comb.length(); i++) {
			val += vals.get(comb.charAt(i));
		}
		return val;
	}
	
	private static int get2Val(String comb1, String comb2, HashMap<Character, Integer> vals, HashSet<String> dict) {
		if (!dict.contains(comb1) || !dict.contains(comb2) || comb1.compareTo(comb2) > 0) {
			return 0;
		}
		int val = 0;
		for (int i = 0; i < comb1.length(); i++) {
			val += vals.get(comb1.charAt(i));
		}
		for (int i = 0; i < comb2.length(); i++) {
			val += vals.get(comb2.charAt(i));
		}
		return val;
	}
}