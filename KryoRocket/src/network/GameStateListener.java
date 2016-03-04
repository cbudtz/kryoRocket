package network;

import dto.GameState;

public interface GameStateListener {
	void receiveGameState(GameState state);

}
