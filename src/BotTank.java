
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
