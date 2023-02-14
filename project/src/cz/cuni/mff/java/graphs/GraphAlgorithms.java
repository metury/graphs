package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * This class implements some of graph algorithms.
 * Mainly it is if the graph is connected or not and minimal cut problem.
 * All of its methods are static.
 */
public class GraphAlgorithms{
	/**
	 * Karger's Stein algorithm for fast min cut.
	 * @param G Is the graph to find min cut.
	 * @return The size of the minimal cut.
	 */
	public static int fastMinCut(Graph G){
		Random r = new Random();
		Graph H = G.clone();
		return fastMinCutRecursion(H, r);
	}
	/**
	 * Recursion on fastMinCut.
	 * @param G Is given graph.
	 * @param r Is pseudo random generator.
	 * @return The number of min cut.
	 */
	private static int fastMinCutRecursion(Graph G, Random r){
		if(G.vertexSize() < 7){
			return minCutBruteForce(G);
		}
		else{
			long t = Math.round(Math.ceil(1+(G.vertexSize()/Math.sqrt(2))));
			Graph H1 = contract(G,t,r);
			Graph H2 = contract(G,t,r);
			int v1 = fastMinCutRecursion(H1,r);
			int v2 = fastMinCutRecursion(H2,r);
			return v1 > v2 ? v2 : v1;
		}
	}
	/**
	 * Recursive algorithm on min cut with brute force.
	 * @param G Is the given graph.
	 * @return The number of the min cut.
	 */
	public static int minCutBruteForce(Graph G){
		Graph H = G.clone();
		return minCutBruteForceRecursion(H,0,G.edgeSize());
	}
	/**
	 * Recursion call on brute force.
	 * @param G is the given graph.
	 * @param removed Is the number of removed edges.
	 * @param min Is the size of minimal cut.
	 * @return The size of minimal cut.
	 */
	private static int minCutBruteForceRecursion(Graph G, int removed, int min){
		if(!isConnected(G)){
			return removed;
		}
		if(removed > min){
			return min;
		}
		for(int i = 0; i < G.edgeSize(); ++i){
			try{
				Graph H = G.clone();
				Edge edge = H.removeEdge(i);
				int removedEdges = 0;
				for(Edge e : edge.getFrom()){
					if(e.isIncident(edge.getTo())){
						H.removeEdge(e);
						removedEdges++;
					}
				}
				int result = minCutBruteForceRecursion(H, removed + 1 + removedEdges, min);
				min = min > result ? result : min;
			} catch(NonexistingEdge ne){
				System.err.println(ne);
			}
		}
		return min;
	}
	/**
	 * Minimal cut using probability. Single step is repeated exactly as many times
	 * as the number of vertices. And then choosing the minimum.
	 * @param G Is the given graph.
	 * @return The size of the min cut.
	 */
	public static int minCutProb(Graph G){
		if(!isConnected(G)){
			return 0;
		}
		Random r = new Random();
		int min = G.edgeSize();
		for(int i = 0; i < G.vertexSize(); ++i){
			Graph H = contract(G,2,r);
			int minX = H.edgeSize();
			min = min > minX ? minX : min;
		}
		return min;
	}
	/**
	 * Change all values of vertices and edges to its ids.
	 * @param G Which graph is used for this change.
	 */
	private static void indexToValues(Graph G){
		for(Vertex v : G){
			v.setValue(v.getId());
		}
		for(int i = 0; i < G.edgeSize(); ++i){
			Edge e;
			try{
				e = G.getEdge(i);
			} catch(NonexistingEdge ne){
				System.err.println(ne);
				return;
			}
			e.setValue(e.getId());
		}
	}
	/**
	 * One single step of contracting random edges till the number of
	 * vertices drop low enough.
	 * @param G Is the given graph.
	 * @param nrVertices Is the lower bound of the number of the vertices.
	 * @param r Is pseudo random generator.
	 * @return Graph that was made after several contractions.
	 */
	private static Graph contract(Graph G, long nrVertices, Random r){
		Graph H = G.clone();
		H.removeLoops();
		while(H.vertexSize() > nrVertices){
			int id = r.nextInt(H.edgeSize());
			try{
				H.contractEdge(id);
			} catch(NonexistingEdge ne){
				System.err.println(ne);
			}
			H.removeLoops();
		}
		return H;
	}
	/**
	 * Visualize the probability algorithm of the min cut problem.
	 * @param G Is the given graph.
	 * @param filePath Is the path to the export file (in markdown).
	 */
	public static void minCutVisualize(Graph G, String filePath){
		Random r = new Random();
		Graph H = minCutVisualizeFirst(G, filePath);
		int stepCount = 1;
		while(H.vertexSize() > 2){
			int id = r.nextInt(H.edgeSize());
			indexToValues(H);
			try{
				try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
					out.write("## Krok ");
					out.write(Integer.toString(stepCount++));
					out.write("\n\nV tomhle kroku kontrahujeme hranu `");
					out.write(G.getEdge(id).toString());
					out.write("`.\n\n```mermaid\n");
				} catch(IOException ioe){
					System.err.println(ioe);
				}
				H.exportMermaid(filePath,true,id);
				try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
					out.write("```\n\nA získáváme:\n\n```mermaid\n");
				} catch(IOException ioe){
					System.err.println(ioe);
				}
				H.contractEdge(id);
				H.exportMermaid(filePath,true);
				try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
					out.write("```\n\n");
				} catch(IOException ioe){
					System.err.println(ioe);
				}
			} catch(NonexistingEdge ne){
				System.err.println(ne);
			}
			H.removeLoops();
		}
		minCutVisualizeLast(G, filePath, H.edgeSize());
	}
	/**
	 * The first part of visualistion of probability algorithm.
	 * @param G Is the given graph.
	 * @param filePath Is the path to the export file (in markdown).
	 * @return Undirected graph similiar to G.
	 */
	private static Graph minCutVisualizeFirst(Graph G, String filePath){
		Graph H = G.clone();
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			out.write("# Problém **MinCut** *(minimální řez)* pomocí pravděpodobnostního algoritmu\n\n");
			out.write("Algoritmus jen v každé iteraci náhodně kontrahuje jednu hranu, dokud nemá právě jen 2 vrcholy, potom počet hran je roven velikosti řezu.\n\n");
			out.write("V našem případě si ukážeme jen jednu iteraci. Normální se algoritmus vícekrát opakuje pro větší pravděpodobnost správného výsledku.\n\n");
			out.write("## Graf na vstupu:\n\n");
			out.write("```mermaid\n");
		} catch(IOException ioe){
			System.err.println(ioe);
		}
		G.exportMermaid(filePath, true);
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
			out.write("```\n\n");
			if(H.isDirected()){
				H.switchDirected();
				out.write("Protože problém minimálního řezu řešíme nad neorientovaným grafem, tak všechny hrany změníme na neorientované.\n\n");
			}
			out.write("Nyní už budeme postupně iterovat. S tím, že v každém momentu očíslujeme vrcholy a hrany pro přehlednost, a taky se zbavíme smyček.\n\n");
		} catch(IOException ioe){
			System.err.println(ioe);
		}
		return H;
	}
	/**
	 * The last part of visualization of the algorithms for min cut problem.
	 * @param G Is the given graph.
	 * @param filePath Is the path to the export file (in markdown).
	 * @param result Is the result of the simple algorithm beforehand.
	 */
	private static void minCutVisualizeLast(Graph G, String filePath, int result){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
			out.write("Tímto konkrétním postupem jsem došli k výsledku, že minimální řez má velikost nejvýše: **");
			out.write(Integer.toString(result));
			out.write("**\n\n*Pokud bychom tento algoritmus zopakovali aspoň tolikrát, kolik je vrcholů, tak získáme výsledek: **");
			out.write(Integer.toString(minCutProb(G)));
			out.write("***\n\n## Další postupy:\n\n");
			if(G.vertexSize() < 7){
				out.write("Protože graf není nijak velký, tak lze použít hledání pomocí hrubé síly. To pak dává výsledek: **");
				out.write(Integer.toString(minCutBruteForce(G)));
				out.write("**\n\nDíky tomu, že jsme mohli použít hledání hrubou silou, tak Karger Steinův algoritmus nám vrátí stejný výsledek, protože v tak malém grafu hend používá hrubou sílu.\n");
			}
			else{
				out.write("Protože graf je celkem velký, tak není doporučený použít hledání hrubou sílou, ale za to můžeme na druhou stranu použít celkem dost efektivní algoritmus Karger-Steinův,");
				out.write(" který používá předem ukázanou kontrakci hrany. Přesně je to rekurzivní používání daného postupu na nižším počtu vrcholů, dokud se nedostane na nějakou hodnotu,");
				out.write(" po které se už použije hrubá síla. Konkrétní výsledek potom je: **");
				out.write(Integer.toString(fastMinCut(G)));
				out.write("**\n");
			}
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	/**
	 * To find out if the given graph is connected.
	 * @param G Given graph.
	 * @return True if it is connected.
	 */
	public static boolean isConnected(Graph G){
		boolean[] found = new boolean[G.vertexSize()];
		if(G.vertexSize() == 0){
			return true;
		}
		else{
			try{
				connectRecursive(G.getVertex(0), found);
			} catch(NonexistingVertex nv){
				System.err.println(nv);
			}
		}
		boolean result = true;
		for(boolean b : found){
			result = result && b;
		}
		return result;
	}
	/**
	 * Recursive call on vertex to look on its neighbours. DFS
	 * @param v Current vertex.
	 * @param found List of found vertices.
	 */
	private static void connectRecursive(Vertex v, boolean[] found){
		found[v.getId()] = true;
		for(Edge e : v){
			if(!found[e.getFrom().getId()]){
				connectRecursive(e.getFrom(), found);
			}
			if(!found[e.getTo().getId()]){
				connectRecursive(e.getTo(), found);
			}
		}
	}
	/**
	 * If  the graph is directed go by the edge directions.
	 * @param G Is the given graph.
	 * @return True if the Graph is strongly connected.
	 */
	public static boolean isStronglyConnected(Graph G){
		if(!G.isDirected()){
			return isConnected(G);
		}
		boolean[] found = new boolean[G.vertexSize()];
		if(G.vertexSize() == 0){
			return true;
		}
		else{
			try{
				stronglyConnectRecursive(G.getVertex(0), found);
			} catch(NonexistingVertex nv){
				System.err.println(nv);
			}
		}
		boolean result = true;
		for(boolean b : found){
			result = result && b;
		}
		return result;
	}
	/**
	 * Recursive call on vertex to look on its neighbours. DFS on directed.
	 * @param v Current vertex.
	 * @param found List of found vertices.
	 */
	private static void stronglyConnectRecursive(Vertex v, boolean[] found){
		found[v.getId()] = true;
		for(Edge e : v){
			if(!found[e.getTo().getId()]){
				connectRecursive(e.getTo(), found);
			}
		}
	}
}
