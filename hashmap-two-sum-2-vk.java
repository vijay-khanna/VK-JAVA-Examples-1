package arrayHashMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Source {
	static HashMap<Long, Integer> arrayNumberSum = new HashMap<Long, Integer>();

	@SuppressWarnings("null")
	public static int getPairsCount(int arraySize, int targetSum, int[] array) {
		Arrays.sort(array);
		int i = 0;
		int j = arraySize - 1;
		int uniquePairs = 0;
		while (i < j) {
			int sum = array[i] + array[j];
			if (sum < targetSum) {
				i++;
			} else if (sum > targetSum) {
				j--;
			} else {
				uniquePairs++;
				i++;
				j--;
				while (i < j && array[i] == array[i - 1]) {
					i++;
				}
				while (i < j && array[j] == array[j + 1]) {
					j--;
				}
			}
		}
		return uniquePairs;
	}

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

		while ((line = bufferedFile.readLine()) != null) { // For use with File //
			// Read... //
			// while ((line = br.readLine()) != null) { // For use with User
			// Provided Input
			if (lineCounter == 1) {
				arraySize = Integer.parseInt(line);

			} else if (lineCounter == 2) {

				stringArray = line.split(" ");
			} else if (lineCounter == 3) {

				targetSum = Integer.parseInt(line);
			}
			lineCounter++;

		} // System.out.println("Array Size : " + arraySize);

		int[] numberArray = new int[stringArray.length];
		for (i = 0; i <= stringArray.length - 1; i++) {
			numberArray[i] = Integer.parseInt(stringArray[i]);
			System.out.print(numberArray[i] + " ");
		}

		System.out.println(arraySize + " " + targetSum + " " + numberArray);
		int pairCount = getPairsCount(arraySize, targetSum, numberArray);

		System.out.println(" Pair Count : " + pairCount);
		// System.out.println(pairCount);

		br.close();

		bufferedFile.close(); // Run for File Read..
	}

}
