package view.PanelCreator;

import javax.swing.JButton;
import javax.swing.JLabel;

import view.GameStagePage;

/**
 * Create panel for deck in game stage page
 *
 */
public class PanelDeckCreator {
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;
	
	/**
	 * Constructor for PanelDeckCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelDeckCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelDeck();
	}

	/**
	 * Initialize panel for the deck
	 */
	private void initPanelDeck() {
		gsp.panelDeck = gsp.createPanel(100, 50, 50, 50, 0, 0, 400, 350);
		
		gsp.buttonDeck = new JButton();
		gsp.setImageIcon("Deck", gsp.buttonDeck);
		
		gsp.labelLastCard = new JLabel();
		gsp.updateLastCardView();
		
		gsp.panelDeck.add(gsp.buttonDeck);
		gsp.panelDeck.add(gsp.labelLastCard);
	}

}
