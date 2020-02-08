/*
NAME: leejasp1
LANG: JAVA
PROG: fence9
*/

package Chapter3TechniquesMoreSubtle.Section4ComputationalGeometry;

import java.io.*;
import java.util.*;

public class fence9 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fence9.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("fence9.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int p1x = Integer.parseInt(st.nextToken());
		int p1y = Integer.parseInt(st.nextToken());
		int p2x = Integer.parseInt(st.nextToken());
		reader.close();
		
		if (p1x == 0) {
			writer.println(pointCount(p2x, p1y, false));
		} else if (p1x < p2x) {
			int left = pointCount(p1x, p1y, false);
			int right = pointCount(p2x - p1x, p1y, false);
			int middle = p1y - 1;
			writer.println(left + right + middle);
		} else if (p1x == p2x) {
			writer.println(pointCount(p1x, p1y, false));
		} else {
			int all = pointCount(p1x, p1y, false);
			int small = pointCount(p1x - p2x, p1y, true);
			writer.println(all - small);
		}
		writer.close();
	}
	
	private static int pointCount(int x, int y, boolean border) {
		int total = (x - 1) * (y - 1);
		int diag = gcd(x, y) - 1;
		int halfTotal = (total - diag) / 2;
		return halfTotal + (border ? diag : 0);
	}
	
	private static int gcd(int num1, int num2) {
		int minNum = Math.min(num1, num2);
		for (int i = minNum; i > 1; i--) {
			if (num1 % i == 0 && num2 % i == 0) {
				return i;
			}
		}
		return 1;
	}
}