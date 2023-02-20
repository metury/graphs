package cz.cuni.mff.java.graphs;

/**
 * Exception when using Dijkstra on graph with negative cycles.
 */
public class NegativeCycle extends Exception{
	/**
	 * Override toString() function.
	 * @return String of the exception.
	 */
	@Override
	public String toString(){
		return "Given graph has a negative cycle, so using Dijkstra was stopped.";
	}
}
