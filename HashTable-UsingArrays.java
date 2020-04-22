package hashTablesComplexOnes;
//Complex Hash Table, Hash Function Example. Using Array.

import java.util.Arrays;

public class HashFunction {

	static String[] theArray;
	int arraySize;
	int itemsInArray = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashFunction theHashFunction = new HashFunction(30);
		String[] elementsToAdd_SetOne = { "1", "5", "9", "13" };

		String[] elementsToAdd_SetTwo = { "100", "510", "170", "214", "268", "398", "235", "802", "900", "723", "699",
				"1", "16", "999", "890", "725", "998", "978", "988", "990", "989", "984", "320", "321", "400", "415",
				"450", "50", "660", "624" };

		// theHashFunction.hashFuncOne(elementsToAdd_SetOne, theHashFunction.theArray);

		theHashFunction.hashFuncTwo(elementsToAdd_SetTwo, theHashFunction.theArray);
		/// theHashFunction.hashFuncTwo(elementsToAdd_SetTwo, theHashFunction.theArray);

		theHashFunction.findKey("660");

		theHashFunction.displayTheHashTableArrayContent(theArray);
	}

	public String findKey(String key) {
		// Find the keys original hash key
		int arrayIndexHash = Integer.parseInt(key) % 29;
		while (theArray[arrayIndexHash] != "-1") {
			if (theArray[arrayIndexHash] == key) {
				// Found the key so return it
				System.out.println(key + " was found in index " + arrayIndexHash);
				return theArray[arrayIndexHash];
			}
			// Look in the next index
			++arrayIndexHash;
			// If we get to the end of the array go back to index 0
			arrayIndexHash %= arraySize;
		}
		// Couldn't locate the key
		return null;
	}

	public void displayTheHashTableArrayContent(String[] arrayToDisplay) {
		int increment = 0;
		for (int m = 0; m < 3; m++) {
			increment += 10;
			for (int n = 0; n < 71; n++) {
				System.out.print("-");
			}

			System.out.println();
			for (int n = increment - 10; n < increment; n++) {
				System.out.format("| %3s " + " ", n);
			}

			System.out.println("|");
			for (int n = 0; n < 71; n++) {
				System.out.print("-");
			}
			System.out.println();

			for (int n = increment - 10; n < increment; n++) {
				if (arrayToDisplay[n].equals("-1")) {
					System.out.print("|      ");
				} else {
					System.out.print(String.format("| %3s " + " ", arrayToDisplay[n]));
				}
			}

			System.out.println("|");

			for (int n = 0; n < 71; n++) {
				System.out.print("-");
			}
			System.out.println();

		}

	}

	public void hashFuncTwo(String[] stringsForArray, String[] theArray) {
		for (int n = 0; n < stringsForArray.length; n++) {
			String newElementVal = stringsForArray[n];
			// Create an index to store the value in by taking
			// the modulus
			int arrayIndex = Integer.parseInt(newElementVal) % 29;
			System.out.println("Modulus Index= " + arrayIndex + " for value " + newElementVal);
			// Cycle through the array until we find an empty space
			while (theArray[arrayIndex] != "-1") {

				System.out.println("Collision Detected in Index #" + arrayIndex + ". It already has a value : "
						+ theArray[arrayIndex] + ".. Try " + (++arrayIndex) + " Instead");
//Increment arrayIndex for next iteration in above loop itself
				// If we get to the end of the array go back to index 0
				arrayIndex %= arraySize;
			}
			theArray[arrayIndex] = newElementVal;
		}
	}

	// hashFunctionOne_StringElementValueAsHashCodeItSelf
	public void hashFuncOne(String[] stringsToBeAddedToArray, String[] theArray) {
		for (int n = 0; n < stringsToBeAddedToArray.length; n++) {
			String currentElementValue = stringsToBeAddedToArray[n];
			theArray[Integer.parseInt(currentElementValue)] = currentElementValue;
		}
	}

	HashFunction(int size) { // Constructor. Initialize array with all elements = -1
		arraySize = size;
		theArray = new String[size];
		Arrays.fill(theArray, "-1");

	}

}
