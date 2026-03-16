import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class DbLibraryStoreMockito {
    public FileLibraryStore storeMock;
    public LibraryService cut;

    @BeforeEach
    public void setUp() {
        storeMock = mock(FileLibraryStore.class);
        cut = new LibraryService(storeMock);
    }



}
