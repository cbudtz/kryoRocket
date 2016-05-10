package dto;

public class JoinResponse {
	public String uuid;

	public JoinResponse() {
		
	}
	public JoinResponse(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "JoinResponse [uuid=" + uuid + "]";
	}


}
