package aplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program {

	public static void main(String[] args) {

		if (args.length == 0) {
			// This program must have at least one command-line argument to work with.
			System.out.println("Usage:   java LineCounts <file-names>");
			return;
		}

		for (String arg : args) {
			try (BufferedReader br = new BufferedReader(new FileReader(arg))) {

				System.out.println("file 1 ------------------------\n\n");
				String line = br.readLine();

				while (line != null) {
					System.out.println(line);
					line = br.readLine();
				}

			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}

		}
	}
}
