/*
NAME: leejasp1
LANG: JAVA
PROG: shuttle
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section4AllTasks;

import java.io.*;
import java.util.*;

public class shuttle {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("shuttle.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("shuttle.out")));
		
		int size = Integer.parseInt(reader.readLine());
		reader.close();
		
		int boardSize = 2 * size + 1;
		int[] board = new int[boardSize];
		for (int i = 0; i < size; i++) {
			board[i] = 1;
		}
		for (int i = size + 1; i < boardSize; i++) {
			board[i] = 2;
		}
		
		ArrayList<Integer> moves = new ArrayList<>();
		while (true) {
			boolean move = false;
			boolean slid = false;
			for (int i = boardSize - 2; i >= 0; i--) {
				if (board[i] != 1) {
					continue;
				}
				if (i <= boardSize - 3 && board[i + 1] == 2 && board[i + 2] == 0) {
					moves.add(i+1);
					board[i + 2] = 1;
					board[i] = 0;
					move = true;
				} else if (!slid && i <= boardSize - 2 && board[i + 1] == 0) {
					moves.add(i+1);
					board[i + 1] = 1;
					board[i] = 0;
					slid = true;
					move = true;
				}
			}
			slid = false;
			for (int i = 1; i < boardSize; i++) {
				if (board[i] != 2) {
					continue;
				}
				if (i >= 2 && board[i - 1] == 1 && board[i - 2] == 0) {
					moves.add(i+1);
					board[i - 2] = 2;
					board[i] = 0;
					move = true;
				} else if (!slid && i >= 1 && board[i - 1] == 0) {
					moves.add(i+1);
					board[i - 1] = 2;
					board[i] = 0;
					slid = true;
					move = true;
				}
			}
			if (!move) {
				break;
			}
		}
		
		for (int i = 0; i < moves.size(); i++) {
			if (i > 0) {
				if (i % 20 == 0) {
					writer.println();
				} else {
					writer.print(" ");
				}
			}
			writer.print(moves.get(i));
		}
		writer.println();
		writer.close();
	}
}