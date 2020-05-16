package mapReduceFinalSubmission;

/*
 * Q4: Pizza Analytics : NO Global Counter to be used.
 * Vijay Khanna
 *
 * * write an algorithm which reads the input dataset, cleans the input dataset i.e. skip the junk records and then performs the following tasks on the cleaned data:

a.    Find out the number of veg and non-veg pizzas sold.
b.    Find out the size wise distribution of pizzas sold.
c.    Find out how many cheese burst pizzas were sold.
d.    Find out how many regular cheese burst pizzas were sold.
e.    Find out the number of cheese burst pizzas whose cost is below Rs 500.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mapReduceFinalSubmission.pizzaAnalytics.GlobalCounters;

public class pizzaMapReduceAnalyticsNoGlobalVariables implements Runnable {
	BufferedReader fileBufferedReader;
	private String fileToBeProcessedLocation;
	private GlobalCounters counters;

	public pizzaMapReduceAnalyticsNoGlobalVariables(String fileToBeProcessedLocation) {
		this.fileToBeProcessedLocation = fileToBeProcessedLocation;

	}

	@Override
	public void run() {
		// System.out.println("hello " + fileToBeProcessedLocation);
		String line;

		try {
			fileBufferedReader = new BufferedReader(new FileReader(fileToBeProcessedLocation));
			// Each Thread will open a Separate File, File name+ Path is passed from Main.
			Path p = Paths.get(fileToBeProcessedLocation);
			Path folder = p.getParent(); // get parent folder name, to create a new file for consolidated records.
			Path fileName = p.getFileName();

			String cleanRecordsFileName = folder + "\\CleanRecords_From_" + fileName; // match the cleaned file to input
																						// file with name modifier.
//Creating Key-Value Pair Files for Each Variable.. Later these will be merged.

			String File_TotalVegPizzaSold = folder + "\\MapCounters_TotalVegPizzaSold_" + fileName;
			String File_TotalNonVegPizzaSold = folder + "\\MapCounters_TotalNonVegPizzaSold_" + fileName;
			String File_TotalAllCheeseBurst = folder + "\\MapCounters_TotalAllCheeseBurst_" + fileName;
			String File_TotalRegularCheeseBurst = folder + "\\MapCounters_TotalRegularCheeseBurst_" + fileName;
			String File_TotalRegularAllCheeseBurstBelow500 = folder
					+ "\\MapCounters_TotalRegularAllCheeseBurstBelow500_" + fileName;
			String File_TotalSizeM = folder + "\\MapCounters_TotalSizeM_" + fileName;
			String File_TotalSizeR = folder + "\\MapCounters_TotalSizeR_" + fileName;
			String File_TotalSizeL = folder + "\\MapCounters_TotalSizeL_" + fileName;

			FileWriter FileWriter_TotalVegPizzaSold = new FileWriter(File_TotalVegPizzaSold);
			FileWriter FileWriter_TotalNonVegPizzaSold = new FileWriter(File_TotalNonVegPizzaSold);
			FileWriter FileWriter_TotalAllCheeseBurst = new FileWriter(File_TotalAllCheeseBurst);
			FileWriter FileWriter_TotalRegularCheeseBurst = new FileWriter(File_TotalRegularCheeseBurst);
			FileWriter FileWriter_TotalRegularAllCheeseBurstBelow500 = new FileWriter(
					File_TotalRegularAllCheeseBurstBelow500);

			FileWriter FileWriter_TotalSizeM = new FileWriter(File_TotalSizeM);
			FileWriter FileWriter_TotalSizeR = new FileWriter(File_TotalSizeR);
			FileWriter FileWriter_TotalSizeL = new FileWriter(File_TotalSizeL);

			FileWriter cleanRecordsFileWriter = new FileWriter(cleanRecordsFileName);

			// loop through the input raw file..
			while ((line = fileBufferedReader.readLine()) != null) {

				String[] line_array = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				// Ignoring "" string for , separation.
				// Split the line into array, so each field can be examined.

				// Check isempty or null for the first 4 fields. If yes, then save to a file.
				// Only clean records will be considered for calculations.
				if (line_array[0] == null || line_array[0].isEmpty() || line_array[1] == null || line_array[1].isEmpty()
						|| line_array[2] == null || line_array[2].isEmpty() || line_array[3] == null
						|| line_array[3].isEmpty()) {
					// Do Nothing in this case, This is a Junk Record..optionally can save these to
					// a text file for further analysis, to rectify the root-cause.

				} else {
					cleanRecordsFileWriter.write(line + "\n");
					char ch_veg_nonVeg = line_array[11].charAt(0); // Need to convert to char, as comparison is not
																	// working with String.

					// Counting for price > 500..
					// increment the specific counters based on condition match..
					int pizza_price = Integer.parseInt(line_array[8]);
					if (pizza_price < 500) {

						FileWriter_TotalRegularAllCheeseBurstBelow500.write("1" + "\n");

					}

					// Check for Veg/Non-Veg Types
					if (ch_veg_nonVeg == 'Y') {
						// Veg Pizza
						FileWriter_TotalVegPizzaSold.write("1" + "\n");

					} else {
						// Non Veg Pizza

						FileWriter_TotalNonVegPizzaSold.write("1" + "\n");
					}
					char ch_size = line_array[6].charAt(0);

					// Check CheeseBurst
					char ch_CheeseBurst = line_array[5].charAt(0);

					if (ch_CheeseBurst == 'Y') {

						FileWriter_TotalAllCheeseBurst.write("1" + "\n");
						if (ch_size == 'R') {

							FileWriter_TotalRegularCheeseBurst.write("1" + "\n");
						}
					}

					// Checking for Size
					switch (ch_size) {
					case 'M':

						FileWriter_TotalSizeM.write("1" + "\n");
						break;
					case 'R':
						FileWriter_TotalSizeR.write("1" + "\n");
						break;
					case 'L':
						FileWriter_TotalSizeL.write("1" + "\n");
						break;
					default:
						System.out.println(" Irregular Size");
					}

				}

			}

			cleanRecordsFileWriter.close();
			fileBufferedReader.close();

			FileWriter_TotalVegPizzaSold.close();
			FileWriter_TotalNonVegPizzaSold.close();
			FileWriter_TotalRegularCheeseBurst.close();
			FileWriter_TotalAllCheeseBurst.close();
			FileWriter_TotalRegularAllCheeseBurstBelow500.close();
			FileWriter_TotalSizeM.close();
			FileWriter_TotalSizeR.close();
			FileWriter_TotalSizeL.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void reducer(String folder) throws IOException {
		int TotalVegPizzaSold = 0;
		int TotalNonVegPizzaSold = 0;
		int TotalSizeM = 0;
		int TotalSizeR = 0;
		int TotalSizeL = 0;
		int TotalAllCheeseBurst = 0;
		int TotalRegularCheeseBurst = 0;
		int TotalRegularAllCheeseBurstBelow500 = 0;
		String line;
		int i;
		for (i = 1; i <= 3; i++) {
			// Master loop through the three files.
			String File_TotalVegPizzaSold = folder + "\\MapCounters_TotalVegPizzaSold_raw_input_file" + i + ".txt";
			String File_TotalNonVegPizzaSold = folder + "\\MapCounters_TotalNonVegPizzaSold_raw_input_file" + i
					+ ".txt";
			String File_TotalAllCheeseBurst = folder + "\\MapCounters_TotalAllCheeseBurst_raw_input_file" + i + ".txt";
			String File_TotalRegularCheeseBurst = folder + "\\MapCounters_TotalRegularCheeseBurst_raw_input_file" + i
					+ ".txt";
			String File_TotalRegularAllCheeseBurstBelow500 = folder
					+ "\\MapCounters_TotalRegularAllCheeseBurstBelow500_raw_input_file" + i + ".txt";
			String File_TotalSizeM = folder + "\\MapCounters_TotalSizeM_raw_input_file" + i + ".txt";
			String File_TotalSizeR = folder + "\\MapCounters_TotalSizeR_raw_input_file" + i + ".txt";
			String File_TotalSizeL = folder + "\\MapCounters_TotalSizeL_raw_input_file" + i + ".txt";

			BufferedReader FileReader_TotalVegPizzaSold = new BufferedReader(new FileReader(File_TotalVegPizzaSold));

			BufferedReader FileReader_TotalNonVegPizzaSold = new BufferedReader(
					new FileReader(File_TotalNonVegPizzaSold));
			BufferedReader FileReader_TotalAllCheeseBurst = new BufferedReader(
					new FileReader(File_TotalAllCheeseBurst));
			BufferedReader FileReader_TotalRegularCheeseBurst = new BufferedReader(
					new FileReader(File_TotalRegularCheeseBurst));
			BufferedReader FileReaderTotalRegularAllCheeseBurstBelow500 = new BufferedReader(
					new FileReader(File_TotalRegularAllCheeseBurstBelow500));

			BufferedReader FileReader_TotalSizeM = new BufferedReader(new FileReader(File_TotalSizeM));
			BufferedReader FileReader_TotalSizeR = new BufferedReader(new FileReader(File_TotalSizeR));
			BufferedReader FileReader_TotalSizeL = new BufferedReader(new FileReader(File_TotalSizeL));

			while ((line = FileReader_TotalVegPizzaSold.readLine()) != null) {
				TotalVegPizzaSold = TotalVegPizzaSold + 1;
			}

			while ((line = FileReader_TotalNonVegPizzaSold.readLine()) != null) {
				TotalNonVegPizzaSold = TotalNonVegPizzaSold + 1;
			}

			while ((line = FileReader_TotalAllCheeseBurst.readLine()) != null) {
				TotalAllCheeseBurst = TotalAllCheeseBurst + 1;
			}

			while ((line = FileReader_TotalRegularCheeseBurst.readLine()) != null) {
				TotalRegularCheeseBurst = TotalRegularCheeseBurst + 1;
			}

			while ((line = FileReaderTotalRegularAllCheeseBurstBelow500.readLine()) != null) {
				TotalRegularAllCheeseBurstBelow500 = TotalRegularAllCheeseBurstBelow500 + 1;
			}

			while ((line = FileReader_TotalSizeM.readLine()) != null) {
				TotalSizeM = TotalSizeM + 1;
			}

			while ((line = FileReader_TotalSizeR.readLine()) != null) {
				TotalSizeR = TotalSizeR + 1;
			}

			while ((line = FileReader_TotalSizeL.readLine()) != null) {
				TotalSizeL = TotalSizeL + 1;
			}

			FileReader_TotalVegPizzaSold.close();
			FileReader_TotalNonVegPizzaSold.close();
			FileReader_TotalRegularCheeseBurst.close();
			FileReader_TotalAllCheeseBurst.close();
			FileReaderTotalRegularAllCheeseBurstBelow500.close();
			FileReader_TotalSizeM.close();
			FileReader_TotalSizeR.close();
			FileReader_TotalSizeL.close();

			Path fileToDeletePath = Paths.get(File_TotalVegPizzaSold);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalNonVegPizzaSold);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalRegularCheeseBurst);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalAllCheeseBurst);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalRegularAllCheeseBurstBelow500);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalSizeR);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalSizeM);
			Files.delete(fileToDeletePath);

			fileToDeletePath = Paths.get(File_TotalSizeL);
			Files.delete(fileToDeletePath);

		}

		System.out.println("TotalVegPizzaSold : " + TotalVegPizzaSold);
		System.out.println("TotalAllCheeseBurst : " + TotalAllCheeseBurst);
		System.out.println("TotalNonVegPizzaSold : " + TotalNonVegPizzaSold);
		System.out.println("TotalSizeM : " + TotalSizeM);
		System.out.println("TotalSizeR : " + TotalSizeR);
		System.out.println("TotalSizeL : " + TotalSizeL);
		System.out.println("TotalRegularCheeseBurst : " + TotalRegularCheeseBurst);
		System.out.println("TotalRegularAllCheeseBurstBelow500 : " + TotalRegularAllCheeseBurstBelow500);

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

//Three files, each mapped to separate thread.
		String inputFile1 = "raw_input_file1.txt";
		String inputFile2 = "raw_input_file2.txt";
		String inputFile3 = "raw_input_file3.txt";
		String folderpath = "C:\\java\\jsamplesvk\\vksamples\\src\\mapReduceFinalSubmission\\";
		String file1Location = folderpath + inputFile1;
		String file2Location = folderpath + inputFile2;
		String file3Location = folderpath + inputFile3;

		pizzaMapReduceAnalyticsNoGlobalVariables pizzaAnalyticsThread1 = new pizzaMapReduceAnalyticsNoGlobalVariables(
				file1Location);
		pizzaMapReduceAnalyticsNoGlobalVariables pizzaAnalyticsThread2 = new pizzaMapReduceAnalyticsNoGlobalVariables(
				file2Location);
		pizzaMapReduceAnalyticsNoGlobalVariables pizzaAnalyticsThread3 = new pizzaMapReduceAnalyticsNoGlobalVariables(
				file3Location);

		new Thread(pizzaAnalyticsThread1).start();
		new Thread(pizzaAnalyticsThread2).start();
		new Thread(pizzaAnalyticsThread3).start();

		try {
			Thread.sleep(6000);
			// Sleep is required else the next block which publishes final results shows as
			// Zero, it executes immediately in parallel to the Map-Threads..
			// Need to wait for the Mapper Threads to finish, before pulling final data from
			// Global Counters.

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		// Aggregate various Map-Outputs and Display Current Values//
		reducer(folderpath);

	}

}
