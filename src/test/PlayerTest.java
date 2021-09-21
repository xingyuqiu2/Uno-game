package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uno.Cards.Card;
import uno.Cards.NormalActionCard;
import uno.Cards.ReverseActionCard;
import uno.Cards.SkipActionCard;
import uno.Cards.WildActionCard;
import uno.Cards.WildDrawFourActionCard;
import uno.Players.BaselineAI;
import uno.Players.HumanPlayer;
import uno.Players.Player;
import uno.Players.StrategicAI;

class PlayerTest {

	@Test
	void testPlayerAndGetMethod() {
		Player player1, player2;
		
		player1 = new BaselineAI("Jack", 0);
		assertEquals(0, player1.getPlayerId());
		assertEquals("Jack", player1.getName());
		
		player2 = new BaselineAI("Newbee", 1);
		assertEquals(1, player2.getPlayerId());
		assertEquals("Newbee", player2.getName());
	}
	
	@Test
	void testHumanPlayerChooseCardOrSkip() {
		Player player;
		ArrayList<Card> listCardAdded;
		int index = -1;
		
		player = new HumanPlayer("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new NormalActionCard(Card.Color.red, 4));
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		
		player.setCardIndexSelected(2);
		HumanPlayer humanPlayer = (HumanPlayer)player;
		humanPlayer.semaphoreCard.release();
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.normal, 0, 0);
		assertEquals(2, index);
		
		player.setCardIndexSelected(0);
		humanPlayer = (HumanPlayer)player;
		humanPlayer.semaphoreCard.release();
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.normal, 0, 0);
		assertEquals(0, index);
		
		player.setCardIndexSelected(-1);
		humanPlayer = (HumanPlayer)player;
		humanPlayer.semaphoreCard.release();
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.normal, 0, 0);
		assertEquals(-1, index);
	}

	@Test
	void testBaselineAIChooseCardOrSkip() {
		Player player;
		ArrayList<Card> listCardAdded;
		int index = -1;
		
		player = new BaselineAI("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new NormalActionCard(Card.Color.red, 4));
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.reverse, -1, 0);
		assertEquals(1, index);
		
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.normal, 4, 0);
		assertEquals(0, index);
		
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.drawTwo, -1, 2);
		assertEquals(-1, index);
	}
	
	@Test
	void testStrategicAIChooseCardOrSkip() {
		Player player;
		int index = -1;
		//Card in hand: 4 blue, 3 green, 2 yellow, 0 red, 2 wild
		player = new StrategicAI("Jack", 0);
		player.addCardToHand(new NormalActionCard(Card.Color.yellow, 1));
		player.addCardToHand(new NormalActionCard(Card.Color.blue, 3));
		player.addCardToHand(new NormalActionCard(Card.Color.green, 2));
		player.addCardToHand(new WildActionCard());
		player.addCardToHand(new WildDrawFourActionCard());
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.green));
		player.addCardToHand(new ReverseActionCard(Card.Color.yellow));
		player.addCardToHand(new SkipActionCard(Card.Color.blue));
		player.addCardToHand(new SkipActionCard(Card.Color.green));
		
		index = player.chooseCardOrSkip(Card.Color.red, Card.Symbol.normal, 3, 0);
		//expect to choose blue 3 since blue is most popular color
		assertEquals(1, index);
		
		index = player.chooseCardOrSkip(Card.Color.green, Card.Symbol.normal, 8, 0);
		//expect to choose green 2 since green is most popular color possible that has a valid card
		assertEquals(2, index);
		
		index = player.chooseCardOrSkip(Card.Color.green, Card.Symbol.wild, -1, 0);
		assertEquals(2, index);
		
		index = player.chooseCardOrSkip(Card.Color.red, Card.Symbol.normal, 2, 0);
		assertEquals(2, index);
		
		index = player.chooseCardOrSkip(Card.Color.green, Card.Symbol.skip, 2, 0);
		assertEquals(9, index);
		
		index = player.chooseCardOrSkip(Card.Color.red, Card.Symbol.normal, 8, 0);
		assertEquals(3, index);
		
		index = player.chooseCardOrSkip(Card.Color.green, Card.Symbol.drawTwo, 2, 0);
		assertEquals(2, index);
		
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.drawTwo, -1, 2);
		assertEquals(-1, index);
		
		index = player.chooseCardOrSkip(Card.Color.blue, Card.Symbol.wildDrawFour, -1, 4);
		assertEquals(4, index);
	}
	
	@Test
	void testHumanPlayerChooseColor() {
		Player player;
		
		player = new HumanPlayer("Jack", 0);
		player.setColorSelected(Card.Color.red);
		HumanPlayer humanPlayer = (HumanPlayer)player;
		humanPlayer.semaphoreColor.release();
		
		WildActionCard wildCard = new WildActionCard();
		player.chooseColor(wildCard);
		
		assertEquals(Card.Color.red, wildCard.getCardColor());
	}

	@Test
	void testBaselineAIChooseColor() {
		Player player;
		boolean notSameColor = false;
		
		player = new BaselineAI("Jack", 0);
		WildActionCard wildCard = new WildActionCard();
		player.chooseColor(wildCard);
		Card.Color firstColor = wildCard.getCardColor();
		for (int i = 0; i < 50; i++) {
			player.chooseColor(wildCard);
			Card.Color currColor = wildCard.getCardColor();
			if (currColor != firstColor) {
				notSameColor = true;
			}
		}
		
		assertTrue(notSameColor);
	}
	
	@Test
	void testStrategicAIChooseColor() {
		Player player;
		WildActionCard wildCard = new WildActionCard();
		Card.Color colorChosen;
		
		player = new StrategicAI("Jack", 0);
		//Card in hand: 3 blue, 2 green, 2 yellow, 1 red, 2 wild
		player.addCardToHand(new ReverseActionCard(Card.Color.red));
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.green));
		player.addCardToHand(new ReverseActionCard(Card.Color.yellow));
		player.addCardToHand(new WildActionCard());
		player.addCardToHand(new WildDrawFourActionCard());
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.green));
		player.addCardToHand(new ReverseActionCard(Card.Color.yellow));
		player.addCardToHand(new SkipActionCard(Card.Color.blue));
		
		player.chooseColor(wildCard);
		colorChosen = wildCard.getCardColor();
		
		assertEquals(Card.Color.blue, colorChosen);
		
		
		player = new StrategicAI("Jack", 0);
		//Card in hand: 4 wild
		player.addCardToHand(new WildActionCard());
		player.addCardToHand(new WildActionCard());
		player.addCardToHand(new WildActionCard());
		player.addCardToHand(new WildDrawFourActionCard());
		
		player.chooseColor(wildCard);
		colorChosen = wildCard.getCardColor();
		
		assertNotEquals(Card.Color.wild, colorChosen);
		
		//Card in hand: 4 wild, 3 blue, 2 green, 2 yellow, 1 red
		player.addCardToHand(new ReverseActionCard(Card.Color.red));
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.green));
		player.addCardToHand(new ReverseActionCard(Card.Color.yellow));
		player.addCardToHand(new ReverseActionCard(Card.Color.blue));
		player.addCardToHand(new ReverseActionCard(Card.Color.green));
		player.addCardToHand(new ReverseActionCard(Card.Color.yellow));
		player.addCardToHand(new SkipActionCard(Card.Color.blue));
		
		player.chooseColor(wildCard);
		colorChosen = wildCard.getCardColor();
		
		assertEquals(Card.Color.blue, colorChosen);
	}

	@Test
	void testAddCardToHand() {
		Player player;
		Card cardAdded;
		
		player = new BaselineAI("Jack", 0);
		WildActionCard wildCard = new WildActionCard();
		player.addCardToHand(wildCard);
		cardAdded = player.getCardInHand(0);
		
		assertEquals(Card.Color.wild, cardAdded.getCardColor());
		assertEquals(Card.Symbol.wild, cardAdded.getCardSymbol());
	}

	@Test
	void testAddNCardToHand() {
		Player player;
		ArrayList<Card> listCardAdded;
		
		player = new BaselineAI("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		
		assertEquals(3, player.getHandSize());
		assertEquals(Card.Color.wild, player.getCardInHand(0).getCardColor());
		assertEquals(Card.Symbol.wild, player.getCardInHand(0).getCardSymbol());
		assertEquals(Card.Color.wild, player.getCardInHand(1).getCardColor());
		assertEquals(Card.Symbol.wildDrawFour, player.getCardInHand(1).getCardSymbol());
		assertEquals(Card.Color.red, player.getCardInHand(2).getCardColor());
		assertEquals(Card.Symbol.reverse, player.getCardInHand(2).getCardSymbol());
	}

	@Test
	void testRemoveCardInHand() {
		Player player;
		ArrayList<Card> listCardAdded;
		
		player = new BaselineAI("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		player.removeCardInHand(1);
		
		assertEquals(Card.Color.wild, player.getCardInHand(0).getCardColor());
		assertEquals(Card.Symbol.wild, player.getCardInHand(0).getCardSymbol());
		assertEquals(Card.Color.red, player.getCardInHand(1).getCardColor());
		assertEquals(Card.Symbol.reverse, player.getCardInHand(1).getCardSymbol());
	}

	@Test
	void testIsHandEmpty() {
		Player player;
		
		player = new BaselineAI("Jack", 0);
		assertTrue(player.isHandEmpty());
		
		WildActionCard wildCard = new WildActionCard();
		player.addCardToHand(wildCard);
		assertFalse(player.isHandEmpty());
	}
	
	@Test
	void testGetHandSize() {
		Player player;
		ArrayList<Card> listCardAdded;
		
		player = new BaselineAI("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		
		assertEquals(2, player.getHandSize());
		
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		player.addNCardToHand(listCardAdded);
		
		assertEquals(6, player.getHandSize());
	}
	
	@Test
	void testGetHand() {
		Player player;
		ArrayList<Card> listCardAdded;
		
		player = new BaselineAI("Jack", 0);
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		listCardAdded.add(new NormalActionCard(Card.Color.yellow, 1));
		listCardAdded.add(new NormalActionCard(Card.Color.blue, 3));
		listCardAdded.add(new NormalActionCard(Card.Color.green, 2));
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.blue));
		listCardAdded.add(new ReverseActionCard(Card.Color.blue));
		player.addNCardToHand(listCardAdded);
		
		ArrayList<Card> hand = player.getHand();
		for (int i = 0; i < listCardAdded.size(); i++) {
			assertEquals(listCardAdded.get(i), hand.get(i));
		}
	}
	
	@Test
	void testChangeHand() {
		Player player;
		ArrayList<Card> listCardAdded;
		
		player = new BaselineAI("Jack", 0);
		player.addCardToHand(new NormalActionCard(Card.Color.yellow, 1));
		
		listCardAdded = new ArrayList<Card>();
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.red));
		listCardAdded.add(new NormalActionCard(Card.Color.yellow, 1));
		listCardAdded.add(new NormalActionCard(Card.Color.blue, 3));
		listCardAdded.add(new NormalActionCard(Card.Color.green, 2));
		listCardAdded.add(new WildActionCard());
		listCardAdded.add(new WildDrawFourActionCard());
		listCardAdded.add(new ReverseActionCard(Card.Color.blue));
		listCardAdded.add(new ReverseActionCard(Card.Color.blue));
		player.changeHand(listCardAdded);
		
		ArrayList<Card> hand = player.getHand();
		for (int i = 0; i < listCardAdded.size(); i++) {
			assertEquals(listCardAdded.get(i), hand.get(i));
		}
	}
	
	@Test
	void testIsAI() {
		Player player;
		
		player = new BaselineAI("Jack", 0);
		assertTrue(player.isAI());
		
		player = new StrategicAI("Jack", 0);
		assertTrue(player.isAI());
		
		player = new HumanPlayer("Jack", 0);
		assertFalse(player.isAI());
	}

}
