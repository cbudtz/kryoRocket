package dto;

import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryo.Kryo;


public class DataTransferObjects {

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String.class);
		kryo.register(GameState.class);
		kryo.register(ArrayList.class);
		kryo.register(PlayerData.class);
		kryo.register(KeyPressMessage.class);
		kryo.register(GameKeys.class);
		kryo.register(ConcurrentHashMap.class);
		kryo.register(HashSet.class);
				
		// TODO Auto-generated method stub
		
	}

	public static final int TCP_PORT = 5151;

}
