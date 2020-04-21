package bloomFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;

public class RunBloomFilterApp {
	static BufferedReader br = null;

	public static void main(String[] args) {
		/**
		 * List of known weak passwords Ensure the user is selecting a complex password.
		 * If the password exists in this list then the password will not be selected.
		 * Always indicate it is week (no False Negative). If password is Stronger, you
		 * may get False Positive saying the password is still weaker.. Which is Okay in
		 * this case.
		 *
		 */
		String fileLocation1 = "C:\\java\\jsamplesvk\\vksamples\\src\\bloomFilter\\alleged-gmail-passwords.txt";
		try {
			br = new BufferedReader(new FileReader(fileLocation1));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int counter = 3500000; // to use 2/3 to build bloom filter and use 1/3 to test the bloom filter.
		int initial_counter = counter;
		int numFalsePos = 0;
		String l = null;
		BloomFilter bf = new BloomFilter(167000000, 20);
		/**
		 * Larger Size of Bloom Filter will reduce the Number of // FalsePositives.
		 * BloomFilter(firstParameter, secondParameter) firstParameter is
		 * SizeOfBloomFilter, SecondParameter is NumberofHashFunctions) Increasing the
		 * Number of HashFunctions will impact #Collisions
		 */
		try {
			while ((l = br.readLine()) != null) {
				if (counter > initial_counter / 3) {
					bf.add(l);
				} else {
					if (bf.isPresent(l)) {
						numFalsePos++;
					}
				}
				counter--;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("total number of collisions while building a bloom filter are " + bf.collisionCount);
		System.out.println("Total number of false positives are " + numFalsePos);
		// System.out.println("False Positive Rate : " + ((numFalsePos /
		// bf.collisionCount) * 100));
		// False Positives should be very small.
	}

}

class BloomFilter {
	BitSet bitSetArrayBits;// BloomFilter array of bits. Use BitSet or booleanArray to save on size

	CustomHashFunction customHashFunction;
	int collisionCount = 0;
	int hashFunctionValue1, hashFunctionValue2; // hash function values
	int bloomFilterSize; // size of the bloom filter
	int numberOfHashFunctions; // total number of hash functions to work with bloom filter

	public BloomFilter(int bitSetArrayBitSize, int numberOfIndependentHashFunctions) {
		/***
		 * Constructor x indicate the size of bloom filter and y indicate the number of
		 * hash functions
		 */
		bitSetArrayBits = new BitSet(bitSetArrayBitSize);

		bitSetArrayBits.clear();
		// System.out.println("BitSet" + bitSetArrayBits);
		customHashFunction = new CustomHashFunction();
		bloomFilterSize = bitSetArrayBits.size();
		// System.out.println("BitSet Size : " + bloomFilterSize);
		numberOfHashFunctions = numberOfIndependentHashFunctions;
	}

	public boolean isPresent(String x) {
		/***
		 * Find Function.
		 *
		 * This function returns true is x is present and false if x is not present-
		 * according to bloom filter.
		 */
		hashFunctionValue1 = customHashFunction.generateHash1(x);
		hashFunctionValue2 = customHashFunction.generateHash2(x);
		// System.out.println("Hash 1 " + hash1);
		for (int i = 1; i <= numberOfHashFunctions; i++) {
			if (bitSetArrayBits.get(Math.abs((hashFunctionValue1 + i * hashFunctionValue2) % bloomFilterSize))) {
				continue;
			} else {
				return false; // Record does not exist in BitSetArray
			}
		}

		return true; // Record Exists in BitSetArray

	}

	public void add(String x) {
		/***
		 * Build the bloom filter by adding the elements to it
		 */
		if (isPresent(x)) {
			collisionCount++; // counting the collisions.
		}

		hashFunctionValue1 = customHashFunction.generateHash1(x);
		hashFunctionValue2 = customHashFunction.generateHash2(x);
		for (int i = 1; i <= numberOfHashFunctions; i++) {
			bitSetArrayBits.set(Math.abs((hashFunctionValue1 + i * hashFunctionValue2) % bloomFilterSize));

			// Creating Independent Set of Hash Values. for Each bit of BitSetArray
		}
	}

}

class CustomHashFunction {

	// typical hash function
	public int generateHash1(String x) {
		int someSeedValueAPrimeNumber = 31;
		String someRandomString = "somerandomstringvijay";
		int hashValue = 1;
		int hash = x.hashCode();
		for (int i = 0; i < someRandomString.length(); i++) {
			hashValue = (someSeedValueAPrimeNumber * hashValue) + someRandomString.charAt(i) + hash;
		}
		return hashValue;
	}

	public int generateHash2(String x) {
		int someSeedValueAPrimeNumber = 17;
		int hashValue = 1;
		String someRandomString = "anotherrandomstringhello";
		int hash = x.hashCode();
		for (int i = 0; i < someRandomString.length(); i++) {
			hashValue = (someSeedValueAPrimeNumber * hashValue) + someRandomString.charAt(i) + hash;
		}
		return hashValue;
	}

}
