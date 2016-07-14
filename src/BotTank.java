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
		super.findPath(board);
	}
	
	public int getNodeValue(Node node){
		
		int value = 0;
		
		if(Math.abs(node.x - (int)x) > 10 || Math.abs(node.y - (int)y) > 10)
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
	
//	public float[] move(Board board){
//		float[] direction = super.move(board);
//		//System.out.println("B: " + direction[0] + ", " + direction[1]);
//		if(Math.abs(direction[0]) > Math.abs(direction[1])){	// x-Axis
//			direction[1] = getPath().get(0).y + 0.9f - y;
//		}
//		else{	// y-Axis
//			direction[0] = getPath().get(0).x + 0.9f - x;;
//		}
//		System.out.println("X: " + x + ", Y: " + y);
//		return direction;
//	}
}
