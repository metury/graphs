package cz.cuni.mff.java.graphs;

import java.util.PriorityQueue;
import java.io.*;

/**
 * Class implementing Dijkstra's algorithm for shortest paths.
 */
public class Dijkstra{
	public static double[] dijkstra(Graph G, Vertex v){
		return dijkstra(G, v, new Edge[G.vertexSize()]);
	}
	public static double[] dijkstra(Graph G, Vertex v, Edge[] predecessors){
		boolean[] addedVertices = new boolean[G.vertexSize()];
		boolean[] addedEdges = new boolean[G.edgeSize()];
		double[] lengths = new double[G.vertexSize()];

		PriorityQueue<Edge> heap = new PriorityQueue<Edge>(G.edgeSize(), (e,f) -> Double.compare(e.getValue(), f.getValue()));
		// This math round can be problematic
		addedVertices[v.getId()] = true;
		for(int i = 0; i < G.vertexSize(); ++i){
			lengths[i] = Double.MAX_VALUE;
		}
		lengths[v.getId()] = 0;
		addEdges(G, v, heap, addedEdges, addedVertices);
		while(heap.size() > 0){
			getMin(G, heap, predecessors, lengths, addedVertices, addedEdges);
		}
		return lengths;
	}
	/**
	 * Add edges from vertex if it is directed or all if the graph is not directed.
	 * Also it was not added.
	 */
	private static void addEdges(Graph G, Vertex v, PriorityQueue<Edge> heap, boolean[] addedEdges, boolean[] addedVertices){
		for(Edge e : v){
			if(!addedEdges[e.getId()]){ // if it wasn't there
				if(!G.isDirected()){
					heap.add(e);
					addedEdges[e.getId()] = true;
				}
				else if(v == e.getFrom()){
					heap.add(e);
					addedEdges[e.getId()] = true;
				}
			}
		}
		addedVertices[v.getId()] = true;
	}
	private static void getMin(Graph G, PriorityQueue<Edge> heap, Edge[] predecessors, double[] lengths, boolean[] addedVertices, boolean[] addedEdges){
		Edge e = heap.poll();
		Vertex vFrom = addedVertices[e.getFrom().getId()] ? e.getFrom() : e.getTo();
		Vertex vTo = addedVertices[e.getFrom().getId()] ? e.getTo() : e.getFrom();
		if(lengths[vTo.getId()] > lengths[vFrom.getId()] + e.getValue()){
			lengths[vTo.getId()] = lengths[vFrom.getId()] + e.getValue();
			predecessors[vTo.getId()] = e;
			addEdges(G, vTo, heap, addedEdges, addedVertices);
		}
	}
	private static boolean[] path(Graph G, Vertex v, Edge[] predecessors){
		int index = v.getId();
		Vertex vertex = v;
		boolean[] path = new boolean[G.edgeSize()];
		while(predecessors[index] != null){
			path[predecessors[index].getId()] = true; // the edge before
			vertex = predecessors[index].getSecondVertex(vertex);
			index = vertex.getId();
		}
		return path;
	}
	private static boolean[] reversePath(Graph G, Vertex v, Edge[] predecessors){
		boolean[] path = path(G, v, predecessors);
		for(int i = 0; i < path.length; ++i){
			path[i] = !path[i];
		}
		return path;
	}
	public static void dijkstraVisualize(Graph G, Vertex v, String filePath){
		Edge[] predecessors = new Edge[G.vertexSize()];
		double[] result = dijkstra(G, v, predecessors);
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			out.write("# Dijkstrův algoritmus pro nejkratší cesty\n\n");
			out.write("Náš vstupní graf je následující:\n\n");
			G.exportMermaid(out, true);
			out.write("Nyní se podíváme na hodnoty nejkratších cest a také na konkrétní cesty.\n\n");
			
			for(int i = 0; i < G.vertexSize(); ++i){
				out.write("## Vrchol ");
				out.write(Integer.toString(i));
				out.write(" je to: ");
				out.write(Double.toString(result[i]));
				out.write("\n\n");
				G.exportMermaid(out, true, reversePath(G, G.getVertex(i), predecessors));
			}
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
