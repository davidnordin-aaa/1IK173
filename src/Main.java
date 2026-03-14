import java.util.Scanner;
import org.h2.tools.Server;
import java.sql.SQLException;

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
				+ "\n4. Not a member *register*"
				+ "\n8. Testa databas");
		System.out.print("\n[Input your choice here]: ");

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

		if(initChoice == 8) { // testa databas
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

/*
import java.util.Scanner;


 * NOTE!
 * This project is an incomplete skeleton!!!
 */

/*
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
						<<<<<<< HEAD
						+ "\n2. Teacher"
						+ "\n3. Librarian"
						+ "\n4. Not a member *register*");
=======
		+ "\n2. Librarian"
				+ "\n3. Not a member *register*"
				+ "\n4. Testa databas");
		System.out.print("\n[Input your choice here]: ");
		int initChoice = scanner.nextInt();
>>>>>>> origin/master

		System.out.print("[Input your choice here]: ");
		//int initChoice = scanner.nextInt();
		int initChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

		if(initChoice == 1) {
			/*
			 * Be användare välja på vilken nivå den studerar (de får låna olika många böcker)
			 * Lista saker som specifik student kan göra
			 * Använd switch


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
		*/