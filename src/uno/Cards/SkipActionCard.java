package uno.Cards;

/**
 * SkipActionCard is the skip symbol card that extends Card.
 */
public class SkipActionCard extends Card {
	public SkipActionCard(Card.Color paramColor) {
		super(paramColor, Card.Symbol.skip);
	}

	@Override
	public boolean isValid(Color validColor, Symbol validSymbol, int validNumber, int numCardsCumulated) {
		if (numCardsCumulated != 0) {
			if (validSymbol == Card.Symbol.drawTwo || validSymbol == Card.Symbol.wildDrawFour) {
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
		return getCardColor() + "_Skip";
	}

}
