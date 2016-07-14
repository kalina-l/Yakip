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
		int xFull = (int) x;
		int yFull = (int) y;
		
		path.clear();
		
		while(path.size() == 0)
		{
			Node dest = findDestination(board);
			
			path = AStar.findPath(board, board.board[xFull][yFull],
					board.board[dest.x][dest.y]);
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
					
					for(int x=0; x<fieldWidth; x++){
						for(int y=0; y<fieldWidth;y++){
							int xPos = i*fieldWidth/2 + x;
							int yPos = j*fieldWidth/2 + y;
							
							tempField[x][y] = field[xPos][yPos];
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
				if(!bestField[x][y].isWall()){
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
		float[] direction = new float[2];
		// TODO Tank soll immer an der Seitenkante seines Pfades laufen
		// TODO Scout soll immer warten
		// Rest läuft einfach mittig
		
		if (getPath().isEmpty()) {
			if(this instanceof BotScout){
				((BotScout)this).findPath(board);
			}
			else findPath(board);
		}
		else{
			direction[0] = path.get(0).x + 0.5f - x;
			direction[1] = path.get(0).y + 0.5f - y;

			if (path.get(0).x == (int) x && path.get(0).y == (int) y) {
				path.remove(0);
			}
		}
		return direction;
	}
	
	public void updatePosition(NetworkClient network, int myPlayerNumber){
		x = network.getX(myPlayerNumber, id);
		y = network.getY(myPlayerNumber, id);
	}
}