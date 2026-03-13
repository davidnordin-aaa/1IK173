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
