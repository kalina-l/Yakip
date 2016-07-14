import lenz.htw.yakip.net.NetworkClient;
import lenz.htw.yakip.net.*;
import java.util.ArrayList;

public class BotScout extends Bot{

	public boolean waiting;
	private Board board;
	private NetworkClient network;
	private int playerID;
	private Timer timer;
	
	public BotScout(int playerID, NetworkClient network){
		super(playerID);
		id = 0;
		this.network = network;
		this.playerID = playerID;
		timer = new Timer(this);
	}
	
	public void findPath(Board board){
		this.board = board;
		if(!waiting)
			super.findPath(board, false);
	}
	
	public float[] move(Board board){
		float[] direction = new float[2];
		
		if (getPath().isEmpty()) {
			this.findPath(board);
			waiting = true;
			timer.start();
			
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
	
	public int getNodeValue(Node node){
		
		int value = 0;
		
		ArrayList<Node> path = AStar.findPath(board, board.board[(int)x][(int)y],
				board.board[node.x][node.y], false);
		
		if(node.unreachable) {
			return -40;
		}
		else if(node.isWall()) {
			//value -= 5;
		} 
		else if(node.value != playerID){
			
			value += 10 - path.size();
			
			value += 15;
			
			for(Node neighbor : board.getSideNeighbours(node)) {
				if(neighbor.isWall())
					value += 15;
			}
			
			for(int i=0; i<4; i++) {
				if(i != playerID) {
					for(int j=0; j<3; j++){
						if((int)network.getX(i, j) == node.x && (int)network.getY(i, j) == node.y){
							value -= 50;
						}
					}
				}
			}
		}
		else{
			if(node.x == (int)x && node.y == (int)y)
			{
				value = -60;
			}
			else
			{
				value = -10 - path.size();
			}
		}
		
		//override this
		return value;
	}
}

class Timer implements Runnable {
	private Thread t;
	private BotScout botScout;

	public Timer(BotScout botScout) {
		this.botScout = botScout;
	}

	public void run() {
		try {
			Thread.sleep(1000); // make a force-move if 4,5 sec have passed
			botScout.waiting = false;
		} catch (InterruptedException e) {
			System.out.println("Timer interrupted");
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "Timer");
			t.start();
		}
	}
}
