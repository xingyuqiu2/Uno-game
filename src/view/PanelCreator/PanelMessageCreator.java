package view.PanelCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import view.GameStagePage;

/**
 * Create panel for message label in game stage page
 *
 */
public class PanelMessageCreator {
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;
	
	/**
	 * Constructor for PanelMessageCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelMessageCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelMessage();
	}
	
	/**
	 * initialize panel for the message
	 */
	private void initPanelMessage() {
		gsp.panelMessage = gsp.createPanel(30, 0, 30, 0, 500, 0, 500, 200);
		
		gsp.labelMessage = new JLabel("");
		gsp.labelMessage.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
		gsp.labelMessage.setForeground(new Color(255, 50, 120));
		gsp.labelMessage.setPreferredSize(new Dimension(500,250));
		gsp.labelMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		gsp.panelMessage.add(gsp.labelMessage);
	}
}
