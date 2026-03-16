public class LibraryService {

	ILibraryStore store;

	public LibraryService(ILibraryStore store) {
		this.store = store;
	}

	public boolean borrow(int isbn, String memberId, String confirmationInput) {
		Member memberInfo = store.getMember(memberId);
		Book book = store.getBook(String.valueOf(isbn));

		if (book == null || memberInfo == null) {
			return false;
		}

		if (confirmationInput != null && confirmationInput.equalsIgnoreCase("yes")) {
			Loan newLoan = executeLoan(memberId, isbn);
			return newLoan != null;
		}

		return false;
	}

	public boolean returnBook(int isbn, String memberId, String confirmationInput) {
		Member memberInfo = store.getMember(memberId);
		Book book = store.getBook(String.valueOf(isbn));

		if (book == null || memberInfo == null) {
			return false;
		}

		if (confirmationInput != null && confirmationInput.equalsIgnoreCase("yes")) {
			return executeReturn(memberId, isbn);
		}

		return false;
	}

	public Loan executeLoan(String memberId, int isbn) {
		Loan newLoan = null;
		if(store.isAlreadyBorrowed(memberId, isbn) == -1){
			if(store.canMemberBorrow(memberId)) {
				long loanID = store.lendItem(memberId, isbn);
				if (loanID > 0) {
					newLoan = store.getLoan(loanID);
				}
			}
		}
		return newLoan;
	}

	public boolean executeReturn(String memberId, int isbn) {
		int loanID = store.isAlreadyBorrowed(memberId, isbn);
		if(store.isSuspendedMember(memberId)) {
			if (loanID > 0) {
				return store.returnItem(memberId, isbn);
			}
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
