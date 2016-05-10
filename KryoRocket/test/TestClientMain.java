import dto.CreateGameMessage;
import dto.CreateGameResponse;
import dto.GameState;
import dto.JoinGameMessage;
import dto.JoinGameResponse;
import dto.JoinMessage;
import dto.JoinResponse;
import network.GameHubListener;
import network.KryoClient;

public class TestClientMain implements GameHubListener {

	public static void main(String[] args) throws InterruptedException {
		new TestClientMain().test();
	
	}

	private volatile String userHash = "N/A";
	private String gameId;

	private void test() throws InterruptedException {
		KryoClient kryoClient = new KryoClient(this);
		new Thread(kryoClient).start();
		Thread.sleep(1000);
		System.out.println("sending");
		kryoClient.receiveJoinMessage(new JoinMessage("brian", "kodeord"));
		Thread.sleep(1000);
		kryoClient.receiveCreateGameMessage(new CreateGameMessage(userHash));
		Thread.sleep(1000);
		kryoClient.receiveJoinMessage(new JoinMessage("brain", "password"));
		Thread.sleep(1000);
		kryoClient.receiveJoinGameMessage(new JoinGameMessage(gameId, userHash));
		Thread.sleep(4000);
		
	}

	@Override
	public void onJoinResponse(JoinResponse joinResponse) {
		System.out.println("modtog svar:" + joinResponse);
		this.userHash = joinResponse.uuid;
		
	}

	@Override
	public void onCreateGameResponse(CreateGameResponse createGameResponse) {
		System.out.println(this.getClass() +"modtog createGameResponse: " + createGameResponse);
		this.gameId = createGameResponse.gameId;
		
	}

	@Override
	public void onJoinGameResponse(JoinGameResponse joinGameResponse) {
		System.out.println(this.getClass() + ": User Joined Game...");
		
	}

	@Override
	public void onGameStateChanged(GameState gameState) {
		System.out.println(this.getClass() + ": received GameState: " + gameState);
		
	}

}
