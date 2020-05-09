package utils;

import entities.Train;
import interfaces.CriteriaInterface;

public class AlphaMDL implements CriteriaInterface {

	@Override
	public void alpha(Train train) {
		System.out.println("MDL");
	}

}
