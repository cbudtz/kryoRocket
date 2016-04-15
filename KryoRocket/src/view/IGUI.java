package view;
import dto.GameState;
import network.EngineListener;
import network.InputListener;

public interface IGUI extends EngineListener {
	void drawGameState(GameState state);
	void registerInputListener(InputListener listener);
}
