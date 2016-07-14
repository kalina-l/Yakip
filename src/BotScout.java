import lenz.htw.yakip.net.NetworkClient;
import lenz.htw.yakip.net.*;
import java.util.ArrayList;

public class BotScout extends Bot{

	public boolean waiting;
	private Board board;
	private NetworkClient network;
	private int playerID;
	
	public BotScout(int playerID, NetworkClient network){
		super(playerID);
		id = 0;
		this.network = network;
		this.playerID = playerID;
	}
	
	public void findPath(Board board){
		this.board = board;
		if(!waiting)
			super.findPath(board, false);
	}
	
	public float[] move(Board board){
		float[] direction = new float[2];
		
		if (getPath().isEmpty()) {
			this.findPath(board);
			waiting = true;
		}
		else{
			direction[0] = getPath().get(0).x + 0.5f - x;
			direction[1] = getPath().get(0).y + 0.5f - y;

			if (getPath().get(0).x == (int) x && getPath().get(0).y == (int) y) {
				getPath().remove(0);
			}
		}
		return direction;
	}
	
	public int getNodeValue(Node node){
		
		int value = 0;
		
		if(node.unreachable) {
			return -99;
		}
		else if(node.isWall()) {
			//value -= 5;
		} 
		else if(node.value != playerID){
			
			ArrayList<Node> path = AStar.findPath(board, board.board[(int)x][(int)y],
					board.board[node.x][node.y], false);
			
			if(path.size() > 6)
				value -= 15;
			
			value += 15;
			
			for(Node neighbor : board.getSideNeighbours(node)) {
				if(neighbor.isWall())
					value += 15;
			}
			
			for(int i=0; i<4; i++) {
				if(i != playerID) {
					for(int j=0; j<3; j++){
						if((int)network.getX(i, j) == node.x && (int)network.getY(i, j) == node.y){
							value -= 30;
						}
					}
				}
			}
		}
		
		//override this
		return value;
	}
}
