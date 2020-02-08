/*
NAME: leejasp1
LANG: JAVA
PROG: heritage
*/

package Chapter3TechniquesMoreSubtle.Section4ComputationalGeometry;

import java.io.*;
import java.util.*;

public class heritage {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("heritage.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));
		
		String inStr = reader.readLine();
		int[] orderInd = new int[26];
		for (int i = 0; i < inStr.length(); i++) {
			orderInd[inStr.charAt(i) - 65] = i;
		}
		String postStr = reader.readLine();
		ArrayList<Integer> postOrder = new ArrayList<>();
		for (int i = 0; i < postStr.length(); i++) {
			postOrder.add(postStr.charAt(i) - 65);
		}
		reader.close();
		
		TreeNode head = new TreeNode(postOrder.remove(0));
		for (int val : postOrder) {
			insert(val, head, orderInd);
		}
		
		postPrint(head, writer);
		writer.println();
		writer.close();
	}
	
	private static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
		
		public TreeNode(int v) {
			val = v;
		}
	}
	
	private static void insert(int val, TreeNode node, int[] orderInd) {
		if (orderInd[val] < orderInd[node.val]) {
			if (node.left == null) {
				node.left = new TreeNode(val);
				return;
			}
			insert(val, node.left, orderInd);
			return;
		}
		if (node.right == null) {
			node.right = new TreeNode(val);
			return;
		}
		insert(val, node.right, orderInd);
	}
	
	private static void postPrint(TreeNode node, PrintWriter writer) {
		if (node == null) {
			return;
		}
		postPrint(node.left, writer);
		postPrint(node.right, writer);
		writer.print((char) (65 + node.val));
	}
}