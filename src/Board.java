import java.util.ArrayList;

import lenz.htw.yakip.net.NetworkClient;

public class Board {
	
	public Node[][] board;
	
	public int[] playerScore;
	
	public Board(NetworkClient network){
		
		board = new Node[32][32];
		playerScore = new int[4];
		
		
		for(int x=0; x<32; x++){
			for(int y=0; y<32;y++){
				//0-3 = playerID
				//4 = empty
				//5 = wall
				if(network.isWall(x, y)){
					board[x][y] = new Node(x, y, 5);
				} else {
					board[x][y] = new Node(x, y, 4);
				}
			}
		}
	}
	
	public ArrayList<Node> getNeighbours(Node cell){
		ArrayList<Node> neighbours = new ArrayList<Node>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if((cell.x + x) >= 0 && (cell.x + x) < 32
					&& (cell.y + y) >= 0 && (cell.y + y) < 32){
					neighbours.add(board[cell.x + x][cell.y + y]);
				}
			}
		}
		return neighbours;
	}
	
    public ArrayList<Node> getSideNeighbours(Node cell) {
    	ArrayList<Node> neighbours = new ArrayList<>();
        if (cell.x - 1 >= 0)
            neighbours.add(board[cell.x - 1][cell.y]);
        if (cell.x + 1 < board[0].length - 1)
            neighbours.add(board[cell.x + 1][cell.y]);
        if (cell.y - 1 >= 0)
            neighbours.add(board[cell.x][cell.y - 1]);
        if (cell.y + 1 < board[1].length - 1)
            neighbours.add(board[cell.x][cell.y + 1]);
        return neighbours;
    }
	
	public void UpdateBoard(int x, int y, byte playerID){
		
		if(board[x][y].value < 4)
		{
			playerScore[board[x][y].value]--;
		}
		
		playerScore[playerID]++;
		
		board[x][y].value = playerID;
	}
	
	public int getWinnerID(){
		
		int bestScore = -100;
		int bestIndex = 0;
		
		for(int i=0; i < playerScore.length; i++){
			if(playerScore[i] > bestScore){
				bestScore = playerScore[i];
				bestIndex = i;
			}
		}
		
		return bestIndex;
	}
	
	public int getThreatID(int myID){
		int bestScore = -100;
		int bestIndex = 0;
		
		for(int i=0; i < playerScore.length; i++){
			if(i != myID){
				if(playerScore[i] > bestScore){
					bestScore = playerScore[i];
					bestIndex = i;
				}
			}
		}
		
		return bestIndex;
	}
}
