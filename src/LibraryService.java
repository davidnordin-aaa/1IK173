import java.util.Objects;
import java.util.Scanner;

public class LibraryService {

	ILibraryStore store;

	public LibraryService(ILibraryStore store) {
		this.store = store;
	}

	public boolean borrow(String isbn, String memberId) {
		boolean status = false;
		Scanner scanner = new Scanner(System.in);
		Member memberInfo = store.getMember(memberId);
		Book book = store.getBook(isbn);

		// more code here...
		System.out.println("Is this the book you want to borrow?");
		System.out.println(book.author + book.title + book.year + book.ISBN);
		String input = scanner.nextLine();
		if (Objects.equals(input, "Yes")){
			System.out.println("Great we will try to get that sorted");
		}
		else{
			System.out.println("We couldnt find that book, please double check your search.");
			Main.main(null);
		}
		/*
		if (isAvailable(book) == true){
			System.out.println("That book was available and it is now borrowed on your account");
		}
		 */
		return status;



	}
}
