package cz.cuni.mff.java.graphs;

import java.util.ArrayList;

/**
 * Class for storing data about one vertex.
 */
class Vertex{
	/** The value of the vertex. */
	private double value;
	/** Incident edges. If the graph is directed, only the outcoming edges are stored. */
	private ArrayList<Edge> incidentEdges;
	/**
	 * Default constructor.
	 * @param value The value of the vertex.
	 */
	public Vertex(double value){
		this.value = value;
	}
	/**
	 * Set new value to the vertex.
	 * @param value The new value.
	 */
	public void setValue(double value){
		this.value = value;
	}
	/**
	 * Get the value of the vertex.
	 * @return The value of the vertex.
	 */
	public double getValue(){
		return value;
	}
	/**
	 * Add edge to incident edges.
	 * @param edge Newly added edge.
	 */
	public void addEdge(Edge edge){
		incidentEdges.add(edge);
	}
	/**
	 * Get the list of incident edges to its vertex.
	 * @return The list of all incident edges.
	 */
	public ArrayList<Edge> getIncident(){
		return incidentEdges;
	}
}
