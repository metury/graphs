package cz.cuni.mff.java.graphs;

import java.lang.StringBuilder;

/**
 *
 *
 */
class NonexistingVertex extends Exception{
	/** */
	private int id;
	/**
	 *
	 *
	 */
	public NonexistingVertex(int id){
		this.id = id;
	}
	/**
	 *
	 *
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("The vertex with id ");
		sb.append(id);
		sb.append(" doesn't exist.");
		return sb.toString();
	}
}
