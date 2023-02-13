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
		//Graph G = gen();
		//System.out.println("+++++++++ Tisk +++++++++");
		//System.out.println(G);
		//G.exportGraph("/home/tomas/git/java/project/src/export.txt");
		//System.out.println("+++++++++ Export +++++++++");
		//G.exportDot("/home/tomas/git/java/project/src/export.dot");
		//System.out.println("+++++++++ Export DOT +++++++++");
		Graph G = new Graph("/home/tomas/git/java/project/src/export.txt");
		//G.importGraph("/home/tomas/git/java/project/src/export.txt");
		G.exportMermaid("/home/tomas/git/java/project/src/export.mermaid.md");
		System.out.println("+++++++++ Import +++++++++");
		System.out.println("+++++++++ Tisk po exportu a importu +++++++++");
		System.out.println(G);
		System.out.println("+++++++++ Iterator vrcholu +++++++++");
		for(Vertex v : G){
			System.out.println(v);
		}
		System.out.println("+++++++++ Incidence +++++++++");
		for(Vertex v : G){
			System.out.print(v);
			System.out.print(" Incident with:");
			for(Edge e : v){
				System.out.print(" ");
				System.out.print(e);
			}
			System.out.println();
		}
		System.out.println(GraphAlgorithms.isConnected(G));
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
}
