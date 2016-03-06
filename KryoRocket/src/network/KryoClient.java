package network;
import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import dto.DataTransferObjects;
import dto.GameState;
import dto.KeyPressMessage;
//TODO refactor: make ClientInterface
public class KryoClient implements Runnable, KeyPressListener{

	private Client client;
	public volatile GameStateListener listener;

	@Override
	public void run() {
		client = new Client();
		client.start();
		DataTransferObjects.register(client);
		
		client.addListener(new TurboClientListener());
		try {
			client.connect(5000, "52.30.89.247", DataTransferObjects.TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final class TurboClientListener extends Listener {
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
		}
		
		@Override
		public void received(Connection connection, Object object) {
			if (object instanceof String){
				System.out.println(object);
			} else if (object instanceof GameState) {
				System.out.println("TurboClientListener - got: " + object.getClass());
				if (listener!=null){
					System.out.println("Sending gameState to listener: " + listener);
					listener.receiveGameState((GameState)object);
				}
			} else {
				System.out.println("TurboClientListener - got:" + object.getClass());
			}
		}
	}
	public static void main(String[] args) {
		KryoClient k = new KryoClient();
		k.run();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void receiveKeyPress(KeyPressMessage keyMessage) {
		if (client!=null){
			client.sendTCP(keyMessage);
			System.out.println("KryoClient: sending Keypress to server");
		} else {
			System.out.println("KryoClient: no network client");
		}
		
	}

}
