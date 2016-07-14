import lenz.htw.yakip.net.*;

public class BotTank extends Bot {

	private Board board;
	private NetworkClient network;
	private int playerID;
	
	public BotTank(int playerID, NetworkClient network){
		
		super(playerID);
		id = 2;
		this.network = network;
		this.playerID = playerID;
	}
	
	public void findPath(Board board){
		this.board = board;
		super.findPath(board, true);
	}
	
	public int getNodeValue(Node node){
		
		int value = 0;
		
		if(Math.abs(node.x - (int)x) > 6 || Math.abs(node.y - (int)y) > 6)
			value -= 10;
		
		if(node.unreachable){
			return -99;
		}
		else if(node.isWall()){
			value -= 15;
		}
		else if(node.value != playerID){
			value += 15;
			
			for(Node neighbor : board.getSideNeighbours(node)) {
				if(neighbor.isWall())
					value -= 5;
			}
		
			if(node.y == (int)y){
				value += 15;
			}
			
			for(int i=0; i<4; i++) {
				if(i != playerID) {
					for(int j=0; j<3; j++){
						if((int)network.getX(i, j) == node.x && (int)network.getY(i, j) == node.y){
							value -= 20;
						}
					}
				}
			}
		}
		
		
		
		//override this
		return value;
	}
	
	public float[] move(Board board){
		float[] direction = new float[2];
		
		if (getPath().isEmpty()) {
			this.findPath(board);
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
}
