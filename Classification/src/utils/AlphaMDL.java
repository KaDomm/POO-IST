package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Alpha;
import entities.Column;
import entities.Train;
import interfaces.CriteriaInterface;

/**
 * @author USER-Admin
 *
 */
public class AlphaMDL implements CriteriaInterface {

	/**
	 *
	 */
	@Override
	public List<Alpha> buildWeightedAlpha(Train train) {

		List<Alpha> alphaList = new ArrayList<>();
		// generate Nc to use in alpha calculation
		train.generateNc();
		double s = (double) train.getColumnC().getR();
		int n = train.getColumnC().getArrayOfEntries().size();

		int numOfSamples = train.getSamples().size();
		// we need to check every node with all the other ( check every pair )
		Iterator<Column> childNode = train.getSamples().listIterator();
		for (int i = 0; i < numOfSamples; i++) {
			Column nodeAsChild = childNode.next();
			Iterator<Column> parentNode = train.getSamples().listIterator();
			for (int parentIndex = 0; parentIndex < numOfSamples; parentIndex++) {
				Column nodeAsParent = parentNode.next();
				Double alpha = 0.0;
				double qi = (double) train.getSamples().get(parentIndex).getR();
				double ri = (double) train.getSamples().get(i).getR();
				// we don't want to check the same node, only different ones
				// they have same value X1->X3 and X3->X1 so we only have to save one of them
				// (i <= parentIndex)
				if (parentIndex >= i)
					continue;
				for (int j = 0; j < nodeAsParent.getR(); j++)
					for (int k = 0; k < nodeAsChild.getR(); k++)
						for (int c = 0; c < train.getColumnC().getR(); c++)
							alpha += this.calcAlpha(train, parentIndex, i, j, k, c);

				alpha -= (((s * (ri - 1.0) * (qi - 1.0)) / 2.0) * Math.log(n));

				Alpha nodeAlpha = new Alpha(parentIndex, i, alpha);
				alphaList.add(nodeAlpha);
			}
		}

		return alphaList;

	}

	/**
	 * @param train
	 * @param parentIndex
	 * @param i
	 * @param j
	 * @param k
	 * @param c
	 * @return
	 */
	private Double calcAlpha(final Train train, final int parentIndex, final int i, final int j, final int k,
			final int c) {

		int n = train.getColumnC().getArrayOfEntries().size();
		double nIJKC = (double) train.nijkcTwoNodes(parentIndex, i, j, k, c);
		if (nIJKC == 0)
			return 0.0;
		// just need to get nC because already run in buildWeightedAlpha()
		int nC = train.getnC()[c];
		int nJ = train.nJikcTwoNodes(parentIndex, i, k, c);
		if (nJ == 0)
			return 0.0;
		int nK = train.nKijcTwoNodes(parentIndex, i, j, c);
		if (nK == 0)
			return 0.0;

		return ((nIJKC / n) * Utils.log2((nIJKC * nC) / (nJ * nK)));
	}

}
