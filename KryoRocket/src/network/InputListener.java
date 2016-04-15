package network;

import dto.JoinMessage;
import dto.KeyPressMessage;

public interface InputListener {
	void receiveKeyPress(KeyPressMessage keyMessage);
	void receiveJoinMessage(JoinMessage object);

}
