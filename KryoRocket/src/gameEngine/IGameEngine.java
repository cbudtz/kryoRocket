package gameEngine;

import dto.KeyPressMessage;
import network.EngineListener;

public interface IGameEngine extends Runnable{
	String joinGame(String name); //returns ID for player
	void onKeyPressMes(KeyPressMessage keyMsg);
	void registerGameStateListener(EngineListener listener);

}
