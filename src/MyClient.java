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
		bots.add(new BotScout());
		bots.add(new BotSoldier());
		bots.add(new BotTank());
		

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
					//System.out.println("x: " + currBot.getPath().get(0).x + ", y: " + currBot.getPath().get(0).y);
					if (currBot.getPath().get(0).x == (int) currBot.x && currBot.getPath().get(0).y == (int) currBot.y) {
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