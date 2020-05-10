package entities;

import java.util.Iterator;
import java.util.List;

import enums.Criteria;

/**
 * @author USER-Admin
 *
 */
public class Train {

	private List<Column> samples;
	private Column columnC;

	private Criteria criteria;

	private int[][][][] nijkc;
	private int[][][] nK;
	private int[][][] nJ;
	private int[] nC;

	// constructors
	/**
	 * @param samples
	 * @param columnC
	 * @param criteria
	 * @param nijkc
	 * @param nK
	 * @param nJ
	 * @param nC
	 */
	protected Train(List<Column> samples, Column columnC, Criteria criteria, int[][][][] nijkc, int[][][] nK,
			int[][][] nJ, int[] nC) {
		this.samples = samples;
		this.columnC = columnC;
		this.criteria = criteria;
		this.nijkc = nijkc;
		this.nK = nK;
		this.nJ = nJ;
		this.nC = nC;
	}

	/**
	 * 
	 */
	public Train() {
	}

	// getters and setter
	/**
	 * @return the samples
	 */
	public List<Column> getSamples() {
		return samples;
	}

	/**
	 * @param samples the samples to set
	 */
	public void setSamples(List<Column> samples) {
		this.samples = samples;
	}

	/**
	 * @return the columnC
	 */
	public Column getColumnC() {
		return columnC;
	}

	/**
	 * @param columnC the columnC to set
	 */
	public void setColumnC(Column columnC) {
		this.columnC = columnC;
	}

	/**
	 * @return the criteria
	 */
	public Criteria getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria the criteria to set
	 */
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the nijkc
	 */
	public int[][][][] getNijkc() {
		return nijkc;
	}

	/**
	 * @param nIjKc2 the nijkc to set
	 */
	public void setNijkc(int[][][][] nIjKc2) {
		this.nijkc = nIjKc2;
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
	 * @return
	 */
	public int[] getNC() {
		return nC;
	}

	/**
	 * @param nC
	 */
	public void setNC(int[] nC) {
		this.nC = nC;
	}

	public void getTrainData() {

		this.computeAllNijkc();
		this.SumKNijc();
		this.SumJNikc();
		this.generateNc();
	}

	private void computeAllNijkc() {

		// Instantiate matrix nIjKc
		int[][][][] nIjKc = createMatrixNijkc();
		// begin iteration of i (for(int i = 0... )
		Iterator<Column> iter = this.samples.listIterator();
		// first case only takes in consideration Parent column
		// get first element from samples list
		Column columnI = iter.next();
		// parent doesn't exist first time
		Column columnParent = null;
		// first case there's no parent so j max it's 1
		int i = 0, j = 0, k = 0, c = 0, nijkc = 0;
		// compute N[1,1,*,*]
		for (k = 0; k < columnI.getR(); k++)
			for (c = 0; c < this.getColumnC().getR(); c++) {
				Iterator<Integer> cEntrie = this.columnC.getArrayOfEntries().listIterator();
				Iterator<Integer> iEntrie = columnI.getArrayOfEntries().listIterator();
				// all columns have the same amount of entries
				while (cEntrie.hasNext() && iEntrie.hasNext()) {
					int ct = cEntrie.next(), xt = iEntrie.next();
					if (ct == c && xt == k)
						nijkc++;
				}
				nIjKc[i][j][k][c] = nijkc;
				nijkc = 0;
			}
		// compute N[*,*,*,*]
		while (iter.hasNext()) {
			// parent is the previous column
			columnParent = columnI;
			columnI = iter.next();
			i++;
			for (j = 0; j < columnParent.getR(); j++)
				for (k = 0; k < columnI.getR(); k++)
					for (c = 0; c < columnC.getR(); c++) {
						Iterator<Integer> cEntrie = columnC.getArrayOfEntries().listIterator();
						Iterator<Integer> iEntrie = columnI.getArrayOfEntries().listIterator();
						Iterator<Integer> pEntrie = columnParent.getArrayOfEntries().listIterator();
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
		this.setNijkc(nIjKc);
	}

	private void SumKNijc() {

		int[][][] nIjc = this.createMatrixNk();
		int sum = 0;

		for (int i = 0; i < this.samples.size(); i++)
			for (int j = 0; j < (i > 0 ? this.samples.get(i - 1).getR() : 1); j++)
				for (int c = 0; c < this.columnC.getR(); c++) {
					for (int k = 0; k < this.samples.get(i).getR(); k++)
						sum += this.nijkc[i][j][k][c];
					nIjc[i][j][c] = sum;
					sum = 0;
				}
		this.setnK(nIjc);
	}

	private void SumJNikc() {

		int[][][] nIkc = createMatrixNj();
		int sum = 0;

		for (int i = 0; i < this.samples.size(); i++)
			for (int k = 0; k < this.samples.get(i).getR(); k++)
				for (int c = 0; c < this.columnC.getR(); c++) {
					for (int j = 0; j < (i > 0 ? this.samples.get(i - 1).getR() : 1); j++)
						sum += this.nijkc[i][j][k][c];
					nIkc[i][k][c] = sum;
					sum = 0;
				}
		this.setnJ(nIkc);
	}

	public int[][][][] createMatrixNijkc() {

		int maxI = this.samples.size();
		int maxJ = 0;
		int maxC = this.columnC.getR();

		for (Column column : this.samples)
			if (column.getR() > maxJ)
				maxJ = column.getR();

		int maxK = maxJ;

		return new int[maxI][maxJ][maxK][maxC];
	}

	public int[][][] createMatrixNk() {

		int maxI = this.samples.size();
		int maxJ = 0;
		int maxC = this.columnC.getR();

		for (Column column : this.samples)
			if (column.getR() > maxJ)
				maxJ = column.getR();

		return new int[maxI][maxJ][maxC];
	}

	public int[][][] createMatrixNj() {

		int maxI = this.samples.size();
		int maxK = 0;
		int maxC = this.columnC.getR();

		for (Column column : this.samples)
			if (column.getR() > maxK)
				maxK = column.getR();

		return new int[maxI][maxK][maxC];
	}

	public void generateNc() {

		Column columnC = this.columnC;
		int r = columnC.getR(), n = 0;
		int[] nc = new int[r];

		for (int c = 0; c < r; c++) {
			Iterator<Integer> cEntrie = columnC.getArrayOfEntries().listIterator();
			while (cEntrie.hasNext()) {
				int cE = cEntrie.next();
				if (cE == c)
					n++;
			}
			nc[c] = n;
			n = 0;
		}
		this.setNC(nc);
	}

	@Override
	public String toString() {
		return "\nsamples: \n" + samples + " \n\n columnC: " + columnC + " \n\n criteria: " + criteria;
	}

	public Integer nijkcTwoNodes(final Train train, final int parentIndex, final int i, final int j, final int k,
			final int c) {

		Integer Nijkc = 0;

		Iterator<Integer> iEntrie = this.samples.get(i).getArrayOfEntries().listIterator();
		Iterator<Integer> pEntrie = this.samples.get(parentIndex).getArrayOfEntries().listIterator();
		Iterator<Integer> cEntrie = this.columnC.getArrayOfEntries().listIterator();
		// all columns have the same amount of entries
		while (iEntrie.hasNext() && pEntrie.hasNext() && cEntrie.hasNext()) {
			int iE = iEntrie.next(), pE = pEntrie.next(), cE = cEntrie.next();
			if (cE == c && iE == k && pE == j)
				Nijkc++;

		}
		return Nijkc;
	}

	public Integer nJikcTwoNodes(final Train train, final int parentIndex, final int i, final int k, final int c) {

		Integer nJ = 0;
		Column parent = train.getSamples().get(parentIndex);

		for (int j = 0; j < parent.getR(); j++) {
			Iterator<Integer> iEntrie = this.samples.get(i).getArrayOfEntries().listIterator();
			Iterator<Integer> pEntrie = parent.getArrayOfEntries().listIterator();
			Iterator<Integer> cEntrie = this.columnC.getArrayOfEntries().listIterator();
			// all columns have the same amount of entries
			while (iEntrie.hasNext() && pEntrie.hasNext() && cEntrie.hasNext()) {
				int iE = iEntrie.next(), pE = pEntrie.next(), cE = cEntrie.next();
				if (cE == c && iE == k && pE == j)
					nJ++;

			}
		}
		return nJ;
	}

	public int nKijcTwoNodes(Train train, final int parentIndex, final int i, final int j, final int c) {

		Integer nK = 0;
		Column child = train.getSamples().get(i);

		for (int k = 0; k < child.getR(); k++) {
			Iterator<Integer> iEntrie = child.getArrayOfEntries().listIterator();
			Iterator<Integer> pEntrie = this.samples.get(parentIndex).getArrayOfEntries().listIterator();
			Iterator<Integer> cEntrie = this.columnC.getArrayOfEntries().listIterator();
			// all columns have the same amount of entries
			while (iEntrie.hasNext() && pEntrie.hasNext() && cEntrie.hasNext()) {
				int iE = iEntrie.next(), pE = pEntrie.next(), cE = cEntrie.next();
				if (cE == c && iE == k && pE == j)
					nK++;

			}
		}
		return nK;
	}
}
