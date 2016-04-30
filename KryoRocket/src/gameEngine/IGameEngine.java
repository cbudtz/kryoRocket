package gameEngine;

import java.util.Map;

import dto.GameSettings;
import dto.KeyPressMessage;
import network.EngineListener;

public interface IGameEngine{
	//Pre action
	void initializegame(GameSettings settings, String gameId); //Runs gameEngine in own thread;
	void joinGame(String playeruuid); //returns SessionID for player
	//In game
	void onKeyPressMes(KeyPressMessage keyMsg);
	void onPlayerSelection(Map<String,String> selections);
	//Hub listens to engine
	void registerGameStateListener(EngineListener listener);

}
