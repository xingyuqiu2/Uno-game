package test;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uno.Game;
import uno.Players.BaselineAI;
import uno.Players.Player;
import view.EndingScene;
import view.GameStagePage;
import view.LaunchPage;
import view.Main;

class ViewTest {

	@Test
	void testLaunchPage() {
		new LaunchPage();
	}
	
	@Test
	void testGameStagePage() {
		ArrayList<Player> listPlayers = new ArrayList<Player>();
		listPlayers.add(new BaselineAI("FirstPlayer", 0));
		listPlayers.add(new BaselineAI("SecondPlayer", 1));
		listPlayers.add(new BaselineAI("ThirdPlayer", 2));
		
		Game game = new Game(listPlayers);
		GameStagePage gameStagePage = new GameStagePage(game);
	}
	
	@Test
	void testEndingScene() {
		new EndingScene("Jack");
	}
	
	@Test
	void testMain() {
		Main m = new Main();
		m.main(null);
	}

}
