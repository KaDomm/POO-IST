package interfaces;

import java.util.List;

import entities.Alpha;
import entities.Train;

public interface CriteriaInterface {

	public List<Alpha> buildWeightedAlpha(Train train);
}
