import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Date;

public class DbLibraryStoreTest {
    private DbLibraryStore store;
/*
    public FileLibraryStore fls;
    public PurchaseManager cut;

    @BeforeEach
    public void setUp() {
        fls = mock(FileLibraryStore.class);
        cut = new PurchaseManager(ps);
    }
 */

    @BeforeEach
    void setUp() {
        store = new DbLibraryStore();
        store.initializeData(); // Ensure tables and sample books exist
    }

    @Test
    void testAddAndGetBook() {
        // Arrange
        Book testBook = new Book("Test Driven Development", 987654, "Kent Beck", 2002);

        // Act
        store.addBook(testBook);
        Book retrieved = store.getBook("987654");

        // Assert
        assertNotNull(retrieved);
        assertEquals("Test Driven Development", retrieved.getTitle());
        assertEquals("Kent Beck", retrieved.getAuthor());
        assertEquals(987654, retrieved.getISBN());
    }

    @Test
    void testAddAndGetAndDeleteMember() { //Test adding and retrieving a member
        String memberId = "1234";
        // Create a test member
        Member testMember = new Member(1234, "John", "Doe", 1, 9999L, 0, 0, false, null);

        // Act
        store.addMember(testMember);
        Member retrieved = store.getMember(memberId);

        // Assert
        assertNotNull(retrieved);
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
        assertEquals(9999L, retrieved.getSsn());

        //test deletion
        store.removeMember(memberId);
        Member deletedMember = store.getMember(memberId);
        assertNull(deletedMember, "Should be null after being deleted for excessive suspensions.");
    }

    @Test
    void testSuspendMember() { // Test member suspension logic
        String memberId = "1642";
        store.addMember(new Member(1642, "Jane", "Doe", 1, 8888L, 0, 0, false, null));

        // Suspend the member once
        store.suspendMember(memberId);
        assertTrue(store.isSuspendedMember(memberId));

        //Suspend two more times, trigger deletion
        store.suspendMember(memberId);
        store.suspendMember(memberId);

        //Member record should no longer exist in the database
        Member deletedMember = store.getMember(memberId);

        // Check if the entire object is null
        assertNull(deletedMember, "Should be null after being deleted for excessive suspensions.");
    }

    @Test
    void testLendAndReturnItem() {
        // Arrange
        store.clearDatabase();
        store.initializeData();
        String memberId = "5555";
        int isbn = 999999;

        store.addBook(new Book("Test Book", isbn, "Author", 2024));
        store.addLibraryItem(isbn);
        store.addMember(new Member(5555, "Alice", "Smith", 1, 7777L, 0, 0, false, null));

        Long loanId = store.lendItem(memberId, isbn);

        // Assert
        assertNotEquals(-1L, loanId, "Lending failed. Check if LIBRARYITEMS table has an available copy for ISBN " + isbn);

        // Act - Return
        boolean returnSuccess = store.returnItem(memberId, isbn);

        // Assert - Return
        assertTrue(returnSuccess, "Should successfully return the borrowed book.");
    }

    @Test
    void testCanMemberBorrowLimits() {
        store.clearDatabase();
        store.initializeData();
        // Arrange: Undergrad (Type 1) has a limit of 3 books
        String memberId = "9000";
        int isbn = 238103; // Witcher (2 copies available in seed)
        store.addLibraryItem(isbn);
        store.addLibraryItem(isbn);
        store.addLibraryItem(146123);

        store.addMember(new Member(9000, "Bob", "Builder", 1, 6666L, 0, 0, false, null));

        // Act & Assert
        assertTrue(store.canMemberBorrow(memberId), "New member should be able to borrow.");

        store.lendItem(memberId, isbn); // 1
        store.lendItem(memberId, isbn); // 2
        store.lendItem(memberId, 146123); // 3 (The Hobbit)

        assertFalse(store.canMemberBorrow(memberId), "Undergrad should not be able to borrow a 4th book.");
    }

    @Test
    void testGetLoan() {
        // Arrange
        store.clearDatabase();
        store.initializeData();

        String memberId = "8888";
        int isbn = 174213; // Dune
        store.addLibraryItem(isbn);
        store.addMember(new Member(8888, "Charlie", "Brown", 2, 4444L, 0, 0, false, null));

        // Act
        long generatedId = store.lendItem(memberId, isbn);

        // Assert
        assertNotEquals(-1, generatedId, "Lending should return a valid ID, not -1");

        Loan activeLoan = store.getLoan(generatedId);
        assertNotNull(activeLoan, "Loan should be retrievable by the returned ID");
        assertEquals(8888, activeLoan.getMemberId());
        assertNotNull(activeLoan.getDueDate());
    }
}
