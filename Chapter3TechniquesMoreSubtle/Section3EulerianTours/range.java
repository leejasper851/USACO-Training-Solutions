/*
NAME: leejasp1
LANG: JAVA
PROG: range
*/

package Chapter3TechniquesMoreSubtle.Section3EulerianTours;

import java.io.*;
import java.util.*;

public class range {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("range.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		
		int size = Integer.parseInt(reader.readLine());
		boolean[][] grid = new boolean[size][size];
		for (int i = 0; i < size; i++) {
			String row = reader.readLine();
			for (int j = 0; j < size; j++) {
				grid[i][j] = (row.charAt(j) == '1');
			}
		}
		reader.close();
		
		for (int currSize = 2; currSize <= size; currSize++) {
			boolean[][] rowGrid = new boolean[size][size];
			for (int row = 0; row < size; row++) {
				int numFalses = 0;
				for (int col = 0; col < currSize; col++) {
					if (!grid[row][col]) {
						numFalses++;
					}
				}
				if (numFalses == 0) {
					rowGrid[row][currSize-1] = true;
				}
				
				for (int col = currSize; col < size; col++) {
					if (!grid[row][col - currSize]) {
						numFalses--;
					}
					if (!grid[row][col]) {
						numFalses++;
					}
					if (numFalses == 0) {
						rowGrid[row][col] = true;
					}
				}
			}
			
			int colCount = 0;
			for (int col = 0; col < size; col++) {
				int numFalses = 0;
				for (int row = 0; row < currSize; row++) {
					if (!rowGrid[row][col]) {
						numFalses++;
					}
				}
				if (numFalses == 0) {
					colCount++;
				}
				
				for (int row = currSize; row < size; row++) {
					if (!rowGrid[row - currSize][col]) {
						numFalses--;
					}
					if (!rowGrid[row][col]) {
						numFalses++;
					}
					if (numFalses == 0) {
						colCount++;
					}
				}
			}
			
			if (colCount > 0) {
				writer.println(currSize + " " + colCount);
			} else {
				break;
			}
		}
		writer.close();
	}
}