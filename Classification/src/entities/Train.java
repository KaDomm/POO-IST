package entities;

import java.util.List;

import entities.enums.Criteria;

public class Train {

	private List<Column> samples;
	private Column columnC;
	private Integer numberOfLines;
	private Integer numberOfColumnsX;

	Criteria criteria;

	// constructors
	protected Train(List<Column> samples, Column columnC, Integer numberOfLines, Integer numberOfColumnsX,
			Criteria criteria) {
		this.samples = samples;
		this.columnC = columnC;
		this.numberOfLines = numberOfLines;
		this.numberOfColumnsX = numberOfColumnsX;
		this.criteria = criteria;
	}

	public Train() {
		super();
	}

	// getters and setter
	public List<Column> getSamples() {
		return samples;
	}

	public void setSamples(List<Column> samples) {
		this.samples = samples;
	}

	public Column getColumnC() {
		return columnC;
	}

	public void setColumnC(Column columnC) {
		this.columnC = columnC;
	}

	public Integer getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(Integer numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public Integer getNumberOfColumnsX() {
		return numberOfColumnsX;
	}

	public void setNumberOfColumnsX(Integer numberOfColumnsX) {
		this.numberOfColumnsX = numberOfColumnsX;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return "\nsamples: \n" + samples + " \n\n columnC: " + columnC + " \n\n criteria: " + criteria;
	}

}
