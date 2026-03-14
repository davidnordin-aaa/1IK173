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
				+ "\n3. Not a member *register*"
				+ "\n4. Testa databas");
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

		if(initChoice == 4) { // testa databas
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
	}
}
