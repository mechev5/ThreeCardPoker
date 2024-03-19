import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeCardLogic {
	public static int evalHand(ArrayList<Card> hand) {
		hand.sort((Card c1, Card c2)-> {
			Integer iC1 = c1.getValue();
			Integer iC2 = c2.getValue();
			if (iC1 == 14) {
				iC1 = 1;
			}
			if (iC2 == 14) {
				iC2 = 1;
			}
			return iC1.compareTo(iC2);
		});
		Card one = hand.get(0);
		Card two = hand.get(1);
		Card three = hand.get(2);
		
		// Straight flush
		if (sameSuit(one, two, three) && isStraight(one, two, three)) {
			return 1;
		}
		// Three of a kind
		if (sameValue(one, two, three)) {
			return 2;
		}
		// Straight
		if (isStraight(one, two, three)) {
			return 3;
		}
		// Flush
		if (sameSuit(one, two, three)) {
			return 4;
		}
		// Pair
		if (sameRankPair(one, two, three)) {
			return 5;
		}
		return 0;
	}
	
	
	private static boolean isStraight(Card one, Card two, Card three) {
		int val1 = one.getValue();
		int val2 = two.getValue();
		int val3 = three.getValue();
		if (val2 == val1 + 1 && val3 == val2 + 1) {
			return true;
		}
		return false;
	}
	
	private static boolean sameSuit(Card one, Card two, Card three) {
		char val1 = one.getSuit();
		char val2 = two.getSuit();
		char val3 = three.getSuit();
		if (val1 == val2 && val1 == val3) {
			return true;
		}
		return false;
	}
	
	private static boolean sameValue(Card one, Card two, Card three) {
		int val1 = one.getValue();
		int val2 = two.getValue();
		int val3 = three.getValue();
		if (val1 == val2 && val1 == val3) {
			return true;
		}
		return false;
	}
	
	private static boolean sameRankPair(Card one, Card two, Card three) {
		int val1 = one.getValue();
		int val2 = two.getValue();
		int val3 = three.getValue();
		if (val1 == val2 && val1 != val3) {
			return true;
		}
		if (val1 == val3 && val1 != val2) {
			return true;
		}
		if (val2 == val3 && val2 != val1) {
			return true;
		}
		return false;
	}
	public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
		int handVal = evalHand(hand);
		int amtWon = bet;
		if (handVal == 5) {
			amtWon += bet;
		}
		if (handVal == 4) {
			amtWon *= 3;
			amtWon += bet;
		}
		if (handVal == 3) {
			amtWon *= 6;
			amtWon += bet;
		}
		if (handVal == 2) {
			amtWon *= 30;
			amtWon += bet;
		}
		if (handVal == 1) {
			amtWon *= 40;
			amtWon += bet;
		}
		if (handVal == 0) {
			return 0;
		}
		return amtWon;
	}
	public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
		int dVal = evalHand(dealer);
		int pHand = evalHand(player);
		if (dVal == 0) { dVal = 6;}
		if (pHand == 0) { pHand = 6;}
		if (dVal < pHand) {
			return 1;
		}
		if (dVal > pHand) {
			return 2;
		}
		
		// Same deck rank
		if (dVal == pHand) {
			// Check for pair
			if (dVal == 5) {
				if (getMaxPair(dealer) > getMaxPair(player)) {
					return 1;
				} else if (getMaxPair(dealer) < getMaxPair(player)) {
					return 2;
				} else {
					if (getThirdInMaxPair(dealer) > getThirdInMaxPair(player)) {
						return 1;
					} else if (getThirdInMaxPair(dealer) < getThirdInMaxPair(player)) {
						return 2;
					} else {
						return 0;
					}
				}
			}
		player.sort((Card c1, Card c2)-> {
			Integer iC1 = c1.getValue();
			Integer iC2 = c2.getValue();
			if (iC1 == 14) {
				iC1 = 1;
			}
			if (iC2 == 14) {
				iC2 = 1;
			}
			return iC1.compareTo(iC2);
		});
		Collections.reverse(player);
		dealer.sort((Card c1, Card c2)-> {
			Integer iC1 = c1.getValue();
			Integer iC2 = c2.getValue();
			if (iC1 == 14) {
				iC1 = 1;
			}
			if (iC2 == 14) {
				iC2 = 1;
			}
			return iC1.compareTo(iC2);
		});
		Collections.reverse(dealer);
		if (dealer.get(0).getValue() > player.get(0).getValue()) {
			return 1;
		} else if (dealer.get(0).getValue() < player.get(0).getValue()) {
			return 2;
		} else {
			if (dealer.get(1).getValue() > player.get(1).getValue()) {
				return 1;
			} else if (dealer.get(1).getValue() < player.get(1).getValue()) {
				return 2;
			} else {
				if (dealer.get(2).getValue() > player.get(2).getValue()) {
					return 1;
				} else if (dealer.get(2).getValue() < player.get(2).getValue()) {
					return 2;
				} else {
					return 0;
				}
			}
		}
		}
		return 0;
	}
	
	
	public static int getMaxPair(ArrayList<Card> deck) {
		if (deck.size() == 0 || evalHand(deck) != 5) {
			return 0;
		}
		if (deck.get(0).getValue() == deck.get(1).getValue()) {
			if (deck.get(0).getValue() == 14) {
				return 1;
			}
			return deck.get(0).getValue();
		}
		if (deck.get(0).getValue() == deck.get(2).getValue()) {
			if (deck.get(0).getValue() == 14) {
				return 1;
			}
			return deck.get(0).getValue();
		}
		if (deck.get(1).getValue() == 14) {
			return 1;
		}
		return deck.get(1).getValue();
	}
	public static int getThirdInMaxPair(ArrayList<Card> deck) {
		if (deck.size() == 0 || evalHand(deck) != 5) {
			return 0;
		}
		if (deck.get(0).getValue() != deck.get(1).getValue()) {
			if (deck.get(0).getValue() == 14) {
				return 1;
			}
			return deck.get(0).getValue();
		}
		if (deck.get(2).getValue() == 14) {
			return 1;
		}
		return deck.get(2).getValue();
	}
}
