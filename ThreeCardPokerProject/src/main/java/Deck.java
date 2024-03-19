import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.image.Image;

public class Deck extends ArrayList<Card> {
	
	private ArrayList<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>();
		String prefix = "";
		String filename = "";
		deck.add(new Card('C', 14, "ace_of_clubs.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_clubs.png";
			deck.add(new Card('C', i, filename));
		}
		deck.add(new Card('C', 11,"jack_of_clubs.png"));
		deck.add(new Card('C', 12, "queen_of_clubs.png"));
		deck.add(new Card('C', 13, "king_of_clubs.png"));
		
		deck.add(new Card('D', 14, "ace_of_diamonds.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_diamonds.png";
			deck.add(new Card('D', i, filename));
		}
		deck.add(new Card('D', 11, "jack_of_diamonds.png"));
		deck.add(new Card('D', 12, "queen_of_diamonds.png"));
		deck.add(new Card('D', 13, "king_of_diamonds.png"));
		
		deck.add(new Card('S', 14,"ace_of_spades.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_spades.png";
			deck.add(new Card('S', i, filename));
		}
		deck.add(new Card('S', 11, "jack_of_spades.png"));
		deck.add(new Card('S', 12, "queen_of_spades.png"));
		deck.add(new Card('S', 13, "king_of_spades.png"));
		
		deck.add(new Card('H', 14, "ace_of_hearts.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_hearts.png";
			deck.add(new Card('H', i, filename));
		}
		deck.add(new Card('H', 11, "jack_of_hearts.png"));
		deck.add(new Card('H', 12, "queen_of_hearts.png"));
		deck.add(new Card('H', 13, "king_of_hearts.png"));
		Collections.shuffle(deck, new Random(System.currentTimeMillis()));
	}
	
	
	public void newDeck() {
		deck = new ArrayList<Card>();
		String prefix = "";
		String filename = "";
		deck.add(new Card('C', 14, "ace_of_clubs.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_clubs.png";
			deck.add(new Card('C', i, filename));
		}
		deck.add(new Card('C', 11,"jack_of_clubs.png"));
		deck.add(new Card('C', 12, "queen_of_clubs.png"));
		deck.add(new Card('C', 13, "king_of_clubs.png"));
		
		deck.add(new Card('D', 14, "ace_of_diamonds.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_diamonds.png";
			deck.add(new Card('D', i, filename));
		}
		deck.add(new Card('D', 11, "jack_of_diamonds.png"));
		deck.add(new Card('D', 12, "queen_of_diamonds.png"));
		deck.add(new Card('D', 13, "king_of_diamonds.png"));
		
		deck.add(new Card('S', 14,"ace_of_spades.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_spades.png";
			deck.add(new Card('S', i, filename));
		}
		deck.add(new Card('S', 11, "jack_of_spades.png"));
		deck.add(new Card('S', 12, "queen_of_spades.png"));
		deck.add(new Card('S', 13, "king_of_spades.png"));
		
		deck.add(new Card('H', 14, "ace_of_hearts.png"));
		for (int i = 2; i < 11; i++) {
			prefix = String.valueOf(i);
			filename = prefix + "_of_hearts.png";
			deck.add(new Card('H', i, filename));
		}
		deck.add(new Card('H', 11, "jack_of_hearts.png"));
		deck.add(new Card('H', 12, "queen_of_hearts.png"));
		deck.add(new Card('H', 13, "king_of_hearts.png"));
		Collections.shuffle(deck, new Random(System.currentTimeMillis()));
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
}
