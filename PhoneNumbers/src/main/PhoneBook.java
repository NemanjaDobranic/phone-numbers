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
		String plainNumber = searchedNumber.replaceAll("[^0-9]", "");
		ArrayList<PhoneNumber> list = new ArrayList<>();
		Iterator<PhoneNumber> it = this.book.iterator();

		while (it.hasNext()) {
			PhoneNumber pn = it.next();
			String digits = pn.phoneNumber.replaceAll("[^0-9]", "");
			if (digits.startsWith(plainNumber))
				list.add(pn);
		}

		Comparator<PhoneNumber> cmpLength = new Comparator<PhoneNumber>() {
			@Override
			public int compare(PhoneNumber pn1, PhoneNumber pn2) {
				Integer len1 = pn1.phoneNumber.length();
				Integer len2 = pn2.phoneNumber.length();

				return len1.compareTo(len2);
			}
		};

		Comparator<PhoneNumber> cmpFormat = new Comparator<PhoneNumber>() {
			@Override
			public int compare(PhoneNumber pn1, PhoneNumber pn2) {
				if (pn2.phoneNumber.startsWith(searchedNumber))
					return 1;

				if (pn1.phoneNumber.startsWith(searchedNumber))
					return -1;

				return 0;
			}
		};

		Collections.sort(list, cmpLength);
		Collections.sort(list, cmpFormat);
		
		if (list.size() > 10)
			return new ArrayList<PhoneNumber>(list.subList(0, 10));

		return list;

	}

}
