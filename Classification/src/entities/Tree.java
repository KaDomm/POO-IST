package entities;

import java.util.List;

public class Tree {

	List<String> parentConfig;
	Double alfa;

	public Tree(List<String> parentConfig, Double alfa) {
		super();
		this.parentConfig = parentConfig;
		this.alfa = alfa;
	}

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
