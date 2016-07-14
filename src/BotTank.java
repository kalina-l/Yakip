
public class BotTank extends Bot {

	public BotTank(int playerID){
		super(playerID);
		id = 2;
	}
	
	public void findPath(Board board){
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
		}
		
		
		
		//override this
		return value;
	}
}
