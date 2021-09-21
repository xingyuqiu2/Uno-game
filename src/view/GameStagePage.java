package view;

import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.GameStagePageActionListener;
import uno.Game;
import uno.GameState;
import uno.Cards.Card;
import uno.Players.Player;
import view.PanelCreator.PanelButtonCreator;
import view.PanelCreator.PanelChooseColorCreator;
import view.PanelCreator.PanelDeckCreator;
import view.PanelCreator.PanelGameStateCreator;
import view.PanelCreator.PanelHandCreator;
import view.PanelCreator.PanelMessageCreator;

/**
 * The game stage page for the game which includes the following.
 * Current game state, such as the color, number, and symbol of the latest card in the discard pile, number of cards to stack, current play in turn, etc.
 * A deck of cards where players can draw cards.
 * Cards of the player of the current turn.
 * Skip button.
 * Button to reveal/hide cards of the player of the current turn (to prevent opponents see current player's cards).
 * Allowing players to set the color after a wild card is played.
 *
 */
public class GameStagePage {
	/**
	 * card width for the image
	 */
	private static final int CARD_WIDTH = 102;
	/**
	 * card height for the image
	 */
	private static final int CARD_HEIGHT = 148;
	
	private static final String MESSAGE_NONE = "";
	private static final String MESSAGE_CHOOSE_COLOR = "<html>Please choose a color</html>";
	
	//All these variables are set to global because classes in PanelCreator will initialize these variables,
	//and ActionListener will use all the buttons and some of the labels.
	/**
	 * holds the frame of this page
	 */
	private JFrame frame;
	/**
	 * panel for the game state which is in the upper right corner
	 */
	public JPanel panelGameState;
	/**
	 * panel for the hand of cards which is in the bottom
	 */
	public JPanel panelHand;
	/**
	 * panel for hide/show and skip button which is in the right side
	 */
	public JPanel panelButton;
	/**
	 * panel for deck which is in the upper left corner
	 */
	public JPanel panelDeck;
	/**
	 * panel for choose color which is in the center
	 */
	public JPanel panelChooseColor;
	/**
	 * panel for displaying message which is in the upper side
	 */
	public JPanel panelMessage;
	
	/**
	 * label for the valid color which is in panelGameState
	 */
	public JLabel labelValidColor;
	/**
	 * label for the valid symbol or number which is in panelGameState
	 */
	public JLabel labelValidSymbolOrNumber;
	/**
	 * label for the number of card stacked which is in panelGameState
	 */
	public JLabel labelnumCardsCumulated;
	/**
	 * label for the current player name which is in panelGameState
	 */
	public JLabel labelcurrentPlayerName;
	/**
	 * label for the message which is in panelMessage
	 */
	public JLabel labelMessage;
	/**
	 * label for the image of last card played which is in panelDeck
	 */
	public JLabel labelLastCard;
	
	/**
	 * button to hide or show the hand cards which is in panelButton
	 */
	public JButton buttonHideShow;
	/**
	 * button to skip which is in panelButton
	 */
	public JButton buttonSkip;
	/**
	 * button of deck, used to draw card, which is in panelDeck
	 */
	public JButton buttonDeck;
	/**
	 * button to choose red color which is in panelChooseColor
	 */
	public JButton buttonChooseRed;
	/**
	 * button to choose green color which is in panelChooseColor
	 */
	public JButton buttonChooseGreen;
	/**
	 * button to choose blue color which is in panelChooseColor
	 */
	public JButton buttonChooseBlue;
	/**
	 * button to choose yellow color which is in panelChooseColor
	 */
	public JButton buttonChooseYellow;
	
	/**
	 * holds the game instance
	 */
	private Game game;
	/**
	 * holds the current player before the hand is changed to the next player
	 */
	private Player currentPlayer;
	/**
	 * holds the actionListener for this class
	 */
	private GameStagePageActionListener actionListener;
	/**
	 * list of button of cards in hand
	 */
	public ArrayList<JButton> listButtonCards;
	/**
	 * whether panel for hand is shown
	 */
	private boolean isPanelHandShow = true;
	
	/**
	 * Constructor for GameStagePage.
	 * It will display the view of game stage.
	 * @param paramGame the game instance
	 */
	public GameStagePage(Game paramGame) {
		game = paramGame;
		listButtonCards = new ArrayList<JButton>();
		
		frame = new JFrame();
		
		new PanelGameStateCreator(this);
		new PanelButtonCreator(this);
		new PanelDeckCreator(this);
		new PanelChooseColorCreator(this);
		new PanelHandCreator(this);
		new PanelMessageCreator(this);
		
		//set frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Uno Game");
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setIconImage(new ImageIcon(this.getClass().getResource("Images/Deck.png")).getImage());
		frame.setVisible(true);
		
		//set background image
		JLabel label = new JLabel();
		label.setBounds(0, 0, 1500, 800);
		
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("Images/Table_3.png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(1550, 800, java.awt.Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(newimg));
		frame.setContentPane(label);
		
		//add all panels to frame
		frame.add(panelGameState);
		frame.add(panelHand);
		frame.add(panelButton);
		frame.add(panelDeck);
		frame.add(panelChooseColor);
		frame.add(panelMessage);
		
		//set actionListener
		actionListener = new GameStagePageActionListener(this, game);
		buttonHideShow.addActionListener(actionListener);		
		buttonSkip.addActionListener(actionListener);			
		buttonDeck.addActionListener(actionListener);			
		buttonChooseRed.addActionListener(actionListener);	
		buttonChooseGreen.addActionListener(actionListener);	
		buttonChooseBlue.addActionListener(actionListener);
		buttonChooseYellow.addActionListener(actionListener);
	}
	
	/**
	 * Create panel and set it, used for all the panels
	 * @param top an integer specifying the width of the top,in pixels
	 * @param left an integer specifying the width of the left side,in pixels
	 * @param bottom an integer specifying the width of the bottom,in pixels
	 * @param right an integer specifying the width of the right side,in pixels
	 * @param x the new x-coordinate of this component
	 * @param y the new y-coordinate of this component
	 * @param width the new width of this component
	 * @param height the new height of this component
	 * @return JPanel created
	 */
	public JPanel createPanel(int top, int left, int bottom, int right, int x, int y, int width, int height) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		panel.setBounds(x, y, width, height);
		panel.setOpaque(false);
		return panel;
	}
	
	/**
	 * Update the view of all panels.
	 */
	public void updateView() {
		GameState gameState = game.getGameState();
		ArrayList<Player> players = game.getPlayers();
		currentPlayer = players.get(gameState.getCurrentPlayerId());
		updateHandView();
		updateGameStateView(gameState);
		updateLastCardView();
		showMessage(MESSAGE_NONE);
	}
	
	/**
	 * Update the view for hands
	 */
	public void updateHandView() {
		for (int i = 0; i < currentPlayer.getHandSize(); i++) {
			String imageName = currentPlayer.isAI() ? "Deck" : currentPlayer.getCardInHand(i).toString();
			if (i < listButtonCards.size()) {
				JButton buttonCard = listButtonCards.get(i);
				setImageIcon(imageName, buttonCard);
			} else {
				JButton buttonCard = new JButton();
				setImageIcon(imageName, buttonCard);
				listButtonCards.add(buttonCard);
				panelHand.add(buttonCard);
				buttonCard.addActionListener(actionListener);
			}
		}
		for (int i = listButtonCards.size() - 1; i >= currentPlayer.getHandSize(); i--) {
			JButton buttonCard = listButtonCards.get(i);
			panelHand.remove(buttonCard);
			listButtonCards.remove(i);
		}
		panelHand.revalidate();
		panelHand.repaint();
		isPanelHandShow = currentPlayer.isAI() ? false : true;
	}
	
	/**
	 * Set the image icon for single button of card given the image name
	 * @param imageName String of current image name
	 * @param buttonCard button for card
	 */
	public void setImageIcon(String imageName, JButton buttonCard) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("Images/" + imageName + ".png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		buttonCard.setIcon(new ImageIcon(newimg));
		buttonCard.setMargin(new Insets(0,0,0,0));
	}
	
	/**
	 * Update the view for game state
	 */
	public void updateGameStateView(GameState gameState) {
		labelValidColor.setText("Color: " + gameState.getValidColor());
		if (gameState.getValidSymbol() == Card.Symbol.normal) {
			labelValidSymbolOrNumber.setText("Type: " + String.valueOf(gameState.getValidNumber()));
		} else {
			labelValidSymbolOrNumber.setText("Type: " + gameState.getValidSymbol());
		}
		labelnumCardsCumulated.setText("Cards Stacked: " + String.valueOf(gameState.getNumCardsCumulated()));
		labelcurrentPlayerName.setText("Player: " + currentPlayer.getName());
	}
	
	/**
	 * Update the view for the last card played
	 */
	public void updateLastCardView() {
		String imageName = game.getLastCard().toString();
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("Images/" + imageName + ".png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		labelLastCard.setIcon(new ImageIcon(newimg));
	}
	
	/**
	 * Show the panel for choose color
	 */
	public void showPanelChooseColor() {
		panelChooseColor.setVisible(true);
		showMessage(MESSAGE_CHOOSE_COLOR);
	}
	
	/**
	 * Show the message in labelMessage
	 * @param message information that need to display
	 */
	public void showMessage(String message) {
		labelMessage.setText(message);
	}
	
	/**
	 * Hide the panel to choose color
	 */
	public void hidePanelChooseColor() {
		panelChooseColor.setVisible(false);
		showMessage(MESSAGE_NONE);
	}
	
	/**
	 * Hide the hand of cards.
	 * The function is called by Hide button
	 */
	public void hideCards() {
		for (int i = 0; i < listButtonCards.size(); i++) {
			JButton currButton = listButtonCards.get(i);
			setImageIcon("Deck", currButton);
		}
		panelHand.revalidate();
		panelHand.repaint();
		isPanelHandShow = false;
	}
	
	public boolean getIsPanelHandShow() {
		return isPanelHandShow;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Close the window for game stage page
	 */
	public void close() {
		frame.dispose();
	}
}
