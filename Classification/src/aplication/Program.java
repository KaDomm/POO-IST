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

		if (args.length < 3) {
			// This program must have 3 command-line argument to work with.
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

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

			// Instantiate List of samples
			train.setSamples(new ArrayList<Column>());

			// first line only have names
			String line = br.readLine();
			// split all names to create all samples objects with name
			String[] names = line.split(",");

			// for cycle until length -1 because last column is column C
			for (int i = 0; i < names.length - 1; i++) {
				String columnName = names[i];
				// instantiate new column object
				Column column = new Column();
				column.setName(columnName);
				// instantiate entries list
				column.setArrayOfEntries(new ArrayList<Integer>());
				train.getSamples().add(column);
			}

			// last element is column C
			String columnCName = names[names.length - 1];
			Column columnC = new Column();
			columnC.setName(columnCName);
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
						iter.next().getArrayOfEntries().add(Integer.valueOf(entries[i]));
					}
				}
				/*
				 * for (int i = 0; i < entries.length - 1; i++) {
				 * train.getSamples().get(i).getArrayOfEntries().add(Integer.valueOf(entries[i])
				 * ); }
				 */

				// last element is column C
				Integer entrieColumnC = Integer.valueOf(entries[entries.length - 1]);
				train.getColumnC().getArrayOfEntries().add(entrieColumnC);
			}

			System.out.println(train);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}
