package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uno.Deck;
import uno.Cards.Card;
import uno.Cards.NormalActionCard;

class DeckTest {

	@Test
	void testInitDrawPile() {
		Deck deck;
		
		int countRedZeroCard = 0;
		int countGreenSkipCard = 0;
		int countWildDrawFourCard = 0;
		
		deck = new Deck();
		deck.initDrawPile();
		
		//count the specific cards
		for (int i = 0; i < 108; i++) {
			Card cardDrawn = deck.drawOneCard();
			if (cardDrawn.getCardColor() == Card.Color.red && cardDrawn.getCardSymbol() == Card.Symbol.normal) {
				NormalActionCard normalCard = (NormalActionCard)cardDrawn;
				if (normalCard.getCardNumber() == 0) {
					countRedZeroCard++;
				}
			}
			if (cardDrawn.getCardColor() == Card.Color.green && cardDrawn.getCardSymbol() == Card.Symbol.skip) {
				countGreenSkipCard++;
			}
			if (cardDrawn.getCardSymbol() == Card.Symbol.wildDrawFour) {
				countWildDrawFourCard++;
			}
			deck.discardCard(cardDrawn);
		}
		
		//check whether these cards are present in deck with right amount
		assertEquals(1, countRedZeroCard);
		assertEquals(2, countGreenSkipCard);
		assertEquals(4, countWildDrawFourCard);
	}

	@Test
	void testShuffleDiscardToDraw() {
		Deck deck;
		Card.Color lastCardColor = null;
		Card.Symbol lastCardSymbol = null;
		int lastCardNumber = -1;
		Card topCard;
		
		deck = new Deck();
		deck.initDrawPile();
		
		//test result when no card in discard pile
		deck.shuffleDiscardToDraw();
		assertEquals(null, deck.getTopCardDiscardPile());
		
		//draw 5 cards from draw pile and discard them
		for (int i = 0; i < 5; i++) {
			Card cardDrawn = deck.drawOneCard();
			if (i == 4) {
				lastCardColor = cardDrawn.getCardColor();
				lastCardSymbol = cardDrawn.getCardSymbol();
				if (lastCardSymbol == Card.Symbol.normal) {
					NormalActionCard card = (NormalActionCard)cardDrawn;
					lastCardNumber = card.getCardNumber();
				}
			}
			deck.discardCard(cardDrawn);
		}
		deck.shuffleDiscardToDraw();
		
		//test whether top card in discard pile is right
		topCard = deck.getTopCardDiscardPile();
		assertEquals(lastCardColor, topCard.getCardColor());
		assertEquals(lastCardSymbol, topCard.getCardSymbol());
		if (lastCardSymbol == Card.Symbol.normal) {
			NormalActionCard normalCard = (NormalActionCard)topCard;
			assertEquals(lastCardNumber, normalCard.getCardNumber());
		}
		
		//test when there is no card left in draw pile after drawing
		for (int i = 0; i < 150; i++) {
			Card cardDrawn = deck.drawOneCard();
			if (i < 50) {
				deck.discardCard(cardDrawn);
			}
		}
	}

	@Test
	void testDrawOneCard() {
		Deck deck;
		Card firstCard;
		Card secondCard;
		
		deck = new Deck();
		
		firstCard = deck.drawOneCard();
		assertEquals(null, firstCard);
		
		deck.fillDrawPile();
		firstCard = deck.drawOneCard();
		secondCard = deck.drawOneCard();
		
		assertEquals(Card.Color.blue, firstCard.getCardColor());
		assertEquals(Card.Symbol.drawTwo, firstCard.getCardSymbol());
		assertEquals(Card.Color.blue, secondCard.getCardColor());
		assertEquals(Card.Symbol.reverse, secondCard.getCardSymbol());
	}

	@Test
	void testDrawNCard() {
		Deck deck;
		ArrayList<Card> arrayCards;
		
		deck = new Deck();
		deck.fillDrawPile();
		arrayCards = deck.drawNCard(2);
		
		assertEquals(2, arrayCards.size());
		assertEquals(Card.Color.blue, arrayCards.get(0).getCardColor());
		assertEquals(Card.Symbol.drawTwo, arrayCards.get(0).getCardSymbol());
		assertEquals(Card.Color.blue, arrayCards.get(1).getCardColor());
		assertEquals(Card.Symbol.reverse, arrayCards.get(1).getCardSymbol());
	}

	@Test
	void testDiscardCard() {
		Deck deck;
		Card firstCard;
		Card secondCard;
		Card topCard;
		
		//draw one card then discard it, test whether top card in discard pile is this card
		deck = new Deck();
		deck.fillDrawPile();
		firstCard = deck.drawOneCard();
		deck.discardCard(firstCard);
		topCard = deck.getTopCardDiscardPile();
		assertEquals(Card.Color.blue, topCard.getCardColor());
		assertEquals(Card.Symbol.drawTwo, topCard.getCardSymbol());
		
		//draw one card again then discard it, test whether top card in discard pile is this card
		secondCard = deck.drawOneCard();
		deck.discardCard(secondCard);
		topCard = deck.getTopCardDiscardPile();
		assertEquals(Card.Color.blue, topCard.getCardColor());
		assertEquals(Card.Symbol.reverse, topCard.getCardSymbol());
	}

	@Test
	void testIsDrawPileEmpty() {
		Deck deck;
		
		deck = new Deck();
		assertTrue(deck.isDrawPileEmpty());
		
		deck.initDrawPile();
		assertFalse(deck.isDrawPileEmpty());
	}
	
	@Test
	void testGetTopCardDiscardPile() {
		Deck deck;
		Card topCard;
		
		deck = new Deck();
		deck.initDrawPile();
		//draw one card and discard it, get the top card in discard pile
		Card cardDrawn = deck.drawOneCard();
		deck.discardCard(cardDrawn);
		topCard = deck.getTopCardDiscardPile();
		
		assertEquals(cardDrawn.getCardColor(), topCard.getCardColor());
		assertEquals(cardDrawn.getCardSymbol(), topCard.getCardSymbol());
		if (topCard.getCardSymbol() == Card.Symbol.normal) {
			NormalActionCard normalCardDrawn = (NormalActionCard)cardDrawn;
			NormalActionCard normalTopCard = (NormalActionCard)topCard;
			assertEquals(normalCardDrawn.getCardNumber(), normalTopCard.getCardNumber());
		}
	}

}
