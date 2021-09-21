package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.LaunchPageActionListener;

/**
 * The launch page for the game.
 * It includes input for number of players and
 * the "Start" button.
 *
 */
public class LaunchPage {
	private static final int GAP_TOP_BOTTOM = 20;
	private static final int GAP_LEFT_RIGHT = 50;
	private static final int PANEL_X = 550;
	private static final int PANEL_Y = 100;
	private static final int PANEL_WIDTH = 450;
	private static final int PANEL_HEIGHT = 100;
	private static final String MESSAGE_NORMAL = "<html>Please enter the number of players that sums to 2-9</html>";
	private static final String MESSAGE_HUMAN_PLAYER = "<html>Input the number of human players</html>";
	private static final String MESSAGE_BASELIINE_AI = "<html>Input the number of baseline AI</html>";
	private static final String MESSAGE_STRATEGIC_AI = "<html>Input the number of strategic AI</html>";
	
	private JFrame frame;
	
	private JPanel panelInfo;
	private JPanel panelHumanPlayer;
	private JPanel panelBaselineAI;
	private JPanel panelStrategicAI;
	private JPanel panelButton;
	
	private JLabel labelInfo;
	private JLabel labelHumanPlayer;
	private JLabel labelBaselineAI;
	private JLabel labelStrategicAI;
	
	private JTextField textFieldInputHP;
	private JTextField textFieldInputBAI;
	private JTextField textFieldInputSAI;
	
	private JButton buttonStartGame;
	
	private LaunchPageActionListener actionListener;
	
	/**
	 * Constructor for LaunchPage.
	 */
	public LaunchPage() {
		frame = new JFrame();
		
		initPanelInfo();
		initPanelHumanPlayer();
		initPanelBaselineAI();
		initPanelStrategicAI();
		initPanelButton();
		
		frame.add(panelInfo);
		frame.add(panelHumanPlayer, BorderLayout.CENTER);
		frame.add(panelBaselineAI, BorderLayout.CENTER);
		frame.add(panelStrategicAI, BorderLayout.CENTER);
		frame.add(panelButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Uno Game");
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		
		//set actionListener
		actionListener = new LaunchPageActionListener(this);
		buttonStartGame.addActionListener(actionListener);
	}
	
	/**
	 * Initialize panel for human player input
	 */
	public void initPanelHumanPlayer() {
		panelHumanPlayer = createPanel(PANEL_Y + PANEL_HEIGHT);
		labelHumanPlayer = createLabel(MESSAGE_HUMAN_PLAYER);
		textFieldInputHP = createTextField();
		panelHumanPlayer.add(labelHumanPlayer);
		panelHumanPlayer.add(textFieldInputHP);
	}
	
	/**
	 * Initialize panel for Baseline AI input
	 */
	public void initPanelBaselineAI() {
		panelBaselineAI = createPanel(PANEL_Y + PANEL_HEIGHT * 2);
		labelBaselineAI = createLabel(MESSAGE_BASELIINE_AI);
		textFieldInputBAI = createTextField();
		panelBaselineAI.add(labelBaselineAI);
		panelBaselineAI.add(textFieldInputBAI);
	}
	
	/**
	 * Initialize panel for Strategic AI input
	 */
	public void initPanelStrategicAI() {
		panelStrategicAI = createPanel(PANEL_Y + PANEL_HEIGHT * 3);
		labelStrategicAI = createLabel(MESSAGE_STRATEGIC_AI);
		textFieldInputSAI = createTextField();
		panelStrategicAI.add(labelStrategicAI);
		panelStrategicAI.add(textFieldInputSAI);
	}
	
	/**
	 * Initialize panel for information message
	 */
	public void initPanelInfo() {
		panelInfo = createPanel(PANEL_Y);
		labelInfo = createLabel(MESSAGE_NORMAL);
		panelInfo.add(labelInfo);
	}
	
	/**
	 * Initialize panel for start button
	 */
	public void initPanelButton() {
		panelButton = createPanel(PANEL_Y + PANEL_HEIGHT * 4);
		buttonStartGame = new JButton("Start");
		buttonStartGame.setBounds(700, 550, 150, 50);
		buttonStartGame.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
		panelButton.add(buttonStartGame);
	}
	
	/**
	 * Create panel and set position and size
	 * @param y the y-coordinate of this component
	 * @return JPanel created
	 */
	public JPanel createPanel(int y) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(GAP_TOP_BOTTOM, GAP_LEFT_RIGHT, GAP_TOP_BOTTOM, GAP_LEFT_RIGHT));
		panel.setBounds(PANEL_X, y, PANEL_WIDTH, PANEL_HEIGHT);
		panel.setLayout(new GridLayout(1, 2));
		panel.setBackground(new Color(200, 180, 230));
		return panel;
	}
	
	/**
	 * Create label and set message
	 * @param message the string to set on label
	 * @return JLabel created
	 */
	public JLabel createLabel(String message) {
		JLabel labelInfo = new JLabel();
		labelInfo.setText(message);
		labelInfo.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
		return labelInfo;
	}
	
	/**
	 * Create text field
	 * @return JTextField created
	 */
	public JTextField createTextField() {
		JTextField textFieldInput = new JTextField();
		textFieldInput.setPreferredSize(new Dimension(200, 50));
		return textFieldInput;
	}
	
	public JLabel getLabelInfo() {
		return labelInfo;
	}
	
	public JTextField getTextFieldInputHP() {
		return textFieldInputHP;
	}
	
	public JTextField getTextFieldInputBAI() {
		return textFieldInputBAI;
	}
	
	public JTextField getTextFieldInputSAI() {
		return textFieldInputSAI;
	}
	
	public JButton getButtonStartGame() {
		return buttonStartGame;
	}
	
	/**
	 * close launch page
	 */
	public void close() {
		frame.dispose();
	}
}
