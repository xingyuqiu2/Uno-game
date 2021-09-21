package uno.Cards;

/**
 * DrawTwoActionCard is the draw two symbol card that extends Card.
 */
public class DrawTwoActionCard extends Card {
	public DrawTwoActionCard(Card.Color paramColor) {
		super(paramColor, Card.Symbol.drawTwo);
	}
	
	@Override
	public boolean isValid(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated) {
		if (numCardsCumulated != 0) {
			if (validSymbol == Card.Symbol.wildDrawFour) {
				return false;
			}
		}
		if (this.getCardColor() == validColor) {
			return true;
		}
		if (this.getCardSymbol() == validSymbol) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getCardColor() + "_DrawTwo";
	}

}
