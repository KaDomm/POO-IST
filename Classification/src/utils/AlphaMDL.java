package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Alpha;
import entities.Column;
import entities.Train;
import interfaces.CriteriaInterface;

public class AlphaMDL implements CriteriaInterface {

	@Override
	public List<Alpha> buildWeightedAlpha(Train train) {

		List<Alpha> alphaList = new ArrayList<>();
		// get Nc to use in alpha calculation
		train.generateNc();

		int numOfSamples = train.getSamples().size();
		// we need to check every node with all the other ( check every pair )
		Iterator<Column> childNode = train.getSamples().listIterator();
		// to track secondNode index
		for (int i = 0; i < numOfSamples; i++) {
			Column nodeAsChild = childNode.next();
			Iterator<Column> parentNode = train.getSamples().listIterator();
			for (int parentIndex = 0; parentIndex < numOfSamples; parentIndex++) {
				Column nodeAsParent = parentNode.next();
				Double alpha = 0.0;
				// we don't want to check the same node, only different ones
				if (i >= parentIndex)
					continue;
				for (int j = 0; j < nodeAsParent.getR(); j++)
					for (int k = 0; k < nodeAsChild.getR(); k++)
						for (int c = 0; c < train.getColumnC().getR(); c++)
							alpha += this.calcAlpha(train, parentIndex, i, j, k, c);

				Alpha nodeAlpha = new Alpha(nodeAsParent, parentIndex, nodeAsChild, i, alpha, false);
				alphaList.add(nodeAlpha);
				nodeAlpha = new Alpha(nodeAsChild, i, nodeAsParent, parentIndex, alpha, false);
				alphaList.add(nodeAlpha);
			}
		}

		return alphaList;

	}

	private Double calcAlpha(final Train train, final int parentIndex, final int i, final int j, final int k,
			final int c) {

		int n = train.getSamples().size();
		double nIJKC = (double) train.nijkcTwoNodes(train, parentIndex, i, j, k, c);
		int nC = train.getNC()[c];
		int nJ = train.nJikcTwoNodes(train, parentIndex, i, k, c);
		int nK = train.nKijcTwoNodes(train, parentIndex, i, j, c);

		return ((nIJKC / n) * Utils.log2((nIJKC * nC) / (nJ * nK)));
	}

}
