package pg.module.two.PhoneBook;

//Implement Address Book using Sorted Arrays. 

class Record {
	String name; // user contact name
	long number;
}

class contactList {
	Record[] contactRecordsList; // Array of type Record
	int currentLastRecordInArray = -1; // Which is final Record Index in phone book

	static final int maxContactRecords = 3; // Some Limit of Maximum Records in PhoneBook

	public contactList() { // Constructor Address Book.
		contactRecordsList = new Record[maxContactRecords]; // Creates a new Array list with #Max Records
	}

	int findIndex(String newNameToBeInsertedToList, int start, int end) { // Implements Binary Search Algorithm.
																			// Simplified algo of findBestIndex
		// Called as findIndex(toDeleteContactName, 0, currentLastRecordInArray);
		// low = start = 0 to start with.
		// high = end = currentLastRecordInArray to start with.
		int mid = -1;
		int low = start;
		int high = end;

		while (low <= high) {
			mid = (low + high) / 2;
			if (contactRecordsList[mid].name.compareTo(newNameToBeInsertedToList) < 0) {
				low = mid + 1;
			} else if (contactRecordsList[mid].name.compareTo(newNameToBeInsertedToList) > 0) {
				high = mid - 1;
			} else
				break;
		}
		if (low > high)
			return -1; // If the name Does Not exist in Array
		else
			return mid; // Return the Index of the Name which exist in Index.
	}

	int findBestIndex(String newNameToBeInsertedToList, int left, int right) { // Implements Binary Search Algorithm
// passed as findBestIndex(newContactNameEnteredByUser, 0, currentLastRecordInArray);
		if (left > right) { // 0 > -1, when there are Zero Records..currentLastRecordInArray is default =
							// -1.
			// System.out.println("Seems First Record....left : " + left + " Right : " +
			// right);
			return 0; // If The Phone Book is Empty, Start inserting new Item-Contacts from Index Zero
						// in Array.
		}
		// now assuming the Array has at least one record here on.
		int mid = -1;

		// Start Binary Approximation Algorithm. Binary Search.
		// Compare with existing items, and find the position Index in array where the
		// new item can be placed to retain Sorting Order

		while (left <= right) { // left will be always be default Zero, right = currentLastRecordInArray
//loop will end when left is equal or very close to right . i.e. Segment has shrunk to 1 digit value. 

			mid = (left + right) / 2; // Finding mid point of extreme left segment and right most element Index.

			if (contactRecordsList[mid].name.compareTo(newNameToBeInsertedToList) > 0) {
				right = mid - 1; // Dictionary like comparison. If newContact < CurrentMidContact, choose left
									// segment. Retain leftmost
									// marker to before value
			} else if (contactRecordsList[mid].name.compareTo(newNameToBeInsertedToList) < 0) {
				left = mid + 1; // If CurrentMidContact < newContact , choose right Segment. Retain Rightmost
								// Marker to before value.
			} else
				break;

		}
		/*
		 * what is mid elements comparison, compared to new element. return the mid
		 * index or mid +1
		 * 
		 */// if current mid element is > new contactName, then pass mid, else (if current
			// mid name is < = newContact) mid+1.
		return (contactRecordsList[mid].name.compareTo(newNameToBeInsertedToList) > 0) ? mid : mid + 1;
	}

	public void addNewContact(String newContactNameEnteredByUser, Long newContactNumberEnteredByUser) {
		System.out.println("Method. : addNewContact");

		if (currentLastRecordInArray < maxContactRecords - 1) { // Check if the System Phonebook size is not going to
																// cross the limit due to this addition.
			Record newItem = new Record();
			newItem.name = newContactNameEnteredByUser;
			newItem.number = newContactNumberEnteredByUser;
			int bestLocationIndexInArrayForNewItem = findBestIndex(newContactNameEnteredByUser, 0,
					currentLastRecordInArray);
			// Find the position best fit for new name-item to ensure sorted array.

			for (int i = currentLastRecordInArray; i >= bestLocationIndexInArrayForNewItem; i--) {
				contactRecordsList[i + 1] = contactRecordsList[i];
				// moving the records ahead by one position in array, from the best index
				// location onwards. Creating space for new item to be added
			}
			contactRecordsList[bestLocationIndexInArrayForNewItem] = newItem; // Setting bestIndex to new Item.
			currentLastRecordInArray++;
			System.out.println("Successfully Added new contact");

		} else
			System.out.println("Phonebook is full, at MaxCapacity.. Need to delete a contact to proceed");

	}

	public void deleteContact(String toDeleteContactName) {
		System.out.println("Method. : deleteContact");
		int LocationIndexInArrayForItem = findIndex(toDeleteContactName, 0, currentLastRecordInArray);
		if (LocationIndexInArrayForItem == -1) {
			System.out.println("No such Item Exists");

		} else {
			for (int i = LocationIndexInArrayForItem; i < currentLastRecordInArray; i++) {
				contactRecordsList[i] = contactRecordsList[i + 1]; // Move down(left) items in Array by one counter
			}
			contactRecordsList[currentLastRecordInArray] = null; // Remove garbage value at last Array position.
			currentLastRecordInArray--; // reduce Last count, due to deletion of one record.
			System.out.println("Contact Deleted : " + toDeleteContactName);

		}
	}

	public void searchContact(String searchContactName) {
		System.out.println("Method. : searchContact");
		int LocationIndexInArrayForSearchItem = findBestIndex(searchContactName, 0, currentLastRecordInArray);
		System.out.println(LocationIndexInArrayForSearchItem);
		if (LocationIndexInArrayForSearchItem == -1) {
			System.out.println("No such Record exists");

		} else {
			System.out.println("Phone Number : " + contactRecordsList[LocationIndexInArrayForSearchItem - 1].number
					+ "... Contact Name : " + searchContactName);
		}
	}

	public void printFullAddressBook() {
		System.out.println("Method. : printFullAddressBook");
		for (int currentIndexPosition = 0; currentIndexPosition <= currentLastRecordInArray; currentIndexPosition++) {
			System.out.println("Name : " + contactRecordsList[currentIndexPosition].name + " .. Number : "
					+ contactRecordsList[currentIndexPosition].number);

		}
	}

}
