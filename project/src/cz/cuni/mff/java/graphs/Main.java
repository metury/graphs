package cz.cuni.mff.java.graphs;

import java.util.Random;
import java.time.Clock;
import java.io.*;

/**
 * Main class for testing and showcasing graphs library.
 */
public class Main{
	/**
	 * Main functin.
	 * @param args Are argumetns.
	 */
	public static void main(String[] args){
		showcase();
		hardTest(1, "./testing/hardTest.md");
		//MST.visualizeMST(G, "./testing/MST.md");
	}
	/**
	 * Generate pseudo random graph.
	 * @param sizeV The number of vertices.
	 * @param sizeE The number of Edges.
	 * @param directed If the graph is directed or not.
	 * @return Generated Graph.
	 */
	public static Graph gen(int sizeV, int sizeE, boolean directed){
		Random r = new Random();
		Graph G = new Graph(directed);
		for(int i = 0; i < sizeV; ++i){
			G.addVertex(i);
		}
		for(int i = 0; i < sizeE; ++i){
			G.addEdge(r.nextDouble(), r.nextInt(sizeV), r.nextInt(sizeV));
		}
		return G;
	}
	/** Showcase of this library. */
	public static void showcase(){
		System.out.println(" + Základní funkce");
		String importt = "./testing/import/import";
		String petersen = "./testing/import/petersen";
		System.out.println("Vytváření grafu pomocí importu ze souboru `" + importt + "`. A taky Petersonův graf z `" + petersen + "`.");
		Graph G = new Graph(importt);
		Graph H = new Graph(petersen);
		
		System.out.println();
		
		System.out.println("Tisk grafu import:");
		System.out.println(G);
		
		System.out.println();
		
		String exportgv = "./testing/export/export.gv";
		String petersengv = "./testing/export/petersen.gv";
		
		System.out.println("Export v DOT Language do souboru `" + exportgv +"`");
		G.exportDot(exportgv);
		System.out.println("Export v DOT Language do souboru `" + petersengv + "`");
		G.exportDot(petersengv);
		
		System.out.println();
		
		String mermaid = "./testing/export/mermaid.md";
		
		System.out.println("Export v Mermaid do souboru `" + mermaid + "'");
		G.exportMermaid(mermaid);
		
		System.out.println();
		
		String contractMermaid = "./testing/export/contract.md";
		
		System.out.println("Kontrakce nulté hrany. A poté tisk po kontrakci a i export v Mermaid do `"+ contractMermaid +"`");
		G.contractEdge(0);
		System.out.println(G);
		G.exportMermaid(contractMermaid);
		
		System.out.println();
		
		System.out.println(" + Souvislost");
		
		System.out.print("Je graf souvislý? ");
		System.out.println(GraphAlgorithms.isConnected(G));
		System.out.print("Je ostře souvislý? ");
		System.out.println(GraphAlgorithms.isStronglyConnected(G));
		System.out.print("Přidáme separátní vrchol. Je graf souvislý? ");
		Vertex v = G.addVertex();
		System.out.println(GraphAlgorithms.isConnected(G));
		
		System.out.println();
		
		System.out.println(" + Minimální řezy");
		
		System.out.print("Jaký je minimální řez grafu pomocí pravděpodobnostního algoritmu? ");
		System.out.println(MinCut.minCutProb(G));
		
		System.out.print("Je to nula, protože není souvislý. A co když odstraníme daný vrchol? ");
		G.removeVertex(v);
		System.out.println(MinCut.minCutProb(G));
		System.out.print("Také lze minimální řez najít pomocí hrubé síly: ");
		System.out.println(MinCut.minCutBruteForce(G));
		System.out.print("Jako poslední je nejideálnější (poměr rychlost a pravděpodobnost korekce) Karger's-Stein algoritmus: ");
		System.out.println(MinCut.fastMinCut(G));
		
		System.out.println();
		
		String minCut = "./testing/showcase/minCut.md";
		String minCutPetersen = "./testing/showcase/minCutPeterse.md";
		System.out.println(" + Ještě generace ukázky algoritmu pro minimální řez. Jsou v `"+ minCut +"` a `"+ minCutPetersen +"`.");
		
		MinCut.minCutVisualize(G, minCut);
		MinCut.minCutVisualize(H, minCutPetersen);
		
		System.out.println();
		String dijkstra = "./testing/showcase/dijkstra.md";
		String didijkstra = "./testing/showcase/didijkstra.md";
		System.out.println(" + Další algoritmus je Dijkstrův algoritmus pro nejkratší cesty v grafu. Generace bude do souboru '"+ dijkstra +"' a '"+ didijkstra +"'");
		
		Graph D = gen(10, 30, false);
		Dijkstra.dijkstraVisualize(D, D.getVertex(0), dijkstra);
		
		Graph D2 = gen(10, 40, true);
		Dijkstra.dijkstraVisualize(D2, D2.getVertex(0), didijkstra);
		
		Graph M = gen(10,30, false);
		Graph M2 = gen(10,40, true);
		
		System.out.println();
		String mst = "./testing/showcase/mst.md";
		String dimst = "./testing/showcase/dimst.md";
		System.out.println(" + Posledním algoritmem je hledání minimální kostry. Opět dva příklady jsou v `"+ mst +"` a `"+ dimst +"`.");
		
		MST.visualizeMST(M, mst);
		MST.visualizeMST(M2, dimst);
	}
	/**
	 * Hard testing methods. For algorithms and time. Make table in markdown.
	 * Also make all graphs in markdown mermaid.
	 * @param MAX Is the number of tested graphs.
	 * @param filePath Path to the export file (in markdown).
	 */
	public static void hardTest(int MAX, String filePath){
		int v = 5;
		Clock clock = Clock.systemDefaultZone();
		System.out.println(" + Testovací část algoritumů (může zabrat delší dobu)");
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			out.write("| Graph | Prob T | Prob R | Brute T | Brute R | KS T | KS R |\n");
			out.write("| ----- | ------ | ------ | ------- | ------- | ---- | ---- |\n");
			System.out.print("Hotové: ");
			for(int i = 0; i < MAX; ++i){
				Graph G = gen(v, 3*v, false);
				out.write("| [V: ");
				out.write(Integer.toString(v));
				out.write(" E: ");
				out.write(Integer.toString(3*v));
				out.write("](./graphs/" + i + ".md) | ");
				long start = clock.millis();
				int result = MinCut.minCutProb(G);
				long end = clock.millis();
				out.write(Long.toString(end - start));
				out.write(" | ");
				out.write(Integer.toString(result));
				out.write(" | ");
				if(v < 16){
					start = clock.millis();
					result = MinCut.minCutBruteForce(G);
					end = clock.millis();
					out.write(Long.toString(end - start));
				}
				else out.write("`x`");
				out.write(" | ");
				if(v < 16) out.write(Integer.toString(result));
				else out.write("`x`");
				out.write(" | ");
				start = clock.millis();
				result = MinCut.fastMinCut(G);
				end = clock.millis();
				out.write(Long.toString(end - start));
				out.write(" | ");
				out.write(Integer.toString(result));
				out.write(" |\n");
				G.exportMermaidMd("./testing/graphs/"+i+".md");
				v += 1;
				System.out.print("["+i+"]");
			}
			out.write("\n*`x` znamená, že brute force už zabere moc času a tedy už není měřený.*");
			System.out.println();
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
