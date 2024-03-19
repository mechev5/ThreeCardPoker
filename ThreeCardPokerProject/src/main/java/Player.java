import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	int anteBet;
	int playBet;
	int pairPlusBet;
	int totalWinnings;
	int cash;
	int maxVal;
	
	public Player() {
		totalWinnings = 0; 
		anteBet = 5;
		playBet = 0;
		pairPlusBet = 5;
		cash = 500;
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
		if (hand.size() >= 1) {
			maxVal = hand.get(0).getValue();
			for (Card c: hand) {
				if (c.getValue() > maxVal) {
					maxVal = c.getValue();
				}
			}
		}
	}

	public int getAnteBet() {
		return anteBet;
	}

	public void setAnteBet(int anteBet) {
		this.anteBet = anteBet;
	}

	public int getPlayBet() {
		return playBet;
	}

	public void setPlayBet(int playBet) {
		this.playBet = playBet;
	}

	public int getPairPlusBet() {
		return pairPlusBet;
	}

	public void setPairPlusBet(int pairPlusBet) {
		this.pairPlusBet = pairPlusBet;
	}

	public int getTotalWinnings() {
		return totalWinnings;
	}

	public void setTotalWinnings(int totalWinnings) {
		this.totalWinnings = totalWinnings;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void addCash(int amt) {
		cash = cash + amt;
	}
	
}
