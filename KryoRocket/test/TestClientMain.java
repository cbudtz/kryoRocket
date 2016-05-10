import dto.CreateGameMessage;
import dto.CreateGameResponse;
import dto.GameState;
import dto.JoinGameResponse;
import dto.JoinMessage;
import dto.JoinResponse;
import network.GameHubListener;
import network.KryoClient;

public class TestClientMain implements GameHubListener {

	public static void main(String[] args) throws InterruptedException {
		new TestClientMain().test();
	
	}

	private void test() throws InterruptedException {
		KryoClient kryoClient = new KryoClient(this);
		new Thread(kryoClient).start();
		Thread.sleep(1000);
		System.out.println("sending");
		kryoClient.receiveJoinMessage(new JoinMessage("brian", "kodeord"));
		kryoClient.receiveCreateGameMessage(new CreateGameMessage());
		Thread.sleep(1000);
		
	}

	@Override
	public void onJoinResponse(JoinResponse joinResponse) {
		System.out.println("modtog svar:" + joinResponse);
		
	}

	@Override
	public void onCreateGameResponse(CreateGameResponse createGameResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoinGameResponse(JoinGameResponse joinGameResponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStateChanged(GameState gameState) {
		// TODO Auto-generated method stub
		
	}

}
