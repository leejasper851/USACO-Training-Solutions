/*
NAME: leejasp1
LANG: JAVA
PROG: race3
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section3Bignums;

import java.io.*;
import java.util.*;

public class race3 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("race3.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("race3.out")));
		
		ArrayList<ArrayList<Integer>> input = new ArrayList<>();
		while (true) {
			String line = reader.readLine();
			if (line.equals("-1")) {
				break;
			}
			input.add(new ArrayList<>());
			StringTokenizer st = new StringTokenizer(line);
			while (true) {
				int adj = Integer.parseInt(st.nextToken());
				if (adj == -2) {
					break;
				}
				input.get(input.size()-1).add(adj);
			}
		}
		reader.close();
		
		int numPoints = input.size();
		ArrayList<Integer>[] arrows = new ArrayList[numPoints];
		boolean[] selfArrow = new boolean[numPoints];
		for (int i = 0; i < numPoints; i++) {
			arrows[i] = new ArrayList<>();
			for (int adj : input.get(i)) {
				if (adj == i) {
					selfArrow[i] = true;
				} else {
					arrows[i].add(adj);
				}
			}
		}
		
		ArrayList<Integer> unavoids = new ArrayList<>();
		for (int i = 1; i < numPoints - 1; i++) {
			boolean[] visited = new boolean[numPoints];
			if (!checkUnavoid(0, i, visited, arrows)) {
				unavoids.add(i);
			}
		}
		Collections.sort(unavoids);
		
		ArrayList<Integer> splits = new ArrayList<Integer>();
		for (int unavoid : unavoids) {
			HashSet<Integer> begPoints = new HashSet<>();
			findPoints(0, begPoints, unavoid, true, arrows);
			HashSet<Integer> endPoints = new HashSet<>();
			findPoints(unavoid, endPoints, unavoid, false, arrows);
			
			boolean split = true;
			for (int i = 0; i < numPoints; i++) {
				if (i != unavoid && begPoints.contains(i) && endPoints.contains(i)) {
					split = false;
					break;
				}
			}
			if (split) {
				splits.add(unavoid);
			}
		}
		Collections.sort(splits);
		
		writer.print(unavoids.size());
		for (int unavoid : unavoids) {
			writer.print(" " + unavoid);
		}
		writer.println();
		writer.print(splits.size());
		for (int split : splits) {
			writer.print(" " + split);
		}
		writer.println();
		writer.close();
	}
	
	private static boolean checkUnavoid(int point, int unavoid, boolean[] visited, ArrayList<Integer>[] arrows) {
		visited[point] = true;
		
		if (point == arrows.length-1) {
			return true;
		}
		if (point == unavoid) {
			return false;
		}
		
		boolean pos = false;
		for (int adj : arrows[point]) {
			if (visited[adj]) {
				continue;
			}
			if (checkUnavoid(adj, unavoid, visited, arrows)) {
				pos = true;
			}
		}
		return pos;
	}
	
	private static void findPoints(int point, HashSet<Integer> points, int split, boolean beg, ArrayList<Integer>[] arrows) {
		points.add(point);
		
		if (beg && point == split) {
			return;
		}
		
		for (int adj : arrows[point]) {
			if (!points.contains(adj)) {
				findPoints(adj, points, split, beg, arrows);
			}
		}
	}
}