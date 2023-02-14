package cz.cuni.mff.java.graphs;

/**
 * Class for storing data about an Edge.
 */
public class Edge{
	/** The value of the current edge. */
	private double value;
	/** The vertex at the beginning (or the first if the graph is undirected). */
	private Vertex from;
	/** The vertex at the end (or the second if the graph is undirected). */
	private Vertex to;
	/** Identification of this edge in graph. */
	private int id;
	/**
	 * General constructor of the edge. Also add this new edgo to its vertices.
	 * @param value Is the value of the edge.
	 * @param from The first vertex (beginning).
	 * @param to The second vertex (ending).
	 * @param id Identication number of this edge.
	 */
	public Edge(double value, Vertex from, Vertex to, int id){
		this.value = value;
		this.from = from;
		this.to = to;
		from.addEdge(this);
		if(from != to){
			to.addEdge(this);
		}
		this.id = id;
	}
	/**
	 * Get the value of the edge.
	 * @return Value of the edge.
	 */
	public double getValue(){
		return value;
	}
	/**
	 * Set the value of the edge.
	 * @param value New value for the edge.
	 */
	public void setValue(double value){
		this.value = value;
	}
	/**
	 * Get the id of the edge.
	 * @return Id of the edge.
	 */
	public int getId(){
		return id;
	}
	/**
	 * Set the id of the edge.
	 * @param id Is the new id.
	 */
	public void setId(int id){
		this.id = id;
	}
	/**
	 * Override toString method for printing edge.
	 * @return String representing the edge.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(from.getId());
		sb.append(";");
		sb.append(value);
		sb.append(";");
		sb.append(to.getId());
		sb.append("]");
		return sb.toString();
	}
	/**
	 * Get the begining vertex.
	 * @return Begining vertex.
	 */
	public Vertex getFrom(){
		return from;
	}
	/**
	 * Get the ending vertex.
	 * @return Ending vertex.
	 */
	public Vertex getTo(){
		return to;
	}
	/**
	 * Set the ending vertex.
	 * @param v New vertex.
	 */
	public void setTo(Vertex v){
		to = v;
	}
	/**
	 * Set the begining vertex.
	 * @param v New vertex.
	 */
	public void setFrom(Vertex v){
		from = v;
	}
	/**
	 * If the edge is loop.
	 * @return True if it is a loop.
	 */
	public boolean isLoop(){
		return from == to;
	}
	/**
	 * If one of the vertices is the same as the one given.
	 * @param v The given vertex.
	 * @return True if one of the vertices is same as the given one.
	 */
	public boolean isIncident(Vertex v){
		return (from == v) || (to == v);
	}
}
