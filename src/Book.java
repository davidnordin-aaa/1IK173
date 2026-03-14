public class Book {
	public String title;
	public String author;
	public int year;
	public int ISBN;

	public Book(){}

	public Book(String title, int ISBN, String author, int year) {
		this.title = title;
		this.ISBN = ISBN;
		this.author = author;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int ISBN) {
		this.ISBN = ISBN;
	}
}
