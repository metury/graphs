package cz.cuni.mff.java.graphs;

import java.lang.StringBuilder;

class WrongIDException extends Exception{
	private int id;
	private String type;
	public WrongIDException(int id, String type){
		this.id = id;
		this.type = type;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Given id ");
		sb.append(id);
		sb.append(" of ");
		sb.append(type);
		sb.append(" doesn't exist.");
		return sb.toString();
	}
}
