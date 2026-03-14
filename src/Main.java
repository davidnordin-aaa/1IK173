import java.util.Scanner;

/*
 * NOTE!
 * This project is an incomplete skeleton!!!
 */

public class Main {

	public static void main(String[] args) {

		//ILibraryStore store = new FileLibraryStore("myfilename.txt");
		ILibraryStore store = new DbLibraryStore();
		LibraryService svc = new LibraryService(store);
		Scanner scanner = new Scanner(System.in);


		// Välkomstmeddelande + meny
		System.out.println(
				"-----------------------------------------"
				+ "\n   ===Welcome to the Library System==="
				+ "\n-----------------------------------------"
				+ "\nWho are you or what would you want to do?"
				+ "\n1. Student"
				+ "\n2. Teacher"
				+ "\n3. Librarian"
				+ "\n4. Not a member *register*");

		System.out.print("[Input your choice here]: ");
		//int initChoice = scanner.nextInt();
		int initChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

		if(initChoice == 1) {
			/*
			 * Be användare välja på vilken nivå den studerar (de får låna olika många böcker)
			 * Lista saker som specifik student kan göra
			 * Använd switch
			 */

			// Menu list title
			System.out.println(
					"\n----------------------------------------------"
					+ "\nSelect from the list what would you like to do"
					+ "\n----------------------------------------------");

			// Menu list content
			System.out.println(
					"1. Lend book"
					+ "\n2. Return book");

			System.out.print("[Input your choice here]: ");
			//int studentChoice = scanner.nextInt();
			int studentChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// Switch (student actions)====================================================================
			switch(studentChoice) {
				// Lend book
				case 1 :
					System.out.print(
							"\n---------------------------------"
							+ "\nWhat book would you like to lend?"
							+ "\n---------------------------------"
							+ "\n[Type here]: ");
					String bookTitleToLend = scanner.nextLine();

					System.out.println("\nLibrarian: Okay, so you want to lend " + "'" + bookTitleToLend + "'");
					System.out.println("Librarian: Please provide me with your ID"
					+ "\n------------------------------------------");

					System.out.print("[Input ID here]: ");
					int studentId = scanner.nextInt();

					// Kolla typ av medlem
					break;

				// Return book
				case 2 :
					System.out.println("Return book");
					break;
			}

		} if(initChoice == 2) { // Teacher
			// Lista saker som bibliotekarie kan göra
			System.out.println("You're a teacher");

		} if(initChoice == 3) { // Librarian
			System.out.println("You're a librarian");

		} if(initChoice == 4) { // Register new member
			System.out.println("You want to register");
			// Kalla på metod för att registrera ny låntagare (tolka att det är personen själv som gör det)
		}









		//System.out.print("Enter your user id: ");
		//String userId = scanner.nextLine();
		/*
		System.out.println("------------------------------------------------------------"
				+ "\nPlease select an option from the menu below:"
				+ "\n------------------------------------------------------------\n[Menu list]"
				+ "\n1. "
				+ "\n2. "
				+ "\n3. "
				+ "\n4.");
		 */

		/*
		boolean done = false;
		int selection = 0;
		while (!done) {			
			System.out.println("\nMenu:");
			System.out.println("1. Lend item.");
			System.out.println("2. Return item.");
			// And some more menu choices.
			System.out.println("9. Quit.");
			System.out.println("Select (1-9):");
			selection = Integer.parseInt(scanner.nextLine());

			switch (selection) {
			case 1: {
				System.out.println("Enter book id:");
				String bookId = scanner.nextLine();
				svc.borrow(bookId, userId);
			}
				break;

			case 2: {
				// Return an item.
			}
				break;
				
			// More cases here!
			
			case 9: {
				done = true;
			}
				break;

			default:
				System.out.println(String.format("%d is not valid.", selection));
			}
		}


		System.out.println("Bye.");
		 */
	}

}
