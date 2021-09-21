package uno;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import uno.Cards.Card;
import uno.Players.HumanPlayer;
import uno.Players.Player;
import view.EndingScene;
import view.GameStagePage;

/**
 * Game is used to start game and perform turn for every player.
 * It interacts with player to decide which card to play and update
 * the game state when player finish their actions for each turn.
 * Game will end when a player has no cards in hand.
 */
public class Game {
	/**
	 * value of game order towards right
	 */
	private static final int GAME_ORDER_TOWARDS_RIGHT = 1;
	/**
	 * index for skip
	 */
	private static final int INDEX_SKIP = -1;
	/**
	 * one second, used in wait function
	 */
	private static final int ONE_SECOND = 1;
	/**
	 * two second, used in wait function
	 */
	private static final int TWO_SECOND = 2;
	/**
	 * message for invalid card pick
	 */
	private static final String MESSAGE_INVALID_CARD = "<html>Please choose a valid card</html>";
	/**
	 * message for wait and next player after the player turn
	 */
	private static final String MESSAGE_WAIT = "<html>Please wait for 2 seconds. Next player is ";
	/**
	 * list of Player
	 */
	private ArrayList<Player> players;
	/**
	 * deck of draw pile and discard pile
	 */
	private Deck deck;
	/**
	 * holds the reference of game state
	 */
	private GameState gameState;
	/**
	 * final winner of the game
	 */
	private Player winner;
	/**
	 * holds the reference of game stage page
	 */
	private GameStagePage gameStagePage;
	/**
	 * number of players
	 */
	private int numPlayer = 0;
	/**
	 * whether need to call sleep function, used in test to disable sleep
	 */
	public boolean needSleep = true;
	
	/**
	 * Constructor for Game
	 * @param par_players list of players
	 */
	public Game(ArrayList<Player> par_players) {
		//set the players and number of players
		players = par_players;
		numPlayer = players.size();
		
		//create deck and initialize draw pile
		deck = new Deck();
		deck.initDrawPile();
		//get the first card on top of the draw pile
		Card firstCard = deck.drawOneCard();
		//while the first card is not normal, discard it and draw another card
		while (firstCard.getCardSymbol() != Card.Symbol.normal) {
			deck.discardCard(firstCard);
			firstCard = deck.drawOneCard();
		}
		//discard first card
		deck.discardCard(firstCard);
		int cardNumber = firstCard.getCardNumber();
		//initialize the initial game state
		gameState = new GameState(firstCard.getCardColor(), firstCard.getCardSymbol(), cardNumber, numPlayer);
		//Assigning 7 cards to each player
		for (int player_i = 0; player_i < numPlayer; player_i++) {
			players.get(player_i).addNCardToHand(deck.drawNCard(7));
		}
	}
	
	/**
	 * Start the main game loop, 
	 * Check the game end condition at the end of while.
	 */
	public void startGame() {
		while (true) {
			//get current player
			int currentPlayerId = gameState.getCurrentPlayerId();
			Player currentPlayer = players.get(currentPlayerId);
			//update view
			gameStagePage.updateView();
			if (currentPlayer.isAI()) {
				//sleep 1 second for AI
				wait(ONE_SECOND);
			}
			//start current player's turn
			playerTurn(currentPlayer);
			//show wait message and update hand view and last card view
			Player nextPlayer = players.get(gameState.getCurrentPlayerId());
			gameStagePage.showMessage(MESSAGE_WAIT + nextPlayer.getName() + "</html>");
			gameStagePage.updateHandView();
			gameStagePage.updateLastCardView();
			//check if the game is over by checking whether hand is empty
			if (currentPlayer.isHandEmpty()) {
				winner = currentPlayer;
				break;
			}
			//sleep 2 seconds
			wait(TWO_SECOND);
		}
		//Close game stage page and show game ending scene
		gameStagePage.close();
		new EndingScene(winner.getName());
		//print winner
		System.out.println("winner is " + winner.getName());
	}
	
	/**
	 * Turn for each player
	 * in which the player will choose their action
	 * @param currentPlayer
	 */
	public void playerTurn(Player currentPlayer) {
		currentPlayer.canMakeAction = true;
		//get the current game state
		Card.Color currentValidColor = gameState.getValidColor();
		Card.Symbol currentValidSymbol = gameState.getValidSymbol();
		int currentValidNumber = gameState.getValidNumber();
		int currentNumCardsCumulated = gameState.getNumCardsCumulated();
		
		int indexInHand;	//index of card in hand
		
		//get the index of card selected from hand
		indexInHand = currentPlayer.chooseCardOrSkip(currentValidColor, currentValidSymbol, currentValidNumber, currentNumCardsCumulated);
		//while card selected is not valid to play, get the card index selected by the current player
		while (indexInHand != INDEX_SKIP && currentPlayer.getCardInHand(indexInHand).isValid
				(currentValidColor, currentValidSymbol, currentValidNumber, currentNumCardsCumulated) == false) {
			gameStagePage.showMessage(MESSAGE_INVALID_CARD);
			indexInHand = currentPlayer.chooseCardOrSkip(currentValidColor, currentValidSymbol, currentValidNumber, currentNumCardsCumulated);
		}
		currentPlayer.canMakeAction = false;
		
		//player need to skip this round on the penalty
		//OR player doesn't have a card to play and need to draw one card
		if (indexInHand == INDEX_SKIP) {
			indexInHand = actionForNoValidCard(currentPlayer);
		} 
		//player has a valid card to play
		if (indexInHand != INDEX_SKIP) {
			playCard(currentPlayer, indexInHand);
		}
	}
	
	/**
	 * Actions for player need to skip this round on the penalty 
	 * OR player doesn't have a card to play and need to draw one card.
	 * Game state is updated for each case.
	 * @param currentPlayer current player
	 * @return index in hand if player draws a valid card, -1 otherwise
	 */
	public int actionForNoValidCard(Player currentPlayer) {
		int indexInHand = INDEX_SKIP;
		//get the current game state
		Card.Color currentValidColor = gameState.getValidColor();
		Card.Symbol currentValidSymbol = gameState.getValidSymbol();
		int currentValidNumber = gameState.getValidNumber();
		int currentNumCardsCumulated = gameState.getNumCardsCumulated();
				
		if (currentNumCardsCumulated != 0) {
			//add cards to hand on penalty, i.e. effects of drawTwo and wildDrawFour cards
			currentPlayer.addNCardToHand(deck.drawNCard(currentNumCardsCumulated));
			gameState.updateGameStateByPenalty();
			//print
			System.out.println(currentPlayer.getName() + " receive penalty and add " + currentNumCardsCumulated + " cards");
		} else {
			//draw one card from draw pile
			Card cardSelected = deck.drawOneCard();
			if (cardSelected == null) {
				//no card left in deck, just skip
				gameState.updateGameStateByPenalty();
				//print
				System.out.println("No card left in deck, need to skip");
				return indexInHand;
			}
			//get the index in hand for the card drawn
			//the index will be used later to check whether card drawn need to be played
			indexInHand = currentPlayer.addCardToHand(cardSelected);
			//print
			System.out.println(currentPlayer.getName() + " choose to draw one card from deck");
			//card drawn is not valid to play, need to skip. Card Drawn valid condition is handled in playCard
			if (cardSelected.isValid(currentValidColor, currentValidSymbol, currentValidNumber, currentNumCardsCumulated) == false) {
				indexInHand = INDEX_SKIP;
				gameState.updateGameStateByPenalty();
				//print
				System.out.println(currentPlayer.getName() + " cannot play this card and skip");
			}
		}
		return indexInHand;
	}
	
	/**
	 * Current player play the selected card.
	 * Game state is updated according to the card played.
	 * Card is then discarded.
	 * @param currentPlayer current player
	 * @param indexInHand index of card in hand
	 */
	public void playCard(Player currentPlayer, int indexInHand) {
		//get the card selected
		Card cardSelected = currentPlayer.getCardInHand(indexInHand);
		//player choose color if card is wild
		if (cardSelected.getCardColor() == Card.Color.wild) {
			if (!currentPlayer.isAI()) {
				gameStagePage.showPanelChooseColor();
			}
			currentPlayer.canMakeAction = true;
			currentPlayer.chooseColor(cardSelected);
			currentPlayer.canMakeAction = false;
		}
		//update the game state
		int cardNumber = cardSelected.getCardNumber();
		gameState.updateGameState(cardSelected.getCardColor(), cardSelected.getCardSymbol(), cardNumber);
		//print
		if (cardSelected.getCardSymbol() == Card.Symbol.normal) {
			System.out.println(currentPlayer.getName() + " play " + cardSelected.getCardColor()
				+ " color number " + cardSelected.getCardNumber() + " card");
		} else {
			System.out.println(currentPlayer.getName() + " play " + cardSelected.getCardColor() 
				+ " color " + cardSelected.getCardSymbol() + " card");
		}
		//player remove this card from hand
		Card toDiscard = currentPlayer.removeCardInHand(indexInHand);
		//if card to discard is wild card, then reset its color to wild
		if (toDiscard.getCardSymbol() == Card.Symbol.wild || toDiscard.getCardSymbol() == Card.Symbol.wildDrawFour) {
			toDiscard.setColor(Card.Color.wild);
		}
		//discard card to discard pile
		deck.discardCard(toDiscard);
		//if player has no card in hand, then return
		if (currentPlayer.isHandEmpty()) {
			return;
		}
		//if card symbol is reverse and two reverses are played in succession, 
		//call doubleReverseRule, i.e. switch hands for all players in the game order direction
		if (cardSelected.getCardSymbol() == Card.Symbol.reverse && gameState.getNumConsecutiveReverseCard() >= 2) {
			doubleReverseRule();
		}
		//if card symbol is drawTwo, 
		//call splitDrawRule, i.e. The person immediately following and preceding the player draws 1 card.
		if (cardSelected.getCardSymbol() == Card.Symbol.drawTwo) {
			splitDrawRule(currentPlayer);
		}
	}
	
	/**
	 * Custom Rule called Double Reverse.
	 * The function is called when two reverses are played in succession. Your hand is passed to the person next to you 
	 * in the direction of the last reverse. The entire table will then have switched hands.
	 */
	public void doubleReverseRule() {
		int gameOrder = gameState.getGameOrder();
		//whether game order is in the direction towards right
		if (gameOrder == GAME_ORDER_TOWARDS_RIGHT) {
			//store the temporary hand of the leftmost player
			ArrayList<Card> tempHand = players.get(0).getHand();
			//for each player in the direction towards left
			for (int player_i = numPlayer - 1; player_i >= 0; player_i--) {
				//pass hand from left to right
				if (player_i != 0) {
					passHandToNextPerson(player_i, gameOrder);
				} else {
					players.get(1).changeHand(tempHand);
				}
			}
		} else {
			//store the temporary hand of the rightmost player
			ArrayList<Card> tempHand = players.get(numPlayer - 1).getHand();
			//for each player in the direction towards right
			for (int player_i = 0; player_i < numPlayer; player_i++) {
				//pass hand from right to left
				if (player_i != numPlayer - 1) {
					passHandToNextPerson(player_i, gameOrder);
				} else {
					players.get(numPlayer - 2).changeHand(tempHand);
				}
			}
		}
		//print
		System.out.println("All players switch hands by Double Reverse Rule");
	}
	
	/**
	 * Pass the hand of current player to the next person.
	 * It is called by doubleReverseRule.
	 * @param currPlayerIdx index of the current player
	 * @param gameOrder game order/direction
	 */
	private void passHandToNextPerson(int currPlayerIdx, int gameOrder) {
		int nextPlayerIdx = (currPlayerIdx + gameOrder + numPlayer) % numPlayer;
		players.get(nextPlayerIdx).changeHand(players.get(currPlayerIdx).getHand());
	}
	
	/**
	 * Custom Rule called Split Draw.
	 * The function is called when a draw 2 card is played.
	 * The person immediately following and preceding the player draws 1 card.
	 * @param currentPlayer current player
	 */
	public void splitDrawRule(Player currentPlayer) {
		//get the index of current, previous, next player
		int currentPlayer_i = currentPlayer.getPlayerId();
		int nextPlayer_i = (currentPlayer_i + 1 + numPlayer) % numPlayer;
		int PreviousPlayer_i = (currentPlayer_i - 1 + numPlayer) % numPlayer;
		//add one card to previous and next player
		players.get(PreviousPlayer_i).addCardToHand(deck.drawOneCard());
		players.get(nextPlayer_i).addCardToHand(deck.drawOneCard());
		//print
		System.out.println("Players before and after " + currentPlayer.getName() + " draw one card by Split Draw Rule");
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameStagePage(GameStagePage paramGameStagePage) {
		gameStagePage = paramGameStagePage;
	}
	
	public Card getLastCard() {
		return deck.getTopCardDiscardPile();
	}
	
	/**
	 * Get the name of the winner
	 * @return String winner name
	 */
	public String getWinnerName() {
		return winner.getName();
	}
	
	/**
	 * Wait function which calls sleep.
	 * @param second number of seconds to wait
	 */
	public void wait(int second) {
		if (needSleep) {
			try {
				TimeUnit.SECONDS.sleep(second);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
