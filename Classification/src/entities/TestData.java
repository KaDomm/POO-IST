package entities;

import java.util.Iterator;
import java.util.List;

import enums.Criteria;

public class TestData extends Train {

	private int[][][][] nijkc;
	private int[][][] nK;
	private int[][][] nJ;
	private double[][][][] theta;

	/**
	 * @param samples
	 * @param columnC
	 * @param criteria
	 * @param nijkc
	 * @param nK
	 * @param nJ
	 * @param theta
	 */
	protected TestData(List<Column> samples, Column columnC, Criteria criteria, int[][][][] nijkc, int[][][] nK,
			int[][][] nJ, double[][][][] theta) {
		super(samples, columnC, criteria);
		this.nijkc = nijkc;
		this.nK = nK;
		this.nJ = nJ;
		this.theta = theta;
	}

	/**
	 * @param samples
	 * @param columnC
	 * @param criteria
	 */
	public TestData(Train train) {
		super(train.getSamples(), train.getColumnC(), train.getCriteria());
	}

	/**
	 * @return the nijkc
	 */
	public int[][][][] getNijkc() {
		return nijkc;
	}

	/**
	 * @param nijkc the nijkc to set
	 */
	public void setNijkc(int[][][][] nijkc) {
		this.nijkc = nijkc;
	}

	/**
	 * @return the nK
	 */
	public int[][][] getnK() {
		return nK;
	}

	/**
	 * @param nK the nK to set
	 */
	public void setnK(int[][][] nK) {
		this.nK = nK;
	}

	/**
	 * @return the nJ
	 */
	public int[][][] getnJ() {
		return nJ;
	}

	/**
	 * @param nJ the nJ to set
	 */
	public void setnJ(int[][][] nJ) {
		this.nJ = nJ;
	}

	/**
	 * @return the theta
	 */
	public double[][][][] getTheta() {
		return theta;
	}

	/**
	 * @param theta2 the theta to set
	 */
	public void setTheta(double[][][][] theta2) {
		this.theta = theta2;
	}

	public List<Integer> generateListOfTheta(List<Alpha> parentList) {

		this.computeAllNijkc(parentList);
		this.SumKNijc(parentList);
		this.SumJNikc();
		this.generateNc();
		this.computeTheta(parentList);
		return null;
	}

	private void computeTheta(List<Alpha> parentList) {

		// N'
		double pseudoCounts = 0.5;

		// Instantiate matrix theta
		double[][][][] theta = createMatrixTheta();

		for (Alpha alpha : parentList) {

			int i = alpha.getChildIndex();
			int ri = this.getSamples().get(i).getR();
			int parentIndex = alpha.getParentIndex();
			for (int j = 0; j < (parentIndex != -1 ? this.getSamples().get(parentIndex).getR() : 1); j++)
				for (int k = 0; k < this.getSamples().get(i).getR(); k++)
					for (int c = 0; c < this.getColumnC().getR(); c++) {
						theta[i][j][k][c] = (((double) this.getNijkc()[i][j][k][c] + pseudoCounts)
								/ (this.getnK()[i][j][c] + (ri * pseudoCounts)));
					}
		}
		this.setTheta(theta);

	}

	private void computeAllNijkc(List<Alpha> parentList) {

		// Instantiate matrix nIjKc
		int[][][][] nIjKc = createMatrixNijkc();

		for (Alpha alpha : parentList) {

			int i = alpha.getChildIndex();
			int parentIndex = alpha.getParentIndex();
			Column child = this.getSamples().get(i);

			int nijkc = 0;
			// is it's the root the parentIndex comes as -1
			if (parentIndex == -1) {
				for (int k = 0; k < child.getR(); k++)
					for (int c = 0; c < this.getColumnC().getR(); c++) {
						Iterator<Integer> cEntrie = this.getColumnC().getArrayOfEntries().listIterator();
						Iterator<Integer> iEntrie = child.getArrayOfEntries().listIterator();
						// all columns have the same amount of entries
						while (cEntrie.hasNext() && iEntrie.hasNext()) {
							int ct = cEntrie.next(), xt = iEntrie.next();
							if (ct == c && xt == k)
								nijkc++;
						}
						nIjKc[i][0][k][c] = nijkc;
						nijkc = 0;
					}
			} else {
				Column parent = this.getSamples().get(parentIndex);
				for (int j = 0; j < parent.getR(); j++)
					for (int k = 0; k < child.getR(); k++)
						for (int c = 0; c < getColumnC().getR(); c++) {
							Iterator<Integer> cEntrie = getColumnC().getArrayOfEntries().listIterator();
							Iterator<Integer> iEntrie = child.getArrayOfEntries().listIterator();
							Iterator<Integer> pEntrie = parent.getArrayOfEntries().listIterator();
							// all columns have the same amount of entries
							while (cEntrie.hasNext() && iEntrie.hasNext() && pEntrie.hasNext()) {
								int cE = cEntrie.next(), iE = iEntrie.next(), pE = pEntrie.next();
								if (cE == c && iE == k && pE == j)
									nijkc++;

							}
							nIjKc[i][j][k][c] = nijkc;
							nijkc = 0;
						}
			}
		}
		this.setNijkc(nIjKc);

	}

	private void SumKNijc(List<Alpha> parentList) {

		int[][][] nIjc = this.createMatrixNk();
		int sum = 0;

		for (Alpha alpha : parentList) {

			int i = alpha.getChildIndex();
			int parentIndex = alpha.getParentIndex();
			for (int j = 0; j < (parentIndex != -1 ? this.getSamples().get(parentIndex).getR() : 1); j++)
				for (int c = 0; c < this.getColumnC().getR(); c++) {
					for (int k = 0; k < this.getSamples().get(i).getR(); k++)
						sum += this.nijkc[i][j][k][c];
					nIjc[i][j][c] = sum;
					sum = 0;
				}
		}
		this.setnK(nIjc);
	}

	private void SumJNikc() {

		int[][][] nIkc = createMatrixNj();
		int sum = 0;

		for (int i = 0; i < this.getSamples().size(); i++)
			for (int k = 0; k < this.getSamples().get(i).getR(); k++)
				for (int c = 0; c < this.getColumnC().getR(); c++) {
					for (int j = 0; j < (i > 0 ? this.getSamples().get(i - 1).getR() : 1); j++)
						sum += this.nijkc[i][j][k][c];
					nIkc[i][k][c] = sum;
					sum = 0;
				}
		this.setnJ(nIkc);
	}

	public int[][][][] createMatrixNijkc() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		int maxK = maxJ;

		return new int[maxI][maxJ][maxK][maxC];
	}

	public double[][][][] createMatrixTheta() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		int maxK = maxJ;

		return new double[maxI][maxJ][maxK][maxC];
	}

	public int[][][] createMatrixNk() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		return new int[maxI][maxJ][maxC];
	}

	public int[][][] createMatrixNj() {

		int maxI = this.getSamples().size();
		int maxK = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxK)
				maxK = column.getR();

		return new int[maxI][maxK][maxC];
	}

}
