/*
NAME: leejasp1
LANG: JAVA
PROG: frameup
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section4AllTasks;

import java.io.*;
import java.util.*;

public class frameup {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("frameup.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("frameup.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());
		char[][] grid = new char[height][width];
		for (int i = 0; i < height; i++) {
			String line = reader.readLine();
			for (int j = 0; j < width; j++) {
				grid[i][j] = line.charAt(j);
			}
		}
		reader.close();
		
		HashMap<Character, Frame> frames = new HashMap<>();
		HashSet<Character> useLets = new HashSet<>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				char let = grid[i][j];
				if (let == '.') {
					continue;
				}
				if (!frames.containsKey(let)) {
					frames.put(let, new Frame(let));
					useLets.add(let);
				}
				Frame frame = frames.get(let);
				frame.top = Math.min(frame.top, i);
				frame.bottom = Math.max(frame.bottom, i);
				frame.left = Math.min(frame.left, j);
				frame.right = Math.max(frame.right, j);
			}
		}
		
		for (Frame frame : frames.values()) {
			for (int i = frame.left; i <= frame.right; i++) {
				char let = grid[frame.top][i];
				checkOverlap(frame, let, frames);
				let = grid[frame.bottom][i];
				checkOverlap(frame, let, frames);
			}
			for (int i = frame.top + 1; i < frame.bottom; i++) {
				char let = grid[i][frame.left];
				checkOverlap(frame, let, frames);
				let = grid[i][frame.right];
				checkOverlap(frame, let, frames);
			}
		}
		
		int numFrames = frames.size();
		ArrayList<String> seqs = new ArrayList<>();
		calcSeqs("", useLets, seqs, frames);
		
		Collections.sort(seqs);
		for (String seq : seqs) {
			writer.println(seq);
		}
		writer.close();
	}
	
	private static class Frame {
		public char let;
		public int top;
		public int bottom;
		public int left;
		public int right;
		HashSet<Character> below;
		
		public Frame(char l) {
			let = l;
			top = Integer.MAX_VALUE;
			bottom = -1;
			left = Integer.MAX_VALUE;
			right = -1;
			below = new HashSet<>();
		}
	}
	
	private static void checkOverlap(Frame currFrame, char let, HashMap<Character, Frame> frames) {
		char currLet = currFrame.let;
		if (let != currLet) {
			frames.get(let).below.add(currLet);
		}
	}
	
	private static void calcSeqs(String seq, HashSet<Character> useLets, ArrayList<String> seqs, HashMap<Character, Frame> frames) {
		if (useLets.isEmpty()) {
			seqs.add(seq);
			return;
		}
		
		for (char let : useLets) {
			boolean canUse = true;
			for (char belowLet : frames.get(let).below) {
				if (useLets.contains(belowLet)) {
					canUse = false;
					break;
				}
			}
			if (!canUse) {
				continue;
			}
			
			HashSet<Character> newLets = (HashSet<Character>) useLets.clone();
			newLets.remove(let);
			calcSeqs(seq + let, newLets, seqs, frames);
		}
	}
}