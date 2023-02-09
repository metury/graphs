package cz.cuni.mff.java.graphs;

/**
 * Main class for starting and testing graph library.
 */
class Main{
	/**
	 * Main functin.
	 * @param args Are argumetns.
	 */
	public static void main(String[] args){
		System.out.println("Spravny package");
		Graph G = new Graph(true);
		try{
			G.addVertex(5,0);
			G.addVertex(6,1);
			G.addEdge(3,0,1,0);
			G.addVertex();
			G.addVertex(6);
			G.addEdge(8,1,3);
			System.out.println(G);
			G.export("/home/tomas/git/java/project/src/export.txt");
			G.exportDot("/home/tomas/git/java/project/src/export.dot", "JmenoGrafu");
			G.importGraph("/home/tomas/git/java/project/src/export.txt");
		} catch(WrongIDException | NonexistingVertex e){
			System.out.println(e);
		}
	}
}
