package dto;

import java.util.concurrent.ConcurrentSkipListSet;

public class GetPublicGamesResponse {
	public ConcurrentSkipListSet<String> gameIDs;

	public GetPublicGamesResponse(ConcurrentSkipListSet<String> returnGameIds) {
		super();
		this.gameIDs = returnGameIds;
	}
	

}
