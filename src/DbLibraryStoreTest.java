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
    void testAddAndGetAndDeleteMember() { //Test adding and retrieving a member
        String memberId = "1234";
        // Create a test member
        Member testMember = new Member(1234, "John", "Doe", 1, 9999, 0, 0, false, null);

        // Act
        store.addMember(testMember);
        Member retrieved = store.getMember(memberId);

        // Assert
        assertNotNull(retrieved);
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
        assertEquals(9999, retrieved.getSsn());

        //test deletion
        store.removeMember(memberId);
        Member deletedMember = store.getMember(memberId);
        assertNull(deletedMember, "Should be null after being deleted for excessive suspensions.");
    }

    @Test
    void testSuspendMember() { // Test member suspension logic
        String memberId = "1642";
        store.addMember(new Member(1642, "Jane", "Doe", 1, 8888, 0, 0, false, null));

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
}
