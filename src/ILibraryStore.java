import java.util.ArrayList;
import java.util.Date;

public interface ILibraryStore {
	public void addBook(Book newBook);
	public void addMember(Member newMember);
	public Book getBook(String id);
	public Member getMember(String id);
	public boolean isSuspendedMember(String id);
	public void removeMember(String id);
	public void suspendMember(String id);
	public boolean canMemberBorrow(String memberId);
	public boolean returnItem(String memberId, int isbn);
	public Long lendItem(String memberId, int isbn);
	public Loan getLoan(long loanId);
	public void addLibraryItem(int isbn);
}
