package uno.Players;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import uno.Cards.Card;

/**
 * Player is the player of the uno game. Player has name, id, and hand which
 * contains list of cards. Player can add cards to hand or remove cards from hand.
 * Player can also choose which card to play or skip.
 */
public abstract class Player {
	protected static final int INDEX_SKIP = -1;
	
	/**
	 * player id, used as index
	 */
	protected final int player_id;
	/**
	 * name of the player
	 */
	protected final String name;
	/**
	 * list of cards in hand
	 */
	protected ArrayList<Card> hand;
	/**
	 * whether current player can make action now.
	 * If true, then player can choose card or choose color.
	 */
	public boolean canMakeAction = false;
	/**
	 * index of card selected in hand
	 */
	protected int cardIndexSelected;
	/**
	 * color selected for the wild type card
	 */
	protected Card.Color colorSelected;
	/**
	 * whether player is AI
	 */
	protected boolean is_AI;
	
	/**
	 * Constructor of Player
	 * @param paramName name of the player
	 * @param paramPlayerId index of the player in list of players
	 */
	public Player(String paramName, int paramPlayerId) {
		name = paramName;
		player_id = paramPlayerId;
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Current player choose a card
	 * @return the index of card selected, -1 if no card to play and skip
	 */
	public abstract int chooseCardOrSkip(Card.Color validColor, Card.Symbol validSymbol, int validNumber, int numCardsCumulated);
	
	/**
	 * Current player choose one color for wild or wildDrawFour card
	 * @param cardSelected card that need to choose color
	 */
	public abstract void chooseColor(Card cardSelected);

	/**
	 * Add one card to hand
	 * @param cardToAdd card to add
	 * @return index of card added in hand
	 */
	public int addCardToHand(Card cardToAdd) {
		hand.add(cardToAdd);
		return hand.size() - 1;
	}
	
	/**
	 * Add multiple cards to hand
	 * @param listCardToAdd list of card to add
	 */
	public void addNCardToHand(ArrayList<Card> listCardToAdd) {
		for (int i = 0; i < listCardToAdd.size(); i++) {
			hand.add(listCardToAdd.get(i));
		}
	}
	
	/**
	 * Remove one card from hand
	 * @param indexInHand index of card in hand
	 * @return card removed
	 */
	public Card removeCardInHand(int indexInHand) {
		Card toRemove = hand.get(indexInHand);
		hand.remove(indexInHand);
		return toRemove;
	}
	
	/**
	 * Set the cardIndexSelected variable.
	 * It is called in ActionListener.
	 * @param handIndex
	 */
	public void setCardIndexSelected(int handIndex) {
		cardIndexSelected = handIndex;
	}
	
	/**
	 * Set the colorSelected to paramColorSelected.
	 * Function is called by GameStagePage class in GUI.
	 * @param paramColorSelected color selected by the player
	 */
	public void setColorSelected(Card.Color paramColorSelected) {
		colorSelected = paramColorSelected;
	}
	
	/**
	 * Get the player id, used as index in list of players
	 * @return player_id index of player in list of players
	 */
	public int getPlayerId() {
		return player_id;
	}

	/**
	 * Get the name of the player
	 * @return name name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the card of index in hand
	 * @param index position of card in hand
	 * @return card in hand
	 */
	public Card getCardInHand(int index) {
		return hand.get(index);
	}
	
	/**
	 * Check whether there is no card in hand
	 * @return boolean of whether hand is empty
	 */
	public boolean isHandEmpty() {
		return hand.size() == 0;
	}
	
	/**
	 * Get the number of cards in hand
	 * @return hand size
	 */
	public int getHandSize() {
		return hand.size();
	}
	
	/**
	 * Get the list of cards in hand
	 * @return ArrayList of cards
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	/**
	 * Change the hand to target hand.
	 * It is used for effect of Double Reverse.
	 * @param targetHand the hand of the target player
	 */
	public void changeHand(ArrayList<Card> targetHand) {
		hand = targetHand;
	}
	
	/**
	 * Check whether player is AI
	 * @return boolean whether player is AI
	 */
	public abstract boolean isAI();
	
}
