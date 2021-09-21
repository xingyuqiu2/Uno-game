package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uno.Cards.Card;
import uno.Cards.DrawTwoActionCard;
import uno.Cards.NormalActionCard;
import uno.Cards.ReverseActionCard;
import uno.Cards.SkipActionCard;
import uno.Cards.WildActionCard;
import uno.Cards.WildDrawFourActionCard;

class CardTest {
	
	@Test
	void testNormalActionCard() {
		NormalActionCard normalCard;
		
		normalCard = new NormalActionCard(Card.Color.blue, 3);
		
		//test Constructor
		assertEquals(Card.Color.blue, normalCard.getCardColor());
		assertEquals(Card.Symbol.normal, normalCard.getCardSymbol());
		assertEquals(3, normalCard.getCardNumber());
		
		//test setColor
		normalCard.setColor(Card.Color.green);
		//normalCard is now green 3
		assertEquals(Card.Color.green, normalCard.getCardColor());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, normalCard.isValid(Card.Color.green, Card.Symbol.normal, 5, 0));
		assertEquals(true, normalCard.isValid(Card.Color.red, Card.Symbol.normal, 3, 0));
		assertEquals(true, normalCard.isValid(Card.Color.green, Card.Symbol.normal, 5, 0));
		assertEquals(true, normalCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(false, normalCard.isValid(Card.Color.yellow, Card.Symbol.reverse, -1, 0));
		assertEquals(false, normalCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, normalCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 0));
		assertEquals(false, normalCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, normalCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 0));
	}
	
	@Test
	void testSkipActionCard() {
		SkipActionCard skipCard;
		
		skipCard = new SkipActionCard(Card.Color.blue);
		
		//test Constructor
		assertEquals(Card.Color.blue, skipCard.getCardColor());
		assertEquals(Card.Symbol.skip, skipCard.getCardSymbol());
		assertEquals(-1, skipCard.getCardNumber());
		
		//test setColor
		skipCard.setColor(Card.Color.red);
		//skipCard is now red skip
		assertEquals(Card.Color.red, skipCard.getCardColor());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, skipCard.isValid(Card.Color.red, Card.Symbol.normal, 5, 0));
		assertEquals(false, skipCard.isValid(Card.Color.green, Card.Symbol.normal, 3, 0));
		assertEquals(true, skipCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(false, skipCard.isValid(Card.Color.red, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, skipCard.isValid(Card.Color.red, Card.Symbol.drawTwo, -1, 0));
		assertEquals(false, skipCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, skipCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 0));
	}

	@Test
	void testReverseActionCard() {
		ReverseActionCard reverseCard;
		
		reverseCard = new ReverseActionCard(Card.Color.yellow);
		
		//test Constructor
		assertEquals(Card.Color.yellow, reverseCard.getCardColor());
		assertEquals(Card.Symbol.reverse, reverseCard.getCardSymbol());
		assertEquals(-1, reverseCard.getCardNumber());
		
		//test setColor
		reverseCard.setColor(Card.Color.green);
		//reverseCard is now green reverse
		assertEquals(Card.Color.green, reverseCard.getCardColor());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, reverseCard.isValid(Card.Color.green, Card.Symbol.normal, 9, 0));
		assertEquals(false, reverseCard.isValid(Card.Color.red, Card.Symbol.normal, 3, 0));
		assertEquals(true, reverseCard.isValid(Card.Color.yellow, Card.Symbol.reverse, -1, 0));
		assertEquals(true, reverseCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(false, reverseCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, reverseCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 0));
		assertEquals(false, reverseCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, reverseCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 0));
		assertEquals(false, reverseCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 0));
	}

	@Test
	void testDrawTwoActionCard() {
		DrawTwoActionCard drawTwoCard;
		
		drawTwoCard = new DrawTwoActionCard(Card.Color.green);
		
		//test Constructor
		assertEquals(Card.Color.green, drawTwoCard.getCardColor());
		assertEquals(Card.Symbol.drawTwo, drawTwoCard.getCardSymbol());
		assertEquals(-1, drawTwoCard.getCardNumber());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, drawTwoCard.isValid(Card.Color.green, Card.Symbol.normal, 9, 0));
		assertEquals(false, drawTwoCard.isValid(Card.Color.red, Card.Symbol.normal, 3, 0));
		assertEquals(false, drawTwoCard.isValid(Card.Color.yellow, Card.Symbol.reverse, -1, 0));
		assertEquals(true, drawTwoCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(true, drawTwoCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, drawTwoCard.isValid(Card.Color.red, Card.Symbol.drawTwo, -1, 4));
		assertEquals(false, drawTwoCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, drawTwoCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 0));
		assertEquals(false, drawTwoCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 0));
	}

	@Test
	void testWildActionCard() {
		WildActionCard wildCard;
		
		wildCard = new WildActionCard();
		
		//test Constructor
		assertEquals(Card.Color.wild, wildCard.getCardColor());
		assertEquals(Card.Symbol.wild, wildCard.getCardSymbol());
		assertEquals(-1, wildCard.getCardNumber());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, wildCard.isValid(Card.Color.green, Card.Symbol.normal, 9, 0));
		assertEquals(true, wildCard.isValid(Card.Color.red, Card.Symbol.normal, 3, 0));
		assertEquals(true, wildCard.isValid(Card.Color.yellow, Card.Symbol.reverse, -1, 0));
		assertEquals(true, wildCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(false, wildCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, wildCard.isValid(Card.Color.red, Card.Symbol.drawTwo, -1, 0));
		assertEquals(false, wildCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, wildCard.isValid(Card.Color.yellow, Card.Symbol.wildDrawFour, -1, 0));
		assertEquals(false, wildCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 8));
	}

	@Test
	void testWildDrawFourActionCard() {
		WildDrawFourActionCard wildDrawFourCard;
		
		wildDrawFourCard = new WildDrawFourActionCard();
		
		//test Constructor
		assertEquals(Card.Color.wild, wildDrawFourCard.getCardColor());
		assertEquals(Card.Symbol.wildDrawFour, wildDrawFourCard.getCardSymbol());
		assertEquals(-1, wildDrawFourCard.getCardNumber());
		
		//test isValid(validColor, validSymbol, validNumber, validNumCardsCumulated)
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.green, Card.Symbol.normal, 9, 0));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.red, Card.Symbol.normal, 3, 0));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.yellow, Card.Symbol.reverse, -1, 0));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.green, Card.Symbol.skip, -1, 0));
		assertEquals(false, wildDrawFourCard.isValid(Card.Color.green, Card.Symbol.drawTwo, -1, 2));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.red, Card.Symbol.drawTwo, -1, 0));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.green, Card.Symbol.wildDrawFour, -1, 4));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.yellow, Card.Symbol.wildDrawFour, -1, 0));
		assertEquals(true, wildDrawFourCard.isValid(Card.Color.red, Card.Symbol.wildDrawFour, -1, 8));
	}
	
	@Test
	void testToString() {
		DrawTwoActionCard drawTwoCard = new DrawTwoActionCard(Card.Color.green);
		assertEquals("green_DrawTwo", drawTwoCard.toString());
		
		NormalActionCard normalCard = new NormalActionCard(Card.Color.blue, 3);
		assertEquals("blue_3", normalCard.toString());
		
		ReverseActionCard reverseCard = new ReverseActionCard(Card.Color.yellow);
		assertEquals("yellow_Reverse", reverseCard.toString());
		
		SkipActionCard skipCard = new SkipActionCard(Card.Color.blue);
		assertEquals("blue_Skip", skipCard.toString());
		
		WildActionCard wildCard = new WildActionCard();
		assertEquals("wild", wildCard.toString());
		
		WildDrawFourActionCard wildDrawFourCard = new WildDrawFourActionCard();
		assertEquals("wildDrawFour", wildDrawFourCard.toString());
	}

}
