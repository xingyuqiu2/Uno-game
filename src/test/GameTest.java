package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uno.Game;
import uno.Cards.Card;
import uno.Cards.DrawTwoActionCard;
import uno.Cards.NormalActionCard;
import uno.Cards.ReverseActionCard;
import uno.Cards.SkipActionCard;
import uno.Cards.WildActionCard;
import uno.Cards.WildDrawFourActionCard;
import uno.Players.BaselineAI;
import uno.Players.Player;
import view.GameStagePage;

class GameTest {

	@Test
	void testGame() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("Maxkim", 0));
		listPlayers.add(new BaselineAI("Zimin", 1));
		listPlayers.add(new BaselineAI("Maya", 2));
		listPlayers.add(new BaselineAI("Rose", 3));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		GameStagePage gameStagePage = new GameStagePage(game);
		game.setGameStagePage(gameStagePage);
		game.startGame();
		assertNotEquals(null, game.getWinnerName());
	}
	
	@Test
	void testPlayerTurn() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		listPlayers.add(new BaselineAI("player2", 1));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		//remove all cards in player's hands
		for (int i = 0; i < 7; i++) {
			listPlayers.get(0).removeCardInHand(0);
			listPlayers.get(1).removeCardInHand(0);
		}
		//add two wild card to first player's hand
		listPlayers.get(0).addCardToHand(new WildActionCard());
		listPlayers.get(0).addCardToHand(new WildActionCard());
		//add one wild card to second player's hand
		listPlayers.get(1).addCardToHand(new WildActionCard());
		
		game.playerTurn(listPlayers.get(0));
		game.playerTurn(listPlayers.get(1));
		assertTrue(listPlayers.get(1).isHandEmpty());
	}
	
	@Test
	void testActionForNoValidCard() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		listPlayers.add(new BaselineAI("player2", 1));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		
		game.getGameState().updateGameState(Card.Color.blue, Card.Symbol.drawTwo, -1);
		game.actionForNoValidCard(listPlayers.get(1));
		assertEquals(9, listPlayers.get(1).getHandSize());
		assertEquals(0, game.getGameState().getCurrentPlayerId());
		
		game.getGameState().updateGameState(Card.Color.red, Card.Symbol.normal, 5);
		game.actionForNoValidCard(listPlayers.get(1));
		assertEquals(10, listPlayers.get(1).getHandSize());
		
		listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player3", 0));
		listPlayers.add(new BaselineAI("player4", 1));
		game = new Game(listPlayers);
		game.needSleep = false;
		
		for (int i = 0; i < 27; i++) {
			game.getGameState().updateGameState(Card.Color.red, Card.Symbol.wildDrawFour, -1);
		}
		game.actionForNoValidCard(listPlayers.get(1));
		game.actionForNoValidCard(listPlayers.get(0));
		assertEquals(1, game.getGameState().getCurrentPlayerId());
	}
	
	@Test
	void testPlayCard() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		listPlayers.add(new BaselineAI("player2", 1));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		//remove all cards in player's hands
		for (int i = 0; i < 7; i++) {
			listPlayers.get(0).removeCardInHand(0);
			listPlayers.get(1).removeCardInHand(0);
		}
		//add two wild card to first player's hand
		listPlayers.get(0).addCardToHand(new WildActionCard());
		listPlayers.get(0).addCardToHand(new WildActionCard());
		listPlayers.get(1).addCardToHand(new NormalActionCard(Card.Color.blue, 1));
		listPlayers.get(1).addCardToHand(new NormalActionCard(Card.Color.blue, 2));
		
		game.playCard(listPlayers.get(0), 0);
		assertEquals(1, listPlayers.get(0).getHandSize());
		assertEquals(1, game.getGameState().getCurrentPlayerId());
		
		game.playCard(listPlayers.get(1), 0);
		assertEquals(1, listPlayers.get(1).getHandSize());
		assertEquals(0, game.getGameState().getCurrentPlayerId());
	}
	
	@Test
	void testDoubleReverseRule() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		listPlayers.add(new BaselineAI("player2", 1));
		listPlayers.add(new BaselineAI("player3", 2));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		//remove all cards in player's hands
		for (int i = 0; i < 7; i++) {
			listPlayers.get(0).removeCardInHand(0);
			listPlayers.get(1).removeCardInHand(0);
			listPlayers.get(2).removeCardInHand(0);
		}
		//add cards to player's hand
		listPlayers.get(0).addCardToHand(new ReverseActionCard(Card.Color.red));
		listPlayers.get(0).addCardToHand(new ReverseActionCard(Card.Color.blue));
		listPlayers.get(0).addCardToHand(new ReverseActionCard(Card.Color.green));
		listPlayers.get(0).addCardToHand(new ReverseActionCard(Card.Color.yellow));
		listPlayers.get(0).addCardToHand(new WildActionCard());
		listPlayers.get(1).addCardToHand(new WildDrawFourActionCard());
		listPlayers.get(2).addCardToHand(new ReverseActionCard(Card.Color.red));
		listPlayers.get(2).addCardToHand(new ReverseActionCard(Card.Color.blue));
		listPlayers.get(2).addCardToHand(new ReverseActionCard(Card.Color.green));
		listPlayers.get(2).addCardToHand(new ReverseActionCard(Card.Color.yellow));
		listPlayers.get(2).addCardToHand(new SkipActionCard(Card.Color.blue));
		
		//test switch all hands by direction to the right
		game.playerTurn(listPlayers.get(0));
		game.playerTurn(listPlayers.get(2));
		
		assertEquals(Card.Symbol.skip, listPlayers.get(0).getCardInHand(3).getCardSymbol());
		assertEquals(Card.Symbol.wild, listPlayers.get(1).getCardInHand(3).getCardSymbol());
		assertEquals(Card.Symbol.wildDrawFour, listPlayers.get(2).getCardInHand(0).getCardSymbol());
		
		
		//test switch all hands by direction to the left
		game.playerTurn(listPlayers.get(0));
		
		assertEquals(Card.Symbol.skip, listPlayers.get(2).getCardInHand(2).getCardSymbol());
		assertEquals(Card.Symbol.wild, listPlayers.get(0).getCardInHand(3).getCardSymbol());
		assertEquals(Card.Symbol.wildDrawFour, listPlayers.get(1).getCardInHand(0).getCardSymbol());
	}
	
	@Test
	void testSplitDrawRule() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		listPlayers.add(new BaselineAI("player2", 1));
		listPlayers.add(new BaselineAI("player3", 2));
		
		Game game = new Game(listPlayers);
		game.needSleep = false;
		//remove all cards in player's hands
		for (int i = 0; i < 7; i++) {
			listPlayers.get(0).removeCardInHand(0);
			listPlayers.get(1).removeCardInHand(0);
			listPlayers.get(2).removeCardInHand(0);
		}
		//add cards to player's hand
		listPlayers.get(0).addCardToHand(new DrawTwoActionCard(Card.Color.red));
		listPlayers.get(0).addCardToHand(new DrawTwoActionCard(Card.Color.blue));
		listPlayers.get(0).addCardToHand(new DrawTwoActionCard(Card.Color.green));
		listPlayers.get(0).addCardToHand(new DrawTwoActionCard(Card.Color.yellow));
		
		game.playerTurn(listPlayers.get(0));
		
		assertEquals(1, listPlayers.get(2).getHandSize());
		assertEquals(1, listPlayers.get(1).getHandSize());
	}
	
	@Test
	void testWait() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("player1", 0));
		
		Game game = new Game(listPlayers);
		//test if wait can function without crash
		game.wait(1);
	}

}
