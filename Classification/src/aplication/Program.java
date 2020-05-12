package aplication;

import java.util.List;

import entities.Alpha;
import entities.TestData;
import entities.Train;
import enums.Criteria;
import interfaces.CriteriaInterface;
import utils.AlphaLL;
import utils.AlphaMDL;
import utils.FileControl;
import utils.Prim;

public class Program {

	public static void main(String[] args) {

		// This program must have 3 command-line argument to work with.
		if (args.length < 3) {
			System.out.println("Invalid number of input arguments");
			return;
		}
		// Instantiate object train
		Train train = FileControl.BuildTrainFromFile(args[0]);
		if (train == null)
			return;
		// if wrong criteria string throws exception and exit
		try {
			train.setCriteria(Criteria.valueOf(args[2]));
		} catch (IllegalArgumentException e) {
			System.out.println("Ivalid criteria argument: " + e.getMessage());
			return;
		}
		// Select type of criteria
		CriteriaInterface alpha = train.getCriteria().equals(Criteria.MDL) ? new AlphaMDL()
				: train.getCriteria().equals(Criteria.LL) ? new AlphaLL() : null;

		Long timeBeforeTree = System.nanoTime();

		List<Alpha> weightedEdges = alpha.buildWeightedAlpha(train);

		List<Alpha> parentList = Prim.generateTree(train.getSamples().size(), weightedEdges);

		Long timeAfterTree = System.nanoTime();

		// Instantiate object test
		TestData test = new TestData(FileControl.BuildTrainFromFile(args[1]));

		Long timeBeforeTest = System.nanoTime();

		List<Integer> resultList = test.generateListOfTheta(parentList);

		Long timeAfterTest = System.nanoTime();

		test.printFinalResult(parentList, timeAfterTree - timeBeforeTree, timeAfterTest - timeBeforeTest);

		// PRINT Nijkc
		for (Alpha alphas : parentList) {

			int i = alphas.getChildIndex();
			int parentIndex = alphas.getParentIndex();
			for (int j = 0; j < (parentIndex != -1 ? test.getSamples().get(parentIndex).getR() : 1); j++)
				for (int k = 0; k < test.getSamples().get(i).getR(); k++) {
					for (int c = 0; c < test.getColumnC().getR(); c++)
						System.out.println("Theta" + (i + 1) + (j + 1) + (k + 1) + (c + 1) + ":"
								+ test.getTheta()[i][j][k][c] + " ");

					System.out.println("-");
				}
			System.out.println();
		}
//
//		// PRINT Sum kNijc
//		for (int i = 0; i < test.getSamples().size(); i++) {
//			for (int j = 0; j < (i > 0 ? test.getSamples().get(i - 1).getR() : 1); j++) {
//				for (int c = 0; c < test.getColumnC().getR(); c++)
//					System.out.println("NK" + (i + 1) + (j + 1) + (c + 1) + ":" + test.getnK()[i][j][c] + " ");
//			}
//			System.out.println("-");
//		}
//		System.out.println();
//
//		// PRINT sum jNikc
//		for (int i = 0; i < test.getSamples().size(); i++) {
//			for (int k = 0; k < test.getSamples().get(i).getR(); k++) {
//				for (int c = 0; c < test.getColumnC().getR(); c++)
//					System.out.println("NJ" + (i + 1) + (k + 1) + (c + 1) + ":" + test.getnJ()[i][k][c] + " ");
//			}
//			System.out.println("-");
//		}
//		System.out.println();
//
//		// PRINT nc
//		for (int c = 0; c < test.getColumnC().getR(); c++)
//			System.out.println("Nc" + (c + 1) + ":" + test.getnC()[c] + " ");
	}
}
