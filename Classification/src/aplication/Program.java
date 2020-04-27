package aplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

		Train train = new Train(Criteria.valueOf(args[2]));

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

			// first line only have names
			String line = br.readLine();
			// split all names and create one object Column with name
			String[] names = line.split(",");
			// for cycle until length -1 because last column is column C
			for (int i = 0; i < names.length - 1; i++) {
				String columnName = names[i];
				Column column = new Column(columnName);
				train.getSamples().add(column);
			}

			// last element is column C
			String columnCName = names[names.length - 1];
			Column columnC = new Column(columnCName);
			train.setColumnC(columnC);

			while (line != null) {
				line = br.readLine();
				if (line == null) {
					continue;
				}
				String[] entries = line.split(",");
				// add entries from 'X' columns (entries.length - 1)
				for (int i = 0; i < entries.length - 1; i++) {
					train.getSamples().get(i).getArrayOfEntries().add(Integer.valueOf(entries[i]));
				}
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
