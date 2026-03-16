import org.apache.logging.log4j.core.pattern.IntegerPatternConverter;

import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		//ILibraryStore store = new FileLibraryStore("myfilename.txt");
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
				+ "\n\n1. Undergraduate student"
				+ "\n2. Postgraduate student"
				+ "\n3. PhD student"
				+ "\n4. Teacher"
				+ "\n5. Librarian"
				+ "\n6. Not a member *register*"
				+ "\n99. Testa databas");

		// Input val
		System.out.print("\n[Input your choice here]: ");
		//int initChoice = scanner.nextInt();
		int initChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur vanliga scanner läste

		// [1. Undergraduate]===========================================================================================
		if(initChoice == 1) {
			int studentRole = initChoice;

			System.out.println("\nLibrarian - Okay, so you're an undergraduate student");

			// Input ID
			System.out.print("Librarian - Please identify yourself to grant member access by providing your ID: "); // Om tid finns förfina med input your choice here (nedanför raden)
			String inputId = scanner.nextLine();

			// Verifying ID
			if(DB.getMember(inputId) == null) {
				System.out.println("Couldn't find member, register");
				scanner.nextLine(); // Klicka enter för att komma tillbaks till main
				main(null);
			}

			// Menu title
			System.out.println(
					"\n----------------------------------------------"
					+ "\nSelect from the list what would you like to do"
					+ "\n----------------------------------------------");

			// Choose option from menu list
			System.out.println(
					"1. Lend book"
					+ "\n2. Return book"
					+ "\n3. Delete account");

			System.out.print("[Input your choice here]: ");
			int undergraduateChoice = Integer.parseInt(scanner.nextLine());
			//int undergraduateChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// [Switch]-------------------------------------------------------------------------------------------------
			switch(undergraduateChoice) {
				case 1 : // Lend book
					// Input ISBN
					System.out.print(
							"\n---------------------------------"
							+ "\nWhat book would you like to lend?"
							+ "\n---------------------------------"
							+ "\n[Enter ISBN here]: ");
					scanner.nextLine();
					int isbnItem = Integer.parseInt(scanner.nextLine());

					System.out.println("\nLibrarian: Okay, so you want to lend a book with ISBN number " + "'" + isbnItem + "'");
					System.out.println("Librarian: Please provide me with your ID, in order to lend the item"
					+ "\n--------------------------------------------------------------------");

					// Input ID
					System.out.print("[Input your ID for confirmation here]: ");
					String studentId = scanner.nextLine();

					// Kalla på borrow-metod i LibraryService
					svc.borrow(isbnItem, studentId);

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
				case 2 : // Return book
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). Librarian - Can I have your ID?");
					String inputtedId = scanner.nextLine();
					System.out.println("Input the books ISBN here: ");
					int returingIsbn = Integer.parseInt(scanner.nextLine());
					svc.returnBook(returingIsbn, inputtedId);
				case 3 : // Return book
					System.out.println("\nLibrarian - Ok so you would like to delete your account?. Librarian - Can I have your ID?");
					String deltionID = scanner.nextLine();
					svc.requestDeletion(deltionID);
					break;
			}

		// [2. Postgraduate]============================================================================================
		} if(initChoice == 2) {
			int studentRole = initChoice;

			System.out.println("\nLibrarian - Okay, so you're a postgraduate student");

			// Input ID
			System.out.print("Librarian - Please identify yourself to grant member access by providing your ID: ");
			String inputId = scanner.nextLine();

			// Menu title
			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			// Choose option from menu list
			System.out.println(
					"1. Lend book"
							+ "\n2. Return book");

			System.out.print("[Input your choice here]: ");
			int postgraduateChoice = Integer.parseInt(scanner.nextLine());
			//int postgraduateChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// [Switch]-------------------------------------------------------------------------------------------------
			switch(postgraduateChoice) {
				case 1 : // Lend book
					// Input ISBN
					System.out.print(
							"\n---------------------------------"
									+ "\nWhat book would you like to lend?"
									+ "\n---------------------------------"
									+ "\n[Enter ISBN here]: ");
					scanner.nextLine();
					int isbnItem = Integer.parseInt(scanner.nextLine());

					System.out.println("\nLibrarian: Okay, so you want to lend a book with ISBN number " + "'" + isbnItem + "'");
					System.out.println("Librarian: Please provide me with your ID, in order to lend the item"
							+ "\n--------------------------------------------------------------------");

					// Input ID
					System.out.print("[Input your ID for confirmation here]: ");
					int studentId = Integer.parseInt(scanner.nextLine());

					// Kalla på borrow-metod i LibraryService
					svc.borrow(isbnItem, inputId);

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

				case 2 : // Return book
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = Integer.parseInt(scanner.nextLine());
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		// [3. PhD]=====================================================================================================
		} if(initChoice == 3) {
			int studentRole = initChoice;

			System.out.println("\nLibrarian - Okay, so you're a PhD student");

			// Input ID
			System.out.print("Librarian - Please identify yourself to grant member access by providing your ID: ");
			String inputId = scanner.nextLine();

			// Menu title
			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			// Choose option from menu list
			System.out.println(
					"1. Lend book"
							+ "\n2. Return book");

			System.out.print("[Input your choice here]: ");
			int phdChoice = Integer.parseInt(scanner.nextLine());
			//int phdChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// [Switch]-------------------------------------------------------------------------------------------------
			switch(phdChoice) {
				case 1 : // Lend book
					// Input ISBN
					System.out.print(
							"\n---------------------------------"
									+ "\nWhat book would you like to lend?"
									+ "\n---------------------------------"
									+ "\n[Enter ISBN here]: ");
					scanner.nextLine();
					int isbnItem = Integer.parseInt(scanner.nextLine());

					System.out.println("\nLibrarian: Okay, so you want to lend a book with ISBN number " + "'" + isbnItem + "'");
					System.out.println("Librarian: Please provide me with your ID, in order to lend the item"
							+ "\n--------------------------------------------------------------------");

					// Input ID
					System.out.print("[Input your ID for confirmation here]: ");
					int studentId = Integer.parseInt(scanner.nextLine());

					// Kalla på borrow-metod i LibraryService
					svc.borrow(isbnItem, inputId);

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

				case 2 : // Return book
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = Integer.parseInt(scanner.nextLine());
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		// [4. Teacher]=================================================================================================
		} if(initChoice == 4) {
			int studentRole = initChoice;

			System.out.println("\nLibrarian - Okay, so you're an Teacher");

			// Input ID
			System.out.print("Librarian - Please identify yourself to grant member access by providing your ID: "); // Om tid finns förfina med input your choice here (nedanför raden)
			String inputId = scanner.nextLine();

			// Menu title
			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			// Choose option from menu list
			System.out.println(
					"1. Lend book"
							+ "\n2. Return book");

			System.out.print("[Input your choice here]: ");
			int teacherChoice = Integer.parseInt(scanner.nextLine());
			//int undergraduateChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// [Switch]-------------------------------------------------------------------------------------------------
			switch(teacherChoice) {
				case 1 : // Lend book
					// Input ISBN
					System.out.print(
							"\n---------------------------------"
									+ "\nWhat book would you like to lend?"
									+ "\n---------------------------------"
									+ "\n[Enter ISBN here]: ");
					scanner.nextLine();
					int isbnItem = Integer.parseInt(scanner.nextLine());

					System.out.println("\nLibrarian: Okay, so you want to lend a book with ISBN number " + "'" + isbnItem + "'");
					System.out.println("Librarian: Please provide me with your ID, in order to lend the item"
							+ "\n--------------------------------------------------------------------");

					// Input ID
					System.out.print("[Input your ID for confirmation here]: ");
					int studentId = Integer.parseInt(scanner.nextLine());
					// Kalla på borrow-metod i LibraryService
					svc.borrow(isbnItem, inputId);

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

				case 2 : // Return book
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = Integer.parseInt(scanner.nextLine());
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		// [5. Librarian]===============================================================================================
		} if(initChoice == 5) {
			/*
			* Registrera nya medlemmar
			* Ett och samma switch för att radera på förfrågan eller för att de varit suspended 2 gånger redan
			* Möjlighet att suspend medlem en viss tid
			 */
			System.out.println("Ok, so you're a librarian");

			// Menu list title
			System.out.println(
					"\n----------------------------------------------"
							+ "\nSelect from the list what would you like to do"
							+ "\n----------------------------------------------");

			// Menu list content
			System.out.println(
					"1. Register new member"
							+ "\n2. Delete member"
							+ "\n3. Suspend member");

			System.out.print("\n[Input your choice here]: ");
			int librarianChoice = Integer.parseInt(scanner.nextLine());

			// [Switch]-------------------------------------------------------------------------------------------------
			switch(librarianChoice) {
				case 1 :
					/*
					* Fyller i ansökan med förnamn, efternamn och personnummer
					* Om personnummer redan är registrerad, men personen har brutit mot reglerna: bibliotekarie notifieras att registreringen inte kan göras
					* Om personnummer redan är registrerad och hen inte har brutit mot regler: händer inget (printa meddelande till bibliotekarie att allt är okej)
					* Om personnummer inte hittas i databas: personens information (fnamn, enamn, personmr) registreras och personen får ett unikt ID
			 		*/
					System.out.println("\nSystem - Ok, so you would like to become a member"
							+ "\nSystem - Then, I would like to get your information");

					scanner.nextLine();
					System.out.print("\nEnter the members first name: ");
					String fName = scanner.nextLine();

					System.out.print("Enter the members last name: ");
					String lName = scanner.nextLine();

					System.out.print("Enter the members social security number: ");
					Long ssn = Long.parseLong(scanner.nextLine());

					// Add member
					Member member = new Member(0, fName, lName, 1, ssn, 0, 0, false, null);
					DB.addMember(member);

					System.out.println("\nLibrarian - You " + "'" + fName + "'" + " have successfully been added as a member at the library!"
					+ "\nLibrarian - You can now start using the system!");
					// Kolla om person brutit mot reglerna så registrering ej kan göras (printa meddelande)
					// Kolla om personnummer redan finns i databas innan lägga till
					break;

				case 2 :
					System.out.println("\nLibrarian - Ok, so you would like to delete a member");
					break;

				case 3 :
					System.out.println("\nLibrarian - Ok, so you would like to suspend a member");
					break;
			}

		// [6. Not a member *register*]=================================================================================
		} if(initChoice == 6) {
			System.out.println("\nLibrarian - Ok, so you would like to become a member"
					+ "\nLibrarian - Then, I would like to get your information");

			// Input details
			System.out.print("\nType your first name: ");
			String fName = scanner.nextLine();

			System.out.print("Type your last name: ");
			String lName = scanner.nextLine();

			System.out.print("Type your social security number: ");
			Long ssn = Long.parseLong(scanner.nextLine());

			System.out.print("\nSelect from the list what member are you registering as: "
			+ "\n--------------------------------------------------------"
			+ "\n1. Postgraduate student" + "\n2. PhD student" + "\n3. Teacher");
			System.out.print("\n[Input choice here]: ");
			int memberType = Integer.parseInt(scanner.nextLine());

			// Add member
			Member member = new Member(0, fName, lName, memberType, ssn, 0, 0, false, null);
			DB.addMember(member);
			System.out.println("You " + "'" + fName + "'" + " have successfully been added as a member at the library!");
			// Kolla om person brutit mot reglerna så registrering ej kan göras (printa meddelande)
			// Kolla om personnummer redan finns i databas innan lägga till












		// [99. Testa databas]===========================================================================================
		} if(initChoice == 99) {
			DbLibraryStore dbstore = new DbLibraryStore();

			// 1. Setup database and initial books
			dbstore.initializeData();

			// 2. Test adding member
			Member testMember = new Member(4128, "Alice", "Smith", 1, 12345678L, 0, 0, false, null);
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
