package network;

import dto.GameState;

public interface EngineListener {
	void receiveGameState(GameState state);

}
