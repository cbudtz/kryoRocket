package view;
import dto.GameState;
import network.GameStateListener;

public interface IGUI extends GameStateListener {
	void drawGameState(GameState state);
}
