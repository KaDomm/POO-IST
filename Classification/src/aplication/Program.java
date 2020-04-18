package aplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program {

	public static void main(String[] args) {
		
		
		final String file1 = args[0];
		final String file2 = args.length > 1 ? args[1] : null ;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file1))){
			
			System.out.println("file 1 ------------------------\n\n");
			String line = br.readLine();
			
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
	
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(file2))){
			
			System.out.println("file 2 ------------------------\n\n");
			
			String line = br.readLine();
			
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
	
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
