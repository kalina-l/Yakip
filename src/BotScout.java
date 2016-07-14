
public class BotScout extends Bot{

	public boolean waiting;
	private Board board;
	
	public BotScout(int playerID){
		super(playerID);
		id = 0;
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
