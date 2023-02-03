package cz.cuni.mff.java.graphs;

/**
 * Class for storing data about an Edge.
 * Generilzed for paramter T holding the values.
 */
public class Edge<T>{
	private T value;
	private Vertex from;
	private Vertex to;
	public Edge(T value){
		this.value = value;
	}
	public T getValue(){
		return value;
	}
	public void setValue(T value){
		this.value = value;
	}
	public void contract(){
		// Delete edge and delete both vertices and make new one.
	}
}
