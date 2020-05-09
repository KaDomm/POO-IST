package utils;

import java.util.Iterator;

import entities.Column;
import entities.Train;
import interfaces.CriteriaInterface;

public class AlphaLL implements CriteriaInterface {

	@Override
	public void alpha(Train train) {

		Double alpha = 0.0;
		// get all data from train
		int[][][][] nijkc = train.getNijkc();
		int[][][] nk = train.getnK();
		int[][][] nj = train.getnJ();
		int[] nc = train.getnC();
		int n = train.getSamples().size(), i = 0;
		// alpha is the weight between 2 columns so we have parent in the 1st iteration
		Iterator<Column> iter = train.getSamples().listIterator();
		// get first element from samples list
		Column columnI = iter.next();
		Column columnParent = null;
		// compute N[1,1,*,*]
		while (iter.hasNext()) {
			i++;
			columnParent = columnI;
			columnI = iter.next();

			for (int j = 0; j < columnParent.getR(); j++)
				for (int k = 0; k < columnI.getR(); k++)
					for (int c = 0; c < train.getColumnC().getR(); c++) {
						alpha = (double) ((nijkc[i][j][k][c] / n)
								* Utils.log2((nijkc[i][j][k][c] * nc[c]) / (nj[i][k][c] * nk[i][j][c])));
					}
		}

	}
}
