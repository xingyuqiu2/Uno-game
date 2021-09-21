package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import uno.Game;
import uno.Cards.Card;
import uno.Players.HumanPlayer;
import uno.Players.Player;
import view.GameStagePage;

/**
 *	ActionListener for the game stage page.
 *	It is used to detect the event for all the buttons and make actions accordingly.
 */
public class GameStagePageActionListener implements ActionListener {
	private static final String MESSAGE_DECK_DRAW_DENIED = "<html>Please play a valid draw type card or skip</html>";
	private static final String MESSAGE_CARDS_HIDE = "<html>Cards are hided</html>";
	private static final String MESSAGE_CARDS_SHOW = "<html>Cards are shown</html>";
	/**
	 * index used to represent player choose to skip
	 */
	private static final int INDEX_SKIP = -1;
	/**
	 * holds the reference to GameStagePage
	 */
	private GameStagePage gameStagePage;
	/**
	 * holds the reference to Game
	 */
	private Game game;
	
	/**
	 * Constructor for GameStagePageActionListener.
	 * @param paramGameStagePage GameStagePage instance
	 * @param paramGame Game instance
	 */
	public GameStagePageActionListener(GameStagePage paramGameStagePage, Game paramGame) {
		gameStagePage = paramGameStagePage;
		game = paramGame;
	}
	
	/**
	 * Process events when current player is not AI
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Player currentPlayer = gameStagePage.getCurrentPlayer();
		//if current player is AI, then ignore the event
		if (currentPlayer.isAI()) {
			return;
		}
		//hide or show button of cards
		if (e.getSource() == gameStagePage.buttonHideShow) {
			if (gameStagePage.getIsPanelHandShow() == true) {
				gameStagePage.hideCards();
				gameStagePage.showMessage(MESSAGE_CARDS_HIDE);
			} else {
				gameStagePage.updateHandView();
				gameStagePage.showMessage(MESSAGE_CARDS_SHOW);
			}
		}
		//if current player cannot make any action, then ignore the event other than hide or show cards
		if (currentPlayer.canMakeAction == false) {
			return;
		}
		//event for button of cards
		ArrayList<JButton> listButtonCards = gameStagePage.listButtonCards;
		int sizeHand = listButtonCards.size();
		for (int i = 0; i < sizeHand; i++) {
			if (e.getSource() == listButtonCards.get(i)) {
				setCardIndex(currentPlayer, i);
				break;
			}
		}
		//other event
		if (e.getSource() == gameStagePage.buttonChooseBlue) {
			setColor(currentPlayer, Card.Color.blue);
		} else if (e.getSource() == gameStagePage.buttonChooseGreen) {
			setColor(currentPlayer, Card.Color.green);
		} else if (e.getSource() == gameStagePage.buttonChooseRed) {
			setColor(currentPlayer, Card.Color.red);
		} else if (e.getSource() == gameStagePage.buttonChooseYellow) {
			setColor(currentPlayer, Card.Color.yellow);
		} else if (e.getSource() == gameStagePage.buttonDeck) {
			if (game.getGameState().getNumCardsCumulated() != 0) {
				gameStagePage.showMessage(MESSAGE_DECK_DRAW_DENIED);
			} else {
				setCardIndex(currentPlayer, INDEX_SKIP);
			}
		} else if (e.getSource() == gameStagePage.buttonSkip) {
			setCardIndex(currentPlayer, INDEX_SKIP);
		}
	}
	
	/**
	 * Set card index and release semaphoreCard
	 * @param currentPlayer current player
	 * @param index index selected in hand, -1 if skip
	 */
	private void setCardIndex(Player currentPlayer, int index) {
		currentPlayer.setCardIndexSelected(index);
		HumanPlayer humanPlayer = (HumanPlayer)currentPlayer;
		humanPlayer.semaphoreCard.release();
	}
	
	/**
	 * Set the color of wild card and release semaphoreColor
	 * @param currentPlayer current player
	 * @param color color selected
	 */
	private void setColor(Player currentPlayer, Card.Color color) {
		currentPlayer.setColorSelected(color);
		gameStagePage.hidePanelChooseColor();
		HumanPlayer humanPlayer = (HumanPlayer)currentPlayer;
		humanPlayer.semaphoreColor.release();
	}
}
