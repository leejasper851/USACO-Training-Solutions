/*
NAME: leejasp1
LANG: JAVA
PROG: fact4
*/

package USACOTraining.Section2Knapsack;

import java.io.*;
import java.util.*;

public class fact4 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fact4.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		
		long num = Integer.parseInt(reader.readLine());
		reader.close();
		
		ArrayList<Long> nums = new ArrayList<>();
		for (long i = 1; i <= num; i++) {
			nums.add(i);
		}
		for (int i = 0; i < nums.size(); i++) {
			if (rightDig(nums.get(i)) == 5) {
				int diff = 1;
				if (nums.get(i - 1) % 2 == 1) {
					diff = 2;
				}
				nums.set(i - diff, nums.get(i - diff) * nums.get(i));
				nums.remove(i);
				i--;
			}
		}
		
		long prev = 1;
		for (long currNum : nums) {
			prev = rightDig(prev * currNum);
		}
		writer.println(prev);
		writer.close();
	}
	
	private static long rightDig(long num) {
		while (num % 10 == 0) {
			num /= 10;
		}
		return num % 10;
	}
}