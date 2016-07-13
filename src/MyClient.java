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
				if (currBot.isEmpty()) {
					firstPath = AStar.findPath(board, board.board[botx][boty],
							board.board[randomWalkableX][randomWalkableY]);
				}
			}
			
			// bot 0 - Scout
			int botx = (int) network.getX(myPlayerNumber, 0);
			int boty = (int) network.getY(myPlayerNumber, 0);
			if (firstPath.isEmpty()) {
				firstPath = AStar.findPath(board, board.board[botx][boty],
						board.board[randomWalkableX][randomWalkableY]);
			} else {
				float xDir = firstPath.get(0).x - botx;
				float yDir = firstPath.get(0).y - boty;
				network.setMoveDirection(0, xDir, yDir);
				if (firstPath.get(0).x == botx && firstPath.get(0).y == boty) {
					firstPath.remove(0);
				}
			}
			
			// test A* on bot 1
			botx = (int)  network.getX(myPlayerNumber, 1);
			boty = (int) network.getY(myPlayerNumber, 1);
			if (secondPath.isEmpty()) {
				secondPath = AStar.findPath(board, board.board[botx][boty],
						board.board[randomWalkableX][randomWalkableY]);
			} else {
				float xDir = secondPath.get(0).x - botx;
				float yDir = secondPath.get(0).y - boty;
				network.setMoveDirection(1, xDir, yDir);
				if (secondPath.get(0).x == botx && secondPath.get(0).y == boty) {
					secondPath.remove(0);
				}
			}
			
			// test A* on bot 2
			botx = (int) network.getX(myPlayerNumber, 2);
			boty = (int) network.getY(myPlayerNumber, 2);
			if (thirdPath.isEmpty()) {
				thirdPath = AStar.findPath(board, board.board[botx][boty],
						board.board[randomWalkableX][randomWalkableY]);
			} else {
				float xDir = thirdPath.get(0).x - botx;
				float yDir = thirdPath.get(0).y - boty;
				network.setMoveDirection(2, xDir, yDir);
				if (thirdPath.get(0).x == botx && thirdPath.get(0).y == boty) {
					thirdPath.remove(0);
				}
			}
			
			ColorChange cc;
			while ((cc = network.getNextColorChange()) != null) {
				board.UpdateBoard(cc.x, cc.y, (byte)cc.newColor);
			}
		}
	}
}