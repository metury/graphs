package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.Random;

public class GraphAlgorithms{
	public static void minCut(Graph G){
		Graph H = G.clone();
		Random r = new Random();
		while(H.vertexSize() > 2){
			H.removeLoops();
			int id = r.nextInt(H.edgeSize());
			try{
				H.contractEdge(id);
			} catch(NonexistingEdge ne){
				System.err.println(ne);
			}
			System.out.println("Edges: " + H.edgeSize() + " Vertices: " + H.vertexSize());
		}
		H.exportDot("/home/tomas/git/java/project/src/mincut.dot");
	}
	public static void minCutVisualize(Graph G, String filePath){
		
	}
	public static Graph shortestPath(Graph G){
		return null;
	}
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
