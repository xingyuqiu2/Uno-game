package uno.Cards;

/**
 * WildActionCard is the wild symbol card that extends Card.
 */
public class WildActionCard extends Card {
	public WildActionCard() {
		super(Card.Color.wild, Card.Symbol.wild);
	}

	@Override
	public boolean isValid(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated) {
		if (numCardsCumulated != 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "wild";
	}
	
}
