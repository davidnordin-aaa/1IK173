import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		ILibraryStore store = new DbLibraryStore();
		LibraryService svc = new LibraryService(store);
		DbLibraryStore DB = new DbLibraryStore();
		Scanner scanner = new Scanner(System.in);

		// [Titel & meny]=============================================================================================
		System.out.println(
				"-----------------------------------------"
						+ "\n   ===Welcome to the Library System==="
						+ "\n-----------------------------------------"
						+ "\nWho are you or what would you want to do?"
						+ "\n\n1. Member"
						+ "\n2. Librarian"
						+ "\n3. Not a member (register)");

		// Input val
		System.out.print("\n[Input your choice here]: ");
		int initChoice = Integer.parseInt(scanner.nextLine());
		loggedIn(initChoice);
	}

	public static void loggedIn(int initChoice) {
		ILibraryStore store = new DbLibraryStore();
		LibraryService svc = new LibraryService(store);
		DbLibraryStore DB = new DbLibraryStore();
		Scanner scanner = new Scanner(System.in);

		// [1. Member]===========================================================================================
		if (initChoice == 1) {
			System.out.println("\nLibrarian - Welcome to the library");

			System.out.print("Librarian - Please identify yourself to grant member access by providing your ID: ");
			String inputId = scanner.nextLine();

			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			System.out.println(
					"1. Lend book"
							+ "\n2. Return book"
							+ "\n3. Delete account");

			System.out.print("[Input your choice here]: ");
			int undergraduateChoice = Integer.parseInt(scanner.nextLine());

			switch (undergraduateChoice) {
				case 1: // Lend book
					System.out.print(
							"\n---------------------------------"
									+ "\nWhat book would you like to lend?"
									+ "\n---------------------------------"
									+ "\n[Enter ISBN here]: ");
					int isbnItem = Integer.parseInt(scanner.nextLine());

					System.out.println("\nLibrarian: Okay, so you want to lend a book with ISBN number '" + isbnItem + "'");

					Book borrowBook = store.getBook(String.valueOf(isbnItem));
					if (borrowBook != null) {
						System.out.println("\nIs this the book you wish to borrow?");
						System.out.println("Book: " + borrowBook.author + " \nWith title: " + borrowBook.title + " \nFrom year: " + borrowBook.year + " \nWith ISBN: " + borrowBook.ISBN);
						System.out.print("[Type 'yes' to confirm]: ");
						String confirmation = scanner.nextLine();

						if (confirmation.equalsIgnoreCase("yes")) {
							System.out.println("Great we will try to get that sorted");
							boolean success = svc.borrow(isbnItem, inputId, confirmation);
							if (success) {
								System.out.println("You are now the proud owner of " + borrowBook.title + " for the next 15 days!\nPress enter to go back to main screen.");
							} else {
								System.out.println("We couldn't borrow that book because it is already loaned to another person, or you hit your limit.\nPress enter to go back to main screen.");
							}
						} else {
							System.out.println("Borrowing cancelled.\nPress enter to return to main screen.");
						}
					} else {
						System.out.println("We couldn't find that book, please double check your search.\nPress enter to return to main screen.");
					}
					scanner.nextLine();
					loggedIn(initChoice);
					break;

				case 2: // Return book
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					String inputtedId = scanner.nextLine();

					System.out.print("Input the books ISBN here: ");
					int returningIsbn = Integer.parseInt(scanner.nextLine());

					Book returnBook = store.getBook(String.valueOf(returningIsbn));
					if (returnBook != null) {
						System.out.println("\nIs this the book you wish to return?");
						System.out.println("Book: " + returnBook.author + " \nWith title: " + returnBook.title + " \nFrom year: " + returnBook.year + " \nWith ISBN: " + returnBook.ISBN);
						System.out.print("[Type 'yes' to confirm]: ");
						String returnConfirmation = scanner.nextLine();

						if (returnConfirmation.equalsIgnoreCase("yes")) {
							System.out.println("Great we will try to get that return sorted");
							boolean success = svc.returnBook(returningIsbn, inputtedId, returnConfirmation);
							if (success) {
								System.out.println("You have now returned " + returnBook.title + ".\nPress enter to go back to main screen.");
							} else {
								System.out.println("We couldn't return " + returnBook.title + ". If you have more problems, contact support.\nPress enter to go back to main screen.");
							}
						} else {
							System.out.println("Return cancelled.\nPress enter to return to main screen.");
						}
					} else {
						System.out.println("We couldn't find that book, please double check your search.\nPress enter to go back to main screen.");
					}
					scanner.nextLine();
					loggedIn(initChoice);
					break;

				case 3: // Delete account
					System.out.println("\nLibrarian - Ok so you would like to delete your account?. \nLibrarian - Can I have your ID?");
					String deletionID = scanner.nextLine();
					svc.requestDeletion(deletionID);
					System.out.println("Your account has been removed. Press enter to return to main screen");
					scanner.nextLine();
					main(null);
					break;
			}
		}

		// Note: To keep the file clean, the exact same switch cases (1 and 2)
		// from Undergraduate above should be copy-pasted into the blocks below for
		// Postgraduate (initChoice == 2), PhD (initChoice == 3), and Teacher (initChoice == 4).

		// [5. Librarian]===============================================================================================
		if (initChoice == 2) {
			System.out.println("Ok, so you're a librarian");

			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			System.out.println(
					"1. Register new member"
							+ "\n2. Delete member"
							+ "\n3. Suspend member");

			System.out.print("[Input your choice here]: ");
			int librarianChoice = Integer.parseInt(scanner.nextLine());

			switch (librarianChoice) {
				case 1:
					System.out.println("\nSystem - Ok, so you would like to become a member"
							+ "\nSystem - Then, I would like to get your information");

					System.out.print("\nType your first name: ");
					String fName = scanner.nextLine();

					System.out.print("Type your last name: ");
					String lName = scanner.nextLine();

					System.out.print("Type your social security number: ");
					Long ssn = Long.parseLong(scanner.nextLine());
					System.out.println("\nSelect from the list as what member to register as: "
							+ "\n---------------------------------------------------"
							+ "\n1. Undergraduate student" + "\n2. Postgraduate student" + "\n3. PhD student" + "\n4. Teacher");
					System.out.print("[Input choice here]: ");
					int memberType = Integer.parseInt(scanner.nextLine());
					System.out.println("-------------------------------------------------------------------------------------");

					Member member = new Member(0, fName, lName, memberType, ssn, 0, 0, false, null);
					DB.addMember(member);
					System.out.println("You '" + fName + "' have successfully been added as a member at the library!");

					System.out.println("This is your ID: " + member.getId()
							+ "\n-------------------------------------------------------------------------------------");

					loggedIn(memberType);
					break;

				case 2:
					System.out.println("\nLibrarian - Ok, so you would like to delete a member");
					break;

				case 3:
					System.out.println("\nLibrarian - Ok, so you would like to suspend a member");
					break;
			}
		}

		// [6. Not a member *register*]=================================================================================
		if (initChoice == 3) {
			System.out.println("\nLibrarian - Ok, so you would like to become a member"
					+ "\nLibrarian - Then, I would like to get your information");

			System.out.print("\nType your first name: ");
			String fName = scanner.nextLine();

			System.out.print("Type your last name: ");
			String lName = scanner.nextLine();

			System.out.print("Type your social security number: ");
			Long ssn = Long.parseLong(scanner.nextLine());
			System.out.println("\nSelect from the list as what member to register as: "
					+ "\n---------------------------------------------------"
					+ "\n1. Undergraduate student" + "\n2. Postgraduate student" + "\n3. PhD student" + "\n4. Teacher");
			System.out.print("[Input choice here]: ");
			int memberType = Integer.parseInt(scanner.nextLine());
			System.out.println("-------------------------------------------------------------------------------------");

			Member member = new Member(0, fName, lName, memberType, ssn, 0, 0, false, null);
			DB.addMember(member);
			System.out.println("You '" + fName + "' have successfully been added as a member at the library!");

			System.out.println("This is your ID: " + member.getId()
					+ "\n-------------------------------------------------------------------------------------");

			main(null);
		}
	}
}