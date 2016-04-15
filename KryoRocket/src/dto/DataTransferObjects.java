package dto;

import com.esotericsoftware.kryonet.EndPoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.esotericsoftware.kryo.Kryo;


public class DataTransferObjects {

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String.class);
		kryo.register(GameState.class);
		kryo.register(GameKeys.class);
		kryo.register(JoinMessage.class);
		kryo.register(JoinResponse.class);
		kryo.register(ConcurrentHashMap.class);
		kryo.register(ConcurrentSkipListSet.class);
		kryo.register(KeyPressMessage.class);
				
		// TODO Auto-generated method stub
		
	}

	public static final int TCP_PORT = 5151;

}
