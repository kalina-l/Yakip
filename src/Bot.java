import java.util.ArrayList;
import java.util.Random;

import lenz.htw.yakip.net.NetworkClient;

public class Bot {
	
	private ArrayList<Node> path;
	public int id;
	public float x;
	public float y;
	int playerID;
	public Node finalDest;
	
	public ArrayList<Node> getPath(){
		return path;
	}
	
	public Bot(int playerID){
		path = new ArrayList<Node>();
		this.playerID = playerID;
	}
	
	public void findPath(Board board){
		findPath(board, false);
	}
	
	public void findPath(Board board, boolean focusHorizontal){
		int xFull = (int) x;
		int yFull = (int) y;
		
		path.clear();
		
		while(path.size() == 0)
		{
			Node dest = findDestination(board);
			
			path = AStar.findPath(board, board.board[xFull][yFull],
					board.board[dest.x][dest.y], focusHorizontal);
		}
	}
	
	private Node[][] getBestField(Node[][] field)
	{
		int fieldWidth = field[0].length / 2;
		
		if(fieldWidth < 2) {
			return field;
		} else {
			int bestValue = -9999;
			
			Node[][] bestField =  new Node[fieldWidth][fieldWidth];
			
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					Node[][] tempField = new Node[fieldWidth][fieldWidth];
					int tempValue = 0;
					
					for(int xi=0; xi<fieldWidth; xi++){
						for(int yi=0; yi<fieldWidth;yi++){
							int xPos = i*fieldWidth/2 + xi;
							int yPos = j*fieldWidth/2 + yi;
							
							tempField[xi][yi] = field[xPos][yPos];
							tempValue += getNodeValue(field[xPos][yPos]);
						}
					}
					
					if(tempValue > bestValue){
						bestField = tempField;
						bestValue = tempValue;
					}
				}
			}
			
			return getBestField(bestField);
		}
	}
	
	private int getNodeValue(Node node){
		
		if(this instanceof BotTank){
			return ((BotTank)this).getNodeValue(node);
		}
		
		if(this instanceof BotSoldier){
			return ((BotSoldier)this).getNodeValue(node);
		}
		
		if(this instanceof BotScout){
			return ((BotScout)this).getNodeValue(node);
		}
		
		int value = 0;
		
		if(node.isWall()){
			value -= 5;
		}
		
		if(node.unreachable)
			return -99;
		
		//override this
		return value;
	}
	
	
	private Node findDestination(Board board){
		
		Node bestNode = null;
		int bestNodeValue = -9999;
		
		Node[][] bestField = getBestField(board.board);
		
		for(int x=0; x<bestField[0].length; x++){
			for(int y=0; y<bestField[1].length; y++){
				if(!bestField[x][y].isWall() && !bestField[x][y].unreachable){
					int tempValue = getNodeValue(bestField[x][y]);
					
					if(tempValue > bestNodeValue){
						bestNodeValue = tempValue;
						bestNode = bestField[x][y];
					}
				}
			}
		}
		
		finalDest = bestNode;
		return bestNode;
		
		/*
		Random rnd = new Random();
		int randomWalkableX = rnd.nextInt(17);
		int randomWalkableY = rnd.nextInt(17);
		while (board.board[randomWalkableX + 5][randomWalkableY + 5].isWall()) {
			randomWalkableX = rnd.nextInt(17);
			randomWalkableY = rnd.nextInt(17);
		}
		randomWalkableX += 5;
		randomWalkableY += 5;
		return board.board[randomWalkableX][randomWalkableY];
		*/
	}
	
	public float[] move(Board board){
		if(this instanceof BotTank){
			return ((BotTank)this).move(board);
		}
		if(this instanceof BotScout){
			return ((BotScout)this).move(board);
		}
		if(this instanceof BotSoldier){
			return ((BotSoldier)this).move(board);
		}
		return null;
	}
	
	public void updatePosition(NetworkClient network, int myPlayerNumber){
		x = network.getX(myPlayerNumber, id);
		y = network.getY(myPlayerNumber, id);
	}
}