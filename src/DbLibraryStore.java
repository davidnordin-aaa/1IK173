import java.sql.*;
import java.time.LocalDate;
/*
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 */

public class DbLibraryStore implements ILibraryStore {

	//private static final Logger logger = LogManager.getLogger(DbLibraryStore.class)
	private static final String DB_URL = "jdbc:h2:./test;AUTO_SERVER=TRUE";
	private static final String USER = "sa";
	private static final String PASS = "";

	private Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public void initializeData() { //use this method when starting program
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			// Ensure tables exists
			stmt.execute("CREATE TABLE IF NOT EXISTS MEMBERS (" +
					"ID VARCHAR(4) PRIMARY KEY, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), " +
					"MEMBER_TYPE INTEGER, SSN BIGINT UNIQUE, DELAYED_RETURNS_COUNTER INTEGER DEFAULT 0, " +
					"SUSPENSION_COUNTER INTEGER DEFAULT 0, IS_SUSPENDED BOOLEAN DEFAULT FALSE, " +
					"SUSPENSION_END_DATE DATE)");

			stmt.execute("CREATE TABLE IF NOT EXISTS BOOKS (" +
					"ISBN INTEGER PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255), PUBLICATIONYEAR INTEGER)");

			// New table creations based on the provided H2 schema
			stmt.execute("CREATE TABLE IF NOT EXISTS LIBRARYITEMS (" +
					"COPY_ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
					"ISBN INTEGER REFERENCES BOOKS(ISBN), " +
					"IS_AVAILABLE BOOLEAN DEFAULT TRUE)");

			stmt.execute("CREATE TABLE IF NOT EXISTS LOANS (" +
					"LOAN_ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
					"MEMBER_ID VARCHAR(4) REFERENCES MEMBERS(ID), " +
					"COPY_ID BIGINT REFERENCES LIBRARYITEMS(COPY_ID), " +
					"LOAN_DATE DATE, " +
					"DUE_DATE DATE)");

			// Check if books need to be seeded
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM BOOKS");
			if (rs.next() && rs.getInt(1) == 0) {
				//logger.info("Seeding initial book data...");
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

				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (238103, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (238103, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (102938, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (102938, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (146123, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (146123, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (174213, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (174213, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (754247, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (754247, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (102938, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (102938, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (352783, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (352783, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (114267, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (114267, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (731246, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (731246, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (572359, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (572359, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (832575, TRUE)");
				stmt.execute("INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (832575, TRUE)");
			}
		} catch (SQLException e) {
			//logger.error("Database initialization failed: {}", e.getMessage());
		}
	}

	public boolean lendItem(String memberId, int isbn) {
		String findItemSql = "SELECT COPY_ID FROM LIBRARYITEMS WHERE ISBN = ? AND IS_AVAILABLE = TRUE LIMIT 1";
		String updateItemSql = "UPDATE LIBRARYITEMS SET IS_AVAILABLE = FALSE WHERE COPY_ID = ?";
		String insertLoanSql = "INSERT INTO LOANS (MEMBER_ID, COPY_ID, LOAN_DATE, DUE_DATE) VALUES (?, ?, ?, ?)";

		try (Connection conn = this.connect()) {
			conn.setAutoCommit(false); // Start transaction

			try (PreparedStatement findStmt = conn.prepareStatement(findItemSql)) {
				findStmt.setInt(1, isbn);
				ResultSet rs = findStmt.executeQuery();

				if (rs.next()) {
					long copyId = rs.getLong("COPY_ID");

					// Mark item as unavailable
					try (PreparedStatement updateStmt = conn.prepareStatement(updateItemSql)) {
						updateStmt.setLong(1, copyId);
						updateStmt.executeUpdate();
					}

					// Create loan record
					try (PreparedStatement insertStmt = conn.prepareStatement(insertLoanSql)) {
						insertStmt.setString(1, memberId);
						insertStmt.setLong(2, copyId);
						insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
						insertStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(15)));
						insertStmt.executeUpdate();
					}

					conn.commit(); // Commit transaction
					return true;
				}
			} catch (SQLException e) {
				conn.rollback(); // Rollback if any step fails
				System.out.println("Transaction failed, rolling back: " + e.getMessage());
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			System.out.println("Database error during lending: " + e.getMessage());
		}
		return false; // Item not available or error occurred
	}

	public boolean returnItem(String memberId, int isbn) {
		String findLoanSql = "SELECT l.LOAN_ID, l.COPY_ID, l.DUE_DATE FROM LOANS l " +
				"JOIN LIBRARYITEMS i ON l.COPY_ID = i.COPY_ID " +
				"WHERE l.MEMBER_ID = ? AND i.ISBN = ? LIMIT 1";
		String updateItemSql = "UPDATE LIBRARYITEMS SET IS_AVAILABLE = TRUE WHERE COPY_ID = ?";
		String deleteLoanSql = "DELETE FROM LOANS WHERE LOAN_ID = ?";
		String updateDelaySql = "UPDATE MEMBERS SET DELAYED_RETURNS_COUNTER = DELAYED_RETURNS_COUNTER + 1 WHERE ID = ?";

		try (Connection conn = this.connect()) {
			conn.setAutoCommit(false); // Start transaction

			try (PreparedStatement findStmt = conn.prepareStatement(findLoanSql)) {
				findStmt.setString(1, memberId);
				findStmt.setInt(2, isbn);
				ResultSet rs = findStmt.executeQuery();

				if (rs.next()) {
					long loanId = rs.getLong("LOAN_ID");
					long copyId = rs.getLong("COPY_ID");
					java.sql.Date dueDate = rs.getDate("DUE_DATE");

					// Check for delayed return
					if (java.sql.Date.valueOf(LocalDate.now()).after(dueDate)) {
						try (PreparedStatement delayStmt = conn.prepareStatement(updateDelaySql)) {
							delayStmt.setString(1, memberId);
							delayStmt.executeUpdate();
						}
					}

					// Mark item as available
					try (PreparedStatement updateStmt = conn.prepareStatement(updateItemSql)) {
						updateStmt.setLong(1, copyId);
						updateStmt.executeUpdate();
					}

					// Delete loan record
					try (PreparedStatement deleteStmt = conn.prepareStatement(deleteLoanSql)) {
						deleteStmt.setLong(1, loanId);
						deleteStmt.executeUpdate();
					}

					conn.commit(); // Commit transaction
					return true;
				}
			} catch (SQLException e) {
				conn.rollback();
				System.out.println("Transaction failed, rolling back: " + e.getMessage());
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			System.out.println("Database error during return: " + e.getMessage());
		}
		return false; // Loan record not found or error occurred
	}

	public boolean canMemberBorrow(String memberId) {
		String sql = "SELECT m.MEMBER_TYPE, COUNT(l.LOAN_ID) as active_loans " +
				"FROM MEMBERS m LEFT JOIN LOANS l ON m.ID = l.MEMBER_ID " +
				"WHERE m.ID = ? GROUP BY m.MEMBER_TYPE";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int type = rs.getInt("MEMBER_TYPE");
				int currentLoans = rs.getInt("active_loans");

				// Enforce limits: Undergrad (3), Master (5), PhD (7), Teacher (10) [cite: 27]
				return switch (type) {
					case 1 -> currentLoans < 3;  // Undergraduate
					case 2 -> currentLoans < 5;  // Postgraduate
					case 3 -> currentLoans < 7;  // PhD
					case 4 -> currentLoans < 10; // Teacher
					default -> false;
				};
			}
		} catch (SQLException e) {
			//logger.error("Error checking borrowing limits: {}", e.getMessage());
		}
		return false;
	}

	private void updateSuspensionStatus(String id, boolean status, LocalDate endDate) {
		String sql = "UPDATE MEMBERS SET is_suspended = ?, suspension_end_date = ? WHERE id = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setBoolean(1, status);
			pstmt.setDate(2, endDate != null ? Date.valueOf(endDate) : null);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			//logger.error("Failed to update suspension status for {}: {}", id, e.getMessage());
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
			//logger.info("Title '{}' added to database.", newBook.title);
		} catch (SQLException e) {
			//logger.error("Failed to add book {}: {}", newBook.ISBN, e.getMessage());
		}
	}

	@Override
	public void addMember(Member newMember) {
		String sql = "INSERT INTO MEMBERS (ID, FIRST_NAME, LAST_NAME, MEMBER_TYPE, SSN) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, String.valueOf(newMember.Id));
			pstmt.setString(2, newMember.FirstName);
			pstmt.setString(3, newMember.LastName);
			pstmt.setInt(4, newMember.MemberType);
			pstmt.setLong(5, newMember.Ssn); // Using Long to prevent SSN overflow
			pstmt.executeUpdate();
			//logger.info("Member {} registered successfully.", newMember.Id);
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
			//logger.error("Error retrieving book {}: {}", isbn, e.getMessage());
		}
		return book;
	}

	@Override
	public Member getMember(String id) {
		String sql = "SELECT * FROM Members WHERE id = ?";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Member member = new Member();
				member.Id = rs.getInt("id");
				member.MemberType = rs.getInt("member_type");
				member.Ssn = rs.getInt("ssn");
				member.DelayedReturnsCounter = rs.getInt("delayed_returns_counter");
				member.SuspensionCounter = rs.getInt("suspension_counter");
				member.IsSuspended = rs.getBoolean("is_suspended");
				member.SuspensionEndDate = rs.getDate("suspension_end_date");
				member.FirstName = rs.getString("first_name");
				member.LastName = rs.getString("last_name");
				return member;
			}
		} catch (SQLException e) {
			//logger.error("Error retrieving member {}: {}", id, e.getMessage());
		}
		return null;
	}

	@Override
	public boolean isSuspendedMember(String id) {
		String sql = "SELECT is_suspended, suspension_end_date FROM MEMBERS WHERE id = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, Integer.parseInt(id));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				boolean isSuspended = rs.getBoolean("is_suspended");
				Date endDate = rs.getDate("suspension_end_date");

				if (isSuspended && endDate != null && endDate.before(Date.valueOf(LocalDate.now()))) {
					updateSuspensionStatus(id, false, null);
					return false;
				}
				return isSuspended;
			}
		} catch (SQLException e) {
			//logger.error("Error checking suspension for {}: {}", id, e.getMessage());
		}
		return false;
	}

	@Override
	public void removeMember(String id) {
		String sql = "DELETE FROM MEMBERS WHERE id = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, id);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				//logger.info("Account for member {} has been deleted.", id);
			}
		} catch (SQLException e) {
			//logger.error("Error removing member {}: {}", id, e.getMessage());
		}
	}

	@Override
	public void suspendMember(String id) {
		String updateSql = "UPDATE MEMBERS SET IS_SUSPENDED = TRUE, " +
				"SUSPENSION_COUNTER = SUSPENSION_COUNTER + 1, " +
				"SUSPENSION_END_DATE = ? WHERE ID = ?";

		try (Connection conn = this.connect()) {
			LocalDate endDate = LocalDate.now().plusDays(15);
			try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
				pstmt.setDate(1, Date.valueOf(endDate));
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			}

			// Check for deletion requirement
			Member m = getMember(id);
			if (m.SuspensionCounter > 2) {
				//logger.warn("Member {} exceeded 2 suspensions. Deleting account.", id);
				removeMember(id);
			} else {
				//logger.info("Member {} suspended until {}", id, endDate);
			}
		} catch (SQLException e) {
			//logger.error("Suspension logic failed: {}", e.getMessage());
		}
	}
}
