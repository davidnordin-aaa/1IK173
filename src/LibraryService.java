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
		if (Objects.equals(input.toLowerCase(), "yes")){
			System.out.println("Great we will try to get that sorted");
			Loan newLoan = executeLoan(memberId,isbn);
			if (newLoan == null){
				System.out.println("We couldnt borrow that book because it is already loaned to another person.");
			}
			else{
				System.out.println("You are now the proud owner of "+ book.title+ " until " + newLoan.DueDate +"." + "\nPress enter to go back to main screen");
				Main.main(null);
			}
		}
		else{
			System.out.println("We couldnt find that book, please double check your search.");
			Main.main(null);
		}

		return status;



	}
	public boolean returnBook(int isbn, String memberId) {
		boolean status = false;
		Scanner scanner = new Scanner(System.in);
		Member memberInfo = store.getMember(memberId);
		String stringIsbn = String.valueOf(isbn);
		Book book = store.getBook(stringIsbn);
		DbLibraryStore DB = new DbLibraryStore();

		System.out.println("\nIs this the book you wish to return?");
		System.out.println("Book: " + book.author + " \nWith title: " + book.title + " \nFrom year: " + book.year + " \nWith ISBN: " + book.ISBN);
		String input = scanner.nextLine();
		if (Objects.equals(input.toLowerCase(), "yes")){
			System.out.println("Great we will try to get that return sorted");
			boolean newReturn = executeReturn(memberId,isbn);
			if (!newReturn){
				System.out.println("We couldnt return "+ book.title +". If you have more problems, contact support");
			}
			else{
				System.out.println("You have now returned "+ book.title+ "." + "\nPress enter to go back to main screen");
				scanner.nextLine();
				Main.main(null);
			}
		}
		else{
			System.out.println("We couldnt find that book, please double check your search.");
			Main.main(null);
		}
		return status;

	}

	public Loan executeLoan(String memberId, int isbn) {
		Loan newLoan = null;
		if(store.isAlreadyBorrowed(memberId, isbn) == -1){
			long loanID = store.lendItem(memberId, isbn);
			if(loanID > 0) {
				newLoan = store.getLoan(loanID);
			}
		}
		return newLoan;
	}

	public boolean executeReturn(String memberId, int isbn) {
		int loanID = store.isAlreadyBorrowed(memberId, isbn);
		if(loanID > 0){
			return store.returnItem(memberId, isbn);
		}
		return false;
	}

	public boolean requestDeletion(String memberID){
		try {
			store.removeMember(memberID);
			if(store.getMember(memberID) == null){
				return true;
			}
		} catch (Exception e){
			return false;
		}
		return false;
	}

}
