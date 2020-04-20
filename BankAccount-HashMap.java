package bankApplicationHashMap;

//Implement a Bank Application.
/**
 * Login Constraints: Username, Password, Mobile is required
Account Information = Name : Account number : Balance
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankAssignmentHashMapFullVersion {
	static Map<Credentials, AccountInfo> database = new HashMap<>();

	public static void main(String[] args) {
		int userChoice = 1;
		String inputUserName;
		String inputPassword;
		Long inputMobile;
		String inputAccountName;
		int inputInitialBankBalance;
		int inputAccountNumber;
		Scanner scanInput = new Scanner(System.in);
		Credentials userCredentials = new Credentials();
		AccountInfo userAccountInfo = new AccountInfo();

		Map<Credentials, AccountInfo> bankUserDataBase = new HashMap<>();

		System.out.println("Begin");

		do {

			System.out.print("Line 1 - Choose :  1->sign in, 2->sign up, 3->Terminate");
			userChoice = scanInput.nextInt();
			System.out.println(userChoice);

			switch (userChoice) {
			case 1: {
				System.out.println("sign in - Enter Login Details : ");
				System.out.println("2nd line. Enter - string -> username , and press Enter");
				inputUserName = scanInput.next();
				System.out.println("3rd line. Enter - string -> password , and press Enter");
				inputPassword = scanInput.next();
				System.out.println("4th line. Enter - number -> mobile number , and press Enter");
				inputMobile = scanInput.nextLong();
				System.out.println(inputUserName + " " + inputPassword + " " + inputMobile);

				userCredentials.setUserName(inputUserName);
				userCredentials.setPassword(inputPassword);
				userCredentials.setMobile(inputMobile);

				if (bankUserDataBase.containsKey(userCredentials)) {
					System.out.println("User found! Retriveing Details...");
					AccountInfo retrivedValue = bankUserDataBase.get(userCredentials);
					System.out.println("Account Name: " + retrivedValue.getAccountName());
					System.out.println("Account Number : " + retrivedValue.getAccountNumber());
				} else {
					System.out.println("-1 User not found");
				}
				break;

			}
			case 2: {
				System.out.print("sign up - Enter Details : ");

				System.out.println("2nd line. Enter - string -> username , and press Enter");
				inputUserName = scanInput.next();
				System.out.println("3rd line. Enter - string -> password , and press Enter");
				inputPassword = scanInput.next();
				System.out.println("4th line. Enter - number -> mobile number , and press Enter");
				inputMobile = scanInput.nextLong();
				System.out.println("5th line. Enter - String -> AccountName , and press Enter");
				inputAccountName = scanInput.next();
				System.out.println("5th line. Enter - number -> InitialBankBalance , and press Enter");
				inputInitialBankBalance = scanInput.nextInt();
				System.out.println("7th line. Enter - number ->AccountNumber , and press Enter");
				inputAccountNumber = scanInput.nextInt();

				System.out.println(inputUserName + " " + inputPassword + " " + inputMobile + " " + inputAccountName
						+ " " + inputInitialBankBalance + " " + inputAccountNumber);

				userCredentials.setUserName(inputUserName);
				userCredentials.setPassword(inputPassword);
				userCredentials.setMobile(inputMobile);

				userAccountInfo.setAccountBalance(inputInitialBankBalance);
				userAccountInfo.setAccountName(inputAccountName);
				userAccountInfo.setAccountNumber(inputAccountNumber);

				bankUserDataBase.put(userCredentials, userAccountInfo);

				break;
			}
			case 3: {
				System.out.print("Choose 3 : Line : Exiting ");
				break;
			}

			}
		} while (userChoice < 3);

		// main function
	}
}

class Credentials {
	// contains username, password and mobile
	// use this class object as key in hashmap to indicate a particular user
	private String userName;
	private String password;
	private Long mobile;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = (int) (prime * result + mobile);
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Credentials otherUser = (Credentials) obj;
		if (password == null) {
			if (otherUser.password != null) {
				return false;
			}
		} else if (!password.equals(otherUser.password)) {
			return false;
		}

		if (userName == null) {
			if (otherUser.userName != null) {
				return false;
			}
		} else if (!userName.equals(otherUser.userName)) {
			return false;
		}

		if (mobile != otherUser.mobile) {
			return false;
		}
		return true;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

}

class AccountInfo {
	// this class contains account name, balance and account Number
	// this class object is used as value to store in hashmap against the user
	// credentials key.
	private String accountName;
	private int accountBalance;
	private int accountNumber;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

}
