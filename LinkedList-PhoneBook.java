package pg.module.two.assignmentSubmission;

class Record {
	String name;
	long number;

	public Record prev; // Double linked list for easy deletion
	public Record next;

	public Record(String newName, long newNumber) {
		name = newName;
		number = newNumber;
		prev = null;
		next = null;
	}

	public String toString() {
		return ("(" + name + "," + number + ")");
	}

}

class AddressBook {
	// ??//List<Record> list;
	// declare 'list' as a linked list in the constructor using Java's built in
	// API's

	public Record head;
	public int N;

	public AddressBook() {
		head = null; // Start with empty list
		N = 0;
	}

	public void add(String name, long number) {
		// Wrap all the details into an object of class Record and add it to the linked
		// list
		// Print: 'Successfully added: contact_name', where contact_name is the name of
		// the contact added
		Record newRecord, currentRecord, prevRecord;
		newRecord = new Record(name, number);
		currentRecord = head;
		prevRecord = head;
		if (N == 0) {

			newRecord.next = null;
			newRecord.prev = null;
			head = newRecord;

		} else {

			//
			while (currentRecord != null) {
				prevRecord = currentRecord;
				currentRecord = currentRecord.next;
			}

			prevRecord.next = newRecord;
			newRecord.prev = prevRecord;
			/*
			 * newRecord.next = head; newRecord.prev = null; head.prev = newRecord; head =
			 * newRecord;
			 */
		}
		N++;

		System.out.println("Successfully added: " + newRecord.name);
	}

	public void findByNumber(long numberToBeSearched) {
		// Check if the number exists
		// If it doesn't, print: 'No such Number exists'
		// else Print: 'Name: contact_name', where contact_name is the name of the
		// contact having that number
		if (N == 0) {
			System.out.println("No such Number exists");
			return; // Nothing to delete
		}
		Record currentRecord;
		currentRecord = head; // Very important: start at first element

		while (currentRecord.next != null) {
			if (currentRecord.number == numberToBeSearched) {
				// System.out.println("Record Found.. " + currentRecord.name);
				break;
			} else
				// System.out.println("currentRecord : " + currentRecord.number + " Name : " +
				// currentRecord.name);
				currentRecord = currentRecord.next;
		}

		if (currentRecord.number == numberToBeSearched) {
			System.out.println("Name: " + currentRecord.name);
		}else
		{
			System.out.println("No such Number exists");
		}
	}

	public void delete(long numberToBeDeleted) {
		// Check if the number exists
		// If it doesn't, print: 'No such Number exists'
		// else delete the item in the linked list having the given number
		// Print: 'Successfully deleted: contact_number', where contact_number is the
		// number to be deleted

		// //

		Record currentRecord, nextRecord, prevRecord;
		currentRecord = head;
		// Very important: start at first element

		// System.out.println("First Record number : " + currentRecord.number + "
		// ToBeDeleted number : "
		// + numberToBeDeleted + " First Record Name : " + currentRecord.name);

		if (N == 0) {
			System.out.println("No such Number exists");
			return; // Nothing to delete
		}

		// Looping to get the Match for Item to Be Deleted..

		while (currentRecord.next != null) {
			if (currentRecord.number == numberToBeDeleted) {
				// System.out.println("Record Found.. will delete it now.." +
				// currentRecord.number);
				break;
			} else
				// System.out.println("currentRecord : " + currentRecord.number + " Name : " +
				// currentRecord.name);
				currentRecord = currentRecord.next;
		}

		// System.out.println("currentRecord : " + currentRecord.number + " ToBeDeleted
		// : " + numberToBeDeleted);

		if (currentRecord.number == numberToBeDeleted) {
			if (N == 1) {
				// First and Only Record
				// System.out.println("Successfully deleted: FIrst and Only Record");
				System.out.println("Successfully deleted: " + currentRecord.number);
				head = null;
				N--;
			}

			else if (currentRecord.prev == null) // Delete FIRST element. FIrst Record contains ToBeDeleted Number.
			{
				// System.out.println("Successfully deleted: FIrst Record");
				System.out.println("Successfully deleted: " + currentRecord.number);
				nextRecord = currentRecord.next;
				nextRecord.prev = null;
				head = nextRecord; // The second is the new "first element"
				N--;
			}

			else if (currentRecord.next == null) // Delete LAST element
			{
				// System.out.println("Successfully deleted: Last Record..");
				System.out.println("Successfully deleted: " + currentRecord.number);

				prevRecord = currentRecord.prev;
				prevRecord.next = null;
				N--;
			} else // Delete a "middle element"
			{
				// System.out.println("Successfully deleted: Record in Middle..");
				System.out.println("Successfully deleted: " + currentRecord.number);

				prevRecord = currentRecord.prev;
				nextRecord = currentRecord.next;

				nextRecord.prev = currentRecord.prev;
				prevRecord.next = currentRecord.next;
				N--;
			}

		} else

		{
			System.out.println("No such Number exists");
		}

	}

	public void printAddressBook() {
		System.out.println("List of contacts:");
		// Print the details of all the contacts in the list in the following format:
		// Name: ABC Number: 123456789
		// Note that the above is just an example
		Record currentRecord;
		currentRecord = head; // Very important: start at first element

		while (currentRecord != null) {

			System.out.println("Name : " + currentRecord.name + " Number : " + currentRecord.number);
			currentRecord = currentRecord.next;

		}
		// System.out.println(r);
	}

}

public class Source {
	public static void main(String[] args) {
		AddressBook myContacts = new AddressBook();

		myContacts.add("John", 9876123450l);
		myContacts.add("Mellisa", 8360789114l);
		myContacts.add("Daman", 9494149900l);
		myContacts.findByNumber(9998760333l);
		myContacts.printAddressBook();
		myContacts.delete(9876123450l);
		myContacts.add("Gregory", 7289880988l);
		myContacts.printAddressBook();
		myContacts.findByNumber(8360789114l);
		myContacts.add("Mary", 7205678901l);
		myContacts.delete(9876123450l);
		myContacts.findByNumber(7205678901l);
		myContacts.printAddressBook();
	}
}
