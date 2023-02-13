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
}
