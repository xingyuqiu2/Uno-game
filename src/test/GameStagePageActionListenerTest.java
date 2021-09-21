package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uno.Game;
import uno.Cards.Card;
import uno.Cards.WildActionCard;
import uno.Players.BaselineAI;
import uno.Players.HumanPlayer;
import uno.Players.Player;
import view.GameStagePage;

class GameStagePageActionListenerTest {
	private ArrayList<Player> listPlayers;
	private Game game;
	private GameStagePage gsp;
	
	@Test
	void testHideShow() {
		initGlobalVariables();
		
		gsp.updateView();
		gsp.buttonHideShow.doClick();
		assertEquals("<html>Cards are hided</html>", gsp.labelMessage.getText());
		gsp.buttonHideShow.doClick();
		assertEquals("<html>Cards are shown</html>", gsp.labelMessage.getText());
	}
	
	@Test
	void testChooseCard() {
		initGlobalVariables();
		
		gsp.updateView();
		listPlayers.get(0).canMakeAction = true;
		gsp.listButtonCards.get(1).doClick();
		assertEquals(1, listPlayers.get(0).chooseCardOrSkip(null, null, 0, 0));
	}
	
	@Test
	void testChooseColor() {
		initGlobalVariables();
		
		gsp.updateView();
		listPlayers.get(0).canMakeAction = true;
		WildActionCard wildCard = new WildActionCard();
		
		gsp.buttonChooseBlue.doClick();
		listPlayers.get(0).chooseColor(wildCard);
		assertEquals(Card.Color.blue, wildCard.getCardColor());
		
		gsp.buttonChooseGreen.doClick();
		listPlayers.get(0).chooseColor(wildCard);
		assertEquals(Card.Color.green, wildCard.getCardColor());
		
		gsp.buttonChooseRed.doClick();
		listPlayers.get(0).chooseColor(wildCard);
		assertEquals(Card.Color.red, wildCard.getCardColor());
		
		gsp.buttonChooseYellow.doClick();
		listPlayers.get(0).chooseColor(wildCard);
		assertEquals(Card.Color.yellow, wildCard.getCardColor());
	}
	
	@Test
	void testDeckButton() {
		initGlobalVariables();
		
		gsp.updateView();
		listPlayers.get(0).canMakeAction = true;
		gsp.buttonDeck.doClick();
		assertEquals(-1, listPlayers.get(0).chooseCardOrSkip(null, null, 0, 0));
		
		game.getGameState().updateGameState(Card.Color.blue, Card.Symbol.drawTwo, -1);
		gsp.updateView();
		listPlayers.get(1).canMakeAction = true;
		gsp.buttonDeck.doClick();
		assertEquals("<html>Please play a valid draw type card or skip</html>", gsp.labelMessage.getText());
	}
	
	@Test
	void testSkipButton() {
		initGlobalVariables();
		
		gsp.updateView();
		listPlayers.get(0).canMakeAction = true;
		gsp.buttonSkip.doClick();
		assertEquals(-1, listPlayers.get(0).chooseCardOrSkip(null, null, 0, 0));
		
		game.getGameState().updateGameState(Card.Color.blue, Card.Symbol.drawTwo, -1);
		gsp.updateView();
		listPlayers.get(1).canMakeAction = true;
		gsp.buttonSkip.doClick();
		assertEquals(-1, listPlayers.get(1).chooseCardOrSkip(null, null, 0, 0));
	}
	
	private void initGlobalVariables() {
		listPlayers = new ArrayList<Player>();
		listPlayers.add(new HumanPlayer("player0", 0));
		listPlayers.add(new HumanPlayer("player1", 1));
		listPlayers.add(new BaselineAI("player2", 2));
		game = new Game(listPlayers);
		gsp = new GameStagePage(game);
		game.setGameStagePage(gsp);
	}

}
