import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class LibraryServiceMockitoTest {
    public FileLibraryStore storeMock;
    public LibraryService cut;

    @BeforeEach
    public void setUp() {
        storeMock = mock(FileLibraryStore.class);
        cut = new LibraryService(storeMock);
    }

    @Test
    void testRequestDeletionSuccess() {
        // Arrange: removeMember is called, then getMember returns null
        doNothing().when(storeMock).removeMember("1001");
        when(storeMock.getMember("1001")).thenReturn(null);

        // Act
        boolean result = cut.requestDeletion("1001");

        // Assert
        assertTrue(result);
        verify(storeMock).removeMember("1001");
        verify(storeMock).getMember("1001");
    }

    @Test
    void testExecuteLoanSuccess() {
        // Arrange: Item not borrowed, lend returns ID 55, getLoan returns the object
        when(storeMock.isAlreadyBorrowed("1001", 12345)).thenReturn(-1);
        when(storeMock.lendItem("1001", 12345)).thenReturn(55L);

        Loan mockLoan = new Loan(55L, 1001, 1, null, null);
        when(storeMock.getLoan(55L)).thenReturn(mockLoan);

        // Act
        Loan result = cut.executeLoan("1001", 12345);

        // Assert
        assertNotNull(result);
        assertEquals(55L, result.getLoanId());
        verify(storeMock).lendItem("1001", 12345);
    }

    @Test
    void testExecuteReturnFailure() {
        // Arrange: Simulate that the item was never borrowed (ID -1)
        when(storeMock.isAlreadyBorrowed("1001", 12345)).thenReturn(-1);

        // Act
        boolean result = cut.executeReturn("1001", 12345);

        // Assert
        assertFalse(result, "Return should fail if no active loan exists");
        verify(storeMock, never()).returnItem(anyString(), anyInt());
    }

    //executeLoan tests
    @Test
    void testValidExecuteLoan() { // Valid: Item is not borrowed, lending succeeds
        when(storeMock.isAlreadyBorrowed("1001", 111111)).thenReturn(-1);
        when(storeMock.lendItem("1001", 111111)).thenReturn(50L);
        when(storeMock.getLoan(50L)).thenReturn(new Loan(50L, 1001, 1, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(15))));

        Loan result = cut.executeLoan("1001", 111111);

        assertNotNull(result.toString(), "Loan should not be null on successful execution");
    }

    @Test
    void testInvalidExecuteLoan() { // Invalid: Item is already borrowed by this user
        when(storeMock.isAlreadyBorrowed("1001", 111111)).thenReturn(50); // Returns an existing loan ID
        Loan result = cut.executeLoan("1001", 111111);
        assertNull(result, "Loan should be null if the item is already borrowed");
    }

    @Test
    void testBoundaryExecuteLoan() { // Boundary: Item is not borrowed, but database fails to create loan (returns -1)
        when(storeMock.isAlreadyBorrowed("1001", 111111)).thenReturn(-1);
        when(storeMock.lendItem("1001", 111111)).thenReturn(-1L);

        Loan result = cut.executeLoan("1001", 111111);

        assertNull(result, "Loan should be null if the database fails to generate a loan ID");
    }

    //executeReturn tests
    @Test
    void testValidExecuteReturn() { // Valid: Loan exists, return item succeeds
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(75);
        when(storeMock.returnItem("1001", 222222)).thenReturn(true);

        boolean result = cut.executeReturn("1001", 222222);

        assertTrue(result, "Should return true when an existing loan is successfully returned");
    }

    @Test
    void testInvalidExecuteReturn() { // Invalid: No loan exists to return
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(-1);

        boolean result = cut.executeReturn("1001", 222222);

        assertFalse(result, "Should return false when attempting to return an unborrowed item");
    }

    @Test
    void testBoundaryExecuteReturn() { // Boundary: Loan exists, but database update fails during return
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(75);
        when(storeMock.returnItem("1001", 222222)).thenReturn(false);

        boolean result = cut.executeReturn("1001", 222222);

        assertFalse(result, "Should return false if the database fails to process the return");
    }

    //requestDeletion tests
    @Test
    void testValidRequestDeletion() { // Valid: Deletion succeeds and user is no longer found
        doNothing().when(storeMock).removeMember("1001");
        when(storeMock.getMember("1001")).thenReturn(null);

        boolean result = cut.requestDeletion("1001");

        assertTrue(result, "Should return true when member is successfully deleted and cannot be found");
    }

    @Test
    void testInvalidRequestDeletion() { // Invalid: Deletion runs, but user is still found in DB afterwards
        doNothing().when(storeMock).removeMember("1001");
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "Test", "User", 1, 123L, 0, 0, false, null));

        boolean result = cut.requestDeletion("1001");

        assertFalse(result, "Should return false if the member still exists after deletion attempt");
    }

    @Test
    void testBoundaryRequestDeletion() { // Boundary: The database throws an exception during the removal process
        doThrow(new RuntimeException("Database connection lost")).when(storeMock).removeMember("1001");

        boolean result = cut.requestDeletion("1001");

        assertFalse(result, "Should return false and catch the exception if database throws an error");
    }

    //borrow tests
    @Test
    void testValidBorrow() { // Valid: Book/Member exist, user says "yes", loan succeeds
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("111111")).thenReturn(new Book("Title", 111111, "Author", 2000));
        when(storeMock.isAlreadyBorrowed("1001", 111111)).thenReturn(-1);
        when(storeMock.lendItem("1001", 111111)).thenReturn(50L);
        when(storeMock.getLoan(50L)).thenReturn(new Loan(50L, 1001, 1, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(15))));

        boolean result = cut.borrow(111111, "1001", "yes");

        assertTrue(result, "Borrow should return true when execution is successful");
    }

    @Test
    void testInvalidBorrow() { // Invalid: The book does not exist in the database
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("111111")).thenReturn(null); // Book missing

        boolean result = cut.borrow(111111, "1001", "yes");

        assertFalse(result, "Borrow should return false if the requested book does not exist");
    }

    @Test
    void testBoundaryBorrow() { // Boundary: User inputs an empty string instead of "yes" or "no"
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("111111")).thenReturn(new Book("Title", 111111, "Author", 2000));

        boolean result = cut.borrow(111111, "1001", "");

        assertFalse(result, "Borrow should return false if the confirmation input is empty");
    }

    //returnBook tests
    @Test
    void testValidReturnBook() { // Valid: Book/Member exist, user says "yes", return succeeds
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("222222")).thenReturn(new Book("Title", 222222, "Author", 2000));
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(75);
        when(storeMock.returnItem("1001", 222222)).thenReturn(true);

        boolean result = cut.returnBook(222222, "1001", "yes");

        assertTrue(result, "ReturnBook should return true when execution is successful");
    }

    @Test
    void testInvalidReturnBook() { // Invalid: User confirms, but the execution fails (e.g., loan not found)
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("222222")).thenReturn(new Book("Title", 222222, "Author", 2000));
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(-1); // No active loan found

        boolean result = cut.returnBook(222222, "1001", "yes");

        assertFalse(result, "ReturnBook should return false if executeReturn fails");
    }

    @Test
    void testBoundaryReturnBook() { // Boundary: User inputs mixed-case confirmation string
        when(storeMock.getMember("1001")).thenReturn(new Member(1001, "A", "B", 1, 1L, 0, 0, false, null));
        when(storeMock.getBook("222222")).thenReturn(new Book("Title", 222222, "Author", 2000));
        when(storeMock.isAlreadyBorrowed("1001", 222222)).thenReturn(75);
        when(storeMock.returnItem("1001", 222222)).thenReturn(true);

        boolean result = cut.returnBook(222222, "1001", "yEs"); // Mixed case

        assertTrue(result, "ReturnBook should return true and ignore case sensitivity on confirmation");
    }
}


