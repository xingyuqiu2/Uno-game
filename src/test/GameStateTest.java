package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uno.GameState;
import uno.Cards.Card;

class GameStateTest {

	@Test
	void testGameStateAndGet() {
		GameState gameState;
		
		gameState = new GameState(Card.Color.red, Card.Symbol.skip, -1, 5);
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.skip, gameState.getValidSymbol());
		assertEquals(0, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState = new GameState(Card.Color.green, Card.Symbol.normal, 7, 5);
		
		assertEquals(Card.Color.green, gameState.getValidColor());
		assertEquals(Card.Symbol.normal, gameState.getValidSymbol());
		assertEquals(7, gameState.getValidNumber());
		assertEquals(0, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
	}

	@Test
	void testUpdateGameState() {
		GameState gameState;
		
		gameState = new GameState(Card.Color.red, Card.Symbol.normal, 9, 5);
		gameState.updateGameState(Card.Color.yellow, Card.Symbol.wild, -1);
		
		assertEquals(Card.Color.yellow, gameState.getValidColor());
		assertEquals(Card.Symbol.wild, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(1, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.yellow, Card.Symbol.normal, 7);
		
		assertEquals(Card.Color.yellow, gameState.getValidColor());
		assertEquals(Card.Symbol.normal, gameState.getValidSymbol());
		assertEquals(7, gameState.getValidNumber());
		assertEquals(2, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.yellow, Card.Symbol.skip, -1);
		
		assertEquals(Card.Color.yellow, gameState.getValidColor());
		assertEquals(Card.Symbol.skip, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(4, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.red, Card.Symbol.skip, -1);
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.skip, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(1, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.red, Card.Symbol.drawTwo, -1);
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.drawTwo, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(2, gameState.getCurrentPlayerId());
		assertEquals(2, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.red, Card.Symbol.wildDrawFour, -1);
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.wildDrawFour, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(3, gameState.getCurrentPlayerId());
		assertEquals(6, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.red, Card.Symbol.reverse, -1);
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.reverse, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(2, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		assertEquals(1, gameState.getNumConsecutiveReverseCard());
		assertEquals(-1, gameState.getGameOrder());
	}

	@Test
	void testUpdateGameStateByPenalty() {
		GameState gameState;
		
		gameState = new GameState(Card.Color.red, Card.Symbol.normal, 9, 5);
		gameState.updateGameState(Card.Color.red, Card.Symbol.skip, -1);
		gameState.updateGameStateByPenalty();
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.skip, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(3, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
		
		gameState.updateGameState(Card.Color.red, Card.Symbol.drawTwo, -1);
		gameState.updateGameStateByPenalty();
		
		assertEquals(Card.Color.red, gameState.getValidColor());
		assertEquals(Card.Symbol.drawTwo, gameState.getValidSymbol());
		assertEquals(-1, gameState.getValidNumber());
		assertEquals(0, gameState.getCurrentPlayerId());
		assertEquals(0, gameState.getNumCardsCumulated());
	}

	@Test
	void testUpdateCurrentPlayerId() {
		GameState gameState;
		
		gameState = new GameState(Card.Color.red, Card.Symbol.normal, 9, 5);
		gameState.updateCurrentPlayerId();
		assertEquals(1, gameState.getCurrentPlayerId());
		gameState.updateCurrentPlayerId();
		gameState.updateCurrentPlayerId();
		assertEquals(3, gameState.getCurrentPlayerId());
	}

	@Test
	void testReversePlayerOrder() {
		GameState gameState;
		
		gameState = new GameState(Card.Color.red, Card.Symbol.normal, 9, 5);
		gameState.updateCurrentPlayerId();
		assertEquals(1, gameState.getCurrentPlayerId());
		
		gameState.reversePlayerOrder();
		gameState.updateCurrentPlayerId();
		gameState.updateCurrentPlayerId();
		assertEquals(4, gameState.getCurrentPlayerId());
		
		gameState.reversePlayerOrder();
		gameState.updateCurrentPlayerId();
		assertEquals(0, gameState.getCurrentPlayerId());
	}

}
