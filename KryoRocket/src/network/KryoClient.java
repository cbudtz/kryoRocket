package network;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoNetException;
import com.esotericsoftware.kryonet.Listener;

import dto.CreateGameMessage;
import dto.CreateGameResponse;
import dto.DataTransferObjects;
import dto.GameState;
import dto.JoinGameMessage;
import dto.JoinGameResponse;
import dto.JoinMessage;
import dto.JoinResponse;
import dto.KeyPressMessage;

public class KryoClient implements Runnable, InputListener{
	@SuppressWarnings("unused")
	private final static String ec2Instance = "52.30.89.247";
	private final static String localHost = "localhost";

	private Client client;
	public volatile GameHubListener listener;
	public String ip;
	
	public KryoClient(GameHubListener listener) {
		this(localHost, listener);
	}
	
	public KryoClient(String remoteIP, GameHubListener listener){
		this.ip = remoteIP;
		this.listener=listener;
	}

	@Override
	public void run() {
		client = new Client();
		client.start();
		DataTransferObjects.register(client);

		client.addListener(new TurboClientListener());
		try {
			client.connect(5000, ip, DataTransferObjects.TCP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final class TurboClientListener extends Listener {
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
		}
//receive from hub
		@Override
		public void received(Connection connection, Object object) {
			
			if (listener == null) throw new KryoNetException(this.getClass() + ": WTF - received object before listener"); //If no listener - just ignore messages.
			if (object instanceof String){
				System.out.println(object);
			} else if (object instanceof GameState) {
				listener.onGameStateChanged((GameState)object);				
			} else if (object instanceof JoinResponse){
				listener.onJoinResponse((JoinResponse) object);
			} else if (object instanceof CreateGameResponse){
				listener.onCreateGameResponse((CreateGameResponse)object);
			} else if (object instanceof JoinGameResponse) {
				listener.onJoinGameResponse((JoinGameResponse)object);
			} else {
				System.err.println("TurboClientListener - got:" + object.getClass());
			}
		}
	}
//receive from GUI;
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
		System.out.println(client);
		if (client!=null)
			client.sendTCP(object);

	}
	@Override
	public void receiveCreateGameMessage(CreateGameMessage createGameMessage) {
		if (client!=null)client.sendTCP(createGameMessage);
	}
	
	@Override
	public void receiveJoinGameMessage(JoinGameMessage joinGameMessage) {
		if (client!= null) {
			client.sendTCP(joinGameMessage);
		}

	}
	@Override
	public void receivePlayerSelection(ConcurrentHashMap<String, String> selections) {
		if (client!=null) {
			client.sendTCP(selections);
		}

	}

}
