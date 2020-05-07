package entities;
import java.util.ArrayList;
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
	//Method to calculate Nijkc taking ijkc as parameters.
	// to be tested and optimized (get(i) in for loop is bad and brings a large computational time)
	public Integer Nijkc(int i, int j, int k, int c) {
		Integer Nijkc = 0;
		//case with no parents
		if(i == 0) {
			int xik = samples.get(i).getArrayOfEntries().get(k);
			for (int n=0;n<samples.get(i).getR();n++) {
				if (columnC.getArrayOfEntries().get(n) == c && samples.get(i).getArrayOfEntries().get(n) == xik) {
					Nijkc++;
				}
			}
		}
		//case with parents
		else {
			int xik = samples.get(i).getArrayOfEntries().get(k);
			int wij = samples.get(i-1).getArrayOfEntries().get(j);
			for (int n=0;n<samples.get(i).getR();n++) {
				if (columnC.getArrayOfEntries().get(n) == c && samples.get(i).getArrayOfEntries().get(n) == xik && samples.get(i-1).getArrayOfEntries().get(n) == wij) {
					Nijkc++;
				}
			}
		}
		return Nijkc;
	}
		
	public double alphaLL(int index){
		//index is i;
		double alpha = 0;
		Column ToCalculate = samples.get(index);
		Column ParentToCalculate = samples.get(index-1);
		//2D Arraylist containing the valuu of the Sum NJ depending on i,k. the first column is the value c=0; and the second column is value of c=1;
		ArrayList<ArrayList<Integer>> NJ = new ArrayList<ArrayList<Integer>>();
		//Nj here is the sum NJ with the kth value;
		Integer Nj;
		//2D Arraylist containing the valuu of the Sum NK depending on i,j. the first column is the value c=0; and the second column is value of c=1;
		ArrayList<ArrayList<Integer>> NK = new ArrayList<ArrayList<Integer>>();
		//Nk here is the sum NK with the jth value;
		Integer Nk;
		//Nc with c = 0 in the first column, c = 1 in the second column
		ArrayList<Integer> NC = new ArrayList<Integer>();
		int Nc0 = 0;
		int Nc1 = 0;
		int N = ToCalculate.getR();
		for(int c=0;c<1;c++) {
			ArrayList<Integer> NJc = new ArrayList<Integer>();
			ArrayList<Integer> NKc = new ArrayList<Integer>();
			for(int k=0;k<ToCalculate.getR();k++) {
				Nj = 0;
				for(int j=0;j<ParentToCalculate.getR();j++) {
					Nj = Nj + this.Nijkc(index, j, k, c);
				}
				NJc.add(Nj);
			}
			for(int j=0;j<ParentToCalculate.getR();j++) {
				Nk = 0;
				for(int k=0;k<ToCalculate.getR();k++) {
					Nk = Nk + this.Nijkc(index, j, k, c);
				}
				NKc.add(Nk);
			}
			if(columnC.getArrayOfEntries().get(c) == 0) {
				Nc0++;
			}
			else{
				Nc1++;
			}
			NJ.add(NJc);
			NK.add(NKc);
		}
		NC.add(Nc0);
		NC.add(Nc1);
		
		for(int j=0;j<ParentToCalculate.getR();j++) {
			for(int k=0;k<ToCalculate.getR();k++) {
				for(int c=0;c<1;c++) {
					double Nijkc = this.Nijkc(index, j, k, c);
					alpha = alpha + Nijkc/N + this.log2(Nijkc*NC.get(c)/(NJ.get(c).get(k)*NK.get(k).get(j)));
				}
			}
		}
		
		return alpha;
	}
	public int log2(int n){
	    if(n <= 0) throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	public int log2(double f){
	    return (int)Math.floor(Math.log(f)/Math.log(2.0));
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
