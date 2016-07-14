import lenz.htw.yakip.net.NetworkClient;
import lenz.htw.yakip.net.*;

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
			super.findPath(board);
	}
	
	public float[] move(Board board){
		float[] direction = super.move(board);
		if(getPath().isEmpty())	waiting = true;
		return direction;
	}
	
	public int getNodeValue(Node node){
		
		int value = 0;
		
		if(Math.abs(node.x - (int)x) > 20 || Math.abs(node.y - (int)y) > 20)
			value -= 10;
		
		
		if(node.unreachable) {
			return -99;
		}
		else if(node.isWall()) {
			value -= 5;
		} 
		else if(node.value != playerID){
			
			value += 15;
			
			for(Node neighbor : board.getSideNeighbours(node)) {
				if(neighbor.isWall())
					value += 5;
			}
			
			for(int i=0; i<4; i++) {
				if(i != playerID) {
					for(int j=0; j<3; j++){
						if((int)network.getX(i, j) == node.x && (int)network.getY(i, j) == node.y){
							value -= 10;
						}
					}
				}
			}
		}
		
		//override this
		return value;
	}
}
