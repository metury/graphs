package cz.cuni.mff.java.graphs;

/**
 * This class implements some of the very basic graph algorithms.
 * Mainly it is if the graph is connected.
 * All of its methods are static.
 */
public class GraphAlgorithms{
	/**
	 * To find out if the given graph is connected.
	 * @param G Given graph.
	 * @return True if it is connected.
	 */
	public static boolean isConnected(Graph G){
		boolean[] found = new boolean[G.vertexSize()];
		if(G.vertexSize() == 0){
			return true;
		}
		else{
			try{
				connectRecursive(G.getVertex(0), found);
			} catch(NonexistingVertex nv){
				System.err.println(nv);
			}
		}
		boolean result = true;
		for(boolean b : found){
			result = result && b;
		}
		return result;
	}
	/**
	 * Recursive call on vertex to look on its neighbours. DFS
	 * @param v Current vertex.
	 * @param found List of found vertices.
	 */
	private static void connectRecursive(Vertex v, boolean[] found){
		found[v.getId()] = true;
		for(Edge e : v){
			if(!found[e.getFrom().getId()]){
				connectRecursive(e.getFrom(), found);
			}
			if(!found[e.getTo().getId()]){
				connectRecursive(e.getTo(), found);
			}
		}
	}
	/**
	 * If  the graph is directed go by the edge directions.
	 * @param G Is the given graph.
	 * @return True if the Graph is strongly connected.
	 */
	public static boolean isStronglyConnected(Graph G){
		if(!G.isDirected()){
			return isConnected(G);
		}
		boolean[] found = new boolean[G.vertexSize()];
		if(G.vertexSize() == 0){
			return true;
		}
		else{
			try{
				stronglyConnectRecursive(G.getVertex(0), found);
			} catch(NonexistingVertex nv){
				System.err.println(nv);
			}
		}
		boolean result = true;
		for(boolean b : found){
			result = result && b;
		}
		return result;
	}
	/**
	 * Recursive call on vertex to look on its neighbours. DFS on directed.
	 * @param v Current vertex.
	 * @param found List of found vertices.
	 */
	private static void stronglyConnectRecursive(Vertex v, boolean[] found){
		found[v.getId()] = true;
		for(Edge e : v){
			if(!found[e.getTo().getId()]){
				connectRecursive(e.getTo(), found);
			}
		}
	}
}
