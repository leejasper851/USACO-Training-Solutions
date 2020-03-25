/*
NAME: leejasp1
LANG: JAVA
PROG: starry
*/

package Chapter5SeriousChallenges.Section1ConvexHulls;

import java.io.*;
import java.util.*;

public class starry {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("starry.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("starry.out")));
		
		int width = Integer.parseInt(reader.readLine());
		int height = Integer.parseInt(reader.readLine());
		boolean[][] grid = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			String row = reader.readLine();
			for (int j = 0; j < width; j++) {
				grid[i][j] = (row.charAt(j) == '1');
			}
		}
		reader.close();
		
		char[][] newGrid = new char[height][width];
		for (char[] row : newGrid) {
			Arrays.fill(row, '0');
		}
		ArrayList<Sim> sims = new ArrayList<Sim>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j]) {
					HashSet<ArrayList<Integer>> coords = new HashSet<>();
					floodFill(i, j, coords, grid);
					HashSet<ArrayList<Integer>> relCoords = getRel(coords);
					
					char sim = 0;
					for (Sim currSim : sims) {
						if (currSim.isSim(relCoords)) {
							sim = currSim.let;
							break;
						}
					}
					
					if (sim == 0) {
						sim = 'a';
						if (!sims.isEmpty()) {
							sim = (char) (sims.get(sims.size()-1).let + 1);
						}
						sims.add(new Sim(sim, relCoords));
					}
					for (ArrayList<Integer> coord : coords) {
						newGrid[coord.get(0)][coord.get(1)] = sim;
					}
				}
			}
		}
		
		for (char[] row : newGrid) {
			writer.println(row);
		}
		writer.close();
	}
	
	private static class Sim {
		public char let;
		public HashSet<ArrayList<Integer>>[] shapes;
		
		public Sim(char l, HashSet<ArrayList<Integer>> relCoords) {
			let = l;
			shapes = new HashSet[8];
			
			shapes[0] = relCoords;
			shapes[1] = getRot1(relCoords);
			shapes[2] = getRot2(relCoords);
			shapes[3] = getRot3(relCoords);
			HashSet<ArrayList<Integer>> refCoords = getRef(relCoords);
			shapes[4] = refCoords;
			shapes[5] = getRot1(refCoords);
			shapes[6] = getRot2(refCoords);
			shapes[7] = getRot3(refCoords);
		}
		
		private static HashSet<ArrayList<Integer>> getRot1(HashSet<ArrayList<Integer>> coords) {
			HashSet<ArrayList<Integer>> newCoords = new HashSet<>();
			for (ArrayList<Integer> coord : coords) {
				ArrayList<Integer> newCoord = new ArrayList<>();
				newCoord.add(coord.get(1));
				newCoord.add(-coord.get(0));
				newCoords.add(newCoord);
			}
			return getRel(newCoords);
		}
		
		private static HashSet<ArrayList<Integer>> getRot2(HashSet<ArrayList<Integer>> coords) {
			HashSet<ArrayList<Integer>> newCoords = new HashSet<>();
			for (ArrayList<Integer> coord : coords) {
				ArrayList<Integer> newCoord = new ArrayList<>();
				newCoord.add(-coord.get(0));
				newCoord.add(-coord.get(1));
				newCoords.add(newCoord);
			}
			return getRel(newCoords);
		}
		
		private static HashSet<ArrayList<Integer>> getRot3(HashSet<ArrayList<Integer>> coords) {
			HashSet<ArrayList<Integer>> newCoords = new HashSet<>();
			for (ArrayList<Integer> coord : coords) {
				ArrayList<Integer> newCoord = new ArrayList<>();
				newCoord.add(-coord.get(1));
				newCoord.add(coord.get(0));
				newCoords.add(newCoord);
			}
			return getRel(newCoords);
		}
		
		private static HashSet<ArrayList<Integer>> getRef(HashSet<ArrayList<Integer>> coords) {
			HashSet<ArrayList<Integer>> newCoords = new HashSet<>();
			for (ArrayList<Integer> coord : coords) {
				ArrayList<Integer> newCoord = new ArrayList<>();
				newCoord.add(-coord.get(0));
				newCoord.add(coord.get(1));
				newCoords.add(newCoord);
			}
			return getRel(newCoords);
		}
		
		public boolean isSim(HashSet<ArrayList<Integer>> relCoords) {
			for (HashSet<ArrayList<Integer>> shape : shapes) {
				if (relCoords.equals(shape)) {
					return true;
				}
			}
			return false;
		}
	}
	
	private static HashSet<ArrayList<Integer>> getRel(HashSet<ArrayList<Integer>> coords) {
		ArrayList<Integer> start = new ArrayList<>();
		start.add(Integer.MAX_VALUE); start.add(Integer.MAX_VALUE);
		for (ArrayList<Integer> coord : coords) {
			if (coord.get(0) < start.get(0)) {
				start.set(0, coord.get(0));
				start.set(1, coord.get(1));
			} else if (coord.get(0) == start.get(0) && coord.get(1) < start.get(1)) {
				start.set(0, coord.get(0));
				start.set(1, coord.get(1));
			}
		}
		
		HashSet<ArrayList<Integer>> relCoords = new HashSet<ArrayList<Integer>>();
		for (ArrayList<Integer> coord : coords) {
			ArrayList<Integer> relCoord = new ArrayList<>();
			relCoord.add(coord.get(0) - start.get(0));
			relCoord.add(coord.get(1) - start.get(1));
			relCoords.add(relCoord);
		}
		return relCoords;
	}
	
	private static void floodFill(int row, int col, HashSet<ArrayList<Integer>> coords, boolean[][] grid) {
		if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || !grid[row][col]) {
			return;
		}
		
		ArrayList<Integer> coord = new ArrayList<>();
		coord.add(row);
		coord.add(col);
		coords.add(coord);
		grid[row][col] = false;
		
		int[][] adjs = {{0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}};
		for (int[] adj : adjs) {
			floodFill(row + adj[0], col + adj[1], coords, grid);
		}
	}
}