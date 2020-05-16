package mapReduceFinalSubmission;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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
		String key;
		String value;

		try {
			fileBufferedReader = new BufferedReader(new FileReader(fileToBeProcessedLocation));
			Path p = Paths.get(fileToBeProcessedLocation);
			Path folder = p.getParent();
			String JunkFileName;

			Path fileName = p.getFileName();
			JunkFileName = folder + "\\JunkRecords_For_" + fileName;
			String cleanRecordsFileName = folder + "\\CleanRecords_From_" + fileName;
			FileWriter junkFileWriter = new FileWriter(JunkFileName);
			FileWriter cleanRecordsFileWriter = new FileWriter(cleanRecordsFileName);
			// System.out.println(cleanRecordsFileName);

			String userAgentFile = folder + "\\UserAgent_Records_For_" + fileName;
			FileWriter userAgentRecordsFileWriter = new FileWriter(userAgentFile);
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

					junkFileWriter.write("Junk Record : ," + fileToBeProcessedLocation + "," + line + "\n");
				} else {
					cleanRecordsFileWriter.write(line + "\n");
					key = line_array[1];
					value = "Test";
					// {"firstName": "John", "lastName": "Doe"}
					String[] user_agent = line_array[3].split(":");

					userAgentRecordsFileWriter.write("{\"user_id\": \"" + key + "\",\"OS Version\":\"" + user_agent[1]
							+ "\"" + ",\"Platform\": \"" + user_agent[2] + "\"}" + "\n");
				}

			}

			junkFileWriter.close();
			cleanRecordsFileWriter.close();
			fileBufferedReader.close();
			userAgentRecordsFileWriter.close();

// check if either of these is null or empty.. and then moving them to Junk File.
			// event_epoch_time, user_id, device_id, user_agent

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static identifyJunkRecords getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void mergeJunkRecordFiles(String folderPath, String junkRecordsFile1, String junkRecordsFile2,
			String junkRecordsFile3) throws IOException {
		String line;
		FileWriter junkFileConsolidationWriter = new FileWriter(folderPath + "\\ConsolidatedRecords_Junk.txt");
		try {
			BufferedReader fileBufferedReader1 = new BufferedReader(new FileReader(junkRecordsFile1));

			while ((line = fileBufferedReader1.readLine()) != null) {

				// System.out.println(line);
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

				// System.out.println(line);
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

				// System.out.println(line);
				junkFileConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		junkFileConsolidationWriter.close();
		Path fileToDeletePath1 = Paths.get(junkRecordsFile1);
		Files.delete(fileToDeletePath1);
		Path fileToDeletePath2 = Paths.get(junkRecordsFile2);
		Files.delete(fileToDeletePath2);
		Path fileToDeletePath3 = Paths.get(junkRecordsFile3);
		Files.delete(fileToDeletePath3);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String inputFile1 = "raw_input_file1.txt";
		String inputFile2 = "raw_input_file2.txt";
		String inputFile3 = "raw_input_file3.txt";
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mergeJunkRecordFiles(folderpath, junkRecordsFile1, junkRecordsFile2, junkRecordsFile3);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mergeUserAgentRecordFiles(folderpath, inputFile1, inputFile2, inputFile3);

	}

	public static void mergeUserAgentRecordFiles(String folderPath, String userAgentFile1, String userAgentFile2,
			String userAgentFile3) throws IOException {
		String line;
		String userAgentRecordsFile1 = folderPath + "\\UserAgent_Records_For_" + userAgentFile1;
		String userAgentRecordsFile2 = folderPath + "\\UserAgent_Records_For_" + userAgentFile2;
		String userAgentRecordsFile3 = folderPath + "\\UserAgent_Records_For_" + userAgentFile3;
		FileWriter userAgentConsolidationWriter = new FileWriter(folderPath + "\\ConsolidatedRecords_UserAgent.txt");

		try {
			BufferedReader fileBufferedReader1 = new BufferedReader(new FileReader(userAgentRecordsFile1));

			while ((line = fileBufferedReader1.readLine()) != null) {

				// System.out.println(line);
				userAgentConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader fileBufferedReader2 = new BufferedReader(new FileReader(userAgentRecordsFile2));

			while ((line = fileBufferedReader2.readLine()) != null) {

				// System.out.println(line);
				userAgentConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader fileBufferedReader3 = new BufferedReader(new FileReader(userAgentRecordsFile3));

			while ((line = fileBufferedReader3.readLine()) != null) {

				// System.out.println(line);
				userAgentConsolidationWriter.write(line + "\n");
			}
			fileBufferedReader3.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userAgentConsolidationWriter.close();

		Path fileToDeletePath1 = Paths.get(userAgentRecordsFile1);
		Files.delete(fileToDeletePath1);

		Path fileToDeletePath2 = Paths.get(userAgentRecordsFile2);
		Files.delete(fileToDeletePath2);

		Path fileToDeletePath3 = Paths.get(userAgentRecordsFile3);
		Files.delete(fileToDeletePath3);

	}

}
