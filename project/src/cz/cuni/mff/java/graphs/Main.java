package cz.cuni.mff.java.graphs;

import java.util.Random;
import java.time.Clock;
import java.io.*;

/**
 * Main class for testing and showcasing graphs library.
 */
class Main{
	/**
	 * Main functin.
	 * @param args Are argumetns.
	 */
	public static void main(String[] args){
		showcase();
		hardTest(25, "./testing/hardTest.md");
	}
	/**
	 * Generate pseudo random graph.
	 * @return Generated Graph.
	 */
	public static Graph gen(int sizeV, int sizeE){
		Random r = new Random();
		Graph G = new Graph(false);
		for(int i = 0; i < sizeV; ++i){
			G.addVertex(i);
		}
		for(int i = 0; i < sizeE; ++i){
			G.addEdge(i, r.nextInt(sizeV), r.nextInt(sizeV));
		}
		return G;
	}
	/** Showcase of this library. */
	public static void showcase(){
		System.out.println(" + Základní funkce");
		System.out.println("Vytváření grafu pomocí importu ze souboru `./testing/import`. A taky Petersonův graf z `./testing/petersen`.");
		Graph G = new Graph("./testing/import"); // Using import.
		Graph H = new Graph("./testing/petersen");
		
		System.out.println();
		
		System.out.println("Tisk grafu import:");
		System.out.println(G);
		
		System.out.println();
		
		System.out.println("Export v DOT Language do souboru `./testing/export.gv`");
		G.exportDot("./testing/export.gv");
		System.out.println("Export v DOT Language do souboru `./testing/exportPetersen.gv`");
		G.exportDot("./testing/exportPetersen.gv");
		
		System.out.println();
		
		System.out.println("Export v Mermaid do souboru `./testing/mermaid.md`");
		G.exportMermaid("./testing/mermaid.md");
		
		System.out.println();
		
		System.out.println("Kontrakce nulté hrany. A poté tisk po kontrakci a i export v Mermaid do `./testing/contract.md.`");
		try{
			G.contractEdge(0);
		} catch(NonexistingEdge ne){
			System.err.println(ne);
		}
		System.out.println(G);
		G.exportMermaid("./testing/contract.md");
		
		System.out.println();
		
		System.out.println(" + Spojitost");
		
		System.out.print("Je graf spojený? ");
		System.out.println(GraphAlgorithms.isConnected(G));
		System.out.print("Je ostře spojený? ");
		System.out.println(GraphAlgorithms.isStronglyConnected(G));
		System.out.print("Přidáme separátní vrchol. Je graf spojený? ");
		Vertex v = G.addVertex();
		System.out.println(GraphAlgorithms.isConnected(G));
		
		System.out.println();
		
		System.out.println(" + Minimální řezy");
		
		System.out.print("Jaký je minimální řez grafu pomocí pravděpodobnostního algoritmu? ");
		System.out.println(GraphAlgorithms.minCutProb(G));
		
		System.out.print("Je to nula, protože není spojený. A co když odstraníme daný vrchol? ");
		G.removeVertex(v);
		System.out.println(GraphAlgorithms.minCutProb(G));
		System.out.print("Také lze minimální řez najít pomocí hrubé síly: ");
		System.out.println(GraphAlgorithms.minCutBruteForce(G));
		System.out.print("Jako poslední je nejideálnější (poměr rychlost a pravděpodobnost korekce) Karger's-Stein algoritmus: ");
		System.out.println(GraphAlgorithms.fastMinCut(G));
		
		System.out.println();
		System.out.println(" + Ještě generace ukázky algoritmu pro minimální řez. Jsou v `./testing/minCut.md` a `./testing/minCutPeterson.md`.");
		
		GraphAlgorithms.minCutVisualize(G, "./testing/minCut.md");
		GraphAlgorithms.minCutVisualize(H, "./testing/minCutPetersen.md");
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
				Graph G = gen(v, 3*v);
				out.write("| [V: ");
				out.write(Integer.toString(v));
				out.write(" E: ");
				out.write(Integer.toString(3*v));
				out.write("](./graphs/" + i + ".md) | ");
				long start = clock.millis();
				int result = GraphAlgorithms.minCutProb(G);
				long end = clock.millis();
				out.write(Long.toString(end - start));
				out.write(" | ");
				out.write(Integer.toString(result));
				out.write(" | ");
				if(v < 16){
					start = clock.millis();
					result = GraphAlgorithms.minCutBruteForce(G);
					end = clock.millis();
					out.write(Long.toString(end - start));
				}
				else out.write("`x`");
				out.write(" | ");
				if(v < 16) out.write(Integer.toString(result));
				else out.write("`x`");
				out.write(" | ");
				start = clock.millis();
				result = GraphAlgorithms.fastMinCut(G);
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
