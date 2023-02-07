package cz.cuni.mff.java.graphs;

import java.util.ArrayList;

/**
 * Class holding data for the graph and all methods on graphs.
 * Such as building the graph, removing parts, contracting edges and exporting and importing the graph.
 */
public class Graph{
	/** Vertices in graph. If it is removed null is present. */
	private ArrayList<Vertex> vertices;
	/** Edges in grap. If one is removed it is replaced by null. */
	private ArrayList<Edge> edges;
	/** If the graph is directed or not. */
	private boolean directed;
	/** Number of edges. */
	private int edgeCount;
	/** Number of vertices. */
	private int vertexCount;
	private final double SPACE_THRESHOLD = 0.7;
	/**
	 * Default constructor.
	 * @param isDirected If the graph is directed or not.
	 */
	public Graph(boolean isDirected){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		directed = isDirected;
	}
	/**
	 * Add vertex to the graph.
	 * @param value Is the value of the vertex.
	 * @param id Is the id of the new vertex. Has to be one bigger than the number of vertices.
	 * @return Id of the newly constructed vertex.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addVertex(double value, int id) throws WrongIDException{
		Vertex v = new Vertex(value, id);
		if(id == vertices.size()){
			vertices.add(v);
			vertexCount++;
			return id;
		}
		throw new WrongIDException(id, "vertex");
	}
	/**
	 * Add vertex to the graph, id is automaticaly given.
	 * @param value The value of the vertex.
	 * @return Id of the newly constructed vertex.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addVertex(double value) throws WrongIDException{
		return addVertex(value, vertices.size());
	}
	/**
	 * Add vertex to the graph, id is automatic and no value is given.
	 * @return Id of the newly constructed vertex.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addVertex() throws WrongIDException{
		return addVertex(Double.NaN, vertices.size());
	}
	/**
	 * Add edge to the graph.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @param id Is the id of the edge. Has to be one more than the number of vertices.
	 * @return Id of the newly constructed edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addEdge(double value, int from, int to, int id) throws WrongIDException, NonexistingVertex{
		if(to >= vertices.size() || vertices.get(to) == null ){
			throw new NonexistingVertex(to);
		}
		if(from >= vertices.size() || vertices.get(from) == null){
			throw new NonexistingVertex(from);
		}
		Edge e = new Edge(value, vertices.get(from), vertices.get(to), directed);
		if(id == edges.size()){
			edges.add(e);
			edgeCount++;
			return id;
		}
		throw new WrongIDException(id, "edge");
	}
	/**
	 * Add edge to the graph. Id is given automaticaly.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Id of the newly constructed edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addEdge(double value, int from, int to) throws WrongIDException, NonexistingVertex{
		return addEdge(value, from, to, edges.size());
	}
	/**
	 * Add edge to the graph. Id is given automaticaly. No value is given.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Id of the newly constructed edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 * @throws cz.cuni.mff.java.graphs.WrongIDException If the given id is wrong.
	 */
	public int addEdge(int from, int to) throws WrongIDException, NonexistingVertex{
		return addEdge(Double.NaN, from, to, edges.size());
	}
	/**
	 * Get vertex by given id.
	 * @param id Is the given id of the vertex.
	 * @return The vertex found.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 */
	public Vertex getVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size() || vertices.get(id) == null){
			throw new NonexistingVertex(id);
		}
		return vertices.get(id);
	}
	/**
	 * Get reference to an Edge by its id.
	 * @param id Is the id of the edge.
	 * @return The edge with its id.
	 * @throws cz.cuni.mff.java.graphs.NonexistingEdge If the edge doesn't exist.
	 */
	public Edge getEdge(int id) throws NonexistingEdge{
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge(id);
		}
		return edges.get(id);
	}
	/**
	 * Get all the edges incident to the Vertex.
	 * @param vertexId Is the id of the given vertex.
	 * @return List of all the edges.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 */
	public ArrayList<Edge> getEdgeIncident(int vertexId) throws NonexistingVertex{
		if(vertexId >= vertices.size() || vertices.get(vertexId) == null){
			throw new NonexistingVertex(vertexId);
		}
		return vertices.get(vertexId).getIncident();
	}
	/**
	 * Remove vertex.
	 * @param id Id of the vertex to be removed.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex doesn't exist.
	 */
	public void removeVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size() || vertices.get(id) == null){
			throw new NonexistingVertex(id);
		}
		vertexCount--;
		vertices.set(id, null);
		double space =  (double) vertexCount / vertices.size();
		if(space < SPACE_THRESHOLD){
			clear();
		}
	}
	/**
	 * Remove edge.
	 * @param id The id of the edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingEdge If the edge doesn't exist.
	 */
	public void removeEdge(int id) throws NonexistingEdge{
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge(id);
		}
		edgeCount--;
		edges.set(id, null);
		double space =  (double) edgeCount / edges.size();
		if(space < SPACE_THRESHOLD){
			clear();
		}
	}
	/**
	 * Get the number of edges.
	 * @return The number of edges.
	 */
	public int edgeSize(){
		return edgeCount;
	}
	/**
	 * Get the number of vertices.
	 * @return The number of vertices.
	 */
	public int vertexSize(){
		return vertexCount;
	}
	private void clear(){
		for(int i = 0; i < vertices.size();){
		Vertex v = vertices.get(i);
			if(v == null){
				vertices.remove(i);
			}
			else{
				v.setId(i++);
			}
		}
		for(int i = 0; i < edges.size();){
			Edge e = edges.get(i);
			if(e == null){
				edges.remove(i);
			}
			else{
				i++;
			}
		}
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		clear();
		for(Vertex v : vertices){
			sb.append(v);
		}
		for(Edge e : edges){
			sb.append(e);
		}
		return sb.toString();
	}
}
