package network;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import dto.DataTransferObjects;
import dto.GameState;
import dto.KeyPressMessage;
import dto.PlayerData;
import dto.GameKeys;

public class KryoServer {
	private volatile Set<GameKeys> keysPressed = new HashSet<>();
	

	Server server;
	public KryoServer(){
		server = new Server(){
			protected Connection newConnection(){
				return new TurboConnection();
			}
		};
		DataTransferObjects.register(server);
		try {
			server.bind(5151);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.addListener(new TurboServerListener());
		server.start();
	}

	public class TurboConnection extends Connection{

	}

	public static void main(String[] args) {
		KryoServer k = new KryoServer();
		PlayerData player = new PlayerData(10,10,0,"Brian");

		while (true){
			for (GameKeys key : k.keysPressed) {
				switch (key) {
				case UP:
					player.yPos--;
					break;
				case DOWN:
					player.yPos++;
					break;
				case LEFT:
					player.xPos--;
					break;
				case RIGHT:
					player.xPos++;
				case FIRE1:
				case FIRE2:
				default:
					break;
				}
			}
			GameState g = new GameState();
			g.players.add(player);
			k.server.sendToAllTCP(g);
			System.out.println("KryoServer: sent gamestate!");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class TurboServerListener extends Listener {


		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof KeyPressMessage){
				KeyPressMessage keyMsg = (KeyPressMessage) object;
						System.out.println();
						System.out.println("KryoServer: Received:" + keyMsg.keysDown);
						keysPressed.clear();
						for (GameKeys gameKey : keyMsg.keysDown) {
							keysPressed.add(gameKey);
						}
			}
			
			super.received(connection, object);
		}
		
	}
}
