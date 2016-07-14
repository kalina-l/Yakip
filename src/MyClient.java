import lenz.htw.yakip.net.*;

import java.util.ArrayList;
import java.util.Random;

import lenz.htw.yakip.*;

public class MyClient {

	private static Board board;
	private static boolean loaded;
	private static int myPlayerNumber;

	public static void main(String args[]) {
		NetworkClient network = new NetworkClient(null, "CPU");
		myPlayerNumber = network.getMyPlayerNumber(); 
		
		ArrayList<Bot> bots = new ArrayList<>();
		BotScout botScout = new BotScout(myPlayerNumber, network);
		bots.add(botScout);
		bots.add(new BotSoldier(myPlayerNumber, network));
		bots.add(new BotTank(myPlayerNumber, network));
		

		while (network.isAlive()) {
			if (!loaded) {
				board = new Board(network);
				loaded = true;
			}

			for(Bot currBot : bots){
				currBot.updatePosition(network, myPlayerNumber);
				float[] direction = currBot.move(board);
				network.setMoveDirection(currBot.id, direction[0], direction[1]);
			}
			
			ColorChange cc;
			while ((cc = network.getNextColorChange()) != null) {
				board.UpdateBoard(cc.x, cc.y, (byte)cc.newColor);
				if(botScout.waiting && (botScout.finalDest.x == cc.x && botScout.finalDest.y == cc.y)){
					botScout.waiting = false;
				}
			}
		}
	}
}