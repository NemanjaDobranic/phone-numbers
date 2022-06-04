package main;

import java.io.File;

public class NumbersCsv {
	private static final String FILE_URL = "https://raw.githubusercontent.com/kkenan/datasets/master/phone_numbers_65535.csv";
	private static final String FILE_NAME = "\\phone_numbers.csv";
	private static NumbersCsv single_instance = null;
	protected File csvFile;

	private NumbersCsv() {
		this.csvFile = new File(System.getProperty("user.dir") + FILE_NAME);
		Thread downloadThread = new Thread(new Download(FILE_URL, csvFile));
		downloadThread.start();
		try {
			downloadThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static NumbersCsv getInstance() {
		if (single_instance == null)
			single_instance = new NumbersCsv();

		return single_instance;
	}
}
