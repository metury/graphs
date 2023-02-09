package cz.cuni.mff.java.graphs;

import java.lang.StringBuilder;

/**
 * Exception when given Vertex does not exist.
 * To be precise given ID of the Vertex.
 */
class NonexistingVertex extends Exception{
	/** Given wrong ID. */
	private int id;
	/**
	 * Default constructor.
	 * @param id Given wrong id.
	 */
	public NonexistingVertex(int id){
		this.id = id;
	}
	/**
	 * Override toString() function.
	 * @return String of the exception.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("The vertex with id ");
		sb.append(id);
		sb.append(" doesn't exist.");
		return sb.toString();
	}
}
