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
	private int id;
	/**
	 * General constructor of the edge. Also add this new edgo to its vertices.
	 * @param value Is the value of the edge.
	 * @param from The first vertex (beginning).
	 * @param to The second vertex (ending).
	 */
	public Edge(double value, Vertex from, Vertex to, int id){
		this.value = value;
		this.from = from;
		this.to = to;
		from.addEdge(this);
		to.addEdge(this);
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
	public int getId(){
		return id;
	}
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
	 * Get the ID of from vertex.
	 * @return Integer representing vertex 'from'.
	 */
	public Vertex getFrom(){
		return from;
	}
	/**
	 * Get the ID of to vertex.
	 * @return Integer represenitng vertex 'to'.
	 */
	public Vertex getTo(){
		return to;
	}
	public void setTo(Vertex v){
		to = v;
	}
	public void setFrom(Vertex v){
		from = v;
	}
}
