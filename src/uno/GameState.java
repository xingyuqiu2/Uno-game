package uno;

import uno.Cards.Card;

/**
 * GameState stores the game state information of the game.
 * When a card is ready to play, its validity needs to be checked
 * using information in game state. When a player end his turn,
 * game state should be updated.
 */
public class GameState {
	private static final int NUM_DRAW_TWO = 2;
	private static final int NUM_DRAW_FOUR = 4;
	private static final int GAME_ORDER_TOWARDS_RIGHT = 1;
	private static final int GAME_ORDER_TOWARDS_LEFT = -1;
	/**
	 * current valid color
	 */
	private Card.Color validColor;
	/**
	 * current valid symbol
	 */
	private Card.Symbol validSymbol;
	/**
	 * current valid number, only used if latest card is normal, default -1
	 */
	private int validNumber;
	/**
	 * player id which is used as index for players
	 */
	private int currentPlayerId;
	/**
	 * the direction of game: 1 towards right or -1 towards left
	 */
	private int gameOrder;
	/**
	 * number of cards that are stacked by the "draw two" card and "wild draw four" cards
	 */
	private int numCardsCumulated;
	/**
	 * number of players in the game
	 */
	private int numPlayer;
	/**
	 * number of consecutive Reverse Card played, 0 if succession breaks
	 */
	private int numConsecutiveReverseCard;
	
	/**
	 * Constructor for GameState.
	 * Initialize the game state according to the first normal card.
	 * @param paramColor color of the first normal card
	 * @param paramSymbol symbol of the first normal card
	 * @param paramNumber number of the first normal card
	 * @param paramNumPlayer number of players in the game
	 */
	public GameState(Card.Color paramColor, Card.Symbol paramSymbol, int paramNumber, int paramNumPlayer) {
		currentPlayerId = 0;
		validColor = paramColor;
		validSymbol = paramSymbol;
		validNumber = paramNumber;
		gameOrder = GAME_ORDER_TOWARDS_RIGHT;
		numCardsCumulated = 0;
		numPlayer = paramNumPlayer;
		numConsecutiveReverseCard = 0;
	}
	
	/**
	 * Update the game state if current player played a card
	 * and apply card effects on game state
	 * @param newColor color of the card played
	 * @param newSymbol symbol of the card played
	 * @param newNumber number of the card played, default -1
	 */
	public void updateGameState(Card.Color newColor, Card.Symbol newSymbol, int newNumber) {
		validColor = newColor;
		validSymbol = newSymbol;
		validNumber = newNumber;
		
		switch(newSymbol) {
		case drawTwo:
			numCardsCumulated += NUM_DRAW_TWO;
			numConsecutiveReverseCard = 0;
			break;
		case normal:
			numCardsCumulated = 0;
			numConsecutiveReverseCard = 0;
			break;
		case reverse:
			reversePlayerOrder();
			numCardsCumulated = 0;
			numConsecutiveReverseCard ++;
			break;
		case skip:
			updateCurrentPlayerId();
			numCardsCumulated = 0;
			numConsecutiveReverseCard = 0;
			break;
		case wild:
			numCardsCumulated = 0;
			numConsecutiveReverseCard = 0;
			break;
		case wildDrawFour:
			numCardsCumulated += NUM_DRAW_FOUR;
			numConsecutiveReverseCard = 0;
			break;
		default:
			break;
			
		}
		updateCurrentPlayerId();
	}
	
	/**
	 * Update game state if current player did not played a card
	 * either because the player doesn't have a valid card or skip on penalty
	 */
	public void updateGameStateByPenalty() {
		numCardsCumulated = 0;
		numConsecutiveReverseCard = 0;
		updateCurrentPlayerId();
	}
	
	/**
	 * Update the player id of current player determined by the game order
	 */
	public void updateCurrentPlayerId() {
		currentPlayerId = (currentPlayerId + gameOrder + numPlayer) % numPlayer;
	}
	
	/**
	 * Reverse the order of the game
	 */
	public void reversePlayerOrder() {
		gameOrder = gameOrder == GAME_ORDER_TOWARDS_RIGHT ? GAME_ORDER_TOWARDS_LEFT : GAME_ORDER_TOWARDS_RIGHT;
	}
	
	/**
	 * Get the current valid color to play
	 * @return validColor valid color
	 */
	public Card.Color getValidColor() {
		return validColor;
	}
	
	/**
	 * Get the current valid symbol to play
	 * @return validSymbol valid symbol
	 */
	public Card.Symbol getValidSymbol() {
		return validSymbol;
	}
	
	/**
	 * Get the current valid number to play
	 * if validSymbol is not normal, then number is -1 by default
	 * @return validNumber valid number
	 */
	public int getValidNumber() {
		return validNumber;
	}
	
	/**
	 * Get the player id of the current player, used as index in list of players
	 * @return currentPlayerId the index of player in list of players
	 */
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}
	
	/**
	 * Get the number of cards stacked by draw two and draw four cards
	 * @return numCardsCumulated number of card stacked
	 */
	public int getNumCardsCumulated() {
		return numCardsCumulated;
	}
	
	/**
	 * Get the number of Reverse Card in succession
	 * @return numConsecutiveReverseCard number of reverse card in succession
	 */
	public int getNumConsecutiveReverseCard() {
		return numConsecutiveReverseCard;
	}
	
	/**
	 * Get the current game order
	 * @return gameOrder direction of game
	 */
	public int getGameOrder() {
		return gameOrder;
	}
}
