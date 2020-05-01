package entities;

import java.util.List;

public class Tree {

	List<String> parentConfig;
	Double alfa;

	// constructors
	public Tree(List<String> parentConfig, Double alfa) {
		this.parentConfig = parentConfig;
		this.alfa = alfa;
	}

	// getters and setter
	public List<String> getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(List<String> parentConfig) {
		this.parentConfig = parentConfig;
	}

	public Double getAlfa() {
		return alfa;
	}

	public void setAlfa(Double alfa) {
		this.alfa = alfa;
	}

}
