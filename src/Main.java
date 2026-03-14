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
				+ "\n1. Undergraduate student"
				+ "\n2. Postgraduate student"
				+ "\n3. PhD student"
				+ "\n4. Teacher"
				+ "\n5. Librarian"
				+ "\n6. Not a member *register*"
				+ "\n10. Testa databas");
		System.out.print("[Input your choice here]: ");

		//int initChoice = scanner.nextInt();
		int initChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

		if(initChoice == 1) { // Undergraduate student
			/*
			 * Be användare välja på vilken nivå den studerar (de får låna olika många böcker)
			 * Lista saker som specifik student kan göra
			 * Använd switch
			 */
			int studentRole = initChoice;

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

					// Kolla typ av medlem med ID
					// Kolla antal böcker medlem lånat tidigare (finns begränsning)
					// Kolla om student får lov att låna (regler)
					// Om ovan är ok, kolla boktitel via ISBN (int)
					/*
					* Om bok inte finns: printa meddelande till bibliotekarien som sen säger till medlem
					* Om bok finns bibliotekarie kollar om det finns en tillgänglig bok? Om inte, printa meddelande till bibliotekarien som säger till medlem
					* Om bok finns och är tillgänglig för utlåning, bok ges till medlem och system uppdaterar medlems lånade böcker och uppdaterar bibliotekets tillgängliga böcker för den titeln som medlemmen lånade


					 */

					break;

				// Return book
				case 2 :
					System.out.println("Return book");
					break;
			}

		} if(initChoice == 2) { // Postgraduate student
			System.out.println("You're a postgraduate student");

		} if(initChoice == 3) { // PhD student
			System.out.println("You're a PhD student");

		} if(initChoice == 4) { // Teacher
			System.out.println("You're a teacher");
			// Kalla på metod för att registrera ny låntagare (tolka att det är personen själv som gör det)

		} if(initChoice == 5) { // Librarian
			System.out.println("You're a librarian");

		} if(initChoice == 6) { //Not a member *register*
			System.out.println("You're not a member");

		} if(initChoice == 8) { // testa databas
			DbLibraryStore dbstore = new DbLibraryStore();

			// 1. Setup database and initial books
			dbstore.initializeData();

			// 2. Test adding member
			Member testMember = new Member(4128, 1, 12345678, 0, 0, false, null, "Alice", "Smith");
			store.addMember(testMember);

			// 3. Test retrieving member
			Member retrieved = store.getMember("4128");
			System.out.println("Retrieved Member: " + retrieved.getFirstName() + " " + retrieved.getLastName());

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