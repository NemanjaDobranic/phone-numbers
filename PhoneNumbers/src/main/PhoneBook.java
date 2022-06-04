package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class PhoneBook {
	private static PhoneBook single_instance = null;
	ArrayList<PhoneNumber> book;

	private PhoneBook() throws FileNotFoundException {
		NumbersCsv ncsv = NumbersCsv.getInstance();
		Scanner scanner = new Scanner(ncsv.csvFile);
		this.book = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] row = line.split(",");

			this.book.add(new PhoneNumber(row[0], row[1]));
		}
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

	public ArrayList<PhoneNumber> autocomplete(String searchedNumber) {
		searchedNumber = searchedNumber.replaceAll("[^0-9]", "");
		ArrayList<PhoneNumber> list = new ArrayList<>();
		Iterator<PhoneNumber> it = this.book.iterator();

		while (it.hasNext()) {
			PhoneNumber pn = it.next();
			String digits = pn.number.replaceAll("[^0-9]", "");
			if (digits.startsWith(searchedNumber))
				list.add(pn);
		}

		Collections.sort(list, new Comparator<PhoneNumber>() {
			@Override
			public int compare(PhoneNumber pn1, PhoneNumber pn2) {
				int len1 = pn1.number.length();
				int len2 = pn2.number.length();

				if (len1 == len2)
					return 0;
				else if (len1 > len2)
					return 1;
				else
					return -1;
			}
		});
		
		//Ovdje treba odamh u json pretoviri da se ne muci covjek

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).number);
		}

		return null;

	}

}
