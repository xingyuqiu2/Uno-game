package uno.Cards;

/**
 * ReverseActionCard is the reverse symbol card that extends Card.
 */
public class ReverseActionCard extends Card {
	public ReverseActionCard(Card.Color paramColor) {
		super(paramColor, Card.Symbol.reverse);
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
		return getCardColor() + "_Reverse";
	}

}
