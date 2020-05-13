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

/**
 * @author USER-Admin
 *
 */
public class Program {

	/**
	 * @param args
	 */
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
		// calculate alpha for each edge
		List<Alpha> weightedEdges = alpha.buildWeightedAlpha(train);
		// get MST
		List<Alpha> parentList = Prim.generateTree(train.getSamples().size(), weightedEdges);

		Long timeAfterTree = System.nanoTime();

		// Instantiate object test(read from file)
		TestData test = new TestData(FileControl.BuildTrainFromFile(args[1]));

		Long timeBeforeTest = System.nanoTime();
		// compute data set
		List<Integer> resultList = test.generateListOfTheta(parentList);

		Long timeAfterTest = System.nanoTime();

		test.printFinalResult(parentList, timeAfterTree - timeBeforeTree, resultList, timeAfterTest - timeBeforeTest);
	}
}
