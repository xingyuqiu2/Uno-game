package uno.Cards;

/**
 * WildDrawFourActionCard is the wild draw four symbol card that extends Card.
 */
public class WildDrawFourActionCard extends Card {
	public WildDrawFourActionCard() {
		super(Card.Color.wild, Card.Symbol.wildDrawFour);
	}

	@Override
	public boolean isValid(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated) {
		if (numCardsCumulated == 0) {
			return true;
		}
		if (validSymbol == Card.Symbol.drawTwo) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "wildDrawFour";
	}
	
}
