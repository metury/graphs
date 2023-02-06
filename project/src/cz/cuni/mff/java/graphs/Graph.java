package cz.cuni.mff.java.graphs;

import java.util.ArrayList;

/**
 * Class holding data for the graph and all methods on graphs.
 */
public class Graph{
	/** Vertices in graph. If it is removed null is present. */
	private ArrayList<Vertex> vertices;
	/** Edges in grap. If one is removed it is replaced by null. */
	private ArrayList<Edge> edges;
	/** If the graph is directed or not. */
	private boolean directed;
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
	 */
	public int addVertex(double value, int id) throws WrongIDException{
		Vertex v = new Vertex(value);
		if(id == vertices.size()){
			vertices.add(v);
			return id;
		}
		throw new WrongIDException(id, "vertex");
	}
	/**
	 * Add vertex to the graph, id is automaticaly given.
	 * @param value The value of the vertex.
	 * @return Id of the newly constructed vertex.
	 */
	public int addVertex(double value) throws WrongIDException{
		return addVertex(value, vertices.size());
	}
	/**
	 * Add vertex to the graph, id is automatic and no value is given.
	 * @return Id of the newly constructed vertex.
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
	 */
	public int addEdge(double value, int from, int to, int id) throws WrongIDException, NonexistingVertex{
		if(to >= vertices.size() || from >= vertices.size() || vertices.get(to) == null || vertices.get(from) == null){
			throw new NonexistingVertex();
		}
		Edge e = new Edge(value, vertices.get(from), vertices.get(to));
		if(id == edges.size()){
			edges.add(e);
		}
		throw new WrongIDException(id, "edge");
	}
	/**
	 * Add edge to the graph. Id is given automaticaly.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Id of the newly constructed edge.
	 */
	public int addEdge(double value, int from, int to) throws WrongIDException, NonexistingVertex{
		return addEdge(value, from, to, edges.size());
	}
	/**
	 * Add edge to the graph. Id is given automaticaly. No value is given.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Id of the newly constructed edge.
	 */
	public int addEdge(int from, int to) throws WrongIDException, NonexistingVertex{
		return addEdge(Double.NaN, from, to, edges.size());
	}
	
	public Vertex getVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size() || vertices.get(id) == null){
			throw new NonexistingVertex();
		}
		return vertices.get(id);
	}
	
	public Edge getEdge(int id) throws NonexistingEdge{
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge();
		}
		return edges.get(id);
	}
	public ArrayList<Edge> getEdgeIncident(int vertexId) throws NonexistingVertex{
		if(vertexId >= vertices.size() || vertices.get(vertexId) == null){
			throw new NonexistingVertex();
		}
		return vertices.get(vertexId).getIncident();
	}
}
