import lenz.htw.yakip.net.*;

import java.util.Random;

import lenz.htw.yakip.*;

public class MyClient {

	private static Board board;
	private static boolean loaded;
	private static int myPlayerNumber;
	
	public static void main(String args[]) {
		NetworkClient network = new NetworkClient(null, "CPU");
		myPlayerNumber = network.getMyPlayerNumber();
		
		while (network.isAlive()) {
			if(!loaded){
				board = new Board(network); 
				loaded = true;
			}
			
			Random rnd = new Random();
			for (int i = 0; i < 3; ++i) {
				network.setMoveDirection(i, rnd.nextFloat() - 0.5f, rnd.nextFloat() - 0.5f);
				network.getX(network.getMyPlayerNumber(), 1);
				ColorChange cc;
				while ((cc = network.getNextColorChange()) != null) {
					board.UpdateBoard(cc.x, cc.y, (byte)cc.newColor);
				}
			}
		}
	}
}