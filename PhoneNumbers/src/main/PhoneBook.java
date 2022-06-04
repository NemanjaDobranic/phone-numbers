package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PhoneBook {
	private static PhoneBook single_instance = null;
	Map<Integer, PhoneNumber> book;

	private PhoneBook() throws FileNotFoundException {
		NumbersCsv ncsv = NumbersCsv.getInstance();
		Scanner scanner = new Scanner(ncsv.csvFile);

		int index = 0;
		this.book = new HashMap<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] row = line.split(",");
			this.book.put(index++, new PhoneNumber(row[0], row[1]));
		}

		for (Map.Entry<Integer, PhoneNumber> entry : this.book.entrySet()) {
			System.out.println("Name: " + entry.getValue().name + " | Phone number: " + entry.getValue().number);
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
