package entities;

import java.util.Iterator;
import java.util.List;

import enums.Criteria;
import utils.Utils;

/**
 * @author USER-Admin
 *
 */
public class Train {

	private List<Column> samples;
	private Column columnC;
	private int[] nC;

	private Criteria criteria;

	// constructors
	/**
	 * @param samples
	 * @param columnC
	 * @param criteria
	 */
	protected Train(List<Column> samples, Column columnC, Criteria criteria) {
		this.samples = samples;
		this.columnC = columnC;
		this.criteria = criteria;
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
	 * @return the nC
	 */
	public int[] getnC() {
		return nC;
	}

	/**
	 * @param nC the nC to set
	 */
	public void setnC(int[] nC) {
		this.nC = nC;
	}

	/**
	 * 
	 */
	public void generateNc() {

		Column columnC = this.getColumnC();
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
		this.setnC(nc);
	}

	/**
	 * @param parentIndex parentIndex
	 * @param i           i
	 * @param j           i
	 * @param k           i
	 * @param c           c
	 * @return nijkc
	 */
	public Integer nijkcTwoNodes(final int parentIndex, final int i, final int j, final int k, final int c) {

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

	/**
	 * @param parentIndex parentIndex
	 * @param i           i
	 * @param k           k
	 * @param c           c
	 * @return nJikc
	 */
	public Integer nJikcTwoNodes(final int parentIndex, final int i, final int k, final int c) {

		Integer nJ = 0;
		Column parent = samples.get(parentIndex);

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

	/**
	 * @param parentIndex parentIndex
	 * @param i           i
	 * @param j           i
	 * @param c           i
	 * @return nKijc
	 */
	public int nKijcTwoNodes(final int parentIndex, final int i, final int j, final int c) {

		Integer nK = 0;
		Column child = samples.get(i);

		for (int k = 0; k < child.getR(); k++) {
			Iterator<Integer> iEntrie = child.getArrayOfEntries().listIterator();
			Iterator<Integer> pEntrie = this.samples.get(parentIndex).getArrayOfEntries().listIterator();
			Iterator<Integer> cEntrie = this.columnC.getArrayOfEntries().listIterator();
			// all columns have the same amount of entries
			while (iEntrie.hasNext() && pEntrie.hasNext() && cEntrie.hasNext()) {
				int iE = iEntrie.next(), pE = pEntrie.next(), cE = cEntrie.next();
				if (iE == k && pE == j && cE == c)
					nK++;

			}
		}
		return nK;
	}

	/**
	 * @param parentList parentList
	 * @param firstTime  firstTime
	 * @param resultList resultList
	 * @param lastTime   lastTime
	 */
	public void printFinalResult(List<Alpha> parentList, long firstTime, List<Integer> resultList, long lastTime) {

		System.out.println("Classifier: \t\t" + this.samples.get(parentList.get(0).getChildIndex()).getName() + " :");
		for (int i = 1; i < parentList.size(); i++) {
			int childIndex = parentList.get(i).getChildIndex();
			int parentIndex = parentList.get(i).getParentIndex();
			System.out.println("\t\t\t" + this.samples.get(childIndex).getName() + " : "
					+ this.samples.get(parentIndex).getName());
		}
		System.out.println();
		System.out.println("Time to build: \t\t" + firstTime / 1000000.0 + " ms\n");
		System.out.println("Testing the classifier:");
		for (int i = 0; i < resultList.size(); i++)
			System.out.println("-> instance " + i + ":\t\t" + resultList.get(i));
		System.out.println();
		System.out.println("Time to test: \t\t" + lastTime / 1000000.0 + " ms\n");
		System.out.println("Resume: \t\t" + Utils.Resume(resultList, this.getColumnC()));
	}
}
