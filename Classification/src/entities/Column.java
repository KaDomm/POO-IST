package entities;

import java.util.ArrayList;

public class Column {

	private String name;
	private ArrayList<Integer> arrayOfEntries;
	Integer r = 0;

	// constructors
	/**
	 * @param name
	 * @param arrayOfEntries
	 * @param r
	 */
	protected Column(String name, ArrayList<Integer> arrayOfEntries, Integer r) {
		this.name = name;
		this.arrayOfEntries = arrayOfEntries;
		this.r = r;
	}

	/**
	 * 
	 */
	public Column() {
		super();
	}

	// getters and setter
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public ArrayList<Integer> getArrayOfEntries() {
		return arrayOfEntries;
	}

	/**
	 * @param arrayOfEntries
	 */
	public void setArrayOfEntries(ArrayList<Integer> arrayOfEntries) {
		this.arrayOfEntries = arrayOfEntries;
	}

	/**
	 * @return
	 */
	public Integer getR() {
		return r;
	}

	/**
	 * @param r
	 */
	public void setR(Integer r) {
		this.r = r;
	}

	@Override
	public String toString() {
		String entries = "";
		for (Integer entrie : arrayOfEntries) {
			entries = entries + " " + entrie;
		}
		return "name: " + name + "  entries:  " + entries + "  r: " + r + "\n";
	}

}
