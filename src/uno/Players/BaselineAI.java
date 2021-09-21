package uno.Players;

import java.util.Random;

import uno.Cards.Card;
import uno.Cards.Card.Color;
import uno.Cards.Card.Symbol;

/**
 * Baseline AI that extends Player.
 * It can choose card and choose color without using GUI.
 *
 */
public class BaselineAI extends Player {

	/**
	 * Constructor for BaselineAI
	 * @param paramName name of player
	 * @param paramPlayerId index of player
	 */
	public BaselineAI(String paramName, int paramPlayerId) {
		super(paramName, paramPlayerId);
		is_AI = true;
	}

	/**
	 * Choose a random valid card
	 */
	@Override
	public int chooseCardOrSkip(Color validColor, Symbol validSymbol, int validNumber, int numCardsCumulated) {
		//choose the first valid card in hand
		for (int hand_i = 0; hand_i < hand.size(); hand_i++) {
			Card cardSelected = hand.get(hand_i);
			if (cardSelected.isValid(validColor, validSymbol, validNumber, numCardsCumulated)) {
				return hand_i;
			}
		}
		//no valid card in hand
		return INDEX_SKIP;
	}

	/**
	 * Choose a random color
	 */
	@Override
	public void chooseColor(Card cardSelected) {
		//randomly choose a color
		Random random = new Random();
		int index = random.nextInt(Card.Color.values().length - 1);
		Card.Color colorSelected = Card.Color.values()[index];
		cardSelected.setColor(colorSelected);
	}

	@Override
	public boolean isAI() {
		return is_AI;
	}

}
