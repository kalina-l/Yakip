
public class BotScout extends Bot{

	private Board board;
	
	public BotScout(int playerID){
		super(playerID);
		id = 0;
	}
	
	public void findPath(Board board){
		this.board = board;
		super.findPath(board);
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
		}
		
		//override this
		return value;
	}
}
