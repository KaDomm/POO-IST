package entities;

import java.util.ArrayList;

/**
 * @author USER-Admin
 *
 */
public class Column {

	private String name;
	private ArrayList<Integer> arrayOfEntries;
	Integer r = 0;

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

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return arrayOfEntries
	 */
	public ArrayList<Integer> getArrayOfEntries() {
		return arrayOfEntries;
	}

	/**
	 * @param arrayOfEntries arrayOfEntries
	 */
	public void setArrayOfEntries(ArrayList<Integer> arrayOfEntries) {
		this.arrayOfEntries = arrayOfEntries;
	}

	/**
	 * @return r
	 */
	public Integer getR() {
		return r;
	}

	/**
	 * @param r r
	 */
	public void setR(Integer r) {
		this.r = r;
	}

}
