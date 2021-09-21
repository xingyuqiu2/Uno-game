package uno.Players;

import java.util.concurrent.Semaphore;

import uno.Cards.Card;
import uno.Cards.Card.Color;
import uno.Cards.Card.Symbol;

/**
 * Human player that extends Player.
 * It can choose card and choose color using GUI.
 *
 */
public class HumanPlayer extends Player{
	/**
	 * semaphore used to handle the GUI for choose card
	 */
	public Semaphore semaphoreCard;
	/**
	 * semaphore used to handle the GUI for choose color
	 */
	public Semaphore semaphoreColor;
	
	/**
	 * Constructor for HumanPlayer
	 * @param paramName name of player
	 * @param paramPlayerId index of player
	 */
	public HumanPlayer(String paramName, int paramPlayerId) {
		super(paramName, paramPlayerId);
		semaphoreCard = new Semaphore(0);
		semaphoreColor = new Semaphore(0);
		is_AI = false;
	}
	
	/**
	 * Use semaphore to detect whether player has chosen a card
	 * @return Index of the card selected in hand
	 */
	@Override
	public int chooseCardOrSkip(Color validColor, Symbol validSymbol, int validNumber, int numCardsCumulated) {
		try {
			semaphoreCard.acquire();
		} catch (InterruptedException e) {}
		return cardIndexSelected;
	}
	
	/**
	 * Use semaphore to detect whether player has chosen a color and then set the color.
	 */
	@Override
	public void chooseColor(Card cardSelected) {
		try {
			semaphoreColor.acquire();
		} catch (InterruptedException e) {}
		cardSelected.setColor(colorSelected);
	}

	@Override
	public boolean isAI() {
		return is_AI;
	}

}
