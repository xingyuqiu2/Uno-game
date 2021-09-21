package view.PanelCreator;

import view.GameStagePage;

/**
 * Create panel for hand in game stage page
 *
 */
public class PanelHandCreator {
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;

	/**
	 * Constructor for PanelHandCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelHandCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelHand();
	}
	
	/**
	 * initialize panel for the hand of button cards
	 */
	private void initPanelHand() {
		gsp.panelHand = gsp.createPanel(0, 50, 0, 50, 0, 450, 1550, 350);
	}
	
}
