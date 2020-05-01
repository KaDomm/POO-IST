package entities;

import java.util.ArrayList;

public class Column {

	private String name;
	private ArrayList<Integer> arrayOfEntries;
	Integer r;

	// constructors
	protected Column(String name, ArrayList<Integer> arrayOfEntries, Integer r) {
		this.name = name;
		this.arrayOfEntries = arrayOfEntries;
		this.r = r;
	}

	public Column() {
		super();
	}

	// getters and setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getArrayOfEntries() {
		return arrayOfEntries;
	}

	public void setArrayOfEntries(ArrayList<Integer> arrayOfEntries) {
		this.arrayOfEntries = arrayOfEntries;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	@Override
	public String toString() {
		String entries = "";
		for (Integer entrie : arrayOfEntries) {
			entries = entries + " " + entrie;
		}
		return "\nname: " + name + "\n entries: \n" + entries + "\n";
	}

}
