import javafx.scene.image.Image;

public class Card {
	char suit;
	int value;
	String imageLocation;
	
	public Card (char s, int v) {
		suit = s;
		value = v;
		imageLocation= "";
	}
	public Card(char s, int v, String iLocation) {
		suit = s;
		value = v;
		this.imageLocation = iLocation;
	}
	public char getSuit() {
		return suit;
	}
	public int getValue() {
		return value;
	}
	public String getiLocation() {
		return imageLocation;
	}
	public void setiLocation(String location) {
		this.imageLocation = location;
	}
}
