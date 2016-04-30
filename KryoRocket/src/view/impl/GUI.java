package view.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.JFrame;

import dto.GameKeys;
import dto.GameState;
import dto.JoinResponse;
import dto.KeyPressMessage;
import dto.PlayerData;
import network.InputListener;
import view.IGUI;

public class GUI implements IGUI {

	private static final String testPlayerID2 = "2";
	private static final String testPlayerID1 = "1";
	public String playerID = testPlayerID1;
	JFrame frame;
	GamePanel panel;
	private InputListener keyListener;
	private Set<GameKeys> gameKeys = new HashSet<>();

	public GUI() {
		frame = new JFrame("Test GUI");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new GamePanel();
		panel.setSize(800, 600);
		frame.add(panel);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("GUI: Key Typed: " + e.getKeyCode());
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				System.out.println("GUI: Key Released: " + keyCode);
				KeyPressMessage keyPressMessage = new KeyPressMessage();
				
				switch (keyCode) {
				case 38:
					gameKeys.remove(GameKeys.UP);
					keyPressMessage.released=GameKeys.UP;
					break;
				case 40:
					gameKeys.remove(GameKeys.DOWN);
					keyPressMessage.released= GameKeys.DOWN;
					break;
				case 37:
					gameKeys.remove(GameKeys.LEFT);
					keyPressMessage.released= GameKeys.LEFT;
					break;
				case 39:
					gameKeys.remove(GameKeys.RIGHT);
					keyPressMessage.released=GameKeys.RIGHT;
					break;
				default:
					break;
				}
				keyPressMessage.keysDown = new ConcurrentSkipListSet<GameKeys>();
				keyPressMessage.keysDown.addAll(gameKeys);
				sendGameKeys(keyPressMessage);
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				System.out.println("GUI: Key Pressed: " + keyCode);
				KeyPressMessage keyPressMessage = new KeyPressMessage();
				
				switch (keyCode) {
				case 38:
					gameKeys.add(GameKeys.UP);
					keyPressMessage.pressed=GameKeys.UP;
					break;
				case 40:
					gameKeys.add(GameKeys.DOWN);
					keyPressMessage.pressed= GameKeys.DOWN;
					break;
				case 37:
					gameKeys.add(GameKeys.LEFT);
					keyPressMessage.pressed= GameKeys.LEFT;
					break;
				case 39:
					gameKeys.add(GameKeys.RIGHT);
					keyPressMessage.pressed=GameKeys.RIGHT;
					break;
				default:
					break;
				}
				keyPressMessage.keysDown = new ConcurrentSkipListSet<>();
				keyPressMessage.keysDown.addAll(gameKeys);
				
				sendGameKeys(keyPressMessage);
			}

			private void sendGameKeys(KeyPressMessage keyPressMessage) {
				if (keyListener!=null){
					keyPressMessage.ShipUUID=playerID;
					System.out.println("GUI: sending gameKeys:" + keyPressMessage.keysDown);
					keyListener.receiveKeyPress(keyPressMessage);
				}
				
			}
		});

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
			state.players.put(testPlayerID1,new PlayerData(100,100+i,i/10f, "Brian"));
			state.players.put(testPlayerID2, new PlayerData(200,100+i,i/10f, "Brian"));
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

	@Override
	public void registerInputListener(InputListener listener) {
		this.keyListener = listener;
		
	}

	

}
