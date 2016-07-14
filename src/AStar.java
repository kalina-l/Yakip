import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public final class AStar {

	private AStar() {
	}

	public static ArrayList<Node> findPath(Board board, Node startNode, Node targetNode, boolean focusHorizontal) {
		Heap<Node> openSet = new Heap<Node>(Node.class, board.board[0].length * board.board[1].length);
		HashSet<Node> closedSet = new HashSet<Node>();
		openSet.Add(startNode);

		while (openSet.Count() > 0) {
			Node currentNode = openSet.RemoveFirst();

			if (currentNode == targetNode) {
				return retracePath(startNode, targetNode);
			}
			
			closedSet.add(currentNode);

			for (Node neighbour : board.getSideNeighbours(currentNode)) {
				if (neighbour.isWall() || closedSet.contains(neighbour)) {
					continue;
				}
				int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbour, focusHorizontal);
				if (newMovementCostToNeighbour < neighbour.gCost || !openSet.Contains(neighbour)) {
					neighbour.gCost = newMovementCostToNeighbour;
					neighbour.hCost = getDistance(neighbour, targetNode, focusHorizontal);
					neighbour.parentNode = currentNode;
					if (openSet.Contains(neighbour))
						openSet.SortUp(neighbour);
					else
						openSet.Add(neighbour);
				}
			}
		}
		if(startNode != targetNode) targetNode.unreachable = true;
		return new ArrayList<Node>();
	}

	private static ArrayList<Node> retracePath(Node startNode, Node endNode) {
		ArrayList<Node> path = new ArrayList<Node>();
		Node currentNode = endNode;
		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.parentNode;
		}
		path.add(startNode);
		Collections.reverse(path);
		for (Node node : path) {
			node.parentNode = null;
		}
		return path;
	}

	private static int getDistance(Node nodeA, Node nodeB, boolean focusHorizontal) {
		// Manhattan
		// int dstX = Math.abs(nodeA.x - nodeB.x);
		// int dstY = Math.abs(nodeA.y - nodeB.y);
		// return dstX + dstY;+
		int multiplicator = 1;
		if(focusHorizontal) multiplicator = 3;

		// Euclidean
		return multiplicator * (int) Math.round(
				Math.sqrt((nodeB.y - nodeA.y) * (nodeB.y - nodeA.y) + (nodeB.x - nodeA.x) * (nodeB.x - nodeA.x)));
	}
}