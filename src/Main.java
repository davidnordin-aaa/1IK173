import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		//ILibraryStore store = new FileLibraryStore("myfilename.txt");
		ILibraryStore store = new DbLibraryStore();
		LibraryService svc = new LibraryService(store);
		DbLibraryStore DB = new DbLibraryStore();
		Scanner scanner = new Scanner(System.in);

		// Välkomstmeddelande + meny
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
				+ "\n10. Testa databas");
		System.out.print("\n[Input your choice here]: ");

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
			int undergraduateChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// Switch (Undergraduate student actions)====================================================================
			switch(undergraduateChoice) {
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
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = scanner.nextInt();
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		} if(initChoice == 2) { // Postgraduate student
			System.out.println("You're a postgraduate student");
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
			int postgraduateChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// Switch (Postgraduate student actions)====================================================================
			switch(postgraduateChoice) {
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
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = scanner.nextInt();
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		} if(initChoice == 3) { // PhD student
			System.out.println("You're a PhD student");
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
			int phdChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// Switch (PhD student actions)====================================================================
			switch(phdChoice) {
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
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = scanner.nextInt();
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		} if(initChoice == 4) { // Teacher
			System.out.println("You're a teacher");
			// Kalla på metod för att registrera ny låntagare (tolka att det är personen själv som gör det)
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
			int teacherChoice = Integer.parseInt(scanner.nextLine()); // fick problem med hur scanner läste

			// Switch (teacher actions)====================================================================
			switch(teacherChoice) {
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
					System.out.println("\nLibrarian - Ok so you would like to return a book(s). \nLibrarian - Can I have your ID?");
					System.out.print("[Input your ID here]: ");
					int inputtedId = scanner.nextInt();
					// Uppdatera medlems antal lånade böcker (just nu) och tillgängligt bokantal i biblioteket
					break;
			}

		} if(initChoice == 5) { // Librarian
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
			int librarianChoice = scanner.nextInt();

			switch(librarianChoice) {
				case 1 :
					/*
					* Fyller i ansökan med förnamn, efternamn och personnummer
					* Om personnummer redan är registrerad, men personen har brutit mot reglerna: bibliotekarie notifieras att registreringen inte kan göras
					* Om personnummer redan är registrerad och hen inte har brutit mot regler: händer inget (printa meddelande till bibliotekarie att allt är okej)
					* Om personnummer inte hittas i databas: personens information (fnamn, enamn, personmr) registreras och personen får ett unikt ID
			 		*/
					System.out.println("\nLibrarian - Ok, so you would like to become a member"
							+ "\nLibrarian - Then, I would like to get your information");

					System.out.print("\nType your first name: ");
					String fName = scanner.nextLine();

					System.out.print("\nType your last name: ");
					String lName = scanner.nextLine();

					System.out.print("\nType your social security number: ");
					String ssn = scanner.nextLine();

					//Member member = new Member(fName, 3333, 1, 123456789, 0, 0, false, null);
					//DB.addMember(member);

					/*
					Lägg till information
					Lägg till ny medlem i databas
					Skicka tillbaks till main (kör om programmet) - skriv meddelande att medlem kommer skickas till main
					 */
					break;

				case 2 :
					System.out.println("Delete member");
					break;

				case 3 :
					System.out.println("Suspend member");
					break;
			}


		} if(initChoice == 6) { // Not a member *register*
			System.out.println("Librarian - Ok, so you would like to become a member?");


		} if(initChoice == 8) { // testa databas
			DbLibraryStore dbstore = new DbLibraryStore();

			// 1. Setup database and initial books
			dbstore.initializeData();

			// 2. Test adding member
			Member testMember = new Member(4128, "Alice", "Smith", 1, 12345678, 0, 0, false, null);
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
