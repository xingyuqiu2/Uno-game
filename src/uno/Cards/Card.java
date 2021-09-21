package uno.Cards;

/**
 * Card represents the card object of the uno game.
 * Card is an abstract class and has color and symbol as its attributes.
 * The validity of card is checked here and color can be set for wild color card.
 */
public abstract class Card {
	//card has attributes: color and symbol
	public enum Color {
		red, yellow, green, blue, wild;
	}
	
	public enum Symbol {
		normal, skip, reverse, drawTwo, wild, wildDrawFour;
	}
	
	/**
	 * color of card
	 */
	private Color color;
	/**
	 * symbol of card
	 */
	private final Symbol symbol;
	
	/**
	 * Constructor for Card
	 * @param paramColor color to set
	 * @param paramSymbol symbol to set
	 */
	public Card(final Color paramColor, final Symbol paramSymbol) {
		color = paramColor;
		symbol = paramSymbol;
	}
	
	/**
	 * Get the color of the card
	 * @return color
	 */
	public Color getCardColor() {
		return color;
	}
	
	/**
	 * Get the symbol of the card
	 * @return symbol
	 */
	public Symbol getCardSymbol() {
		return symbol;
	}
	
	/**
	 * Check whether the selected card is valid to play.
	 * This method need to be override by subclass
	 * @param validColor
	 * @param validSymbol
	 * @param validNumber
	 * @return true if selected card is valid to play
	 */
	public abstract boolean isValid(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated);
	
	/**
	 * Get the card number for normal card.
	 * Default is -1 if card is not normal.
	 * @return card number
	 */
	public int getCardNumber() {
		return -1;
	}
	
	/**
	 * Set the color of the card
	 * @param colorSelected the card to set
	 */
	public void setColor(Card.Color colorSelected) {
		color = colorSelected;
	}
	
	/**
	 * Return the name of the image corresponding to the card.
	 * It is used to retrieve image for button.
	 */
	public abstract String toString();
}
