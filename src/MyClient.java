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
		bots.add(new BotScout(myPlayerNumber));
		bots.add(new BotSoldier(myPlayerNumber));
		bots.add(new BotTank(myPlayerNumber));
		

		while (network.isAlive()) {
			if (!loaded) {
				board = new Board(network);
				loaded = true;
			}

			for(Bot currBot : bots){
				currBot.updatePosition(network, myPlayerNumber);
				if (currBot.getPath().isEmpty()) {
					currBot.findPath(board);
				}
				else{
					float xDir = currBot.getPath().get(0).x + 0.5f - currBot.x;
					float yDir = currBot.getPath().get(0).y + 0.5f - currBot.y;
					network.setMoveDirection(currBot.id, xDir, yDir);
					if (currBot.getPath().get(0).x + 0.5f == currBot.x && currBot.getPath().get(0).y == currBot.y) {
						currBot.getPath().remove(0);
					}
				}
			}
			
			ColorChange cc;
			while ((cc = network.getNextColorChange()) != null) {
				board.UpdateBoard(cc.x, cc.y, (byte)cc.newColor);
			}
		}
	}
}