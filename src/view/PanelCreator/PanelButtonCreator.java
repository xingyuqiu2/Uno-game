package view.PanelCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;

import view.GameStagePage;

/**
 * Create panel for show / hide and skip buttons in game stage page
 *
 */
public class PanelButtonCreator {
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;
	
	/**
	 * Constructor for PanelButtonCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelButtonCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelButton();
	}
	
	/**
	 * Initialize panel for the show/hide and skip button
	 */
	private void initPanelButton() {
		gsp.panelButton = gsp.createPanel(50, 35, 30, 50, 1100, 300, 440, 150);
		gsp.panelButton.setLayout(new FlowLayout());
		
		gsp.buttonHideShow = new JButton();
		gsp.buttonHideShow.setPreferredSize(new Dimension(150, 50));
		gsp.buttonHideShow.setText("Hide / Show");
		gsp.buttonHideShow.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
		
		gsp.buttonSkip = new JButton();
		gsp.buttonSkip.setPreferredSize(new Dimension(150, 50));
		gsp.buttonSkip.setText("Skip");
		gsp.buttonSkip.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
		
		gsp.panelButton.add(gsp.buttonHideShow);
		gsp.panelButton.add(gsp.buttonSkip);
	}
}
