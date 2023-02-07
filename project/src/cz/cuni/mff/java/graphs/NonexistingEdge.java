package cz.cuni.mff.java.graphs;

import java.lang.StringBuilder;

/**
 *
 *
 */
class NonexistingEdge extends Exception{
	/** */
	private int id;
	/**
	 *
	 *
	 */
	public NonexistingEdge(int id){
		this.id = id;
	}
	/**
	 *
	 *
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("The edge with id ");
		sb.append(id);
		sb.append(" doesn't exist.");
		return sb.toString();
	}
}
