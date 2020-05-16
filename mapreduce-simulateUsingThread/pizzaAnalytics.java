package mapReduceFinalSubmission;

/*
 * Q3: Pizza Analytics..
 * Vijay Khanna
 * global counter object is accessible to all the map tasks.
 *
 * write an algorithm which reads the input dataset, cleans the input dataset i.e. skip the junk records and then performs the following tasks on the cleaned data:

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
import java.nio.file.Path;
import java.nio.file.Paths;

public class pizzaAnalytics implements Runnable {
	BufferedReader fileBufferedReader;
	private String fileToBeProcessedLocation;
	private GlobalCounters counters;

	public pizzaAnalytics(String fileToBeProcessedLocation, GlobalCounters counters) {
		this.fileToBeProcessedLocation = fileToBeProcessedLocation;
		this.counters = counters;
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
						counters.incrementByTotalRegularAllCheeseBurstBelow500(1);
					}

					// Check for Veg/Non-Veg Types
					if (ch_veg_nonVeg == 'Y') {
						// Veg Pizza
						counters.incrementByTotalVegPizzaSold(1);
					} else {
						// Non Veg Pizza
						counters.incrementByTotalNonVegPizzaSold(1);
					}
					char ch_size = line_array[6].charAt(0);

					// Check CheeseBurst
					char ch_CheeseBurst = line_array[5].charAt(0);

					if (ch_CheeseBurst == 'Y') {
						counters.incrementByTotalAllCheeseBurst(1);
						if (ch_size == 'R') {
							counters.incrementByTotalRegularCheeseBurst(1);
							// Increment for Regular CheeseBurst
						}
					}

					// Checking for Size
					switch (ch_size) {
					case 'M':
						counters.incrementByTotalSizeM(1);
						break;
					case 'R':
						counters.incrementByTotalSizeR(1);
						break;
					case 'L':
						counters.incrementByTotalSizeL(1);
						break;
					default:
						System.out.println(" Irregular Size");
					}

				}

			}

			cleanRecordsFileWriter.close();
			fileBufferedReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class GlobalCounters {
		// Storing Global Counters so these can be accessed by Threads as well as Main .
		public int TotalVegPizzaSold;
		public int TotalNonVegPizzaSold;
		public int TotalSizeM;
		public int TotalSizeR;
		public int TotalSizeL;
		public int TotalAllCheeseBurst;
		public int TotalRegularCheeseBurst;
		public int TotalRegularAllCheeseBurstBelow500;

		public void incrementByTotalVegPizzaSold(int counter) {
			TotalVegPizzaSold = counter + TotalVegPizzaSold;
		}

		public void incrementBy(int counter) {
			TotalVegPizzaSold = counter + TotalVegPizzaSold;
		}

		public void incrementByTotalAllCheeseBurst(int counter) {
			TotalAllCheeseBurst = counter + TotalAllCheeseBurst;
		}

		public void incrementByTotalNonVegPizzaSold(int counter) {
			TotalNonVegPizzaSold = counter + TotalNonVegPizzaSold;
		}

		public void incrementByTotalSizeM(int counter) {
			TotalSizeM = counter + TotalSizeM;
		}

		public void incrementByTotalSizeR(int counter) {
			TotalSizeR = counter + TotalSizeR;
		}

		public void incrementByTotalSizeL(int counter) {
			TotalSizeL = counter + TotalSizeL;
		}

		public void incrementByTotalRegularCheeseBurst(int counter) {
			TotalRegularCheeseBurst = counter + TotalRegularCheeseBurst;
		}

		public void incrementByTotalRegularAllCheeseBurstBelow500(int counter) {
			TotalRegularAllCheeseBurstBelow500 = counter + TotalRegularAllCheeseBurstBelow500;
		}

		public void setTotalVegPizzaSold(int totalVegPizzaSold) {
			TotalVegPizzaSold = totalVegPizzaSold;
		}

		public void setTotalNonVegPizzaSold(int totalNonVegPizzaSold) {
			TotalNonVegPizzaSold = totalNonVegPizzaSold;
		}

		public void setTotalSizeM(int totalSizeM) {
			TotalSizeM = totalSizeM;
		}

		public void setTotalSizeR(int totalSizeR) {
			TotalSizeR = totalSizeR;
		}

		public int getTotalVegPizzaSold() {
			return TotalVegPizzaSold;
		}

		public int getTotalNonVegPizzaSold() {
			return TotalNonVegPizzaSold;
		}

		public int getTotalSizeM() {
			return TotalSizeM;
		}

		public int getTotalSizeR() {
			return TotalSizeR;
		}

		public int getTotalSizeL() {
			return TotalSizeL;
		}

		public int getTotalAllCheeseBurst() {
			return TotalAllCheeseBurst;
		}

		public int getTotalRegularCheeseBurst() {
			return TotalRegularCheeseBurst;
		}

		public int getTotalRegularAllCheeseBurstBelow500() {
			return TotalRegularAllCheeseBurstBelow500;
		}

		public void setTotalSizeL(int totalSizeL) {
			TotalSizeL = totalSizeL;
		}

		public void setTotalAllCheeseBurst(int totalAllCheeseBurst) {
			TotalAllCheeseBurst = totalAllCheeseBurst;
		}

		public void setTotalRegularCheeseBurst(int totalRegularCheeseBurst) {
			TotalRegularCheeseBurst = totalRegularCheeseBurst;
		}

		public void setTotalRegularAllCheeseBurstBelow500(int totalRegularAllCheeseBurstBelow500) {
			TotalRegularAllCheeseBurstBelow500 = totalRegularAllCheeseBurstBelow500;
		}

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GlobalCounters counters = new GlobalCounters();
//Initialise all Counters.
		counters.setTotalVegPizzaSold(0);
		counters.setTotalAllCheeseBurst(0);
		counters.setTotalNonVegPizzaSold(0);
		counters.setTotalSizeM(0);
		counters.setTotalSizeR(0);
		counters.setTotalSizeL(0);
		counters.setTotalRegularCheeseBurst(0);
		counters.setTotalRegularAllCheeseBurstBelow500(0);

//Three files, each mapped to separate thread.
		String inputFile1 = "raw_input_file1.txt";
		String inputFile2 = "raw_input_file2.txt";
		String inputFile3 = "raw_input_file3.txt";
		String folderpath = "C:\\java\\jsamplesvk\\vksamples\\src\\mapReduceFinalSubmission\\";
		String file1Location = folderpath + inputFile1;
		String file2Location = folderpath + inputFile2;
		String file3Location = folderpath + inputFile3;

		pizzaAnalytics pizzaAnalyticsThread1 = new pizzaAnalytics(file1Location, counters);
		pizzaAnalytics pizzaAnalyticsThread2 = new pizzaAnalytics(file2Location, counters);
		pizzaAnalytics pizzaAnalyticsThread3 = new pizzaAnalytics(file3Location, counters);

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

		// Display Current Values//
		System.out.println("TotalVegPizzaSold : " + counters.getTotalVegPizzaSold());
		System.out.println("TotalAllCheeseBurst : " + counters.getTotalAllCheeseBurst());
		System.out.println("TotalNonVegPizzaSold : " + counters.getTotalNonVegPizzaSold());
		System.out.println("TotalSizeM : " + counters.getTotalSizeM());
		System.out.println("TotalSizeR : " + counters.getTotalSizeR());
		System.out.println("TotalSizeL : " + counters.getTotalSizeL());
		System.out.println("TotalRegularCheeseBurst : " + counters.getTotalRegularCheeseBurst());
		System.out.println("TotalRegularAllCheeseBurstBelow500 : " + counters.getTotalRegularAllCheeseBurstBelow500());

	}

}
