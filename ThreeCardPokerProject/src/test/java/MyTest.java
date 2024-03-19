import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	//ThreeCardLogic Tests
	@Test
	void tclTestEvalHandsNoRank() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		deckP1.add(new Card('C', 14));
		deckP1.add(new Card('S', 7));
		deckP1.add(new Card('H', 10));
		deckP2.add(new Card('D', 14));
		deckP2.add(new Card('S', 3));
		deckP2.add(new Card('H', 11));
		deckP3.add(new Card('D', 4));
		deckP3.add(new Card('C', 8));
		deckP3.add(new Card('S', 9));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		assertEquals(0, ThreeCardLogic.evalHand(p1.getHand()));
		assertEquals(0, ThreeCardLogic.evalHand(p2.getHand()));
		assertEquals(0, ThreeCardLogic.evalHand(p3.getHand()));
	}
	@Test
	void tclTestEvalHandsMultipleRanksInDeck() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		// Three of a kind / flush
		deckP1.add(new Card('C', 10));
		deckP1.add(new Card('S', 10));
		deckP1.add(new Card('H', 10));
		// Straight flush / flush
		deckP2.add(new Card('D', 10));
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('D', 9));
		// Pair / Three of a Kind
		deckP3.add(new Card('D', 7));
		deckP3.add(new Card('C', 7));
		deckP3.add(new Card('S', 7));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		
		int resultP1 = ThreeCardLogic.evalHand(deckP1);
		int resultP2 = ThreeCardLogic.evalHand(deckP2);
		int resultP3 = ThreeCardLogic.evalHand(deckP3);
		
		assertEquals(2, resultP1);
		assertEquals(1, resultP2);
		assertEquals(2, resultP3);
	}
	@Test
	// Check if comparing the evalHand function produces the same result as compareHands
	void tclTestCompareHands() {
		Player p1 = new Player();
		
		Dealer theDealer = new Dealer();
		
		for (int i = 0; i < 100; i++) {
			p1.setHand(theDealer.dealHand());
			int pVal = ThreeCardLogic.evalHand(p1.getHand());
			int dVal = ThreeCardLogic.evalHand(theDealer.getDealersHand());
			if (pVal > dVal) {
				assertEquals(1, ThreeCardLogic.compareHands(p1.getHand(), theDealer.getDealersHand()));
			} else if (pVal < dVal) {
				assertEquals(2, ThreeCardLogic.compareHands(p1.getHand(), theDealer.getDealersHand()));
			}
		}
	}
	@Test
	// Test that players win when having a better deck.
	void tclTestPlayersWin() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		// Straight flush rank for players
		deckP1.add(new Card('D', 10));
		deckP1.add(new Card('D', 9));
		deckP1.add(new Card('D', 8));
		deckP2.add(new Card('D', 12));
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('D', 10));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		// Queen high
		Dealer dealer = new Dealer();
		ArrayList<Card> dh = new ArrayList<Card>();
		dh.add(new Card('D', 12));
		dh.add(new Card('S', 5));
		dh.add(new Card('H', 2));
		dealer.setDealerHand(dh);
		assertEquals(2, ThreeCardLogic.compareHands(dealer.getDealersHand(), p1.getHand()));
		assertEquals(2, ThreeCardLogic.compareHands(dealer.getDealersHand(), p2.getHand()));
	}
	@Test
	// Test that the dealer wins when its hand is better.
	void tclTestDealerWins() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		// High rank for players
		deckP1.add(new Card('D',14));
		deckP1.add(new Card('S', 3));
		deckP1.add(new Card('H', 5));
		deckP2.add(new Card('C', 5));
		deckP2.add(new Card('C', 3));
		deckP2.add(new Card('H',14));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		// Pair
		Dealer dealer = new Dealer();
		ArrayList<Card> dh = new ArrayList<Card>();
		dh.add(new Card('D', 12));
		dh.add(new Card('S', 12));
		dh.add(new Card('H', 2));
		dealer.setDealerHand(dh);
		assertEquals(1, ThreeCardLogic.compareHands(dealer.getDealersHand(), p1.getHand()));
		assertEquals(1, ThreeCardLogic.compareHands(dealer.getDealersHand(), p2.getHand()));
	}
	
	// Test that a tie occurs when ranks are the same.
	@Test
	void tclTestTied() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		// Three of a kind rank for players
		deckP1.add(new Card('D', 6));
		deckP1.add(new Card('S', 6));
		deckP1.add(new Card('H', 5));
		deckP2.add(new Card('D', 6));
		deckP2.add(new Card('S', 6));
		deckP2.add(new Card('H', 5));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		// Three of a kind rank for dealer
		Dealer dealer = new Dealer();
		ArrayList<Card> dh = new ArrayList<Card>();
		dh.add(new Card('C', 6));
		dh.add(new Card('H', 6));
		dh.add(new Card('H', 5));
		dealer.setDealerHand(dh);
		assertEquals(0, ThreeCardLogic.compareHands(dealer.getDealersHand(), p1.getHand()));
		assertEquals(0, ThreeCardLogic.compareHands(dealer.getDealersHand(), p2.getHand()));
	}
	@Test
	// Test hands with same rank but different third card
	void tclTestThirdCardTieBreaker() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		deckP1.add(new Card('D', 6));
		deckP1.add(new Card('S', 6));
		deckP1.add(new Card('H', 10));
		deckP2.add(new Card('D', 6));
		deckP2.add(new Card('S', 6));
		deckP2.add(new Card('H',14));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		Dealer dealer = new Dealer();
		ArrayList<Card> dh = new ArrayList<Card>();
		dh.add(new Card('C', 6));
		dh.add(new Card('H', 6));
		dh.add(new Card('H', 5));
		dealer.setDealerHand(dh);
		assertEquals(2, ThreeCardLogic.compareHands(dealer.getDealersHand(), p1.getHand()));
		assertEquals(1, ThreeCardLogic.compareHands(dealer.getDealersHand(), p2.getHand()));
	}
	@Test
	void tclTestPPWinningsWin() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		// Pair
		deckP1.add(new Card('D', 6));
		deckP1.add(new Card('S', 6));
		deckP1.add(new Card('H', 10));
		// Three of a kind
		deckP2.add(new Card('D', 6));
		deckP2.add(new Card('S', 6));
		deckP2.add(new Card('H', 6));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		
		int expectedP1 = (100*1) + 100;
		int expectedP2 = (100*30) + 100;
		int p1Winnings = ThreeCardLogic.evalPPWinnings(p1.getHand(), 100);
		int p2Winnings = ThreeCardLogic.evalPPWinnings(p2.getHand(), 100);
		assertEquals(expectedP1, p1Winnings);
		assertEquals(expectedP2, p2Winnings);
	}
	@Test
	void tclTestPPWinningsLoss() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		deckP1.add(new Card('C',14));
		deckP1.add(new Card('S', 3));
		deckP1.add(new Card('H', 4));
		deckP2.add(new Card('D',14));
		deckP2.add(new Card('S', 10));
		deckP2.add(new Card('H', 3));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		
		int expectedP1 = 0;
		int expectedP2 = 0;
		int p1Winnings = ThreeCardLogic.evalPPWinnings(p1.getHand(), 100);
		int p2Winnings = ThreeCardLogic.evalPPWinnings(p2.getHand(), 100);
		assertEquals(expectedP1, p1Winnings);
		assertEquals(expectedP2, p2Winnings);
	}
	@Test
	void tclTestPPWinningsNoBet() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		// Pair
		deckP1.add(new Card('C', 10));
		deckP1.add(new Card('S', 10));
		deckP1.add(new Card('H', 10));
		// Three of a kind
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('S', 11));
		deckP2.add(new Card('H', 11));
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		
		int expectedP1 = (0*40) + 0;
		int expectedP2 = (0*40) + 0;
		int p1Winnings = ThreeCardLogic.evalPPWinnings(p1.getHand(), 0);
		int p2Winnings = ThreeCardLogic.evalPPWinnings(p2.getHand(), 0);
		assertEquals(expectedP1, p1Winnings);
		assertEquals(expectedP2, p2Winnings);
	}
	@Test
	void tclTestEvalHand() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player p5 = new Player();
		Player p6 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		ArrayList<Card> deckP4 = new ArrayList<Card>();
		ArrayList<Card> deckP5 = new ArrayList<Card>();
		ArrayList<Card> deckP6 = new ArrayList<Card>();
		
		deckP1.add(new Card('C',14));
		deckP1.add(new Card('S', 3));
		deckP1.add(new Card('H', 5));
		
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('S', 11));
		deckP2.add(new Card('H', 2));
		
		deckP3.add(new Card('D', 11));
		deckP3.add(new Card('D', 11));
		deckP3.add(new Card('D', 2));
		
		deckP4.add(new Card('D', 7));
		deckP4.add(new Card('S', 8));
		deckP4.add(new Card('H', 9));
		
		deckP5.add(new Card('D', 7));
		deckP5.add(new Card('S', 7));
		deckP5.add(new Card('H', 7));
		
		deckP6.add(new Card('D', 9));
		deckP6.add(new Card('D', 10));
		deckP6.add(new Card('D', 11));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		p4.setHand(deckP4);
		p5.setHand(deckP5);
		p6.setHand(deckP6);
		
		// High
		assertEquals(0, ThreeCardLogic.evalHand(p1.getHand()));
		// Pair
		assertEquals(5, ThreeCardLogic.evalHand(p2.getHand()));
		// Flush
		assertEquals(4, ThreeCardLogic.evalHand(p3.getHand()));
		// Straight
		assertEquals(3, ThreeCardLogic.evalHand(p4.getHand()));
		// Three of a kind
		assertEquals(2, ThreeCardLogic.evalHand(p5.getHand()));
		// Straight Flush
		assertEquals(1, ThreeCardLogic.evalHand(p6.getHand()));
	}
	@Test
	void tclTestEvalHandRandomOrder() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player p5 = new Player();
		Player p6 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		ArrayList<Card> deckP4 = new ArrayList<Card>();
		ArrayList<Card> deckP5 = new ArrayList<Card>();
		ArrayList<Card> deckP6 = new ArrayList<Card>();
		
		deckP1.add(new Card('C',14));
		deckP1.add(new Card('S', 3));
		deckP1.add(new Card('H', 5));
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('S', 2));
		deckP2.add(new Card('H', 11));
		deckP3.add(new Card('D', 11));
		deckP3.add(new Card('D', 11));
		deckP3.add(new Card('D', 2));
		deckP4.add(new Card('D', 9));
		deckP4.add(new Card('S', 7));
		deckP4.add(new Card('H', 8));
		deckP5.add(new Card('D', 7));
		deckP5.add(new Card('S', 7));
		deckP5.add(new Card('H', 7));
		deckP6.add(new Card('D', 10));
		deckP6.add(new Card('D', 11));
		deckP6.add(new Card('D', 9));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		p4.setHand(deckP4);
		p5.setHand(deckP5);
		p6.setHand(deckP6);
		
		// High
		assertEquals(0, ThreeCardLogic.evalHand(p1.getHand()));
		// Pair
		assertEquals(5, ThreeCardLogic.evalHand(p2.getHand()));
		// Flush
		assertEquals(4, ThreeCardLogic.evalHand(p3.getHand()));
		// Straight
		assertEquals(3, ThreeCardLogic.evalHand(p4.getHand()));
		// Three of a kind
		assertEquals(2, ThreeCardLogic.evalHand(p5.getHand()));
		// Straight Flush
		assertEquals(1, ThreeCardLogic.evalHand(p6.getHand()));
	}
	@Test
	// Test whether a deck that qualifies for multiple rankings passes with the correct amount.
	void tclTestEvalPPWinningsSameDeckMultipleRanks() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		// Three of a kind / flush
		deckP1.add(new Card('C', 10));
		deckP1.add(new Card('S', 10));
		deckP1.add(new Card('H', 10));
		// Straight flush / flush
		deckP2.add(new Card('D', 11));
		deckP2.add(new Card('D', 10));
		deckP2.add(new Card('D', 9));
		// Pair / Three of a Kind
		deckP3.add(new Card('D', 7));
		deckP3.add(new Card('C', 7));
		deckP3.add(new Card('S', 7));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		
		int resultP1 = ThreeCardLogic.evalPPWinnings(p1.getHand(), 100);
		int resultP2 = ThreeCardLogic.evalPPWinnings(p2.getHand(), 100);
		int resultP3 = ThreeCardLogic.evalPPWinnings(p3.getHand(), 100);
		
		assertEquals((30*100) + 100, resultP1);
		assertEquals((40*100) + 100, resultP2);
		assertEquals((30*100) + 100, resultP3);
	}
	
	@Test
	void tclTestCompareHandsStraightRankDiffSuit() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		
		deckP1.add(new Card('C', 3));
		deckP1.add(new Card('D', 2));
		deckP1.add(new Card('C',14));
		
		deckP2.add(new Card('D',14));
		deckP2.add(new Card('H', 3));
		deckP2.add(new Card('S', 2));
		
		deckP3.add(new Card('S', 2));
		deckP3.add(new Card('C', 3));
		deckP3.add(new Card('H',14));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		
		Dealer dealer = new Dealer();
		ArrayList<Card> deckDealer = new ArrayList<Card>();
		deckDealer.add(new Card('S', 3));
		deckDealer.add(new Card('D',14));
		deckDealer.add(new Card('C', 2));
		dealer.setDealerHand(deckDealer);
		
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p1.getHand()));
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p2.getHand()));
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p3.getHand()));
	}
	@Test
	void tclTestCompareHandsPairDiffSuits() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		
		deckP1.add(new Card('C', 2));
		deckP1.add(new Card('C',14));
		deckP1.add(new Card('H', 2));
		
		deckP2.add(new Card('D', 2));
		deckP2.add(new Card('D',14));
		deckP2.add(new Card('C', 2));
		
		deckP3.add(new Card('H',14));
		deckP3.add(new Card('H', 2));
		deckP3.add(new Card('C', 2));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		
		Dealer dealer = new Dealer();
		ArrayList<Card> deckDealer = new ArrayList<Card>();
		deckDealer.add(new Card('S', 2));
		deckDealer.add(new Card('S',14));
		deckDealer.add(new Card('D', 2));
		dealer.setDealerHand(deckDealer);
		
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p1.getHand()));
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p2.getHand()));
		assertEquals(0, ThreeCardLogic.compareHands(deckDealer, p3.getHand()));
	}
	@Test
	void tclTestCompareHandsFlushDiffSuits() {
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		ArrayList<Card> deckP3 = new ArrayList<Card>();
		
		deckP1.add(new Card('C', 11));
		deckP1.add(new Card('C', 2));
		deckP1.add(new Card('C', 7));
		
		deckP2.add(new Card('D', 7));
		deckP2.add(new Card('D', 5));
		deckP2.add(new Card('D', 2));
		
		deckP3.add(new Card('H', 14));
		deckP3.add(new Card('H', 2));
		deckP3.add(new Card('H', 7));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		p3.setHand(deckP3);
		
		Dealer dealer = new Dealer();
		ArrayList<Card> deckDealer = new ArrayList<Card>();
		deckDealer.add(new Card('S', 3));
		deckDealer.add(new Card('S', 7));
		deckDealer.add(new Card('S', 2));
		dealer.setDealerHand(deckDealer);
		
		assertEquals(2, ThreeCardLogic.compareHands(deckDealer, p1.getHand()));
		assertEquals(2, ThreeCardLogic.compareHands(deckDealer, p2.getHand()));
		assertEquals(1, ThreeCardLogic.compareHands(deckDealer, p3.getHand()));
	}
	@Test
	void tclTestCompareHandsThreeOfAKind() {
		Player p1 = new Player();
		Player p2 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		ArrayList<Card> deckP2 = new ArrayList<Card>();
		
		deckP1.add(new Card('C', 8));
		deckP1.add(new Card('D', 8));
		deckP1.add(new Card('H', 8));
		
		deckP2.add(new Card('D', 4));
		deckP2.add(new Card('S', 4));
		deckP2.add(new Card('H', 4));
		
		p1.setHand(deckP1);
		p2.setHand(deckP2);
		
		Dealer dealer = new Dealer();
		ArrayList<Card> deckDealer = new ArrayList<Card>();
		deckDealer.add(new Card('D', 6));
		deckDealer.add(new Card('C', 6));
		deckDealer.add(new Card('S', 6));
		dealer.setDealerHand(deckDealer);
		
		assertEquals(2, ThreeCardLogic.compareHands(deckDealer, p1.getHand()));
		assertEquals(1, ThreeCardLogic.compareHands(deckDealer, p2.getHand()));
	}
	@Test
	void tclTestEvalHandQueenHighKingHigh() {
		Player p1 = new Player();
		ArrayList<Card> deckP1 = new ArrayList<Card>();
		
		deckP1.add(new Card('C', 13));
		deckP1.add(new Card('D', 8));
		deckP1.add(new Card('H', 4));
		p1.setHand(deckP1);
		
		Dealer dealer = new Dealer();
		ArrayList<Card> deckDealer = new ArrayList<Card>();
		deckDealer.add(new Card('D', 5));
		deckDealer.add(new Card('S', 12));
		deckDealer.add(new Card('H', 2));
		dealer.setDealerHand(deckDealer);
		
		assertEquals(2, ThreeCardLogic.compareHands(dealer.getDealersHand(), p1.getHand()));
	}
	@Test
	// Expect dealer to almost always win, tie in rare cases where a straight flush is achieved.
	void tclTestDealerHighestRankvsRandomDecks() {
		Dealer dealer = new Dealer();
		ArrayList<Card> dealerHand = new ArrayList<Card>();
		dealerHand.add(new Card('S', 13));
		dealerHand.add(new Card('S', 12));
		dealerHand.add(new Card('S', 11));
		dealer.setDealerHand(dealerHand);
		for (int i = 0; i < 100; i++) {
			ArrayList<Card> test = new ArrayList<Card>();
			test = dealer.dealHand();
			if (test != dealer.getDealersHand()) {
				assertEquals(1, ThreeCardLogic.compareHands(dealer.getDealersHand(), test));
			}
		}
	}
	@Test
	// Stress test evalWinnings for correct amount; 
	void tclTestEvalWinningsStressTest() {
		Player p1 = new Player();
		Dealer dealer = new Dealer();
		int bet = 100;
		for (int i = 0; i < 100; i++) {
			p1.setHand(dealer.dealHand());
			int rank = ThreeCardLogic.evalHand(p1.getHand());
			if (rank == 5) {
				int exp = (1*bet) + bet;
				assertEquals(exp, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			} else if (rank == 4) {
				int exp = (3*bet) + bet;
				assertEquals(exp, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			} else if (rank == 3) {
				int exp = (6*bet) + bet;
				assertEquals(exp, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			} else if (rank == 2) {
				int exp = (30*bet) + bet;
				assertEquals(exp, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			} else if (rank == 1) {
				int exp = (40*bet) + bet;
				assertEquals(exp, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			} else if (rank == 0) {
				assertEquals(0, ThreeCardLogic.evalPPWinnings(p1.getHand(), bet));
			}
		}
	}
	
	
//	// Deck and Dealer Tests
	
	@Test
	// Test that dealHand gives three cards.
	void dealerTestdealHandSize() {
		Dealer dealer = new Dealer();
		Player p1 = new Player();
		for (int i = 0; i < 100; i++) {
			p1.setHand(dealer.dealHand());
			assertEquals(3, p1.getHand().size());
		}
	}
	@Test
	// Deal different hands to each player
	void dealerTestDifferentHands() {
		Player p1 = new Player();
		Player p2 = new Player();
		Dealer dealer = new Dealer();
		for (int i = 0; i < 100; i++) {
			p1.setHand(dealer.dealHand());
			p2.setHand(dealer.dealHand());
			assertEquals(false, sameHand(p1.getHand(), p2.getHand()));
		}
	}
	@Test
	// Deal new deck that is different
	void deckTestNewDeck() {
		Deck deck = new Deck();
		ArrayList<Card> oldDeck = deck.getDeck();
		deck.newDeck();
		assertEquals(false, oldDeck.equals(deck.getDeck()));
	}
	@Test
	// Deal more than 52 cards
	void dealerTestResetDeckTest() {
		Dealer dealer = new Dealer();
		for (int i = 0; i < 50; i++) {
			dealer.dealHand();
		}
		// Insure deck has correct size after 50 deals
		assertEquals(43, dealer.getTheDeck().getDeck().size());
	}
	@Test
	// Check for deck size of 52
	void deckTestFiftyTwoCards() {
		Deck deck = new Deck();
		assertEquals(52, deck.getDeck().size());
	}
	@Test
	// Test for all 4 suits
	void deckTestContainFourSuits() {
		Deck deck = new Deck();
		ArrayList<Character> suits = new ArrayList<Character>();
		ArrayList<Card> deckCards = deck.getDeck();
		for (int i = 0; i < 52; i++) {
			if (!suits.contains(deckCards.get(i).getSuit())) {
				suits.add(deckCards.get(i).getSuit());
			}
		}
		assertEquals(4, suits.size());
	}
	@Test
	// Test for 52 unique cards
	void deckTestUniqueCards() {
		Deck deck = new Deck();
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Card> deckCards = deck.getDeck();
		for (int i = 0; i < 52; i++) {
			// Insure that there are no duplicate cards
			if (!cards.contains(deckCards.get(i))) {
				cards.add(deckCards.get(i));
			}
		}
		assertEquals(52, cards.size());
	}
	@Test
	// Test that a player never receives the same hand as the dealer
	void dealerTestdealHandDifference() {
		Dealer dealer = new Dealer();
		Player p1 = new Player();
		for (int i = 0; i < 400; i++) {
			p1.setHand(dealer.dealHand());
			assertEquals(false, dealer.getDealersHand().equals(p1.getHand()));
		}
	}
	@Test
	// Test that cards are shuffled
	void deckTestShuffledCheck() {
		Deck deck = new Deck();
		ArrayList<Card> inOrder = createInOrderArray();
		assertEquals(false, deck.getDeck().equals(inOrder));
	}
	@Test
	// Test that cards are never shuffled the same way
	void deckTestNewDeckDifferentShuffle() {
		Deck deck = new Deck();
		for (int i = 0; i < 100; i++) {
			ArrayList<Card> deck1 = deck.getDeck();
			deck.newDeck();
			ArrayList<Card> deck2 = deck.getDeck();
			assertEquals(false, deck1.equals(deck2));
		}
	}
	// Used in dealer tests
	boolean sameHand(ArrayList<Card> h1, ArrayList<Card> h2) {
		int same = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (h1.get(i) == h2.get(j)) {
					same++;
				}
			}
		}
		if (same > 1) {
			return true;
		}
		return false;
	}
	ArrayList<Card> createInOrderArray() {
		ArrayList<Card> deck = new ArrayList<Card>();
		deck.add(new Card('C', 14));
		for (int i = 2; i < 11; i++) {
			deck.add(new Card('C', i));
		}
		deck.add(new Card('C', 11));
		deck.add(new Card('C', 12));
		deck.add(new Card('C', 13));
		
		deck.add(new Card('D', 14));
		for (int i = 2; i < 11; i++) {
			deck.add(new Card('D', i));
		}
		deck.add(new Card('D', 11));
		deck.add(new Card('D', 12));
		deck.add(new Card('D', 13));
		
		deck.add(new Card('S', 14));
		for (int i = 2; i < 11; i++) {
			deck.add(new Card('S', i));
		}
		deck.add(new Card('S', 11));
		deck.add(new Card('S', 12));
		deck.add(new Card('S', 13));
		
		deck.add(new Card('H', 14));
		for (int i = 2; i < 11; i++) {
			deck.add(new Card('H', i));
		}
		deck.add(new Card('H', 11));
		deck.add(new Card('H', 12));
		deck.add(new Card('H', 13));
		return deck;
	}
}
