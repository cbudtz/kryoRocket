package dto;

import com.esotericsoftware.kryonet.EndPoint;

import view.Menu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.esotericsoftware.kryo.Kryo;


public class DataTransferObjects {

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		//Basic types
		kryo.register(String.class);
		kryo.register(ConcurrentHashMap.class);
		kryo.register(ConcurrentSkipListSet.class);
		
		//States - from server
		kryo.register(GameState.class);
		kryo.register(PlayerData.class);
		kryo.register(ShotData.class);
		//from client
		kryo.register(KeyPressMessage.class);
		kryo.register(GameKeys.class);
		
		
		//Before game messages with response
		kryo.register(JoinMessage.class);
		kryo.register(JoinResponse.class);
		
		kryo.register(CreateGameMessage.class);
		kryo.register(GameSettings.class);
		kryo.register(CreateGameResponse.class);
		
		kryo.register(JoinGameMessage.class);
		kryo.register(JoinGameResponse.class);
		
		//In-game messages
		
		
				
		// TODO Auto-generated method stub
		
	}

	public static final int TCP_PORT = 5151;

}
