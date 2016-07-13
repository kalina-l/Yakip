import java.util.ArrayList;
import java.util.Random;

import lenz.htw.yakip.net.NetworkClient;

public class Bot {
	
	private ArrayList<Node> path;
	public int id;
	public float x;
	public float y;
	
	public ArrayList<Node> getPath(){
		return path;
	}
	
	public Bot(){
	}
	
	public void findPath(Board board){
		int xFull = (int) x;
		int yFull = (int) y;
		
		Node dest = findDestination(board);
		
		AStar.findPath(board, board.board[xFull][yFull],
				board.board[dest.x][dest.y]);
	}
	
	private Node findDestination(Board board){
		Random rnd = new Random();
		int randomWalkableX = rnd.nextInt(17);
		int randomWalkableY = rnd.nextInt(17);
		while (board.board[randomWalkableX + 5][randomWalkableY + 5].isWall()) {
			randomWalkableX = rnd.nextInt(17);
			randomWalkableY = rnd.nextInt(17);
		}
		randomWalkableX += 5;
		randomWalkableY += 5;
		return board.board[randomWalkableX][randomWalkableY];
	}
	
	public void updatePosition(NetworkClient network, int myPlayerNumber){
		x = network.getX(myPlayerNumber, id);
		y = network.getY(myPlayerNumber, id);
	}
}