/*
NAME: leejasp1
LANG: JAVA
PROG: fence
*/

package Chapter3TechniquesMoreSubtle.Section3EulerianTours;

import java.io.*;
import java.util.*;

public class fence {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fence.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));
		
		int numFences = Integer.parseInt(reader.readLine());
		HashMap<Integer, PriorityQueue<Integer>> fences = new HashMap<>();
		for (int i = 0; i < numFences; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int inter1 = Integer.parseInt(st.nextToken());
			int inter2 = Integer.parseInt(st.nextToken());
			if (!fences.containsKey(inter1)) {
				fences.put(inter1, new PriorityQueue<>());
			}
			if (!fences.containsKey(inter2)) {
				fences.put(inter2, new PriorityQueue<>());
			}
			fences.get(inter1).add(inter2);
			fences.get(inter2).add(inter1);
		}
		reader.close();
		
		int startInter = Integer.MAX_VALUE;
		boolean odd = false;
		for (Map.Entry<Integer, PriorityQueue<Integer>> inter : fences.entrySet()) {
			if (inter.getValue().size() % 2 == 1) {
				if (odd) {
					startInter = Math.min(startInter, inter.getKey());
				} else {
					odd = true;
					startInter = inter.getKey();
				}
			} else {
				if (!odd) {
					startInter = Math.min(startInter, inter.getKey());
				}
			}
		}
		
		ArrayList<Integer> path = new ArrayList<>();
		findEuler(startInter, fences, path);
		
		for (int i = path.size()-1; i >= 0; i--) {
			writer.println(path.get(i));
		}
		writer.close();
	}
	
	private static void findEuler(int inter, HashMap<Integer, PriorityQueue<Integer>> fences, ArrayList<Integer> path) {
		while (fences.get(inter).size() > 0) {
			int adjInter = fences.get(inter).remove();
			fences.get(adjInter).remove(inter);
			findEuler(adjInter, fences, path);
		}
		path.add(inter);
	}
}