import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DbLibraryStoreTest {
    private DbLibraryStore store;

    @BeforeEach
    void setUp() {
        store = new DbLibraryStore();
        store.initializeData(); // Ensure tables and sample books exist
    }

    @Test
    void testAddAndGetMember() { //Test adding and retrieving a member
        // Create a test member
        Member testMember = new Member("John Doe", 1234, 1, 9999, 0, 0, false, null);

        // Act
        store.addMember(testMember);
        Member retrieved = store.getMember("1234");

        // Assert
        assertNotNull(retrieved);
        assertEquals("John Doe", retrieved.getName());
        assertEquals(9999, retrieved.getSsn());
    }

    @Test
    void testSuspendMember() { // Test member suspension logic
        String memberId = "1642";
        store.addMember(new Member("Jane Doe", 1642, 1, 8888, 0, 0, false, null));

        // Act: Suspend the member once
        store.suspendMember(memberId);

        // Assert: Check status
        assertTrue(store.isSuspendedMember(memberId));

        // Act: Suspend three times total (should trigger deletion)
        store.suspendMember(memberId);
        store.suspendMember(memberId);

        // Assert: Member should be removed from the database
        Member deletedMember = store.getMember(memberId);
        assertNull(deletedMember.Name); // Check if the returned object is empty
    }
}
