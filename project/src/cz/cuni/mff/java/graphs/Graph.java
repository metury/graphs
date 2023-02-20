package cz.cuni.mff.java.graphs;

import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;

/**
 * Class holding data for the graph and all methods on graphs.
 * Such as building the graph, removing parts, contracting edges and exporting and importing the graph.
 * This class implements Iterable, so it can be iterated through its vertices.
 */
public class Graph implements Iterable<Vertex>, Cloneable{
	/** Vertices in the graph. */
	private ArrayList<Vertex> vertices;
	/** Edges in the graph. */
	private ArrayList<Edge> edges;
	/** If the graph is directed or not. */
	private boolean directed;
	/**
	 * Default constructor.
	 * @param isDirected If the graph is directed or not.
	 */
	public Graph(boolean isDirected){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		directed = isDirected;
	}
	/**
	 * Constructor using import from text file.
	 * @param filePath Path to the file containing graph.
	 */
	public Graph(String filePath){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		importGraph(filePath);
	}
	public boolean isDirected(){
		return directed;
	}
	/**
	 * Add vertex to the graph.
	 * @param value Is the value of the vertex.
	 * @param id Is the id of the new vertex. If it is not sequent add it in between or make all pre-vertices.
	 * @return Newly constructed Vector.
	 */
	public Vertex addVertex(double value, int id){
		while(id > vertices.size()){
			vertices.add(new Vertex(Double.NaN, vertices.size()));
		}
		Vertex v = new Vertex(value, id);
		vertices.add(id, v);
		return v;
	}
	/**
	 * Add vertex to the graph, id is automaticaly given.
	 * @param value The value of the vertex.
	 * @return Newly constructed Vector.
	 */
	public Vertex addVertex(double value){
		return addVertex(value, vertices.size());
	}
	/**
	 * Add vertex to the graph, id is automatic and no value is given.
	 * @return Newly constructed Vector.
	 */
	public Vertex addVertex(){
		return addVertex(Double.NaN, vertices.size());
	}
	/**
	 * Add edge to the graph.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @param id Is the id of the edge. Has to be one more than the number of vertices.
	 * @return Newly constructed edge.
	 */
	public Edge addEdge(double value, int from, int to, int id){
		while(to >= vertices.size() ){
			addVertex();
		}
		while(from >= vertices.size()){
			addVertex();
		}
		Edge e = new Edge(value, vertices.get(from), vertices.get(to), id);
		edges.add(id, e);
		return e;
	}
	/**
	 * Add edge to the graph. Id is given automaticaly.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Newly constructed edge.
	 */
	public Edge addEdge(double value, int from, int to){
		return addEdge(value, from, to, edges.size());
	}
	/**
	 * Add edge to the graph. Id is given automaticaly. No value is given.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Newly constructed edge.
	 */
	public Edge addEdge(int from, int to){
		return addEdge(Double.NaN, from, to, edges.size());
	}
	/**
	 * Get vertex by given id.
	 * @param id Is the given id of the vertex.
	 * @return The vertex found.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public Vertex getVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size()){
			throw new NonexistingVertex(id);
		}
		return vertices.get(id);
	}
	/**
	 * Get reference to an Edge by its id.
	 * @param id Is the id of the edge.
	 * @return The edge with its id.
	 * @throws cz.cuni.mff.java.graphs.NonexistingEdge If the edge does not exist.
	 */
	public Edge getEdge(int id) throws NonexistingEdge{
		if(id >= edges.size()){
			throw new NonexistingEdge(id);
		}
		return edges.get(id);
	}
	/**
	 * Remove vertex. May change ids.
	 * @param id Id of the vertex to be removed.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 * @return Reference to the removed Vertex.
	 */
	public Vertex removeVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size()){
			throw new NonexistingVertex(id);
		}
		Vertex v = vertices.get(id);
		vertices.remove(id);
		reIndex();
		return v;
	}
	/**
	 * Remove vertex by its reference if it is present in the graph. May change ids.
	 * @param v Given vertex.
	 */
	public void removeVertex(Vertex v){
		try{
			if(v == vertices.get(v.getId()))
				removeVertex(v.getId());
		} catch(NonexistingVertex nv){
			System.err.println(nv);
		}
	}
	/**
	 * Remove edge. May change ids.
	 * @param id The id of the edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingEdge If the edge does not exist.
	 * @return Reference to the removed Edge.
	 */
	public Edge removeEdge(int id) throws NonexistingEdge{
		if(id >= edges.size()){
			throw new NonexistingEdge(id);
		}
		Edge e = edges.get(id);
		e.getFrom().removeEdge(e);
		e.getTo().removeEdge(e);
		edges.remove(id);
		reIndex();
		return e;
	}
	/**
	 * Remove edge if it is present. May chaneg ids.
	 * @param e Given edge.
	 */
	public void removeEdge(Edge e){
		try{
			if(e == edges.get(e.getId()))
				removeEdge(e.getId());
		} catch(NonexistingEdge ne){
			System.err.println(ne);
		}
	}
	/**
	 * Get the number of edges.
	 * @return The number of edges.
	 */
	public int edgeSize(){
		return edges.size();
	}
	/**
	 * Get the number of vertices.
	 * @return The number of vertices.
	 */
	public int vertexSize(){
		return vertices.size();
	}
	/**
	 * Contract edge in graph. May change ids.
	 * @param id Id of the edge.
	 * @throws NonexistingEdge If the given edge does not exist.
	 */
	public void contractEdge(int id) throws NonexistingEdge{
		if(id >= edges.size()){
			throw new NonexistingEdge(id);
		}
		Edge e = edges.get(id);
		Vertex from = e.getFrom();
		Vertex to = e.getTo();
		Vertex v = addVertex();
		for(Edge edge : from){
			if(from == edge.getFrom()){
				edge.setFrom(v);
				v.addEdge(edge);
			}
			if(from == edge.getTo()){
				edge.setTo(v);
				v.addEdge(edge);
			}
		}
		for(Edge edge : to){
			if(to == edge.getFrom()){
				edge.setFrom(v);
				v.addEdge(edge);
			}
			if(to == edge.getTo()){
				edge.setTo(v);
				v.addEdge(edge);
			}
		}
		removeVertex(from);
		removeVertex(to);
		removeEdge(e);
	}
	/**
	 * Remove all edge loops.
	 */
	public void removeLoops(){
		for(int i = 0; i < edges.size();){
			if(edges.get(i).isLoop()){
				edges.remove(i);
			}
			else{
				i++;
			}
		}
	}
	/**
	 * Get string representing the graph. 
	 * Vertex - (id;value), Edge - [idFrom;value;idTo]
	 * @return String representign the graph.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(directed);
		sb.append("\n");
		for(Vertex v : vertices){
			sb.append(v);
		}
		sb.append("\n");
		for(Edge e : edges){
			sb.append(e);
		}
		return sb.toString();
	}
	/**
	 * Rewrite all indeces in the graph.
	 */
	private void reIndex(){
		int index = 0;
		for(Vertex v : vertices){
			v.setId(index++);
		}
		index = 0;
		for(Edge e : edges){
			e.setId(index++);
		}
	}
	/**
	 * Export the graph to file.
	 * @param filePath Path to the expot file.
	 */
	public void exportGraph(String filePath){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			out.write(this.toString());
		} catch(IOException ioe){
			System.err.println("Given file " + filePath + " cannot be used.");
		}
	}
	/**
	 * Export graph to the DOT language. https://www.graphviz.org/doc/info/lang.html
	 * @param filePath Path to the export file.
	 */
	public void exportDot(String filePath){
		exportDot(filePath, "GrahpName");
	}
	/**
	 * Export graph to the DOT language. https://www.graphviz.org/doc/info/lang.html
	 * @param filePath Path to the export file.
	 * @param graphName Name of the graph.
	 */
	public void exportDot(String filePath, String graphName){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			if(directed){
				out.write("digraph ");
			}
			else{
				out.write("graph ");
			}
			out.write(graphName);
			out.write(" {\n");
			for(Vertex v : vertices){
				out.write(vertexDot(v));
			}
			for(Edge e : edges){
				out.write(edgeDot(e));
			}
			out.write("}");
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	/**
	 * Create String representing vertex in Dot language.
	 * @param v Given vertex.
	 * @return Constructed string.
	 */
	private String vertexDot(Vertex v){
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(Integer.toString(v.getId()));
		if(!Double.isNaN(v.getValue())){
			sb.append(" [label=\"");
			sb.append(v.getValue());
			sb.append("\"]");
		}
		else{
			sb.append(" [label=\" \"]");
		}
		sb.append(";\n");
		return sb.toString();
	}
	/**
	 * Create String representing edge in Dot language.
	 * @param e Given edge.
	 * @return Constructed string.
	 */
	private String edgeDot(Edge e){
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(e.getFrom().getId());
		if(directed){
			sb.append(" -> ");
		}
		else{
			sb.append(" -- ");
		}
		sb.append(e.getTo().getId());
		if(!Double.isNaN(e.getValue())){
			sb.append(" [label=\"");
			sb.append(e.getValue());
			sb.append("\"]");
		}
		sb.append(";\n");
		return sb.toString();
	}
	/**
	 * Export graph as a mermaid diagram to a BuffereWriter.
	 * @param out Is the output writer which is used for the export.
	 * @param markdown Is boolean if the code header and footer should be added.
	 * @param dottedEdges Is a list of edges, that are dotted.
	 * @throws IOException If the given writer will not work properly.
	 */
	public void exportMermaid(BufferedWriter out, boolean markdown, boolean[] dottedEdges) throws IOException{
		if(markdown){
			out.write("```mermaid\n");
		}
		out.write("graph TD;\n");
		for(Vertex v : vertices){
			out.write(mermaidVertex(v));
		}
		for(int i = 0; i < edges.size(); ++i){
			if(dottedEdges[i]){
				out.write(mermaidEdgeDotted(edges.get(i)));
			}
			else{
				out.write(mermaidEdge(edges.get(i)));
			}
		}
		if(markdown){
			out.write("```\n");
		}
		out.write("\n");
	}
	/**
	 * Export graph as a mermaid diagram to a BuffereWriter.
	 * @param out Is the output writer which is used for the export.
	 * @param markdown Is boolean if the code header and footer should be added.
	 * @throws IOException If the given writer will not work properly.
	 */
	public void exportMermaid(BufferedWriter out, boolean markdown) throws IOException{
		exportMermaid(out, markdown, new boolean[edges.size()]);
	}
	/**
	 * Export graph to mermaid syntax. https://mermaid.js.org/
	 * @param filePath Which file should be used for export.
	 */
	public void exportMermaid(String filePath){
		exportMermaid(filePath, false);
	}
	/**
	 * Export graph to mermaid syntax. https://mermaid.js.org/
	 * @param filePath Which file should be used for export.
	 * @param append If the text should be appended to the file or overwrite the file.
	 */
	public void exportMermaid(String filePath, boolean append){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
			exportMermaid(out, false);
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	/**
	 * Export mermaid graph to markdown file.
	 * @param filePath Which export file to use.
	 */
	public void exportMermaidMd(String filePath){
		exportMermaidMd(filePath, false);
	}
	/**
	 * Export mermaid graph to markdown file.
	 * @param filePath Which export file to use.
	 * @param append If the file should be appended or not.
	 */
	public void exportMermaidMd(String filePath, boolean append){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
			exportMermaid(out, true);
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	/**
	 * Export graph to mermaid syntax. With one edge dotted. https://mermaid.js.org/
	 * @param filePath Which file should be used for export.
	 * @param append If the text should be appended to the file or overwrite the file.
	 * @param idDotted Which edge should be dotted.
	 */
	public void exportMermaid(String filePath, boolean append, int idDotted){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
			boolean[] dotted = new boolean[edges.size()];
			dotted[idDotted] = true;
			exportMermaid(out, false, dotted);
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	/**
	 * Make String representing vertex in mermaid.
	 * @param v Given vertex.
	 * @return Constructed String.
	 */
	private String mermaidVertex(Vertex v){
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(v.getId());
		sb.append("(\"");
		if(!Double.isNaN(v.getValue())){
			sb.append(v.getValue());
		}
		else{
			sb.append(" ");
		}
		sb.append("\")");
		sb.append(";\n");
		return sb.toString();
	}
	/**
	 * Make String representing edge in mermaid.
	 * @param e Given edge.
	 * @return Constructed String.
	 */
	private String mermaidEdge(Edge e){
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(e.getFrom().getId());
		if(!Double.isNaN(e.getValue())){
			sb.append(" -- \"");
			sb.append(e.getValue());
			sb.append("\"");
		}
		if(directed){
			sb.append(" --> ");
		}
		else{
			sb.append(" --- ");
		}
		sb.append(e.getTo().getId());
		sb.append(";\n");
		return sb.toString();
	}
	/**
	 * Make String representing dotted edge in mermaid.
	 * @param e Given edge.
	 * @return Constructed String.
	 */
	private String mermaidEdgeDotted(Edge e){
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(e.getFrom().getId());
		if(!Double.isNaN(e.getValue())){
			sb.append(" -. \"");
			sb.append(e.getValue());
			sb.append("\"");
		}
		if(directed){
			sb.append(" -.-> ");
		}
		else{
			sb.append(" -.- ");
		}
		sb.append(e.getTo().getId());
		sb.append(";\n");
		return sb.toString();
	}
	/**
	 * Import graph from given file in text format.
	 * @param filePath File to be imported from.
	 * @return If the import was succesful or not. If the format is correct.
	 */
	public boolean importGraph(String filePath){
		try(BufferedReader in = new BufferedReader(new FileReader(filePath))){
			vertices.clear();
			edges.clear();
			int c;
			String s = in.readLine();
			if(s.equals("true")){
				directed = true;
			}
			else{
				directed = false;
			}
			StringBuilder sb = new StringBuilder();
			while((c = in.read()) != -1){
				char ch = (char) c;
				if( ch != ')' && ch != ']' && ch != '\n'){
					sb.append(ch);		
				}
				else if(ch == ')'){
					sb.delete(0,1); // remove '('
					String[] components = sb.toString().split(";");
					if(components.length == 1){
						addVertex(Double.parseDouble(components[0]));
					}
					else if(components.length == 2){
						addVertex(Double.parseDouble(components[1]), Integer.parseInt(components[0]));
					}
					sb = new StringBuilder();
				}
				else if(ch == ']'){
					sb.delete(0,1); // remove '['
					String[] components = sb.toString().split(";");
					if(components.length == 2){
						addEdge(Integer.parseInt(components[0]), Integer.parseInt(components[1]));
					}
					else if(components.length == 3){
						addEdge(Double.parseDouble(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));
					}
					else{
						return false;
					}
					sb = new StringBuilder();
				}
			}
			return true;
		} catch(IOException ioe){
			System.err.println(ioe);
			return false;
		} catch(NumberFormatException nfe){
			System.err.println(nfe);
			return false;
		}
	}
	/**
	 * Iterator fo iterating through vertices.
	 * @return Newly constructed Iterator.
	 */
	public Iterator<Vertex> iterator(){
		return new Iterator<Vertex>(){
			private int index = 0;
			public boolean hasNext(){
				return index < vertices.size();
			}
			public Vertex next(){
				return vertices.get(index++);
			}
		};
	}
	public boolean switchDirected(){
		directed = !directed;
		return directed;
	}
	/**
	 * Override cloning graphs.
	 * @return Newly copied graph.
	 */
	@Override
	public Graph clone(){
		Graph G = new Graph(directed);
		for(Vertex v : vertices){
			G.addVertex(v.getValue(), v.getId());
		}
		for(Edge e : edges){
			G.addEdge(e.getValue(), e.getFrom().getId(), e.getTo().getId());
		}
		return G;
	}
	/**
	 * Merge other graph to this one.
	 * @param H Is the second graph.
	 */
	public void merge(Graph H){
		for(Vertex v : H){
			addVertex(v.getValue(), v.getId());
		}
		for(int i = 0; i < H.edgeSize(); ++i){
			Edge e = H.getEdge(i);
			addEdge(e.getValue(), e.getFrom().getId(), e.getTo().getId());
		}
		reIndex();
	}
}
