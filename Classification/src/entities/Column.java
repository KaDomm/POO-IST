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

}
