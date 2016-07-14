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
		
		if(node.unreachable){
			return -40;
		}
		else if(node.isWall()){
			//value -= 15;
		}
		else {
			if(node.value != playerID) {
		
				value += 15;
				
				if(node.value == board.getThreatID(playerID)){
					value += 15;
				}
				
				if(node.value == 4)
					value += 20;
			
				if(node.y == (int)y){
					value += 15;
				}
			}
			else{
				value -= 30;
			}
			
			for(Node neighbor : board.getSideNeighbours(node)) {
				if(neighbor.isWall())
					value -= 5;
			}
			
			for(int i=0; i<4; i++) {
				if(i != playerID) {
					for(int j=0; j<3; j++){
						if((int)network.getX(i, j) == node.x && (int)network.getY(i, j) == node.y){
							value -= 200;
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
			
			/*
			if(Math.abs(direction[0]) < 0.2f && Math.abs(direction[1]) < 0.2f){
				getPath().remove(0);
			}
			*/

			
			if (getPath().get(0).x == (int) x && getPath().get(0).y == (int) y) {
				getPath().remove(0);
			}
			
		}
		return direction;
	}
}
