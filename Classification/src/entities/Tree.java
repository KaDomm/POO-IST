package entities;

import java.util.List;

public class Tree {

	List<String> parentConfig;
	Double alfa;

	// constructors
	/**
	 * @param parentConfig
	 * @param alfa
	 */
	public Tree(List<String> parentConfig, Double alfa) {
		this.parentConfig = parentConfig;
		this.alfa = alfa;
	}

	// getters and setter
	/**
	 * @return
	 */
	public List<String> getParentConfig() {
		return parentConfig;
	}

	/**
	 * @param parentConfig
	 */
	public void setParentConfig(List<String> parentConfig) {
		this.parentConfig = parentConfig;
	}

	/**
	 * @return
	 */
	public Double getAlfa() {
		return alfa;
	}

	/**
	 * @param alfa
	 */
	public void setAlfa(Double alfa) {
		this.alfa = alfa;
	}

}
