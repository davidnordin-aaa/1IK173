import java.sql.*;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbLibraryStore implements ILibraryStore {

	private static final Logger logger = LogManager.getLogger(DbLibraryStore.class);
	private static final String DB_URL = "jdbc:h2:./test;AUTO_SERVER=TRUE";
	private static final String USER = "sa";
	private static final String PASS = "";

	//connect to database
	private Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public void initializeData() { //use this method when starting program
		logger.info("Initializing database, seeding data");

		//ensure library infrastructure exists
		try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
			// Ensure tables exists, prepared statement prevents SQL injection
			stmt.execute("CREATE TABLE IF NOT EXISTS MEMBERS (" +
					"ID INTEGER PRIMARY KEY, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), " +
					"MEMBER_TYPE INTEGER, SSN BIGINT UNIQUE, DELAYED_RETURNS_COUNTER INTEGER DEFAULT 0, " +
					"SUSPENSION_COUNTER INTEGER DEFAULT 0, IS_SUSPENDED BOOLEAN DEFAULT FALSE, " +
					"SUSPENSION_END_DATE DATE)");

			stmt.execute("CREATE TABLE IF NOT EXISTS BOOKS (" +
					"ISBN INTEGER PRIMARY KEY, TITLE VARCHAR(255), AUTHOR VARCHAR(255), PUBLICATIONYEAR INTEGER)");

			stmt.execute("CREATE TABLE IF NOT EXISTS LIBRARYITEMS (" +
					"COPY_ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
					"ISBN INTEGER REFERENCES BOOKS(ISBN), " +
					"IS_AVAILABLE BOOLEAN DEFAULT TRUE)");

			stmt.execute("CREATE TABLE IF NOT EXISTS LOANS (" +
					"LOAN_ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
					"MEMBER_ID INTEGER REFERENCES MEMBERS(ID), " +
					"COPY_ID BIGINT REFERENCES LIBRARYITEMS(COPY_ID), " +
					"LOAN_DATE DATE, " +
					"DUE_DATE DATE)");

			//Check if books need to be seeded
			//rs acts as the bridge between db and java application, retrieves data from db
			//executeQuery performs SQL statements
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM BOOKS");
			if (rs.next() && rs.getInt(1) == 0) {
				//add ten books into library db
				logger.info("Seeding initial book data...");
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

				//add 2 copies of each books into library db
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
				logger.info("Database seeding successful");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Database initialization failed: {}", e.getMessage());
		}
	}

	//checks if a member already has borrowed a library item of an ISBN.
	//Returns loanId if one is found so item is borrowed, otherwise return -1
	public int isAlreadyBorrowed(String memberId, int isbn) {
		//String updateItemSql = "UPDATE LIBRARYITEMS SET IS_AVAILABLE = FALSE WHERE COPY_ID = ?";
		String sql = "SELECT LOAN_ID from TEST.PUBLIC.LOANS\n" +
				"join MEMBERS on LOANS.MEMBER_ID = MEMBERS.ID\n" +
				"join LIBRARYITEMS on LOANS.COPY_ID = LIBRARYITEMS.COPY_ID\n" +
				"where MEMBER_ID = ? and ISBN = ? limit 1";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, Integer.parseInt(memberId));
			pstmt.setInt(2, isbn);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("LOAN_ID");
			}
			else{
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Transaction failed, rolling back: " + e.getMessage());
		}
		return -1;
	}

	//automatically updates suspension status when called by is_Suspended
	//changes is_Suspended to true for 15 days when applying suspension
	//changes is_Suspended to false and suspension_end_date to null when removing suspension
	private void updateSuspensionStatus(String id, boolean status, LocalDate endDate) {
		String sql = "UPDATE MEMBERS SET IS_SUSPENDED = ?, SUSPENSION_END_DATE = ? WHERE ID = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setBoolean(1, status);
			pstmt.setDate(2, endDate != null ? Date.valueOf(endDate) : null);
			pstmt.setInt(3, Integer.parseInt(id));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error updating suspension status: " + e.getMessage());
		}
	}

	//extract data from SQL resultset, then construct corresponding objects. "Translation"-logic
	private Book mapBook(ResultSet rs) throws SQLException {
		return new Book(
				rs.getString("TITLE"),
				rs.getInt("ISBN"),
				rs.getString("AUTHOR"),
				rs.getInt("PUBLICATIONYEAR")
		);
	}

	//extract data from SQL resultset, then construct corresponding objects. "Translation"-logic
	private Member mapMember(ResultSet rs) throws SQLException {
		return new Member(
				rs.getInt("ID"),
				rs.getString("FIRST_NAME"),
				rs.getString("LAST_NAME"),
				rs.getInt("MEMBER_TYPE"),
				rs.getLong("SSN"),
				rs.getInt("DELAYED_RETURNS_COUNTER"),
				rs.getInt("SUSPENSION_COUNTER"),
				rs.getBoolean("IS_SUSPENDED"),
				rs.getDate("SUSPENSION_END_DATE")
		);
	}

	//adds library item into the db table
	public void addLibraryItem(int isbn) {
		String sql = "INSERT INTO LIBRARYITEMS (ISBN, IS_AVAILABLE) VALUES (?, TRUE)";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, isbn);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error adding ISBN {}: {}", isbn, e.getMessage());
		}
	}

	//gets data from a specific loanId such as memberId and due date
	public Loan getLoan(long loanId) {
		String sql = "SELECT * FROM LOANS WHERE LOAN_ID = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, loanId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Loan(
						rs.getLong("LOAN_ID"),
						rs.getInt("MEMBER_ID"),
						rs.getInt("COPY_ID"),
						rs.getDate("LOAN_DATE"),
						rs.getDate("DUE_DATE")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Transaction failed, rolling back: " + e.getMessage());
		}
		return null;
	}

	//lending an item, all SQL statements must succeed or nothing will be applied
	public Long lendItem(String memberId, int isbn) {
		logger.debug("Attempting to lend ISBN {} to member ID {}", isbn, memberId);
		//finds one particular item of the ISBN with LIMIT 1, if none found then return -1
		String findItemSql = "SELECT COPY_ID FROM LIBRARYITEMS WHERE ISBN = ? AND IS_AVAILABLE = TRUE LIMIT 1";
		String updateItemSql = "UPDATE LIBRARYITEMS SET IS_AVAILABLE = FALSE WHERE COPY_ID = ?";
		String insertLoanSql = "INSERT INTO LOANS (MEMBER_ID, COPY_ID, LOAN_DATE, DUE_DATE) VALUES (?, ?, ?, ?)";

		try (Connection conn = this.connect()) {
			conn.setAutoCommit(false);
			//do not commit any changes until explicitly told
			//if system crashes after marking item as "borrowed" but before creating a loan record
			//then the book would disappear from library inventory and db

			try (PreparedStatement findStmt = conn.prepareStatement(findItemSql)) {
				findStmt.setInt(1, isbn);
				ResultSet rs = findStmt.executeQuery();
				//finds library item in db through SQL, extracts a COPY_ID if an item is found

				if (rs.next()) {
					long copyId = rs.getLong("COPY_ID");

					try (PreparedStatement updateStmt = conn.prepareStatement(updateItemSql)) {
						updateStmt.setLong(1, copyId);
						updateStmt.executeUpdate();
						//updates availabilty status for the item to be borrowed
					}

					//create a loan record and update object details
					try (PreparedStatement insertStmt = conn.prepareStatement(insertLoanSql, new String[]{"LOAN_ID"})) {
						insertStmt.setString(1, memberId);
						insertStmt.setLong(2, copyId);
						insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
						insertStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(15)));
						insertStmt.executeUpdate();

						//db sends back the LOAN_ID created in the previous step
						try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								long loanId = generatedKeys.getLong(1);
								//captures this ID to return it to the calling method
								logger.info("Succesfully lent item (ISBN: {}) to member {}", isbn, memberId);
								conn.commit(); //explicitly tell code to commit and save all changes
								return loanId;
							}else{
								logger.warn("Lending failed, ISBN {} not available", isbn);
							}
						}
					}
				}
			} catch (SQLException e) {
				logger.warn("Transaction failed, rolling back: {}", e.getMessage());
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Transaction failed, rolling back: {}", e.getMessage());
		}
		return -1L; // Item not available or error occurred
	}

	//processing logic for a "transaction" of returning libraryItem
	public boolean returnItem(String memberId, int isbn) {
		logger.debug("Processing return for ISBN {} from member {}", isbn, memberId);
		//uses join between LOANS and LIBRARYITEMS to get necessary details from primary keys and foreign keys
		//join to verify that a member has a copy of a specific library item borrowed out
		String findLoanSql = "SELECT l.LOAN_ID, l.COPY_ID, l.DUE_DATE FROM LOANS l " +
				"JOIN LIBRARYITEMS i ON l.COPY_ID = i.COPY_ID " +
				"WHERE l.MEMBER_ID = ? AND i.ISBN = ? LIMIT 1";
		String updateItemSql = "UPDATE LIBRARYITEMS SET IS_AVAILABLE = TRUE WHERE COPY_ID = ?";
		String deleteLoanSql = "DELETE FROM LOANS WHERE LOAN_ID = ?";
		String updateDelaySql = "UPDATE MEMBERS SET DELAYED_RETURNS_COUNTER = DELAYED_RETURNS_COUNTER + 1 WHERE ID = ?";

		try (Connection conn = this.connect()) {
			conn.setAutoCommit(false); // Start transaction

			try (PreparedStatement findStmt = conn.prepareStatement(findLoanSql)) {
				findStmt.setInt(1, Integer.parseInt(memberId));
				findStmt.setInt(2, isbn);
				ResultSet rs = findStmt.executeQuery();

				if (rs.next()) {
					long loanId = rs.getLong("LOAN_ID");
					long copyId = rs.getLong("COPY_ID");
					java.sql.Date dueDate = rs.getDate("DUE_DATE");

					// Check for delayed return
					//if one day is past, trigger update to DELAYED_RETURNS_COUNTER to be used elsewhere
					if (java.sql.Date.valueOf(LocalDate.now()).after(dueDate)) {
						logger.warn("Delayed return detected for member {}, increasing delay counter.", memberId);
						try (PreparedStatement delayStmt = conn.prepareStatement(updateDelaySql)) {
							findStmt.setInt(1, Integer.parseInt(memberId));
							delayStmt.executeUpdate();
						}
					}

					//mark item as available
					//changes is_Available to true, item is visible and available for next user
					try (PreparedStatement updateStmt = conn.prepareStatement(updateItemSql)) {
						updateStmt.setLong(1, copyId);
						updateStmt.executeUpdate();
					}

					//delete loan record
					//if any of these steps fails, catch-block rolls back any changes made
					//so a member doesnt "return" an item if the book actually isnt available again
					try (PreparedStatement deleteStmt = conn.prepareStatement(deleteLoanSql)) {
						deleteStmt.setLong(1, loanId);
						deleteStmt.executeUpdate();
					}
					logger.info("Item {} successfully returned by member {}", isbn, memberId);
					conn.commit(); // Commit transaction
					return true;
				}
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				System.out.println("Transaction failed, rolling back: " + e.getMessage());
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error returning ISBN {}: {}", isbn, e.getMessage());
		}
		return false; // Loan record not found or error occurred
	}

	//uses left join to and count to see how many active loans a member has
	//applies a switch expression to enforce borrowing limits based on memberType limit
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

				// Enforce borrowing limits
				return switch (type) {
					case 1 -> currentLoans < 3;  // Undergraduate
					case 2 -> currentLoans < 5;  // Postgraduate
					case 3 -> currentLoans < 7;  // PhD
					case 4 -> currentLoans < 10; // Teacher
					default -> false;
				};
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error checking borrowing limits: {}", e.getMessage());
		}
		return false;
	}

	//gets all existing IDs into a hashset, then iterates starting from 1000 to 9999 to find first available number
	private int generateUniqueId(Connection conn) throws SQLException {
		String query = "SELECT ID FROM MEMBERS";
		java.util.Set<Integer> existingIds = new java.util.HashSet<>();

		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				existingIds.add(rs.getInt("ID"));
			}
		}

		// Attempt to find a unique 4-digit number (1000 - 9999)
		for (int i = 1000; i <= 9999; i++) {
			if (!existingIds.contains(i)) {
				return i;
			}
		}
		throw new SQLException("No unique IDs available in the 4-digit range.");
	}

	//insert a new row into BOOKS table using a prepared statement
	@Override
	public void addBook(Book newBook) {
		String sql = "INSERT INTO books (isbn, title, author, publicationyear) VALUES (?, ?, ?, ?)";

		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, newBook.getISBN());
			pstmt.setString(2, newBook.getTitle());
			pstmt.setString(3, newBook.getAuthor());
			pstmt.setInt(4, newBook.getYear());
			pstmt.executeUpdate();
			logger.info("Title '{}' added to database.", newBook.title);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Failed to add book {}: {}", newBook.ISBN, e.getMessage());
		}
	}

	//creates a new member, use generateUniqueId to create unused 4-digit number int
	//inserts default values into member too (0 delays, 0 suspensions, not suspended)
	@Override
	public void addMember(Member newMember) {
		String sql = "INSERT INTO MEMBERS (ID, FIRST_NAME, LAST_NAME, MEMBER_TYPE, SSN, " +
				"DELAYED_RETURNS_COUNTER, SUSPENSION_COUNTER, IS_SUSPENDED, SUSPENSION_END_DATE) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = this.connect()) {
			//generate unique 4-digit ID
			int uniqueId = generateUniqueId(conn);

			//default values for new members
			int initialDelayedReturns = 0;
			int initialSuspensionCounter = 0;
			boolean initialSuspensionStatus = false;

			//fills out object details to db
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, uniqueId);
				pstmt.setString(2, newMember.getFirstName());
				pstmt.setString(3, newMember.getLastName());
				pstmt.setInt(4, newMember.getMemberType());
				pstmt.setLong(5, newMember.getSsn());
				pstmt.setInt(6, initialDelayedReturns);
				pstmt.setInt(7, initialSuspensionCounter);
				pstmt.setBoolean(8, initialSuspensionStatus);
				pstmt.setNull(9, Types.DATE); // Suspension end date is null for new members

				pstmt.executeUpdate();

				newMember.setId(uniqueId); // Optional way to update object
			}
		} catch (SQLException e) {
			logger.error("Error adding member: {}", e.getMessage());
		}
	}

	//Queries books by ISBN. If found, use mapBook to convert db row into java Book object
	@Override
	public Book getBook(String id) {
		String sql = "SELECT * FROM BOOKS WHERE ISBN = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, Integer.parseInt(id));
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapBook(rs);
				}
			}
		} catch (SQLException | NumberFormatException e) {
			logger.error("Error getting book: {}", e.getMessage());
		}
		return null;
	}

	//retrieve a members profile from db using mapMember to transform SQL into a member object
	@Override
	public Member getMember(String id) {
		String sql = "SELECT * FROM MEMBERS WHERE ID = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, Integer.parseInt(id));
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapMember(rs);
				}
			}
		} catch (SQLException | NumberFormatException e) {
			logger.error("Error getting member: {}", e.getMessage());
		}
		return null;
	}

	//checks if is_Suspended is true
	//returns false if not suspended, returns true if suspended
	//if member is suspended and end date has passed then update is_Suspended to false and endDate to null
	@Override
	public boolean isSuspendedMember(String id) {
		Member member = getMember(id);
		if (member == null) return false;

		if (member.isSuspended()) {
			Date endDate = (Date) member.getSuspensionEndDate();
			if (endDate != null && endDate.before(Date.valueOf(LocalDate.now()))) {
				updateSuspensionStatus(id, false, null);
				return false;
			}
			return true;
		}
		return false;
	}

	//executes a delete statement in db, used both for manual requests and 3-suspensions violations
	@Override
	public void removeMember(String id) {
		String sql = "DELETE FROM MEMBERS WHERE ID = ?";
		try (Connection conn = this.connect();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error removing member: {}", e.getMessage());
		}
	}

	//updates suspension status and adds 15 days for suspension starting this day
	@Override
	public void suspendMember(String id) {
		String updateSql = "UPDATE MEMBERS SET IS_SUSPENDED = TRUE, " +
				"SUSPENSION_COUNTER = SUSPENSION_COUNTER + 1, " +
				"SUSPENSION_END_DATE = ? WHERE ID = ?";
		try (Connection conn = this.connect()) {
			//gets todays date from the pc system, adds 15 days then converts format to fit SQL
			Date endDate = Date.valueOf(LocalDate.now().plusDays(15));
			try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
				pstmt.setDate(1, endDate);
				pstmt.setInt(2, Integer.parseInt(id));
				pstmt.executeUpdate();
			}

			Member updatedMember = getMember(id);
			//logic for enforcing suspensions, if counter exceeds 2 then delete member
			if (updatedMember != null && updatedMember.getSuspensionCounter() > 2) {
				logger.fatal("Member {} deleted due to repeated violations", id);
				removeMember(id);
			}
		} catch (SQLException e) {
			logger.error("Error during member suspension: {}", e.getMessage());
		}
	}
}
