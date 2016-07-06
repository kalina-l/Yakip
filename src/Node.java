import java.util.ArrayList;

public class Node implements Comparable<Node>, IHeapItem<Node>{

	public int x;
	public int y;
	//0-3 = playerID
	//4 = empty
	//5 = wall
	public int value;
	public int gCost;
	public int hCost;
	public Node parentNode;
	private int heapIndex;
	
	public Node(int x, int y, int value){
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public boolean isWall(){
		return(value == 5);
	}
	
	private int fCost(){
		return gCost + hCost;
	}
	
	@Override
	public boolean equals(Object object) {
	    if (!(object instanceof Node)) {
	        return false;
	    }

	    Node coObject = (Node) object;
	    
	    return this.x == coObject.x && this.y == coObject.y;
	}

	@Override
	public int compareTo(Node o) {
		int compare = Integer.compare(fCost(), fCost());
        if (compare == 0) {
            compare = Integer.compare(hCost, o.hCost);
        }
        return -compare;
	}

	@Override
	public int getHeapIndex() {
		return heapIndex;
	}
	
	@Override
	public void setHeapIndex(int value) {
		 heapIndex = value;
	}
}
