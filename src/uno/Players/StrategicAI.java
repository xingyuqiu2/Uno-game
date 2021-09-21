package uno.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import uno.Cards.Card;
import uno.Cards.Card.Color;
import uno.Cards.Card.Symbol;

/**
 * Strategic AI that extends Player.
 * It can choose card and choose color without using GUI.
 *
 */
public class StrategicAI extends Player {

	/**
	 * Constructor for StrategicAI
	 * @param paramName name of player
	 * @param paramPlayerId index of player
	 */
	public StrategicAI(String paramName, int paramPlayerId) {
		super(paramName, paramPlayerId);
		is_AI = true;
	}

	/**
	 * Try best to choose a valid card with the color that matches the popular color in hand
	 */
	@Override
	public int chooseCardOrSkip(Color validColor, Symbol validSymbol, int validNumber, int numCardsCumulated) {
		//map -> <color, list of valid card index in hand>
		Map<Card.Color, ArrayList<Integer>> map = new HashMap<>();
		Card.Color[] colors = Card.Color.values();
		for (int i = 0; i < colors.length; i++) {
			map.put(colors[i], new ArrayList<Integer>());
		}
		for (int hand_i = 0; hand_i < hand.size(); hand_i++) {
			Card cardSelected = hand.get(hand_i);
			if (cardSelected.isValid(validColor, validSymbol, validNumber, numCardsCumulated)) {
				Card.Color currColor = hand.get(hand_i).getCardColor();
				map.get(currColor).add(hand_i);
			}
		}
		//choose the card with the most popular color possible
		ArrayList<Card.Color> orderedColors = computeOrderedColors();
		for (int i = 0; i < orderedColors.size(); i++) {
			Card.Color currColor = orderedColors.get(i);
			if (map.get(currColor).size() != 0) {
				return map.get(currColor).get(0);
			}
		}
		//no valid card in hand
		return INDEX_SKIP;
	}

	/**
	 * Choose the most popular color in hand
	 */
	@Override
	public void chooseColor(Card cardSelected) {
		ArrayList<Card.Color> orderedColors = computeOrderedColors();
		Card.Color popularColor = orderedColors.get(0);
		if (popularColor == Card.Color.wild) {
			//popular color is wild
			if (orderedColors.size() > 1) {
				//get the next popular color
				cardSelected.setColor(orderedColors.get(1));
			} else {
				//all cards in hand are wild or wildDrawFour cards, get random color
				Random random = new Random();
				int index = random.nextInt(Card.Color.values().length - 1);
				Card.Color colorSelected = Card.Color.values()[index];
				cardSelected.setColor(colorSelected);
			}
		} else {
			cardSelected.setColor(popularColor);
		}
	}
	
	/**
	 * Compute the list of color in descending order of popularity in hand
	 * @return ArrayList of color
	 */
	public ArrayList<Card.Color> computeOrderedColors() {
		//map -> <color, count>
		Map<Card.Color, Integer> map = new HashMap<>();
		for (int i = 0; i < hand.size(); i++) {
			Card.Color currColor = hand.get(i).getCardColor();
			map.put(currColor, map.getOrDefault(currColor, 0) + 1);
		}
		//sort the counts in ascending order
		ArrayList<Integer> listCountColors = new ArrayList<>(map.values());
		Collections.sort(listCountColors);
		//create list to store colors in descend order of counts
		ArrayList<Card.Color> orderedColors = new ArrayList<>();
		for (int i = listCountColors.size() - 1; i >= 0; i--) {
			int currCount = listCountColors.get(i);
			for (Entry<Card.Color, Integer> entry : map.entrySet()) {
				if (entry.getValue() == currCount) {
					orderedColors.add(entry.getKey());
					map.put(entry.getKey(), -1);
					break;
				}
			}
		}
		return orderedColors;
	}

	@Override
	public boolean isAI() {
		return is_AI;
	}

}
