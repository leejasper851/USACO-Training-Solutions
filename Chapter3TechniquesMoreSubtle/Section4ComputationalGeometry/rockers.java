/*
NAME: leejasp1
LANG: JAVA
PROG: rockers
*/

package Chapter3TechniquesMoreSubtle.Section4ComputationalGeometry;

import java.io.*;
import java.util.*;

public class rockers {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("rockers.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("rockers.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numSongs = Integer.parseInt(st.nextToken());
		int lenLim = Integer.parseInt(st.nextToken());
		int numDisks = Integer.parseInt(st.nextToken());
		int[] songLens = new int[numSongs];
		st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numSongs; i++) {
			songLens[i] = Integer.parseInt(st.nextToken());
		}
		reader.close();
		
		boolean solved = false;
		for (int i = numSongs; i > 0; i--) {
			int[] inds = new int[i];
			for (int j = 0; j < i; j++) {
				inds[j] = j;
			}
			int moveInd = i-1;
			
			while (true) {
				if (fits(songLens, inds, numDisks, lenLim)) {
					solved = true;
					writer.println(i);
					break;
				}
				if (moveInd == -1) {
					break;
				}
				moveInd = nextInds(inds, moveInd, numSongs);
			}
			if (solved) {
				break;
			}
		}
		if (!solved) {
			writer.println(0);
		}
		writer.close();
	}
	
	private static int nextInds(int[] inds, int moveInd, int numSongs) {
		if (inds.length == numSongs) {
			return -1;
		}
		if (moveInd == 0 && inds[moveInd + 1] == inds[moveInd] + 1) {
			return -1;
		}
		inds[moveInd]++;
		if (moveInd == inds.length-1) {
			if (inds[moveInd] == numSongs-1) {
				return moveInd - 1;
			}
			return moveInd;
		}
		if (inds[moveInd + 1] == inds[moveInd] + 1) {
			return moveInd - 1;
		}
		for (int i = moveInd + 1; i < inds.length; i++) {
			inds[i] = inds[i - 1] + 1;
		}
		return inds.length-1;
	}
	
	private static boolean fits(int[] songLens, int[] inds, int numDisks, int lenLim) {
		int diskSpace = lenLim;
		for (int ind : inds) {
			if (songLens[ind] > lenLim) {
				return false;
			}
			if (songLens[ind] > diskSpace) {
				numDisks--;
				diskSpace = lenLim;
				if (numDisks == 0) {
					return false;
				}
			}
			diskSpace -= songLens[ind];
		}
		return true;
	}
}