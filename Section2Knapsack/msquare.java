/*
NAME: leejasp1
LANG: JAVA
PROG: msquare
*/

package USACOTraining.Section2Knapsack;

import java.io.*;
import java.util.*;

public class msquare {
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("msquare.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		
		ArrayList<Integer> goal = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 8; i++) {
			goal.add(Integer.parseInt(st.nextToken()));
		}
		reader.close();
		
		ArrayList<Integer> a = new ArrayList<>();
		a.add(1);
		a.add(2);
		ArrayList<Integer> b = new ArrayList<>();
		b.add(1);
		b.add(2);
		
		ArrayList<ArrayList<Integer>> squares = new ArrayList<>();
		HashSet<ArrayList<Integer>> storeSq = new HashSet<>();
		ArrayList<Long> posCount = new ArrayList<>();
		ArrayList<Integer> init = new ArrayList<>();
		for (int i = 1; i <= 8; i++) {
			init.add(i);
		}
		squares.add(init);
		storeSq.add(init);
		posCount.add(0L);
		boolean equal = false;
		for (int i = 0; i < 30; i++) {
			int origSize = squares.size();
			for (int j = 0; j < origSize; j++) {
				if (squares.get(0).equals(goal)) {
					writer.println(i);
					printAns(posCount.get(0), (long) Math.pow(3, i), writer);
					writer.println();
					equal = true;
					break;
				}
				
				ArrayList<Integer> aRes = transA(squares.get(0));
				if (storeSq.add(aRes)) {
					squares.add(aRes);
					posCount.add(posCount.get(0) * 3);
				}
				ArrayList<Integer> bRes = transB(squares.get(0));
				if (storeSq.add(bRes)) {
					squares.add(bRes);
					posCount.add(posCount.get(0) * 3 + 1);
				}
				ArrayList<Integer> cRes = transC(squares.remove(0));
				if (storeSq.add(cRes)) {
					squares.add(cRes);
					posCount.add(posCount.get(0) * 3 + 2);
				}
				posCount.remove(0);
			}
			if (equal) {
				break;
			}
		}
		writer.close();
	}
	
	private static ArrayList<Integer> transA(ArrayList<Integer> orig) {
		ArrayList<Integer> newSq = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			newSq.add(orig.get(8 - i - 1));
		}
		return newSq;
	}
	
	private static ArrayList<Integer> transB(ArrayList<Integer> orig) {
		ArrayList<Integer> newSq = (ArrayList<Integer>) orig.clone();
		newSq.add(0, newSq.remove(3));
		newSq.add(newSq.remove(4));
		return newSq;
	}
	
	private static ArrayList<Integer> transC(ArrayList<Integer> orig) {
		ArrayList<Integer> newSq = (ArrayList<Integer>) orig.clone();
		newSq.set(1, orig.get(6));
		newSq.set(2, orig.get(1));
		newSq.set(5, orig.get(2));
		newSq.set(6, orig.get(5));
		return newSq;
	}
	
	private static void printAns(long place, long total, PrintWriter writer) {
		if (total == 1) {
			return;
		}
		
		long part = total / 3;
		if (part == 1) {
			if (place == 0) {
				writer.print("A");
			} else if (place == 1) {
				writer.print("B");
			} else {
				writer.print("C");
			}
			return;
		}
		
		if (place < part) {
			writer.print("A");
			printAns(place, part, writer);
		} else if (place < part * 2) {
			writer.print("B");
			printAns(place - part, part, writer);
		} else {
			writer.print("C");
			printAns(place - part * 2, part, writer);
		}
	}
}