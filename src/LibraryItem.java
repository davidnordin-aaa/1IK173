public class LibraryItem {
    int CopyID;
    int ISBN;
    boolean IsAvailable;

    public LibraryItem(int copyID, int ISBN, boolean isAvailable) {
        CopyID = copyID;
        this.ISBN = ISBN;
        IsAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void setAvailable(boolean available) {
        IsAvailable = available;
    }

    public int getCopyID() {
        return CopyID;
    }

    public int getISBN() {
        return ISBN;
    }
}
