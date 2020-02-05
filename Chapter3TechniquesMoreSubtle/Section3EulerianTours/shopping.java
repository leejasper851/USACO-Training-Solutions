/*
NAME: leejasp1
LANG: JAVA
PROG: shopping
*/

package Chapter3TechniquesMoreSubtle.Section3EulerianTours;

import java.io.*;
import java.util.*;

public class shopping {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("shopping.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("shopping.out")));
		
		int numOffers = Integer.parseInt(reader.readLine());
		ArrayList<Offer> begOffers = new ArrayList<>();
		for (int i = 0; i < numOffers; i++) {
			begOffers.add(new Offer());
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int numProds = Integer.parseInt(st.nextToken());
			for (int j = 0; j < numProds; j++) {
				begOffers.get(i).addProd(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			begOffers.get(i).setPrice(Integer.parseInt(st.nextToken()));
		}
		int numProds = Integer.parseInt(reader.readLine());
		HashMap<Integer, Integer> prodCodes = new HashMap<>();
		ArrayList<Integer> prodAmts = new ArrayList<Integer>();
		for (int i = 0; i < numProds; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int prodCode = Integer.parseInt(st.nextToken());
			prodCodes.put(prodCode, i);
			prodAmts.add(Integer.parseInt(st.nextToken()));
			begOffers.add(new Offer());
			begOffers.get(begOffers.size()-1).addProd(prodCode, 1);
			begOffers.get(begOffers.size()-1).setPrice(Integer.parseInt(st.nextToken()));
		}
		reader.close();
		
		ArrayList<ArrayList<Integer>> offers = new ArrayList<>();
		ArrayList<Integer> prices = new ArrayList<>();
		for (Offer begOffer : begOffers) {
			ArrayList<Integer> offer = new ArrayList<>();
			for (int i = 0; i < numProds; i++) {
				offer.add(0);
			}
			for (int i = 0; i < begOffer.prods.size(); i++) {
				offer.set(prodCodes.get(begOffer.prods.get(i)), begOffer.counts.get(i));
			}
			offers.add(offer);
			prices.add(begOffer.price);
		}
		
		ArrayList<Integer> currAmts = new ArrayList<>();
		int numNodes = 1;
		for (int i = 0; i < numProds; i++) {
			numNodes *= prodAmts.get(i) + 1;
			currAmts.add(0);
		}
		HashMap<ArrayList<Integer>, Integer> minPrices = new HashMap<>();
		minPrices.put((ArrayList<Integer>) currAmts.clone(), 0);
		if (!currAmts.isEmpty()) {
			currAmts.set(numProds-1, 1);
		}
		
		for (int i = 1; i < numNodes; i++) {
			int minPrice = Integer.MAX_VALUE;
			for (int j = 0; j < offers.size(); j++) {
				ArrayList<Integer> newAmts = new ArrayList<>();
				boolean under = false;
				for (int k = 0; k < numProds; k++) {
					int newAmt = currAmts.get(k) - offers.get(j).get(k);
					if (newAmt < 0) {
						under = true;
						break;
					}
					newAmts.add(newAmt);
				}
				if (under) {
					continue;
				}
				minPrice = Math.min(minPrice, minPrices.get(newAmts) + prices.get(j));
			}
			minPrices.put((ArrayList<Integer>) currAmts.clone(), minPrice);
			
			for (int j = numProds-1; j >= 0; j--) {
				if (currAmts.get(j) == prodAmts.get(j)) {
					currAmts.set(j, 0);
					continue;
				}
				currAmts.set(j, currAmts.get(j) + 1);
				break;
			}
		}
		
		writer.println(minPrices.get(prodAmts));
		writer.close();
	}
	
	private static class Offer {
		public ArrayList<Integer> prods;
		public ArrayList<Integer> counts;
		public int price;
		
		public Offer() {
			prods = new ArrayList<>();
			counts = new ArrayList<>();
			price = -1;
		}
		
		public void addProd(int p, int c) {
			prods.add(p);
			counts.add(c);
		}
		
		public void setPrice(int p) {
			price = p;
		}
	}
}