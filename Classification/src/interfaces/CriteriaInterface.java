package interfaces;

import java.util.List;

import entities.Alpha;
import entities.Train;

/**
 * @author USER-Admin
 *
 */
public interface CriteriaInterface {

	/**
	 * @param train
	 * @return
	 */
	public List<Alpha> buildWeightedAlpha(Train train);
}
