package utils;

import java.util.Iterator;
import java.util.List;

import entities.Column;

/**
 * @author USER-Admin
 *
 */
public class Utils {

	/**
	 * @param f
	 * @return
	 */
	public static double log2(double f) {
		return Math.log(f) / Math.log(2.0);
	}

	/**
	 * @param resultList
	 * @param columnC
	 * @return
	 */
	public static String Resume(List<Integer> resultList, Column columnC) {

		double sizeOfColumnC = resultList.size();
		double right = 0.0;
		double trueNegatives = 0.0, falsePositives = 0.0; // specificity
		double truePositives = 0.0, falseNegatives = 0.0; // sensitivity
		Iterator<Integer> resltIter = resultList.iterator();
		Iterator<Integer> cIter = columnC.getArrayOfEntries().iterator();
		while (resltIter.hasNext()) {
			Integer ourResult = resltIter.next();
			Integer solution = cIter.next();

			if (ourResult.equals(solution)) {
				right++;// accuracy

				if (ourResult.equals(0)) {
					trueNegatives++;// specificity

				} else
					truePositives++;// sensitivity

			} else if (!ourResult.equals(0)) {
				falsePositives++;// specificity

			} else if (ourResult.equals(0)) {
				falseNegatives++;// sensitivity
			}

		}
		return right / sizeOfColumnC + ", " + trueNegatives / (trueNegatives + falsePositives) + ", "
				+ truePositives / (truePositives + falseNegatives) + ", "
				+ 2 * truePositives / (2 * truePositives + falsePositives + falseNegatives);
	}

}
