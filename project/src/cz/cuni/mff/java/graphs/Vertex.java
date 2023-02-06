package cz.cuni.mff.java.graphs;

import java.util.*;

class Vertex{
	private double value;
	private ArrayList<Edge> incidentEdges;
	public Vertex(double value){
		this.value = value;
	}
	public void setValue(double value){
		this.value = value;
	}
	public double getValue(double value){
		return value;
	}
	public void addEdge(Edge edge){
		incidentEdges.add(edge);
	}
	public ArrayList<Edge> getIncident(){
		return incidentEdges;
	}
}
