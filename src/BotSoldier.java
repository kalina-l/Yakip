
public class BotSoldier extends Bot{

	public BotSoldier(int playerID){
		super(playerID);
		id = 1;
	}
	
	public void findPath(Board board){
		super.findPath(board);
	}
	
public int getNodeValue(Node node){
		
		int value = 0;
		
		if(Math.abs(node.x - (int)x) > 16 || Math.abs(node.y - (int)y) > 16)
			value -= 10;
		
		
		if(node.unreachable) {
			return -99;
		}
		else if(node.isWall()) {
			value -= 15;
		} 
		else if(node.value != playerID){
			
			value += 15;
			
			if(node.value != 4){
				value += 15;
			}
		}
		
		//override this
		return value;
	}
}
