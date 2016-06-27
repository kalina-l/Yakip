import lenz.htw.yakip.net.NetworkClient;

public class Board {
	
	public byte[][] board;
	
	public Board(NetworkClient network){
		
		//0-3 = playerID
		//4 = empty
		//5 = wall
		board = new byte[32][32];
		
		for(int x=0; x<32; x++){
			for(int y=0; y<32;y++){
				if(network.isWall(x, y)){
					board[x][y] = 5;
				} else {
					board[x][y] = 4;
				}
			}
		}
	}
	
	public boolean isWall(int x, int y){
		return(board[x][y] == 5);
	}
	
	public void UpdateBoard(int x, int y, byte playerID){
		board[x][y] = playerID;
	}
	
	
}
