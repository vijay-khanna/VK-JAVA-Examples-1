package mapReduceFinalSubmission;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Question 1 : Read Three files into three threads, Simulate map only algorithm
 * and if at least one of the following fields is NULL: event_epoch_time,
 * user_id, device_id, user_agent. Use the write(key, value) method to generate
 * output from the map or reduce algorithm. The records in the output dataset
 * are known as JUNK RECORDS.
 *
 * @author vijay CSV : File Contents. 0 event_epoch_time
 *
 *         1 user_id
 *
 *         2 device_id
 *
 *         3 user_agent
 *
 *         4 pizza_name
 *
 *         5 isCheeseBurst
 *
 *         6 Size
 *
 *         7 AddedToppings(colon separated string)
 *
 *         8 Price
 *
 *         9 CouponCode
 *
 *         10 Order_Event
 *
 *         11 isVeg
 *
 *         " 1,515,507,894,000.00 ",xyz@gmail.com,DeviceId004,iPhone7Plus:IOS
 *         10.3.3:Safari,Veg
 *         Extravaganza,Y,M,NULL,500,CRAZYFRI,Delivered,Y,,,,,,,,,,,,,, "
 *         1,515,507,904,000.00
 *         ",aef@gmail.com,DeviceId005,SamsungGalaxyS6:Android 6.0
 *         Marshmallow:App,Pizza
 *         Mania,Y,M,NULL,200,NULL,Delivered,Y,,,,,,,,,,,,,, "
 *         1,515,507,874,000.00 ",,DeviceId0033,iPhone7Plus:IOS
 *         10.3.3:Safari,Farmhouse,Y,L,Chicken,600,NULL,Delivered,Y,,,,,,,,,,,,,,
 */

public class identifyJunkRecords implements Runnable {
	BufferedReader fileBufferedReader;
	private String fileToBeProcessedLocation;

	public identifyJunkRecords(String fileToBeProcessedLocation) {
		this.fileToBeProcessedLocation = fileToBeProcessedLocation;
	}

	@Override
	public void run() {
		// System.out.println("hello " + fileToBeProcessedLocation);
		String line;
		try {
			fileBufferedReader = new BufferedReader(new FileReader(fileToBeProcessedLocation));
			Path p = Paths.get(fileToBeProcessedLocation);
			Path folder = p.getParent();
			String JunkFileName;
			Path fileName = p.getFileName();
			JunkFileName = folder + "\\JunkRecords_For_" + fileName;
			FileWriter junkFileWriter = new FileWriter(JunkFileName);

			// System.out.println("Folder : " + folder + "File Name : " + fileName);

			// File file = ...;
			// boolean result = Files.deleteIfExists(file.toPath());

			// System.out.println(fileBufferedReader.readLine());
			while ((line = fileBufferedReader.readLine()) != null) {
				// process the line
				String[] line_array = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				// Ignoring "" string for , separation.

				// Check isempty or null for the first 4 fields. If yes, then save to a file.
				if (

				line_array[0] == null || line_array[0].isEmpty() || line_array[1] == null || line_array[1].isEmpty()
						|| line_array[2] == null || line_array[2].isEmpty() || line_array[3] == null
						|| line_array[3].isEmpty()) {
					// System.out.println("*****JUNK RECORD " + fileToBeProcessedLocation + " " +
					// line_array[0] + " - "
					// + line_array[1] + " - " + line_array[2] + " - " + line_array[3] + " - " +
					// line_array[4]
					// + " - " + line_array[5]);
					junkFileWriter.write("Junk Record : ," + fileToBeProcessedLocation + "," + line + "\n");
				}

			}

			junkFileWriter.close();
// check if either of these is null or empty.. and then moving them to Junk File.
			// event_epoch_time, user_id, device_id, user_agent

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void mergeFiles(String folderPath, String junkRecordsFile1, String junkRecordsFile2,
			String junkRecordsFile3) throws IOException {
		String line;
		FileWriter junkFileConsolidationWriter = new FileWriter(folderPath + "\\JunkFileConsolidatedRecords.txt");
		try {
			BufferedReader fileBufferedReader1 = new BufferedReader(new FileReader(junkRecordsFile1));

			while ((line = fileBufferedReader1.readLine()) != null) {

				System.out.println(line);
				junkFileConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader fileBufferedReader2 = new BufferedReader(new FileReader(junkRecordsFile2));

			while ((line = fileBufferedReader2.readLine()) != null) {

				System.out.println(line);
				junkFileConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader fileBufferedReader3 = new BufferedReader(new FileReader(junkRecordsFile3));

			while ((line = fileBufferedReader3.readLine()) != null) {

				System.out.println(line);
				junkFileConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		junkFileConsolidationWriter.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inputFile1 = "input_file1.txt";
		String inputFile2 = "input_file2.txt";
		String inputFile3 = "input_file3.txt";
		String folderpath = "C:\\java\\jsamplesvk\\vksamples\\src\\mapReduceFinalSubmission\\";
		String file1Location = folderpath + inputFile1;
		String file2Location = folderpath + inputFile2;
		String file3Location = folderpath + inputFile3;

		identifyJunkRecords identifyJunkRecordsThread1 = new identifyJunkRecords(file1Location);
		identifyJunkRecords identifyJunkRecordsThread2 = new identifyJunkRecords(file2Location);
		identifyJunkRecords identifyJunkRecordsThread3 = new identifyJunkRecords(file3Location);

		new Thread(identifyJunkRecordsThread1).start();
		new Thread(identifyJunkRecordsThread2).start();
		new Thread(identifyJunkRecordsThread3).start();

		String junkRecordsFile1 = folderpath + "\\JunkRecords_For_" + inputFile1;
		String junkRecordsFile2 = folderpath + "\\JunkRecords_For_" + inputFile2;
		String junkRecordsFile3 = folderpath + "\\JunkRecords_For_" + inputFile3;

		mergeFiles(folderpath, junkRecordsFile1, junkRecordsFile2, junkRecordsFile3);

	}
}
