import lenz.htw.yakip.net.NetworkClient;

public class BotSoldier extends Bot{
	
	private NetworkClient network;
	private int playerID;

	public BotSoldier(int playerID, NetworkClient network){
		super(playerID);
		id = 1;
		this.playerID = playerID;
		this.network = network;
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
			
			value += 10;
			
			if(node.value != 4){
				value += 5;
				
				if(Math.abs(node.x - (int)x) < 4 || Math.abs(node.y - (int)y) > 4) {
					value += 15;
				}
				
				for(int j=0; j<3; j++){
					if(j != id) {
						if((int)network.getX(playerID, j) == node.x && (int)network.getY(playerID, j) == node.y){
							value -= 10;
						}
					}
				}
			}
		}
		
		//override this
		return value;
	}
}
