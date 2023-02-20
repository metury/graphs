package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.*;

public class MST{
	public static ArrayList<Edge> findMST(Graph G){
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>(G.edgeSize(), (e,f) -> Double.compare(e.getValue(), f.getValue()));
		boolean[] addedVertices = new boolean[G.vertexSize()];
		boolean[] addedEdges = new boolean[G.edgeSize()];
		ArrayList<Edge> result = new ArrayList<Edge>();
		int missingVertices = G.vertexSize();
		if(G.vertexSize() == 0){
			return result;
		}
		for(Edge e : G.getVertex(0)){
			if(!e.isLoop()){
				heap.add(e);
				addedEdges[e.getId()] = true;
			}
		}
		while(heap.size() > 0 && missingVertices > 0){
			Edge e = heap.poll();
			addEdgeToMST(e, result, addedVertices, heap, addedEdges);
		}
		return result;
	}
	private static void addEdgeToMST(Edge e, ArrayList<Edge> result, boolean[] addedVertices, PriorityQueue<Edge> heap, boolean[] addedEdges){
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
			return;
		}
		addedVertices[e.getFrom().getId()] = addedVertices[e.getTo().getId()] = true;
		for(Edge edge : v){
			if(!edge.isLoop() && !addedEdges[edge.getId()]){
				heap.add(edge);
				addedEdges[edge.getId()] = true;
			}
		}
	}
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
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
