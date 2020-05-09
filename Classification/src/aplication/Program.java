package aplication;

import entities.Train;
import enums.Criteria;
import interfaces.CriteriaInterface;
import utils.AlphaLL;
import utils.AlphaMDL;
import utils.FileControl;

public class Program {

	public static void main(String[] args) {

		// This program must have 3 command-line argument to work with.
		if (args.length < 3) {
			System.out.println("Invalid number of input arguments");
			return;
		}

		// Instantiate object train
		Train train = FileControl.BuildTrainFromFile(args[0], args[2]);
		if (train == null)
			return;

		train.getTrainData();
		CriteriaInterface alpha = null;

		// Select type of criteria
		alpha = train.getCriteria().equals(Criteria.MDL) ? new AlphaMDL()
				: train.getCriteria().equals(Criteria.LL) ? new AlphaLL() : null;

		alpha.alpha(train);

		// PRINT Nijkc
//		for (int i = 0; i < train.getSamples().size(); i++) {
//			for (int j = 0; j < (i > 0 ? train.getSamples().get(i - 1).getR() : 1); j++) {
//				for (int k = 0; k < train.getSamples().get(i).getR(); k++) {
//					for (int c = 0; c < train.getColumnC().getR(); c++)
//						System.out.println(
//								"N" + (i + 1) + (j + 1) + (k + 1) + (c + 1) + ":" + train.getNijkc()[i][j][k][c] + " ");
//				}
//				System.out.println("-");
//			}
//			System.out.println();
//		}

		// PRINT Sum kNijc
//		for (int i = 0; i < train.getSamples().size(); i++) {
//			for (int j = 0; j < (i > 0 ? train.getSamples().get(i - 1).getR() : 1); j++) {
//				for (int c = 0; c < train.getColumnC().getR(); c++)
//					System.out.println("NK" + (i + 1) + (j + 1) + (c + 1) + ":" + train.getnK()[i][j][c] + " ");
//			}
//			System.out.println("-");
//		}
//		System.out.println();

		// PRINT sum jNikc
//		for (int i = 0; i < train.getSamples().size(); i++) {
//			for (int k = 0; k < train.getSamples().get(i).getR(); k++) {
//				for (int c = 0; c < train.getColumnC().getR(); c++)
//					System.out.println("NJ" + (i + 1) + (k + 1) + (c + 1) + ":" + train.getnJ()[i][k][c] + " ");
//			}
//			System.out.println("-");
//		}
//		System.out.println();

		// PRINT nc
//		for (int c = 0; c < train.getColumnC().getR(); c++)
//			System.out.println("Nc" + (c + 1) + ":" + train.getnC()[c] + " ");
	}
}
