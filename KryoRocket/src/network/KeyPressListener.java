package network;

import dto.KeyPressMessage;

public interface KeyPressListener {
	void receiveKeyPress(KeyPressMessage keyMessage);

}
