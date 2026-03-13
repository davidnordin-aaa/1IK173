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


		// Välkomstmeddelande
		System.out.println(
				"-----------------------------------------"
				+ "\n   ===Welcome to the Library System==="
				+ "\n-----------------------------------------"
				+ "\nWho are you or what would you want to do?"
				+ "\n1. Student"
				+ "\n2. Librarian"
				+ "\n3. Not a member *register*");
		System.out.print("\n[Input your choice here]: ");
		int initChoice = scanner.nextInt();

		if(initChoice == 1) { // Student
			/*
			* Be användare välja på vilken nivå den studerar (de får låna olika många böcker)
			* Lista saker som specifik student kan göra
			 */
			System.out.println("Något ska hända här");

		} if(initChoice == 2) { // Bibliotekarie
			// Lista saker som bibliotekarie kan göra
			System.out.println("Något ska hända här");

		} if(initChoice == 3) { // Registrera som ny medlem
			// Kalla på metod för att registrera ny låntagare (tolka att det är personen själv som gör det)
			System.out.println("Något ska hända här");
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
			System.out.println("3. Test Database.");
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
				case 3: { //testing database
					DbLibraryStore dbstore = new DbLibraryStore();

					// 1. Setup database and initial books
					dbstore.initializeData();

					// 2. Test adding member
					Member testMember = new Member("Alice Smith", 4128, 1, 12345678, 0, 0, false, null);
					store.addMember(testMember);

					// 3. Test retrieving member
					Member retrieved = store.getMember("4128");
					System.out.println("Retrieved Member: " + retrieved.getName());

					// 4. Test suspension logic
					store.suspendMember("4128");
					System.out.println("Is suspended? " + store.isSuspendedMember("4128"));
				}
				break;
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
