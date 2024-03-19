import java.util.ArrayList;

public class Dealer {
	private Deck theDeck;
	private ArrayList<Card> dealersHand;
	private int maxVal;
	
	public Dealer() {
		theDeck = new Deck();
		dealersHand = dealHand();
		maxVal = dealersHand.get(0).getValue();
		if (maxVal == 14) {
			maxVal = 1;
		}
		for (Card c: dealersHand) {
			if (c.getValue() > maxVal && c.getValue() != 14) {
				maxVal = c.getValue();
			}
		}
	}
	
	public ArrayList<Card> dealHand() {
		if (theDeck.getDeck().size() <= 34) {
			theDeck.newDeck();
		}
		ArrayList<Card> newHand = new ArrayList<Card>();
		for (int i = 0; i < 3; i++) {
			newHand.add(theDeck.getDeck().get(0));
			theDeck.getDeck().remove(0);
		}
		return newHand;
	}
	
	public void dealNewHand() {
		dealersHand = dealHand();
		maxVal = dealersHand.get(0).getValue();
		if (maxVal == 14) {
			maxVal = 1;
		}
		for (Card c: dealersHand) {
			if (c.getValue() > maxVal && c.getValue() != 14) {
				maxVal = c.getValue();
			}
		}
	}
	
	public ArrayList<Card> getDealersHand() {
		return dealersHand;
	}
	
	public void setDealerHand(ArrayList<Card> dh) {
		dealersHand = dh;
	}
	
	public int getMaxVal() {
		return maxVal;
	}
	
	public Deck getTheDeck() {
		return theDeck;
	}
	
}
