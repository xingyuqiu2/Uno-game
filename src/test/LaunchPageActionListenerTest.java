package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import view.LaunchPage;

class LaunchPageActionListenerTest {

	@Test
	void testLaunchPageActionListener() {
		LaunchPage lp = new LaunchPage();
		
		lp.getTextFieldInputHP().setText("x");
		lp.getButtonStartGame().doClick();
		assertEquals("<html>Input invalid, please enter integers</html>", lp.getLabelInfo().getText());
		
		lp.getTextFieldInputHP().setText("3");
		lp.getTextFieldInputBAI().setText("4");
		lp.getTextFieldInputSAI().setText("4");
		lp.getButtonStartGame().doClick();
		assertEquals("<html>Number invalid, total number of players should between 2-9</html>", lp.getLabelInfo().getText());
		
		lp.getTextFieldInputHP().setText("1");
		lp.getTextFieldInputBAI().setText("1");
		lp.getTextFieldInputSAI().setText("1");
		lp.getButtonStartGame().doClick();
		assertEquals("<html>Game start successfully</html>", lp.getLabelInfo().getText());
	}

}
