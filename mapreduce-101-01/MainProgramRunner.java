package mapReduce01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
Example for usage of hash functions in Map-Reduce programs.

Problem Statement: There are two text files present, each containing nearly 1000 words.
You need to generate the total frequency of each word present in both the files (total) and,
save it in a text file or print out the output.

The program must adhere to the following constraints.
1. Two files must be processed in parallel.
2. The application flow must be in the following way as shown in the image below.
3. You must have an additional layer of at least 3 aggregators.
* Aggregators are nothing but the functions which sum up the total count of words from both inputs.
4. Distribute the words to the 3 aggregators with the help of hash function.
5. The final aggregator appends all the results from the 3 aggregators and then outputs the results.

read the files in parallel using  multithreading.
**Thread 1 handles file 1 and thread 2 handles file 2.
**Every time the thread computes the hash function value for each word and accordingly push to the relevant aggregator.
**Hash function points to the aggregator to which the word is sent and the count is updated accordingly.

Once both the threads end, you need to collect the <word, count> from each aggregator in the layer 1,
 and need to send it to final aggregator so that every word along with its frequency is printed.

File1 -> Program -> Aggregator1 \
				  X	Aggregator2 ---> Aggregator  -> Output
File2 -> Program -> Aggregator3 /
 ## Aggregator2 Takes inputs from Program-File1 and Program-File2.

 */

import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class MainProgramRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String randomTextFileLocation1 = "C:\\java\\jsamplesvk\\vksamples\\src\\mapReduce01\\randomtext_1.txt";
		String randomTextFileLocation2 = "C:\\java\\jsamplesvk\\vksamples\\src\\mapReduce01\\randomtext_2.txt";

		new MapThreads(true, 3); // setting number of aggregators in the layer.

		MapThreads firstMapThreadForFirstFile = new MapThreads(randomTextFileLocation1);
		MapThreads secondMapThreadForSecondFile = new MapThreads(randomTextFileLocation2);

		Thread thread1ForFile1 = new Thread(firstMapThreadForFirstFile);
		Thread thread2ForFile2 = new Thread(secondMapThreadForSecondFile);
		thread1ForFile1.start();
		thread2ForFile2.start();

		/**
		 *
		 * // main function can wait in the following way // boolean thread1IsAlive =
		 * true; // boolean thread2IsAlive = true; // do { // if (thread1IsAlive &&
		 * !t1.isAlive()) { // thread1IsAlive = false; // System.out.println("Thread 1
		 * is dead."); // } // if (thread2IsAlive && !t2.isAlive()) { // thread2IsAlive
		 * = false; // System.out.println("Thread 2 is dead."); // } // // }
		 * while(thread1IsAlive || thread2IsAlive);
		 */
		try {
			thread1ForFile1.join();// join() allows one thread to wait until another thread completes execution

			thread2ForFile2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Aggregator finalAggregator = new Aggregator();
		int numOfAggregators = MapThreads.IntermediateAggregationlayer_1.size();
		System.out.println("# Aggregators : " + numOfAggregators);
		for (int i = 0; i < numOfAggregators; i++) {
			finalAggregator.wordCount.putAll(MapThreads.IntermediateAggregationlayer_1.get(i).wordCount);
			System.out.println(" Aggregator #" + i + " " + MapThreads.IntermediateAggregationlayer_1.get(i).wordCount);

		}

		Set<Entry<String, Integer>> hashSet = finalAggregator.wordCount.entrySet();

		for (Entry<String, Integer> e : hashSet) { // Printing Final Aggregator, Aggregated Values.
			System.out.println(e.getKey() + ": " + e.getValue());
		}

	}

}

class Aggregator {
	Map<String, Integer> wordCount = new HashMap<String, Integer>();

//Each Aggregator is HashMap to store count of Unique Words.
	// Increment the Word Count by one, or add new word with Count=1
	public void add(String aWord) {
		if (wordCount.containsKey(aWord)) {
			wordCount.replace(aWord, wordCount.get(aWord) + 1);
		} else {
			wordCount.put(aWord, 1);
		}
	}

}

class MapThreads implements Runnable {
	// IntermediateAggregationlayer_1 : Array List of all Aggregators.
	static List<Aggregator> IntermediateAggregationlayer_1 = new ArrayList<Aggregator>();
	BufferedReader bufferedReader_DiskFile = null;
	String eachLine;
	String line;

	// using constructor we create required number of aggregators only once.
	public MapThreads(boolean proceedCreationStep, int totalParallelAggregators_Layer1) {
		if (proceedCreationStep == true) {
			for (int currentArrayIndex = 0; currentArrayIndex < totalParallelAggregators_Layer1; currentArrayIndex++) {
				IntermediateAggregationlayer_1.add(new Aggregator());
			}
		}
	}

	public MapThreads(String randomText_FileLocation) {
		try {
			bufferedReader_DiskFile = new BufferedReader(new FileReader(randomText_FileLocation));

			// System.out.println(bufferedReader_DiskFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while ((eachLine = bufferedReader_DiskFile.readLine()) != null) {
				eachLine = eachLine.replaceAll("[.,]", "");
				StringTokenizer stringTokenizer = new StringTokenizer(eachLine, " ");
				while (stringTokenizer.hasMoreTokens()) {
					String eachWord = stringTokenizer.nextToken();
					// adding the word to aggregator indicated by hash function and updating the
					// count;
//					System.out.println(temp.hashCode());
					IntermediateAggregationlayer_1
							.get(Math.abs((eachWord.hashCode()) % IntermediateAggregationlayer_1.size())).add(eachWord);
					System.out.println("Each Word " + eachWord + " : " + Thread.currentThread().getName());

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
