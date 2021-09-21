package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import uno.Game;
import uno.Players.BaselineAI;
import uno.Players.HumanPlayer;
import uno.Players.Player;
import uno.Players.StrategicAI;
import view.GameStagePage;
import view.LaunchPage;

/**
 *	ActionListener for the game launch page.
 *	It is used to detect the number of players input and decide to start the game.
 */
public class LaunchPageActionListener implements ActionListener {
	/**
	 * maximum number of players
	 */
	private static final int MIN_NUM_PLAYER = 2;
	/**
	 * minimum number of players
	 */
	private static final int MAX_NUM_PLAYER = 9;
	/**
	 * number of text field in the view
	 */
	private static final int NUM_TEXT_FIELD = 3;
	/**
	 * regular expression string for nonnegative integer, which is used to check validity of input
	 */
	private static final String VALID_INPUT_REGEX = "[0-9]+";
	private static final String MESSAGE_INVALID_INPUT = "<html>Input invalid, please enter integers</html>";
	private static final String MESSAGE_INVALID_NUMBER = "<html>Number invalid, total number of players should between 2-9</html>";
	private static final String MESSAGE_SUCCESS = "<html>Game start successfully</html>";
	/**
	 * holds the reference to LaunchPage
	 */
	private LaunchPage launchpage;
	/**
	 * number of players including human player and AI
	 */
	private int numPlayer = 0;
	/**
	 * array of three inputs in integer
	 */
	private int[] inputsNumber;
	
	/**
	 * Constructor of LaunchPageActionListener.
	 * @param paramLaunchpage LaunchPage instance
	 */
	public LaunchPageActionListener(LaunchPage paramLaunchpage) {
		launchpage = paramLaunchpage;
		inputsNumber = new int[NUM_TEXT_FIELD];
	}
	
	/**
	 * Process event for start button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == launchpage.getButtonStartGame()) {
			JTextField textFieldHP = launchpage.getTextFieldInputHP();
			JTextField textFieldBAI = launchpage.getTextFieldInputBAI();
			JTextField textFieldSAI = launchpage.getTextFieldInputSAI();
			String[] inputsString = new String[NUM_TEXT_FIELD];
			int inputsIndex = 0;
			inputsString[inputsIndex++] = textFieldHP.getText().strip();
			inputsString[inputsIndex++] = textFieldBAI.getText().strip();
			inputsString[inputsIndex++] = textFieldSAI.getText().strip();
			for (int i = 0; i < inputsString.length; i++) {
				if (inputsString[i] != "" && inputsString[i].matches(VALID_INPUT_REGEX)) {
					inputsNumber[i] = Integer.parseInt(inputsString[i]);
					numPlayer += inputsNumber[i];
				} else {
					launchpage.getLabelInfo().setText(MESSAGE_INVALID_INPUT);
					numPlayer = 0;
					return;
				}
			}
			
			if (numPlayer >= MIN_NUM_PLAYER && numPlayer <= MAX_NUM_PLAYER) {
				launchpage.getLabelInfo().setText(MESSAGE_SUCCESS);
				openGameStagePage();
			} else {
				launchpage.getLabelInfo().setText(MESSAGE_INVALID_NUMBER);
				numPlayer = 0;
			}
		}
	}
	
	/**
	 * Initialize different players and use thread to open the game stage page
	 */
	private void openGameStagePage() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		int inputsIndex = 0;
		for (int i = 0; i < inputsNumber[inputsIndex]; i++) {
			listPlayers.add(new HumanPlayer("HumanPlayer_" + i, i));
		}
		inputsIndex++;
		for (int i = 0; i < inputsNumber[inputsIndex]; i++) {
			listPlayers.add(new BaselineAI("BaselineAI_" + i, i));
		}
		inputsIndex++;
		for (int i = 0; i < inputsNumber[inputsIndex]; i++) {
			listPlayers.add(new StrategicAI("StrategicAI_" + i, i));
		}
		
		launchpage.close();
		Game game = new Game(listPlayers);
		GameStagePage gameStagePage = new GameStagePage(game);
		game.setGameStagePage(gameStagePage);
		new Thread() {
             public void run(){
            	 game.startGame();
             }
		}.start();
	}

}
