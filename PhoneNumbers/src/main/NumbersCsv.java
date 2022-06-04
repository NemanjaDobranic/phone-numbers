package main;

import java.io.File;

public class NumbersCsv {
	private static final String FILE_URL = "https://raw.githubusercontent.com/kkenan/datasets/master/phone_numbers_65535.csv";
	private static final String FILE_NAME = "\\phone_numbers.csv";
	private static NumbersCsv single_instance = null;

	private NumbersCsv() {
		File out = new File(System.getProperty("user.dir") + FILE_NAME);
		new Thread(new Download(FILE_URL, out)).start();
	}

	public static NumbersCsv getInstance() {
		if (single_instance == null)
			single_instance = new NumbersCsv();

		return single_instance;
	}
}
