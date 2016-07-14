
public class BotScout extends Bot{

	public boolean waiting;
	
	public BotScout(int playerID){
		super(playerID);
		id = 0;
	}
	
	public void findPath(Board board){
		if(!waiting)
			super.findPath(board);
	}
	
	public float[] move(Board board){
		float[] direction = super.move(board);
		if(getPath().isEmpty())	waiting = true;
		return direction;
	}
}
