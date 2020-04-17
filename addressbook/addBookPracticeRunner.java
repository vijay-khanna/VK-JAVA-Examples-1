package pg.module.two.PhoneBook;

import java.io.IOException;
import java.util.Scanner;

public class addBookPracticeRunner {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int valueInput = 5;
		contactList cellContacts = new contactList();
		Scanner userInputScanner = new Scanner(System.in);

		while (valueInput != 0) {

			System.out.println("Welcome to Address Book. Enter the Operation Number.");
			System.out.println("1. Add new Entry");
			System.out.println("2. Delete a Contact");
			System.out.println("3. Find a Contact");
			System.out.println("4. Print Full Address Book");
			System.out.println("0. Exit Applicaion");
			valueInput = userInputScanner.nextInt();

			// System.out.println("User Input Value : " + valueInput);
			switch (valueInput) {
			case 1: {
				System.out.println("Adding new Entry. User Input Value : " + valueInput);
				System.out.println("Enter the name and number.. Space separated");
				String[] completeUserStringArray;
				Scanner userInputString = new Scanner(System.in);
				completeUserStringArray = userInputString.nextLine().split("\\s");

				System.out.println(
						"User Input String : " + completeUserStringArray[0] + " " + completeUserStringArray[1]);
				cellContacts.addNewContact(completeUserStringArray[0], Long.parseLong(completeUserStringArray[1]));

				break;
			}
			case 2: {
				System.out.println("Deleting Entry. User Input Value : " + valueInput);
				System.out.println("Enter the name to be deleted. :");
				String nameToBeDeleted;
				Scanner scanUserInput = new Scanner(System.in);
				nameToBeDeleted = scanUserInput.nextLine();
				System.out.println("Name to be deleted is....: " + nameToBeDeleted);
				cellContacts.deleteContact(nameToBeDeleted);
				break;
			}
			case 3: {
				System.out.println("Finding a Users Phone number. User Input Value : " + valueInput);
				System.out.println("Enter the user name, Whose Number is required : ");
				String nameToBeSearched;
				Scanner scanUserInput = new Scanner(System.in);
				nameToBeSearched = scanUserInput.nextLine();
				System.out.println("Name to be Searched is....: " + nameToBeSearched);
				cellContacts.searchContact(nameToBeSearched);
				break;
			}
			case 4: {
				System.out.println("Printing Full Contact List. User Input Value : " + valueInput);
				cellContacts.printFullAddressBook();
				break;
			}
			case 0: {
				System.out.println("Exited on User Command. Input Value 0");

				break;
			}
			default: {
				System.out.println("Wrong Option Selected, Choose from Menu Options Numbers. Selected # " + valueInput);
				break;
			}

			}

		}
		userInputScanner.close();
		System.out.println("Closed the Scanner.");
	}

}
