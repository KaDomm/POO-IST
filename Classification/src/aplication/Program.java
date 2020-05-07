package aplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import entities.Column;
import entities.Train;
import entities.enums.Criteria;

public class Program {

	public static void main(String[] args) {

		// This program must have 3 command-line argument to work with.
		if (args.length < 3) {
			System.out.println("Invalid number of input arguments");
			return;
		}

		// Instantiate object train
		Train train = new Train();

		// if wrong criteria string throws exception and exit
		try {
			train.setCriteria(Criteria.valueOf(args[2]));
		} catch (IllegalArgumentException e) {
			System.out.println("Ivalid criteria argument: " + e.getMessage());
			return;
		}

		// --- READ FILE AND INSTANCIATE NEEDED OBJECTS ---
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
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
				// add entries from 'X' columns (entries.length - 1)

				// suggestion : during a lecture, the teacher said that she will check if we
				// access an element of a list with get(i) in a for loop
				// it's apparently bad to do it because at each i-iteration, you start from the
				// beginning of the list and you go through it till the i-element, which is a
				// loss of time

				Iterator<Column> iter = train.getSamples().listIterator();
				while (iter.hasNext()) {
					for (int i = 0; i < entries.length - 1; i++) {
						Column column = iter.next();
						column.getArrayOfEntries().add(Integer.valueOf(entries[i]));
						if (Integer.valueOf(entries[i]) > column.getR() - 1) {
							column.setR(Integer.valueOf(entries[i]) + 1);
						}
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
		}

		// after reading the file we need to create a graph with different order of the
		// samples

		System.out.println("resul: " + train.Nijkc(1, 2, 2, 2));

		// then
		if (train.getCriteria().equals(Criteria.MDL)) {
			System.out.println("Find best solution with MDL");
		} else {
			System.out.println("Find best solution with LL");
		}

	}
}
