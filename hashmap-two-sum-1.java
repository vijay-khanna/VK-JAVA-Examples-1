
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
// Take user inputs / file read. Two Sum to check how many pairs match the sum required. 

public class Source {
	static HashMap<Long, Integer> arrayNumberSum = new HashMap<Long, Integer>();

	static int getPairsCount(int arraySize, int targetSum, int[] array) {
		int countOfPairs = 0;
		int currentHashMapValue = 0;

		for (Long key : arrayNumberSum.keySet()) {
			// System.out.println(key);
			currentHashMapValue = arrayNumberSum.get(key);
			System.out.println("Key : " + key + " Value : " + currentHashMapValue);

			if (key == targetSum) {

				// System.out.println(currentHashMapValue);
				countOfPairs = currentHashMapValue;
			}
		}

		return countOfPairs;

	}

	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// //Open File for some time
		String location = "C:\\java\\jsamplesvk\\vksamples\\src\\arrayHashMap\\sampleInput.txt";

		BufferedReader bufferedFile = new BufferedReader(new FileReader(location));
		// // Use for File Input
		String line;
		int lineCounter = 1;
		int i, j, targetSum = 0;

		int arraySize = 0;
		int currentHashMapValue = 0;
		int handleFirstHashPut = 0;
		String[] stringArray = null;

		while ((line = bufferedFile.readLine()) != null) { // For use with File
			// Read...
			// while ((line = br.readLine()) != null) { // For use with User Provided Input
			if (lineCounter == 1) {
				arraySize = Integer.parseInt(line);

			} else if (lineCounter == 2) {

				stringArray = line.split(" ");
			} else if (lineCounter == 3) {

				targetSum = Integer.parseInt(line);
			}
			lineCounter++;

		}
		// System.out.println("Array Size : " + arraySize);

		int[] numberArray = new int[stringArray.length];
		for (i = 0; i <= stringArray.length - 1; i++) {
			numberArray[i] = Integer.parseInt(stringArray[i]);
			// System.out.print(numberArray[i] + " ");
			// System.out.print(Integer.parseInt(stringArray[i]));
		}
		// System.out.println(" ");
		// System.out.println("TargetSum : " + targetSum);
		long tempSum;

		// Printing Sum of All Pairs from the Array.
		for (i = 0; i <= numberArray.length - 1; i++) {
			for (j = 0; j <= numberArray.length - 1; j++) {
				tempSum = ((numberArray[i]) + (numberArray[j]));
				// System.out.println("i :" + i + " j : " + j + " ..i value : " + numberArray[i]
				// + " " + " j value : "
				// + numberArray[j] + " Sum : " + tempSum);
				// System.out.println(" tempSum =" + tempSum);

				arrayNumberSum.merge(tempSum, 1, Integer::sum);

				// System.out.println(tempSum + " " + (currentHashMapValue + 1));

				currentHashMapValue = arrayNumberSum.get(tempSum);

				System.out.println("Sumation = " + tempSum + " Current Hash Map Value :" + currentHashMapValue);

			}

		}

		int pairCount = getPairsCount(arraySize, targetSum, numberArray);
		System.out.println(pairCount);
		br.close();

		bufferedFile.close(); // Run for File Read..
	}

}
