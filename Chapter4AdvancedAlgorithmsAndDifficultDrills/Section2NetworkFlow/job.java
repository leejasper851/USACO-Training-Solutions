/*
NAME: leejasp1
LANG: JAVA
PROG: job
*/

package Chapter4AdvancedAlgorithmsAndDifficultDrills.Section2NetworkFlow;

import java.io.*;
import java.util.*;

public class job {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("job.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("job.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numJobs = Integer.parseInt(st.nextToken());
		int numAs = Integer.parseInt(st.nextToken());
		int numBs = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(reader.readLine());
		int[] as = new int[numAs];
		for (int i = 0; i < numAs; i++) {
			as[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(reader.readLine());
		int[] bs = new int[numBs];
		for (int i = 0; i < numBs; i++) {
			bs[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(as);
		Arrays.sort(bs);
		reader.close();
		
		int halfT = 0;
		int[] prevEnd = new int[numAs];
		Stack<Integer> aTimes = new Stack<>();
		for (int i = 0; i < numJobs; i++) {
			int minT = Integer.MAX_VALUE;
			int minA = 0;
			for (int j = 0; j < numAs; j++) {
				if (prevEnd[j] + as[j] < minT) {
					minT = prevEnd[j] + as[j];
					minA = j;
				}
			}
			halfT = Math.max(halfT, minT);
			prevEnd[minA] = minT;
			aTimes.add(minT);
		}
		
		int maxT = 0;
		int[] prevStart = new int[numBs];
		int[] count = new int[numBs];
		Arrays.fill(prevStart, -1);
		while (!aTimes.isEmpty()) {
			int aTime = aTimes.pop();
			int minT = Integer.MAX_VALUE;
			int minB = 0;
			for (int j = 0; j < numBs; j++) {
				int currT = 0;
				if (prevStart[j] == -1 || aTime + bs[j] <= prevStart[j]) {
					currT = aTime + bs[j];
				} else {
					currT = aTime + (count[j] + 1) * bs[j];
				}
				if (currT < minT) {
					minT = currT;
					minB = j;
				}
			}
			maxT = Math.max(maxT, minT);
			prevStart[minB] = aTime;
			count[minB]++;
		}
		
		writer.println(halfT + " " + maxT);
		writer.close();
	}
}