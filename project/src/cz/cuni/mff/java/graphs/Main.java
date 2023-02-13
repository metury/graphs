package cz.cuni.mff.java.graphs;

import java.util.Random;

/**
 * Main class for starting and testing graph library.
 */
class Main{
	/**
	 * Main functin.
	 * @param args Are argumetns.
	 */
	public static void main(String[] args){
		test();
	}
	/**
	 * Generate pseudo random graph.
	 * @return Generated Graph.
	 */
	public static Graph gen(){
		Random r = new Random();
		Graph G = new Graph(false);
		for(int i = 0; i < 4; ++i){
			G.addVertex(i);
		}
		for(int i = 0; i < 5; ++i){
			G.addEdge(i, r.nextInt(4), r.nextInt(4));
		}
		return G;
	}
	/** Testing method. */
	public static void test(){
		System.out.println("Vytváření grafu pomocí importu ze souboru `./testing/import`.");
		Graph G = new Graph("./testing/import"); // Using import.
		
		System.out.println();
		
		System.out.println("Tisk grafu:");
		System.out.println(G);
		
		System.out.println();
		
		System.out.println("Export v DOT Language do souboru ./testing/export.gv");
		G.exportDot("./testing/export.gv");
		
		System.out.println();
		
		System.out.println("Export v Mermaid do souboru ./testing/mermaid.md");
		G.exportMermaid("./testing/mermaid.md");
		
		System.out.println();
		
		System.out.println("Kontrakce nulté hrany. A poté tisk po kontrakci a i export v Mermaid do ./testing/contract.md.");
		try{
		G.contractEdge(0);
		} catch(NonexistingEdge ne){
			System.err.println(ne);
		}
		System.out.println(G);
		G.exportMermaid("./testing/contract.md");
		
		System.out.println();
		
		System.out.print("Je graf spojený? ");
		System.out.println(GraphAlgorithms.isConnected(G));
		System.out.print("Pokud je orientovaný, je ostře spojený? ");
		System.out.println(GraphAlgorithms.isStronglyConnected(G));
		System.out.print("Přidáme separátní vrchol. Je graf spojený? ");
		Vertex v = G.addVertex();
		System.out.println(GraphAlgorithms.isConnected(G));
		
		System.out.println();
		
		System.out.print("Jaký je minCut grafu? ");
		System.out.println(GraphAlgorithms.minCut(G));
		
		System.out.print("Je to nula, protože není spojený. A co když odstraníme daný vrchol? ");
		G.removeVertex(v);
		System.out.println(GraphAlgorithms.minCut(G));
		
		GraphAlgorithms.minCutVisualize(G, "./testing/minCut.md");
	}
}
