package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.*;

/**
 * Class implementing algorithms for minimal spanning tree.
 */
public class MST{
	/**
	 * Algorithm finding the minimla spanning tree.
	 * Used algorithm is starting with one vertex and building tree from it.
	 * @param G Given graph.
	 * @return List of edges in minimal spanning tree.
	 */
	public static ArrayList<Edge> findMST(Graph G){
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>(G.edgeSize(), (e,f) -> Double.compare(e.getValue(), f.getValue()));
		boolean[] addedVertices = new boolean[G.vertexSize()];
		boolean[] addedEdges = new boolean[G.edgeSize()];
		ArrayList<Edge> result = new ArrayList<Edge>();
		int missingVertices = G.vertexSize();
		while(missingVertices > 0){
			int currentId = 0;
			for(int i = 0; i < addedVertices.length; ++i){
				if(!addedVertices[i]){
					currentId = i;
					missingVertices -= 1;
					addedVertices[i] = true;
					break;
				}
			}
			for(Edge e : G.getVertex(currentId)){
				if(!e.isLoop()){
					heap.add(e);
					addedEdges[e.getId()] = true;
				}
			}
			while(heap.size() > 0){
				Edge e = heap.poll();
				missingVertices -= addEdgeToMST(e, result, addedVertices, heap, addedEdges);
			}
		}
		return result;
	}
	/**
	 * Method for adding edge to the MST if the conditions are holding.
	 * @param e Which edge is taken to consideration.
	 * @param result All the edges in MST.
	 * @param addedVertices Which vertices are already in MST.
	 * @param heap Heap holding all edges incident to the tree.
	 * @param addedEdges Which edges were already taken in consideration.
	 * @return The number of vertices gone through.
	 */
	private static int addEdgeToMST(Edge e, ArrayList<Edge> result, boolean[] addedVertices, PriorityQueue<Edge> heap, boolean[] addedEdges){
		Vertex v;
		if(!addedVertices[e.getFrom().getId()]){
			v = e.getFrom();
			result.add(e);
		}
		else if (!addedVertices[e.getTo().getId()]){
			v = e.getTo();
			result.add(e);
		}
		else{
			return 0;
		}
		addedVertices[e.getFrom().getId()] = addedVertices[e.getTo().getId()] = true;
		for(Edge edge : v){
			if(!edge.isLoop() && !addedEdges[edge.getId()]){
				heap.add(edge);
				addedEdges[edge.getId()] = true;
			}
		}
		return 1;
	}
	/**
	 * Visualization of found minimal spanning tree with given graph.
	 * @param G Is the given graph.
	 * @param filePath Which markdown file to use as an export.
	 */
	public static void visualizeMST(Graph G, String filePath){
		Graph H = G.clone();
		if(H.isDirected()){
			H.switchDirected();
		}
		ArrayList<Edge> MST = findMST(H);
		boolean[] edges = new boolean[H.edgeSize()];
		for(int i = 0; i < edges.length; ++i){
			edges[i] = true;
		}
		for(Edge e : MST){
			edges[e.getId()] = false;
		}
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			out.write("# Nalezení minimální kostry (*MST*)\n\n");
			out.write("Máme daný na vstupu graf:\n\n");
			G.exportMermaid(out, true);
			if(G.isDirected()){
				out.write("Protože daný graf je orientovaný, tak jej převedeme na neorientovaný.\n\n");
			}
			out.write("Hledanou nejlehčí kostrou je znázorněna na grafu:\n\n");
			H.exportMermaid(out, true, edges);
			out.write("Váha dané minimální kostry je: ");
			double sum = 0;
			for(Edge e : MST){
				sum += e.getValue();
			}
			out.write(Double.toString(sum));
			out.write("\n");
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
