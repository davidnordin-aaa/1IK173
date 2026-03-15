import java.util.Objects;
import java.util.Scanner;

public class LibraryService {

	ILibraryStore store;

	public LibraryService(ILibraryStore store) {
		this.store = store;
	}

	public boolean borrow(int isbn, String memberId) {
		boolean status = false;
		Scanner scanner = new Scanner(System.in);
		Member memberInfo = store.getMember(memberId);
		String stringIsbn = String.valueOf(isbn);
		Book book = store.getBook(stringIsbn);
		DbLibraryStore DB = new DbLibraryStore();
		// more code here...
		System.out.println("\nIs this the book you wish to borrow?");
		System.out.println("Book: " + book.author + " \nWith title: " + book.title + " \nFrom year: " + book.year + " \nWith ISBN: " + book.ISBN);
		String input = scanner.nextLine();
		if (Objects.equals(input, "Yes")){
			System.out.println("Great we will try to get that sorted");
			Loan newLoan = executeLoan(memberId,isbn);
			if (newLoan == null){
				System.out.println("We couldnt borrow that book because it is already loaned to another person.");
			}
			else{
				System.out.println("You are now the proud owner of "+ book.title+ " until " + newLoan.DueDate +".");
			}
		}
		else{
			System.out.println("We couldnt find that book, please double check your search.");
			Main.main(null);
		}

		return status;



	}

	private Loan executeLoan(String memberId, int isbn) {
		DbLibraryStore DB = new DbLibraryStore();
		Loan newLoan = null;
		if(!DB.isAlreadyBorrowed(isbn)){
			long loanID = DB.lendItem(memberId,isbn);
			if(loanID > 0) {
				newLoan = DB.getLoan(loanID);
			}
		}
		return newLoan;
	}

}
