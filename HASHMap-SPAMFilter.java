package oneTemp;

//Create a SPAM Filter, given a set of Known SPAM Phone numbers. 
//Uses HashMap to store and find/scan the numbers against the database. 

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapDemo {

	private static HashMap<Long, String> spamList = new HashMap<Long, String>();
	static {
		spamList.put(111L, "true"); // true means it is a SPAMmer.
		spamList.put(112L, "true");
		spamList.put(113L, "true");
		spamList.put(114L, "true");
		spamList.put(115L, "true");
		spamList.put(116L, "true");
	}

	public static String isSpam(Long phoneNumber) {
		String val = "";
		if (spamList.containsKey(phoneNumber)) {
			val = spamList.get(phoneNumber);
		} else {
			val = "false";
		}
		return val;
	}

	public static void main(String[] args) {
		ArrayList<Long> phoneNum = new ArrayList<Long>();
		phoneNum.add(111L);
		phoneNum.add(101L);
		phoneNum.add(105L);
		phoneNum.add(113L);
		phoneNum.add(118L);
		
		System.out.println("Phone Number : " + "Is It SPAM ?");

		for (Long currentPhoneItem : phoneNum) {
			System.out.println(currentPhoneItem + "          :   " + HashMapDemo.isSpam(currentPhoneItem));
		}

	}
}
