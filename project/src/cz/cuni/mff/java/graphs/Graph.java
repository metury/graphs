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
	/** Vertices in graph. If it is removed null is present. */
	private ArrayList<Vertex> vertices;
	/** Edges in grap. If one is removed it is replaced by null. */
	private ArrayList<Edge> edges;
	/** If the graph is directed or not. */
	private boolean directed;
	/** Number of edges. */
	private int edgeCount;
	/** Number of vertices. */
	private int vertexCount;
	/** Threshold on how many null ponters can be in arrays, when removing edge or vertex. */
	private final double SPACE_THRESHOLD = 0.7;
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
	 * @param fileName Path to the file containing graph.
	 */
	public Graph(String fileName){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		importGraph(fileName);
	}
	/**
	 * Add vertex to the graph.
	 * @param value Is the value of the vertex.
	 * @param id Is the id of the new vertex. Has to be one bigger than the number of vertices.
	 * @return Newly constructed Vector.
	 */
	public Vertex addVertex(double value, int id){
		Vertex v = new Vertex(value, id);
		vertices.add(id, v);
		vertexCount++;
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
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public Edge addEdge(double value, int from, int to, int id) throws NonexistingVertex{
		if(to >= vertices.size() || vertices.get(to) == null ){
			throw new NonexistingVertex(to);
		}
		if(from >= vertices.size() || vertices.get(from) == null){
			throw new NonexistingVertex(from);
		}
		Edge e = new Edge(value, vertices.get(from), vertices.get(to), id);
		edges.add(id, e);
		edgeCount++;
		return e;
	}
	/**
	 * Add edge to the graph. Id is given automaticaly.
	 * @param value Is the value of the edge.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Newly constructed edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public Edge addEdge(double value, int from, int to) throws NonexistingVertex{
		return addEdge(value, from, to, edges.size());
	}
	/**
	 * Add edge to the graph. Id is given automaticaly. No value is given.
	 * @param from Is the id of the vertex where the edge is starting.
	 * @param to Is the id of the vertex where the edge is ending.
	 * @return Newly constructed edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public Edge addEdge(int from, int to) throws NonexistingVertex{
		return addEdge(Double.NaN, from, to, edges.size());
	}
	/**
	 * Get vertex by given id.
	 * @param id Is the given id of the vertex.
	 * @return The vertex found.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public Vertex getVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size() || vertices.get(id) == null){
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
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge(id);
		}
		return edges.get(id);
	}
	/**
	 * Remove vertex.
	 * @param id Id of the vertex to be removed.
	 * @throws cz.cuni.mff.java.graphs.NonexistingVertex If the vertex does not exist.
	 */
	public void removeVertex(int id) throws NonexistingVertex{
		if(id >= vertices.size() || vertices.get(id) == null){
			throw new NonexistingVertex(id);
		}
		vertexCount--;
		vertices.set(id, null);
		double space =  (double) vertexCount / vertices.size();
		if(space < SPACE_THRESHOLD){
			clear();
		}
	}
	public void removeVertex(Vertex v){
		try{
			if(v == vertices.get(v.getId()))
				removeVertex(v.getId());
		} catch(NonexistingVertex nv){
			System.err.println("Given vertex with id " + v.getId() + " does not exist.");
		}
	}
	/**
	 * Remove edge.
	 * @param id The id of the edge.
	 * @throws cz.cuni.mff.java.graphs.NonexistingEdge If the edge does not exist.
	 */
	public void removeEdge(int id) throws NonexistingEdge{
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge(id);
		}
		edgeCount--;
		edges.set(id, null);
		double space =  (double) edgeCount / edges.size();
		if(space < SPACE_THRESHOLD){
			clear();
		}
	}
	public void removeEdge(Edge e){
		try{
			if(e == edges.get(e.getId()))
				removeEdge(e.getId());
		} catch(NonexistingEdge ne){
			System.err.println("Given edge with id " + e.getId() + " does not exist.");
		}
	}
	/**
	 * Get the number of edges.
	 * @return The number of edges.
	 */
	public int edgeSize(){
		return edgeCount;
	}
	/**
	 * Get the number of vertices.
	 * @return The number of vertices.
	 */
	public int vertexSize(){
		return vertexCount;
	}
	/**
	 * Clear all null pointers in both arrays.
	 * May change some ids of vertices and edges!
	 */
	private void clear(){
		for(int i = 0; i < vertices.size();){
		Vertex v = vertices.get(i);
			if(v == null){
				vertices.remove(i);
			}
			else{
				v.setId(i++);
			}
		}
		for(int i = 0; i < edges.size();){
			Edge e = edges.get(i);
			if(e == null){
				edges.remove(i);
			}
			else{
				e.setId(i++);
			}
		}
	}
	/**
	 * Contract edge in graph.
	 * @param id Id of the edge.
	 */
	 // TODO Kontrakce funguje špatně.
	public void contractEdge(int id) throws NonexistingEdge{
		if(id >= edges.size() || edges.get(id) == null){
			throw new NonexistingEdge(id);
		}
		Edge e = edges.get(id);
		Vertex from = e.getFrom();
		Vertex to = e.getTo();
		Vertex v = addVertex();
		for(Edge edge : from){
			if(from == edge.getFrom()){
				edge.setFrom(v);
			}
			if(from == edge.getTo()){
				edge.setTo(v);
			}
		}
		for(Edge edge : to){
			if(to == edge.getFrom()){
				edge.setFrom(v);
			}
			if(to == edge.getTo()){
				edge.setTo(v);
			}
		}
		removeVertex(from);
		removeVertex(to);
		removeEdge(e);
	}
	/**
	 * Get string representing the graph. 
	 * Vertex - (id;value), Edge - [idFrom;value;idTo]
	 * @return String representign the graph.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		clear();
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
			clear();
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
			System.err.println("Given file " + filePath + " cannot be used.");
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
	 * Export graph to mermaid syntax. https://mermaid.js.org/
	 * @param filePath Which file should be used for export.
	 */
	public void exportMermaid(String filePath){
		try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath))){
			clear();
			out.write("graph TD;\n");
			for(Vertex v : vertices){
				out.write(mermaidVertex(v));
			}
			for(Edge e : edges){
				out.write(mermaidEdge(e));
			}
		} catch(IOException ioe){
			System.err.println("Given file " + filePath + " cannot be used.");
		}
	}
	/**
	 * Make String representing vertex in mermaid.
	 * @param v Given vertex.
	 * @return Constructed String.
	 */
	private String mermaidVertex(Vertex v){
		StringBuilder sb = new StringBuilder();
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
					sb.delete(0,1);
					String[] components = sb.toString().split(";");
					addVertex(Double.parseDouble(components[1]), Integer.parseInt(components[0]));
					sb = new StringBuilder();
				}
				else if(ch == ']'){
					sb.delete(0,1);
					String[] components = sb.toString().split(";");
					addEdge(Double.parseDouble(components[1]), Integer.parseInt(components[0]), Integer.parseInt(components[2]));
					sb = new StringBuilder();
				}
			}
			return true;
		} catch(IOException | NonexistingVertex ioe){
			System.err.println("Given file " + filePath + " cannot be used.");
			return false;
		}
	}
	/**
	 * Iterator fo iterating through vertices.
	 * @return Newly constructed Iterator.
	 */
	public Iterator<Vertex> iterator(){
		clear();
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
	/**
	 * Override cloning graphs.
	 * @return Newly copied graph.
	 */
	@Override
	public Graph clone(){
		Graph G = new Graph(directed);
		clear();
		for(Vertex v : vertices){
			G.addVertex(v.getValue(), v.getId());
		}
		for(Edge e : edges){
			try{
				G.addEdge(e.getValue(), e.getFrom().getId(), e.getTo().getId());
			} catch(NonexistingVertex nv){
				System.err.println(nv);
			}
		}
		return G;
	}
}
