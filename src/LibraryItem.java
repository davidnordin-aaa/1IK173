public class LibraryItem {
    public int CopyID;
    public int ISBN;
    public boolean IsAvailable;

    public LibraryItem(int copyID, int ISBN, boolean isAvailable) {
        this.CopyID = copyID;
        this.ISBN = ISBN;
        this.IsAvailable = isAvailable;
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
