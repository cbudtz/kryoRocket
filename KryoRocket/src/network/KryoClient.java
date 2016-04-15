package network;
import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import dto.DataTransferObjects;
import dto.GameState;
import dto.JoinMessage;
import dto.JoinResponse;
import dto.KeyPressMessage;

public class KryoClient implements Runnable, InputListener{
	@SuppressWarnings("unused")
	private final String ec2Instance = "52.30.89.247";
	private final String localHost = "localhost";

	private Client client;
	public volatile EngineListener listener;

	@Override
	public void run() {
		client = new Client();
		client.start();
		DataTransferObjects.register(client);

		client.addListener(new TurboClientListener());
		try {
			client.connect(5000, localHost, DataTransferObjects.TCP_PORT);
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
			} else if (object instanceof JoinResponse){
				System.out.println(this.getClass() + ": Sending JoinResponse to listener");
				listener.receiveJoinResponse((JoinResponse) object);
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

	@Override
	public void receiveJoinMessage(JoinMessage object) {
		if (client!=null)
			client.sendTCP(object);
		
	}

}
