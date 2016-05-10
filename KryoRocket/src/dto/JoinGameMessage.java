package dto;

public class JoinGameMessage {
	public String gameuuid;
	public String playeruuid;
	
	public JoinGameMessage() {
		// TODO Auto-generated constructor stub
	}

	public JoinGameMessage(String gameuuid, String playeruuid) {
		super();
		this.gameuuid = gameuuid;
		this.playeruuid = playeruuid;
	}
	
	

}
