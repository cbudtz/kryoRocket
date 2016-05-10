package gameEngine;

import java.util.Map;

import dto.GameSettings;
import dto.GameState;
import dto.KeyPressMessage;
import network.EngineListener;

public interface IGameEngine{
	//Pre action
	GameState initializegame(GameSettings settings, String gameId, EngineListener engineListener); //Runs gameEngine in own thread returns initial Gamestate;
	void joinGame(String playeruuid); //returns SessionID for player
	//In game
	void onKeyPressMes(KeyPressMessage keyMsg);
	void onPlayerSelection(Map<String,String> selections);
	//Hub listens to engine
	void registerGameStateListener(EngineListener listener);

}
