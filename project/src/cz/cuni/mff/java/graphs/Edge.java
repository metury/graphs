package cz.cuni.mff.java.graphs;

/**
 * Class for storing data about an Edge.
 * Generilzed for paramter T holding the values.
 */
public class Edge{
	private double value;
	private Vertex from;
	private Vertex to;
	public Edge(double value, Vertex from, Vertex to){
		this.value = value;
		this.from = from;
		this.to = to;
		from.addEdge(this);
		to.addEdge(this);
	}
	public double getValue(){
		return value;
	}
	public void setValue(double value){
		this.value = value;
	}
	public void contract(){
		// Delete edge and delete both vertices and make new one.
	}
}
