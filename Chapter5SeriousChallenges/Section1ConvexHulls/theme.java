/*
NAME: leejasp1
LANG: JAVA
PROG: theme
*/

package Chapter5SeriousChallenges.Section1ConvexHulls;

import java.io.*;
import java.util.*;

public class theme {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("theme.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("theme.out")));
		
		int numNotes = Integer.parseInt(reader.readLine());
		int[] notes = new int[numNotes];
		StringTokenizer st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numNotes; i++) {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(reader.readLine());
			}
			notes[i] = Integer.parseInt(st.nextToken());
		}
		reader.close();
		
		int numDiffs = numNotes - 1;
		int[] diff = new int[numDiffs];
		HashSet<Integer> diffs = new HashSet<>();
		for (int i = 0; i < numNotes - 1; i++) {
			diff[i] = notes[i + 1] - notes[i];
			diffs.add(diff[i]);
		}
		if (diffs.size() == 1) {
			writer.println(numNotes / 2);
			writer.close();
			return;
		}
		
		int maxLen = -1;
		boolean easy = (diffs.size() > 7 || numNotes < 5000);
		int numDivs1 = easy ? 200 : 2245;
		int numDivs2 = 40;
		ArrayList<Integer>[] begSeqs = new ArrayList[numDiffs - 3];
		for (int i = 0; i < numDiffs - 3; i++) {
			ArrayList<Integer> begSeq = new ArrayList<>();
			begSeq.add(diff[i]);
			begSeq.add(diff[i + 1]);
			begSeq.add(diff[i + 2]);
			begSeq.add(diff[i + 3]);
			begSeqs[i] = begSeq;
		}
		
		for (int len = 4; len < numNotes / 2; len += numDivs1) {
			HashMap<ArrayList<Integer>, Integer> seqs = new HashMap<>();
			for (int i = 0; i <= numDiffs - len; i++) {
				if (!easy && i > numDiffs - len * 2 && i < len) {
				continue;
				}
				if (len > 4) {
					for (int j = numDivs1; j >= 1; j--) {
						begSeqs[i].add(diff[i + len - j]);
					}
				}
				if (maxLen == len) {
					continue;
				}
				if (seqs.containsKey(begSeqs[i])) {
					if (seqs.get(begSeqs[i]) < i - len) {
						maxLen = len;
					}
				} else {
					seqs.put(begSeqs[i], i);
				}
			}
			if (maxLen < len) {
				break;
			}
		}
		
		int maxLen1 = maxLen;
		if (maxLen1 > -1) {
			for (int i = 0; i < begSeqs.length; i++) {
				while (begSeqs[i].size() > maxLen1) {
					begSeqs[i].remove(begSeqs[i].size()-1);
				}
			}
			for (int len = maxLen1 + numDivs2; len < maxLen1 + numDivs1; len += numDivs2) {
				HashMap<ArrayList<Integer>, Integer> seqs = new HashMap<>();
				for (int i = 0; i <= numDiffs - len; i++) {
					for (int j = numDivs2; j >= 1; j--) {
						begSeqs[i].add(diff[i + len - j]);
					}
					if (maxLen == len) {
						continue;
					}
					if (seqs.containsKey(begSeqs[i])) {
						if (seqs.get(begSeqs[i]) < i - len) {
							maxLen = len;
						}
					} else {
						seqs.put(begSeqs[i], i);
					}
				}
				if (maxLen < len) {
					break;
				}
			}
			
			int maxLen2 = maxLen;
			for (int i = 0; i < begSeqs.length; i++) {
				while (begSeqs[i].size() > maxLen2) {
					begSeqs[i].remove(begSeqs[i].size()-1);
				}
			}
			for (int len = maxLen2 + 1; len < maxLen2 + numDivs2; len++) {
				HashMap<ArrayList<Integer>, Integer> seqs = new HashMap<>();
				for (int i = 0; i <= numDiffs - len; i++) {
					begSeqs[i].add(diff[i + len-1]);
					if (maxLen == len) {
						continue;
					}
					if (seqs.containsKey(begSeqs[i])) {
						if (seqs.get(begSeqs[i]) < i - len) {
							maxLen = len;
						}
					} else {
						seqs.put(begSeqs[i], i);
					}
				}
				if (maxLen < len) {
					break;
				}
			}
		}
		writer.println(maxLen + 1);
		writer.close();
	}
}