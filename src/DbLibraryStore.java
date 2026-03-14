import java.sql.*;

public class DbLibraryStore implements ILibraryStore {

	private static final String DB_URL = "\"jdbc:h2:./test;AUTO_SERVER=TRUE\"";
	private static final String USER = "sa";
	private static final String PASS = "";

	private Connection connect() throws SQLException {
		try {
			Class.forName("org.h2.Driver"); // Force the JVM to load the H2 driver class
		} catch (ClassNotFoundException e) {
			System.out.println("H2 Driver not found in Classpath: " + e.getMessage());
		}
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public void initializeData() { //use this method when starting program
		String checkSql = "SELECT COUNT(*) FROM books";
		try (Connection conn = this.connect();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(checkSql)) {

			if (rs.next() && rs.getInt(1) == 0) { //manually insert 10 books
				stmt.execute("INSERT INTO books VALUES (238103, 'The Witcher - The Last Wish', 'Andrzej Sapkowski', 1993)");
				stmt.execute("INSERT INTO books VALUES (102938, '1984', 'George Orwell', 1949)");
				stmt.execute("INSERT INTO books VALUES (146123, 'The Hobbit', 'J.R.R. Tolkien', 1937)");
				stmt.execute("INSERT INTO books VALUES (174213, 'Dune', 'Frank Herbert', 1965)");
				stmt.execute("INSERT INTO books VALUES (754247, 'The Lord of the Rings', 'J.R.R. Tolkien', 1959)");
				stmt.execute("INSERT INTO books VALUES (352783, 'Crime and Punishment', 'Fyodor Dostoevsky', 1866)");
				stmt.execute("INSERT INTO books VALUES (114267, 'Animal Farm', 'George Orwell', 1945)");
				stmt.execute("INSERT INTO books VALUES (731246, 'And Then There Were None', 'Agatha Christie', 1939)");
				stmt.execute("INSERT INTO books VALUES (572359, 'Manufacturing Consent', 'Edward S. Herman, Noam Chomsky', 1988)");
				stmt.execute("INSERT INTO books VALUES (832575, 'A Game of Thrones', 'George R. R. Martin', 1996)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addBook(Book newBook) {
		String sql = "INSERT INTO books (isbn, title, author, publicationyear) VALUES (?, ?, ?, ?)";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, newBook.ISBN);
			pstmt.setString(2, newBook.title);
			pstmt.setString(3, newBook.author);
			pstmt.setInt(4, newBook.year);

			pstmt.executeUpdate();
			System.out.println("Title registered successfully.");
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
	}

	@Override
	public void addMember(Member newMember) {
		String sql = "INSERT INTO Members (id, name, member_type, ssn, delayed_returns_counter, suspension_counter," +
				"is_suspended, suspension_end_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, newMember.Id);
			pstmt.setString(2, newMember.Name);
			pstmt.setInt(3, newMember.MemberType);
			pstmt.setInt(4, newMember.Ssn);
			pstmt.setInt(5, newMember.DelayedReturnsCounter);
			pstmt.setInt(6, newMember.SuspensionCounter);
			pstmt.setBoolean(7, newMember.isSuspended());
			pstmt.setDate(8, (Date) newMember.SuspensionEndDate);

			pstmt.executeUpdate();
			System.out.println("Member registered successfully.");
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}

	}

	@Override
	public Book getBook(String id) {
		String sql = "SELECT * FROM books WHERE isbn = ?";
		Book book = new Book();

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				book.title = rs.getString("title");
				book.author = rs.getString("author");
				book.year = rs.getInt("publicationyear");
				book.ISBN = rs.getInt("isbn");
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving book: " + e.getMessage());
		}
		System.out.println("DbLibraryStore::getBook()");
		return book;
	}

	@Override
	public Member getMember(String id) {
		String sql = "SELECT * FROM Members WHERE id = ?";
		Member member = new Member();

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				member.Id = rs.getInt("id");
				member.Name = rs.getString("name");
				member.MemberType = rs.getInt("member_type");
				member.Ssn = rs.getInt("ssn");
				member.DelayedReturnsCounter = rs.getInt("delayed_returns_counter");
				member.SuspensionCounter = rs.getInt("suspension_counter");
				member.IsSuspended = rs.getBoolean("is_suspended");
				member.SuspensionEndDate = rs.getDate("suspension_end_date");
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving member: " + e.getMessage());
		}

		System.out.println("DbLibraryStore::getMember()");
		return member;
	}

	@Override
	public boolean isSuspendedMember(String id) {
		String sql = "SELECT is_suspended FROM Members WHERE id = ?";
		boolean suspended = false;

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				suspended = rs.getBoolean("is_suspended");
			}
		} catch (SQLException e) {
			System.out.println("Error retrieving suspension status: " + e.getMessage());
		}
		return suspended;
	}

	@Override
	public void removeMember(String id) {
		String sql = "DELETE FROM Members WHERE id = ?";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {// The member code is now "released" for future use
				System.out.println("Member " + id + " has been removed from the system.");
			}
		} catch (SQLException e) {
			System.out.println("Error removing member: " + e.getMessage());
		}
	}

	@Override
	public void suspendMember(String id) {
		// First, increment the suspension counter and set status to active
		String updateSql = "UPDATE Members SET is_suspended = TRUE, suspension_counter = suspension_counter + 1 WHERE id = ?";
		// Check if the member has exceeded the suspension limit
		String checkSql = "SELECT suspension_counter FROM Members WHERE id = ?";

		try (Connection conn = this.connect();
			 PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql);
			 PreparedStatement pstmtCheck = conn.prepareStatement(checkSql)) {

			int memberId = Integer.parseInt(id);

			// Apply the 15-day suspension
			pstmtUpdate.setInt(1, memberId);
			pstmtUpdate.executeUpdate();

			// Check if they have been suspended more than twice
			pstmtCheck.setInt(1, memberId);
			ResultSet rs = pstmtCheck.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("suspension_counter");
				if (count > 2) {
					System.out.println("Member has been suspended more than twice. Deleting account...");
					removeMember(id);
				} else {
					System.out.println("Member " + id + " is now suspended for 15 days.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error processing suspension: " + e.getMessage());
		}
	}
}
