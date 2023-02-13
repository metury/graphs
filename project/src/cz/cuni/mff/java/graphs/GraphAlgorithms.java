package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class GraphAlgorithms{
	public static int fastMinCut(Graph G){
		Random r = new Random();
		Graph H = G.clone();
		return minC(H, r);
	}
	public static int minC(Graph G, Random r){
		if(G.vertexSize() < 7){
			return minCutBruteForce(G);
		}
		else{
			long t = Math.round(Math.ceil(G.vertexSize()/Math.sqrt(2) + 1));
			Graph H1 = minCutStep(G,t,r);
			Graph H2 = minCutStep(G,t,r);
			int v1 = minC(H1,r);
			int v2 = minC(H2,r);
			return v1 > v2 ? v2 : v1;
		}
	}
	public static int minCutBruteForce(Graph G){
		Graph H = G.clone();
		return minCutBruteForceRecursion(H,0,G.edgeSize());
	}
	private static int minCutBruteForceRecursion(Graph G, int removed, int min){
		if(!isConnected(G)){
			return removed;
		}
		for(int i = 0; i < G.edgeSize(); ++i){
			try{
				Edge e = G.removeEdge(i);
				int result = minCutBruteForceRecursion(G, removed+1, min);
				min = min > result ? result : min;
				G.addEdge(e.getFrom().getId(), e.getTo().getId());
			} catch(NonexistingEdge ne){
				System.err.println(ne);
			}
		}
		return min;
	}
	public static int minCutProb(Graph G){
		if(!isConnected(G)){
			return 0;
		}
		Random r = new Random();
		int min = G.edgeSize();
		for(int i = 0; i < G.vertexSize(); ++i){
			Graph H = minCutStep(G,2,r);
			int minX = H.edgeSize();
			min = min > minX ? minX : min;
		}
		return min;
	}
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
	private static Graph minCutStep(Graph G, long nrVertices, Random r){
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
	public static void minCutVisualize(Graph G, String filePath){
		Random r = new Random();
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
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true))){
			out.write("Tímto konkrétním postupem jsem došli k výsledku, že minimální řez má velikost nejvýše: **");
			out.write(Integer.toString(H.edgeSize()));
			out.write("**\n\n*Pokud bychom tento algoritmus zopakovali aspoň tolikrát, kolik je vrcholů, tak získáme výsledek: **");
			out.write(Integer.toString(minCutProb(G)));
			out.write("***");
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
