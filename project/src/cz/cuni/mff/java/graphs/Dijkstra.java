package cz.cuni.mff.java.graphs;

import java.util.PriorityQueue;
import java.io.*;

/**
 * Class implementing Dijkstra's algorithm for shortest paths.
 */
public class Dijkstra{
	/**
	 * Standard Dijkstra's algorithm, which returns only lengths.
	 * This algorithm returns wrong answers when negative cycle exist. But the algorithm stops every time.
	 * @param G WHich graph to use.
	 * @param v Which vertex is the root.
	 * @return List of all lengths to each vertex. Indexed by their ids.
	 */
	public static double[] dijkstra(Graph G, Vertex v){
		return dijkstra(G, v, new Edge[G.vertexSize()]);
	}
	/**
	 * Dijkstra algorithm also getting predecessors.
	 * In thisn instance predecessors are incoming edge.
	 * @param G WHich graph to use.
	 * @param v Which vertex is the root.
	 * @param predecessors Is an array where will be the predecessors stored. The size has to be same as the number of vertices.
	 * @return List of all lengths to each vertex. Indexed by their ids.
	 */
	public static double[] dijkstra(Graph G, Vertex v, Edge[] predecessors){
		boolean[] addedVertices = new boolean[G.vertexSize()];
		boolean[] addedEdges = new boolean[G.edgeSize()];
		double[] lengths = new double[G.vertexSize()];
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>(G.edgeSize(), (e,f) -> Double.compare(e.getValue(), f.getValue()));
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
	 * @param G Given graph.
	 * @param v Is the current vertex.
	 * @param heap The heap of the dijkstra's algorithm.
	 * @param addedEdges Edges which have been gone through.
	 * @param addedVertices Vertices which have been gone through.
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
	/**
	 * Get the minimum from the heap and if the lengths is shorter, change the values.
	 * @param G Is the given graph.
	 * @param heap The heap from Dijkstra's algorithm.
	 * @param predecessors Is the array of predecessors from Dijkstra's algorithm.
	 * @param lengths The array of lengths in the Dijkstra's algorithm.
	 * @param addedVertices Vertices which have been gone through.
	 * @param addedEdges Edges which have been gone through.
	 */
	private static void getMin(Graph G, PriorityQueue<Edge> heap, Edge[] predecessors, double[] lengths, boolean[] addedVertices, boolean[] addedEdges){
		Edge e = heap.poll();
		Vertex vFrom = addedVertices[e.getFrom().getId()] ? e.getFrom() : e.getTo();
		Vertex vTo = e.getSecondVertex(vFrom);
		if(lengths[vTo.getId()] > lengths[vFrom.getId()] + e.getValue()){
			lengths[vTo.getId()] = lengths[vFrom.getId()] + e.getValue();
			predecessors[vTo.getId()] = e;
			addEdges(G, vTo, heap, addedEdges, addedVertices);
		}
	}
	/**
	 * Get back the path from root to given vertex.
	 * @param G Is the given graph.
	 * @param v Is the given vertex.
	 * @param predecessors The array that was given by the Dijkstra's algorithm.
	 * @throws NegativeCycle Thrown if the graph has negative cycle.
	 * @return The array representing all edges in the path. True if it is in path.
	 */
	private static boolean[] path(Graph G, Vertex v, Edge[] predecessors) throws NegativeCycle{
		int index = v.getId();
		Vertex vertex = v;
		boolean[] path = new boolean[G.edgeSize()];
		Vertex first = v;
		while(predecessors[index] != null){
			path[predecessors[index].getId()] = true;
			vertex = predecessors[index].getSecondVertex(vertex);
			index = vertex.getId();
			if(first == vertex){
				throw new NegativeCycle();
			}
		}
		return path;
	}
	/**
	 * Get back the path from root to given vertex.
	 * @param G Is the given graph.
	 * @param v Is the given vertex.
	 * @param predecessors The array that was given by the Dijkstra's algorithm.
	 * @throws NegativeCycle Thrown if the graph has negative cycle.
	 * @return The array representing all edges in the path. False if it is in path.
	 */
	private static boolean[] reversePath(Graph G, Vertex v, Edge[] predecessors) throws NegativeCycle{
		boolean[] path = path(G, v, predecessors);
		for(int i = 0; i < path.length; ++i){
			path[i] = !path[i];
		}
		return path;
	}
	/**
	 * Visualize the result of Dijkstra's algorithm. For each vertex show the length and the path.
	 * @param G Is the given graph.
	 * @param v Is the root for all the paths.
	 * @param filePath The path to the markdown file for the export.
	 * @throws NegativeCycle Thrown if the graph consist a negative cycle.
	 */
	public static void dijkstraVisualize(Graph G, Vertex v, String filePath) throws NegativeCycle{
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
