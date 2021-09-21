package uno.Cards;

/**
 * NormalActionCard is the normal symbol card that extends Card.
 * It has number as its private variable.
 */
public class NormalActionCard extends Card {
	private int number;
	
	public NormalActionCard(Card.Color paramColor, int paramNumber) {
		super(paramColor, Card.Symbol.normal);
		number = paramNumber;
	}

	@Override
	public boolean isValid(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated) {
		if (numCardsCumulated != 0) {
			if (validSymbol == Card.Symbol.drawTwo || validSymbol == Card.Symbol.wildDrawFour) {
				return false;
			}
		}
		if (this.getCardColor() == validColor) {
			return true;
		}
		if (validSymbol == Card.Symbol.normal && number == validNumber) {
			return true;
		}
		return false;
	}

	@Override
	public int getCardNumber() {
		return number;
	}

	@Override
	public String toString() {
		return getCardColor() + "_" + String.valueOf(number);
	}
}
