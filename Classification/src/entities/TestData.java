package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import enums.Criteria;

/**
 * @author USER-Admin
 *
 */
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
	 * @param train train
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

	/**
	 * @param parentList parentList
	 * @return resultList
	 */
	public List<Integer> generateListOfTheta(List<Alpha> parentList) {

		this.computeAllNijkc(parentList);
		this.SumKNijc(parentList);
		this.SumJNikc();
		this.generateNc();
		this.computeTheta(parentList);
		return this.estimation(parentList);
	}

	/**
	 * @param parentList
	 */
	private List<Integer> estimation(List<Alpha> parentList) {

		List<Integer> finalEstimationColumnC = new ArrayList<>();
		// number of lines in the file
		int numberOfEntries = this.getSamples().get(0).getArrayOfEntries().size();
		// number of possible values for c
		int s = this.getColumnC().getR();
		// we go through all lines from file
		for (int line = 0; line < numberOfEntries; line++) {
			// for each line we need to multiply thetaIJKC from each column
			// we need to have all tethas[i][j][k][c] multiply but c value changes
			// each possible value of [c] have is own multiplication(check equation slack)
			double[] thetaCs = new double[this.getColumnC().getR()];
			Arrays.fill(thetaCs, 1.0);
			// for each line we need to check the value of child and the parent
			for (Alpha alpha : parentList) {
				// from parentList we always know who is the parent
				int i = alpha.getChildIndex();
				int parentIndex = alpha.getParentIndex();
				Column child = this.getSamples().get(i);
				// the value from the child's column
				int k = child.getArrayOfEntries().get(line);
				// it doesn't have parent -> j = 0 else get the value in that column
				int j = parentIndex != -1 ? child.getArrayOfEntries().get(parentIndex) : 0;
				// we have different thetas for each c
				for (int c = 0; c < s; c++)
					thetaCs[c] *= this.getTheta()[i][j][k][c];
			}

			finalEstimationColumnC.add(finalEstimation(thetaCs, s, numberOfEntries));
		}

		return finalEstimationColumnC;
	}

	/**
	 * @param thetaCs
	 * @param s
	 * @param numberOfEntries
	 * @return
	 */
	private int finalEstimation(double[] thetaCs, int s, int numberOfEntries) {

		// at this point there's all thetas ijkc multiplied
		// but we need to multiply thetaC
		double denominatorEstimationC = 0; // to be easier denominator is always the same
		for (int c = 0; c < s; c++) {
			double thetaC = ((double) this.getnC()[c] + 0.5) / ((double) numberOfEntries + s * 0.5);
			thetaCs[c] *= thetaC;
			denominatorEstimationC += thetaCs[c];
		}

		double betterEstimation = 0;
		int betterIndexC = 0;
		for (int c = 0; c < s; c++)
			if ((thetaCs[c] / denominatorEstimationC) > betterEstimation) {
				betterEstimation = thetaCs[c] / denominatorEstimationC;
				betterIndexC = c;
			}

		return betterIndexC;
	}

	/**
	 * @param parentList
	 */
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
					for (int c = 0; c < this.getColumnC().getR(); c++)
						theta[i][j][k][c] = (((double) this.getNijkc()[i][j][k][c] + pseudoCounts)
								/ (this.getnK()[i][j][c] + (ri * pseudoCounts)));

		}
		this.setTheta(theta);

	}

	/**
	 * @param parentList
	 */
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

	/**
	 * @param parentList
	 */
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

	/**
	 * 
	 */
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

	/**
	 * @return
	 */
	private int[][][][] createMatrixNijkc() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		int maxK = maxJ;

		return new int[maxI][maxJ][maxK][maxC];
	}

	/**
	 * @return
	 */
	private double[][][][] createMatrixTheta() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		int maxK = maxJ;

		return new double[maxI][maxJ][maxK][maxC];
	}

	/**
	 * @return
	 */
	private int[][][] createMatrixNk() {

		int maxI = this.getSamples().size();
		int maxJ = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxJ)
				maxJ = column.getR();

		return new int[maxI][maxJ][maxC];
	}

	/**
	 * @return
	 */
	private int[][][] createMatrixNj() {

		int maxI = this.getSamples().size();
		int maxK = 0;
		int maxC = this.getColumnC().getR();

		for (Column column : this.getSamples())
			if (column.getR() > maxK)
				maxK = column.getR();

		return new int[maxI][maxK][maxC];
	}

}
