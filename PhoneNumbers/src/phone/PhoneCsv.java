package phone;

import java.io.File;

import misc.Download;

public class PhoneCsv {
	private static final String FILE_URL = "https://raw.githubusercontent.com/kkenan/datasets/master/phone_numbers_65535.csv";
	private static final String FILE_NAME = "\\phone_numbers.csv";
	private static PhoneCsv single_instance = null;
	protected File csvFile;

	private PhoneCsv() {
		this.csvFile = new File(System.getProperty("user.dir") + FILE_NAME);
		if (!csvFile.exists() || !csvFile.isFile()) {
			Thread downloadThread = new Thread(new Download(FILE_URL, csvFile));
			downloadThread.start();
			try {
				downloadThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static PhoneCsv getInstance() {
		if (single_instance == null)
			single_instance = new PhoneCsv();

		return single_instance;
	}
}
