package uno;

import java.util.ArrayList;
import java.util.Random;

import uno.Cards.Card;
import uno.Cards.DrawTwoActionCard;
import uno.Cards.NormalActionCard;
import uno.Cards.ReverseActionCard;
import uno.Cards.SkipActionCard;
import uno.Cards.WildActionCard;
import uno.Cards.WildDrawFourActionCard;

/**
 * Deck stores the draw pile and discard pile. It initializes 108 cards
 * to draw pile before the game start. Deck can perform draw card and
 * discard actions and update the draw pile and discard pile. When no cards
 * are left in draw pile, the card in discard pile should be shuffled to 
 * draw pile.
 */
public class Deck {
	/**
	 * total number of cards in deck
	 */
	private static final int TATAL_NUM_CARDS = 108;
	/**
	 * number of different colors
	 */
	private static final int NUM_COLORS = 4;
	/**
	 * two cards for cards other than normal, wild color cards
	 */
	private static final int NUM_SPECIAL_CARD_FOR_EACH_COLOR = 2;
	/**
	 * number one on card
	 */
	private static final int ONE = 1;
	/**
	 * number nine on card
	 */
	private static final int NINE = 9;
	/**
	 * array of cards to draw
	 */
	private Card drawPile[];
	/**
	 * array of cards discarded
	 */
	private Card discardPile[];
	/**
	 * number of cards in draw pile
	 */
	private int nCardsDrawPile;
	/**
	 * number of cards in discard pile
	 */
	private int nCardsDiscardPile;
	
	/**
	 * Deck constructor.
	 */
	public Deck() {
		nCardsDrawPile = 0;
		nCardsDiscardPile = 0;
		drawPile = new Card[TATAL_NUM_CARDS];
		discardPile = new Card[TATAL_NUM_CARDS];
	}
	
	/**
	 * Initialize the draw pile with 108 cards in random order
	 */
	public void initDrawPile() {
		fillDrawPile();
		shuffle();
	}
	
	/**
	 * Fill the draw file with 108 cards
	 */
	public void fillDrawPile() {
		//get the array of colors
		Card.Color arrayColors[] = Card.Color.values();
		//for each color
		for (int color_i = 0; color_i < NUM_COLORS; color_i++) {
			//add zero, wild, wildDrawFour once
			drawPile[nCardsDrawPile++] = new NormalActionCard(arrayColors[color_i], 0);
			drawPile[nCardsDrawPile++] = new WildActionCard();
			drawPile[nCardsDrawPile++] = new WildDrawFourActionCard();
			//for each symbol in {normal one to nine, skip, reverse, drawTwo}, add twice
			for (int times = 0; times < NUM_SPECIAL_CARD_FOR_EACH_COLOR; times++) {
				//for each normal card with number from one to nine
				for (int number = ONE; number <= NINE; number++) {
					drawPile[nCardsDrawPile++] = new NormalActionCard(arrayColors[color_i], number);
				}
				drawPile[nCardsDrawPile++] = new SkipActionCard(arrayColors[color_i]);
				drawPile[nCardsDrawPile++] = new ReverseActionCard(arrayColors[color_i]);
				drawPile[nCardsDrawPile++] = new DrawTwoActionCard(arrayColors[color_i]);
			}
		}
	}
	
	/**
	 * Shuffle the drawPile into random order
	 */
	public void shuffle() {
		Random random = new Random();
		//for each card in draw pile
		for (int drawPile_i = 0; drawPile_i < nCardsDrawPile; drawPile_i++) {
			//get a random position in draw pile
			int randomPos = drawPile_i + random.nextInt(nCardsDrawPile - drawPile_i);
			//switch the position of current card and card in randomPos
			Card tempCard = drawPile[drawPile_i];
			drawPile[drawPile_i] = drawPile[randomPos];
			drawPile[randomPos] = tempCard;
		}
	}
	
	/**
	 * Set aside the top card in the discard pile,
	 * the rest of the discard pile is shuffled to create a new deck.
	 */
	public void shuffleDiscardToDraw() {
		if (nCardsDiscardPile <= 1) {
			return;
		}
		//remove the top card and store it
		Card topCard = discardPile[--nCardsDiscardPile];
		//move cards in discard pile to draw pile
		while (nCardsDiscardPile > 0) {
			drawPile[nCardsDrawPile++] = discardPile[--nCardsDiscardPile];
		}
		//add top card to discard pile
		discardPile[nCardsDiscardPile++] = topCard;
		//shuffle the draw pile
		shuffle();
	}
	
	/**
	 * To draw one card from draw pile.
	 * Shuffle discard pile to draw pile if draw pile is empty after drawing.
	 * @return card drawn
	 */
	public Card drawOneCard() {
		if (isDrawPileEmpty() == true) {
			return null;
		}
		Card cardDrawn = drawPile[--nCardsDrawPile];
		if (isDrawPileEmpty() == true) {
			shuffleDiscardToDraw();
		}
		return cardDrawn;
	}
	
	/**
	 * To draw multiple cards from draw pile.
	 * Shuffle discard pile to draw pile if draw pile is empty after drawing.
	 * @param numCardDraw number of card to draw
	 * @return list of card drawn
	 */
	public ArrayList<Card> drawNCard(int numCardDraw) {
		ArrayList<Card> listCardDrawn = new ArrayList<Card>();
		for (int i = 0; i < numCardDraw; i++) {
			Card cardDrawn = drawOneCard();
			if (cardDrawn == null) {
				break;
			}
			listCardDrawn.add(cardDrawn);
		}
		return listCardDrawn;
	}
	
	/**
	 * Discard the card that player plays
	 * @param cardToDiscard the card to discard
	 */
	public void discardCard(Card cardToDiscard) {
		if (nCardsDiscardPile == 108) {
			return;
		}
		discardPile[nCardsDiscardPile++] = cardToDiscard;
	}
	
	/**
	 * Check whether there is no card in draw pile
	 * @return boolean of whether draw pile is empty
	 */
	public boolean isDrawPileEmpty() {
		return nCardsDrawPile <= 0;
	}
	
	/**
	 * Get the top card of the discard pile, which is the lasted card played
	 * @return top card in discard pile
	 */
	public Card getTopCardDiscardPile() {
		if (nCardsDiscardPile <= 0) {
			return null;
		}
		return discardPile[nCardsDiscardPile - 1];
	}
}
