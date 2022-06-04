package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PhoneBook {
	private static PhoneBook single_instance = null;
	Map<Integer, ArrayList<String>> book;

	private PhoneBook() throws FileNotFoundException {
		NumbersCsv ncsv = NumbersCsv.getInstance();
		Scanner scanner = new Scanner(ncsv.csvFile);
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			System.out.println(scanner.next() + "|");
		}

		System.out.println("Scanner loop is finished");
		scanner.close();
	}

	public static PhoneBook getInstance() {
		if (single_instance == null)
			try {
				single_instance = new PhoneBook();
			} catch (FileNotFoundException e) {
				System.out.println("Could not instantiate phone book, because CSV file is not downloaded.");
			}

		return single_instance;
	}

	/*
	 * Bice ovdje metode za regexr za proanalzenje
	 */

}
