
public class BotTank extends Bot {

	private Board board;
	
	public BotTank(int playerID){
		super(playerID);
		id = 2;
	}
	
	public void findPath(Board board){
		this.board = board;
		super.findPath(board, true);
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
