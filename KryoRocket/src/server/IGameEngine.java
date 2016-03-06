package server;

import java.util.UUID;

import dto.KeyPressMessage;

public interface IGameEngine {
	UUID joinGame(); //returns ID for player
	void onKeyPressMes(KeyPressMessage keyMsg);

}
