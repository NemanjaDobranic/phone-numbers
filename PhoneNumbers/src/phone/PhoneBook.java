package phone;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class PhoneBook {
	private static PhoneBook single_instance = null;
	ArrayList<Phone> book;

	private PhoneBook() throws FileNotFoundException {
		PhoneCsv ncsv = PhoneCsv.getInstance();
		Scanner scanner = new Scanner(ncsv.csvFile);
		this.book = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] row = line.split(",");

			this.book.add(new Phone(row[0], row[1]));
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

	public ArrayList<Phone> autocomplete(String searchedNumber) {
		String plainNumber = searchedNumber.replaceAll("[^0-9]", "");
		ArrayList<Phone> list = new ArrayList<>();
		Iterator<Phone> it = this.book.iterator();

		while (it.hasNext()) {
			Phone pn = it.next();
			String digits = pn.phoneNumber.replaceAll("[^0-9]", "");
			if (digits.startsWith(plainNumber))
				list.add(pn);
		}

		Comparator<Phone> cmpLength = new Comparator<Phone>() {
			@Override
			public int compare(Phone pn1, Phone pn2) {
				Integer len1 = pn1.phoneNumber.length();
				Integer len2 = pn2.phoneNumber.length();

				return len1.compareTo(len2);
			}
		};

		Comparator<Phone> cmpFormat = new Comparator<Phone>() {
			@Override
			public int compare(Phone pn1, Phone pn2) {
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
			return new ArrayList<Phone>(list.subList(0, 10));

		return list;

	}

}
