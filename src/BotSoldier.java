
public class BotSoldier extends Bot{

	public BotSoldier(int playerID){
		super(playerID);
		id = 1;
	}
	
	public void findPath(Board board){
		super.findPath(board, false);
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
			
			value += 10;
			
			if(node.value != 4){
				value += 5;
				
				if(Math.abs(node.x - (int)x) < 4 || Math.abs(node.y - (int)y) > 4) {
					value += 15;
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
