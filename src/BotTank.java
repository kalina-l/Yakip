
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
		
		if(node.isWall()){
			value -= 15;
		}
		
		if(node.value != playerID){
			value += 15;
		}
		
		if(node.unreachable)
			return -99;
		
		//override this
		return value;
	}
}
