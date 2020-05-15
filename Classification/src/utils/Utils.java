package utils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import entities.Column;

/**
 * @author USER-Admin
 *
 */
public class Utils {

	/**
	 * @param f value
	 * @return result
	 */
	public static double log2(double f) {
		return Math.log(f) / Math.log(2.0);
	}

	/**
	 * @param resultList resultList
	 * @param columnC    columnC
	 * @return resume
	 */
	public static String Resume(List<Integer> resultList, Column columnC) {

		DecimalFormat df = new DecimalFormat("0.00");

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
		return df.format(right / sizeOfColumnC) + ", " + df.format(trueNegatives / (trueNegatives + falsePositives))
				+ ", " + df.format(truePositives / (truePositives + falseNegatives)) + ", "
				+ df.format(2 * truePositives / (2 * truePositives + falsePositives + falseNegatives));
	}

}
