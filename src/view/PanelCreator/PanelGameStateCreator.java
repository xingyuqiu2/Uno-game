package view.PanelCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import view.GameStagePage;

/**
 * Create panel for game state in game stage page
 *
 */
public class PanelGameStateCreator {
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;
	
	/**
	 * Constructor for PanelGameStateCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelGameStateCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelGameState();
	}
	
	/**
	 * Initialize panel for the game state
	 */
	private void initPanelGameState() {
		gsp.panelGameState = gsp.createPanel(50, 50, 50, 50, 1100, 0, 450, 300);
		gsp.panelGameState.setLayout(new GridLayout(4, 1));
		
		//label for game state
		gsp.labelValidColor = new JLabel("Color:");
		adjustGameStateLabel(gsp.labelValidColor);
		
		gsp.labelValidSymbolOrNumber = new JLabel("Type:");
		adjustGameStateLabel(gsp.labelValidSymbolOrNumber);
		
		gsp.labelnumCardsCumulated = new JLabel("Cards Stacked:");
		adjustGameStateLabel(gsp.labelnumCardsCumulated);
		
		gsp.labelcurrentPlayerName = new JLabel("Player:");
		adjustGameStateLabel(gsp.labelcurrentPlayerName);
		
		gsp.panelGameState.add(gsp.labelValidColor);
		gsp.panelGameState.add(gsp.labelValidSymbolOrNumber);
		gsp.panelGameState.add(gsp.labelnumCardsCumulated);
		gsp.panelGameState.add(gsp.labelcurrentPlayerName);
	}
	
	/**
	 * Adjust label for game state by setting it to
	 * fixed font, color, size, alignment
	 * @param label the label that need to adjust
	 */
	private void adjustGameStateLabel(JLabel label) {
		label.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 25));
		label.setForeground(Color.ORANGE);
		label.setPreferredSize(new Dimension(150, 50));
		label.setHorizontalAlignment(SwingConstants.LEFT);
	}
}
