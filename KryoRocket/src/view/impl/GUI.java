package view.impl;

import javax.swing.JFrame;

import dto.GameState;
import dto.PlayerData;
import view.IGUI;

public class GUI implements IGUI {

	JFrame frame;
	GamePanel panel;

	public GUI() {
		frame = new JFrame("Test GUI");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new GamePanel();
		panel.setSize(800, 600);
		frame.add(panel);
		frame.setVisible(true);

	}

	@Override
	public void drawGameState(GameState state) {
		panel.state = state;
		panel.repaint();

	}

	public static void main(String[] args) {
		GUI g = new GUI();

		for (int i =0;i<400; i++){
			GameState state = new GameState();
			state.players.add(new PlayerData(100,100+i,i/10f, "Brian"));
			state.players.add(new PlayerData(200,100+i,i/10f, "Brian"));
			g.drawGameState(state);
			delay(200);
		}
	}

	public static void delay(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveGameState(GameState state) {
		System.out.println("GUI: GameState received!: " + state);
		drawGameState(state);
		
	}

}
