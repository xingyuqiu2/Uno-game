package view.PanelCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;

import view.GameStagePage;

/**
 * Create panel for choose color in game stage page
 *
 */
public class PanelChooseColorCreator {
	/**
	 * card width for the image
	 */
	private static final int CARD_WIDTH = 102;
	/**
	 * card height for the image
	 */
	private static final int CARD_HEIGHT = 148;
	/**
	 * GameStagePage reference
	 */
	GameStagePage gsp;
	
	/**
	 * Constructor for PanelChooseColorCreator
	 * @param gameStagePage GameStagePage instance
	 */
	public PanelChooseColorCreator(GameStagePage gameStagePage) {
		gsp = gameStagePage;
		initPanelChooseColor();
	}
	
	/**
	 * Initialize panel for choose color
	 */
	private void initPanelChooseColor() {
		gsp.panelChooseColor = gsp.createPanel(50, 5, 50, 5, 500, 200, 500, 220);
		gsp.panelChooseColor.setLayout(new FlowLayout());
		
		gsp.buttonChooseBlue = createColorButton(Color.cyan);
		gsp.buttonChooseRed = createColorButton(Color.red);
		gsp.buttonChooseGreen = createColorButton(Color.green);
		gsp.buttonChooseYellow = createColorButton(Color.yellow);
		
		gsp.panelChooseColor.add(gsp.buttonChooseBlue);
		gsp.panelChooseColor.add(gsp.buttonChooseRed);
		gsp.panelChooseColor.add(gsp.buttonChooseGreen);
		gsp.panelChooseColor.add(gsp.buttonChooseYellow);
		
		gsp.panelChooseColor.setVisible(false);
	}
	
	/**
	 * Create a color button for choosing color.
	 * It is called in initPanelChooseColor.
	 * @param color color of the button
	 * @return JButton created
	 */
	private JButton createColorButton(Color color) {
		JButton button = new JButton();
		button.setBackground(color);
		button.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
		return button;
	}
}
