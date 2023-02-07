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
	/** If the edge is directed or not. */
	private boolean directed;
	/**
	 * General constructor of the edge. Also add this new edgo to its vertices.
	 * @param value Is the value of the edge.
	 * @param from The first vertex (beginning).
	 * @param to The second vertex (ending).
	 * @param directed If the edge is directed.
	 */
	public Edge(double value, Vertex from, Vertex to, boolean directed){
		this.value = value;
		this.from = from;
		this.to = to;
		this.directed = directed;
		from.addEdge(this);
		to.addEdge(this);
		if(!directed){
			to.addEdge(this);
			from.addEdge(this);
		}
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
	 * Contract this edge.
	 *
	 */
	public void contract(){
		// Delete edge and delete both vertices and make new one.
	}
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
}
