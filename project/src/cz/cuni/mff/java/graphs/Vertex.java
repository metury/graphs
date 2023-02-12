package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class for storing data about one vertex.
 * Implements Iterable through its incident edges.
 */
public class Vertex implements Iterable<Edge>{
	/** The value of the vertex. */
	private double value;
	/** ID of this vertex. */
	private int id;
	/** Incident edges. */
	private ArrayList<Edge> incidentEdges;
	/**
	 * Default constructor.
	 * @param value The value of the vertex.
	 * @param id Is the id of the vertex.
	 */
	public Vertex(double value, int id){
		incidentEdges = new ArrayList<Edge>();
		this.value = value;
		this.id = id;
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
	 * Override the toString() method for Vertex.
	 * @return String of this vertex.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(id);
		sb.append(";");
		sb.append(value);
		sb.append(")");
		return sb.toString();
	}
	/**
	 * Get the ID of this vertex.
	 * @return The ID.
	 */
	public int getId(){
		return id;
	}
	/**
	 * Set new ID to this vertex, used when removing null pointers.
	 * @param id New ID.
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * Clear all null pointers in incident Edges.
	 */
	private void clear(){
		for(int i = 0; i < incidentEdges.size();){
			if(incidentEdges.get(i) == null){
				incidentEdges.remove(i);
			}
			else
				++i;
		}
	}
	/**
	 * Iterator for incident edges.
	 * @return Newly constructed Iterator.
	 */
	public Iterator<Edge> iterator(){
		clear();
		return new Iterator<Edge>(){
			int index = 0;
			public boolean hasNext(){
				return index < incidentEdges.size();
			}
			public Edge next(){
				return incidentEdges.get(index++);
			}
		};
	}
}
