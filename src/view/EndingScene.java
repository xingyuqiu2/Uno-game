package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Game ending scene page to indicate the winner.
 *
 */
public class EndingScene {
	private JFrame frame;
	private JPanel panel;
	private JLabel labelGameEnd;
	private JLabel labelInfo;
	
	/**
	 * Constructor for EndingScene.
	 * It will display the winner name of the game when game ends.
	 * @param winnerName the name of the winner
	 */
	public EndingScene(String winnerName) {
		frame = new JFrame();
		
		panel = new JPanel();
		
		labelGameEnd = new JLabel();
		labelGameEnd.setHorizontalAlignment(JLabel.CENTER);
		labelGameEnd.setVerticalAlignment(JLabel.CENTER);
		labelGameEnd.setText("Game Is Over");
		labelGameEnd.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 40));
		labelGameEnd.setForeground(Color.ORANGE);
		labelGameEnd.setBackground(Color.DARK_GRAY);
		labelGameEnd.setOpaque(true);
		
		labelInfo = new JLabel();
		labelInfo.setHorizontalAlignment(JLabel.CENTER);
		labelInfo.setVerticalAlignment(JLabel.BOTTOM);
		labelInfo.setText("Winner is " + winnerName);
		labelInfo.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 50));
		labelInfo.setForeground(Color.MAGENTA);
		
		panel.add(labelGameEnd);
		panel.add(labelInfo);
		
		panel.setBorder(BorderFactory.createEmptyBorder(100, 450, 500, 450));
		panel.setLayout(new GridLayout(0, 1));
		
		frame.add(panel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Uno Game");
		frame.pack();
		frame.setVisible(true);
	}
}
