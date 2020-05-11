package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import entities.Column;
import entities.Train;

public class FileControl {

	public static Train BuildTrainFromFile(String File) {

		Train train = new Train();

		try (BufferedReader br = new BufferedReader(new FileReader(File))) {
			// Instantiate List of samples
			train.setSamples(new ArrayList<Column>());

			// first line only have names
			String line = br.readLine();
			// split all names to create all samples and columnC objects with name
			String[] names = line.split(",");

			// for cycle until length -1 because last column is column C
			for (int i = 0; i < names.length - 1; i++) {
				// instantiate new column object
				Column column = new Column();
				column.setName(names[i]);
				// instantiate entries list
				column.setArrayOfEntries(new ArrayList<Integer>());
				train.getSamples().add(column);
			}

			// last element is column C
			Column columnC = new Column();
			columnC.setName(names[names.length - 1]);
			columnC.setArrayOfEntries(new ArrayList<Integer>());
			train.setColumnC(columnC);

			while (line != null) {
				line = br.readLine();
				if (line == null) {
					continue;
				}
				String[] entries = line.split(",");

				Iterator<Column> iter = train.getSamples().listIterator();
				for (int i = 0; i < entries.length - 1; i++) {
					Column column = iter.next();
					column.getArrayOfEntries().add(Integer.valueOf(entries[i]));
					if (Integer.valueOf(entries[i]) > column.getR() - 1) {
						column.setR(Integer.valueOf(entries[i]) + 1);
					}
				}

				// last element is column C
				Integer entrieColumnC = Integer.valueOf(entries[entries.length - 1]);
				train.getColumnC().getArrayOfEntries().add(entrieColumnC);
				if (Integer.valueOf(entries[entries.length - 1]) > train.getColumnC().getR() - 1) {
					train.getColumnC().setR(Integer.valueOf(entries[entries.length - 1]) + 1);
				}
			}

		} catch (IOException e) {
			System.out.println("Error Wrong train file: " + e.getMessage());
			return null;
		}

		return train;
	}

}
