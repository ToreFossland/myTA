/*
File: DBBooking.java      
Date 	 	Author 	Changes
--------------------------------------------
Feb 28 19 	David 	Created
Mar 01 19   David	Creates booking
Mar 01 19   Tore	Inserts Student email
Mar 01 19	Tore 	Decrement avaible places and checks if greater than 0
Mar 02 19   Tore    Added function for database info gathering to halltime objects
*/

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import gui.App;
import halltimes.Booking;
import halltimes.Halltime;
import user.User;

public class DBBooking extends DBConnection {
	
	private static ArrayList<Booking> bookingsTA = new ArrayList<Booking>(); // Fjerne booking fra denne on confirm
	private static ArrayList<Integer> weeksTA = new ArrayList<Integer>();
	private static ArrayList<Booking> availableBookingsTA = new ArrayList<Booking>();
	private static ArrayList<Integer> availableWeeksTA = new ArrayList<Integer>();

	private static ArrayList<Booking> bookingsStudent = new ArrayList<Booking>();
	private static ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
	private static ArrayList<Booking> availableBookingsStudent = new ArrayList<Booking>();
	private static ArrayList<Integer> availableWeeksStudent = new ArrayList<Integer>();

//	//ny HallTimeID
//	public static int newHtId() throws Exception {
//
//		int htID = 0;
//		try {
//			Connection con = getConnection();
//			PreparedStatement statement = con.prepareStatement("SELECT * FROM HallTime ORDER BY idHallTime DESC LIMIT 1");
//			ResultSet forrigeId = statement.executeQuery();
//			forrigeId.next();
//			htID = forrigeId.getInt("idHallTime") + 1;
//			
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return htID;
//	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * ArrayList<Halltime> halltimes = new ArrayList<Halltime>();
	 * 
	 * LocalTime timeStart = LocalTime.of(10, 00, 00); LocalTime timeEnd =
	 * LocalTime.of(14, 00, 00); halltimes.add(new Halltime("TDT4140", 5, 3,
	 * timeStart, timeEnd,15)); timeStart = LocalTime.of(13, 00, 00); timeEnd =
	 * LocalTime.of(15, 00, 00); halltimes.add(new Halltime("TDT4140", 5, 3,
	 * timeStart, timeEnd, 13)); supervisorAddHalltime(halltimes, 20); }
	 */

	static Connection con = null;
	static PreparedStatement statement = null;
	static ResultSet result = null;

	// Sjekker om halltid ligger inne
	public static boolean halltimeExists(Halltime halltime) {

		boolean eksisterer = false;
		try {

			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			statement = con.prepareStatement("SELECT Course_courseCode, week, day, timeStart, timeEnd FROM HallTime");
			result = statement.executeQuery();

			while (result.next() && eksisterer == false) {
				if (Objects.equals(result.getString("Course_courseCode"), halltime.getCourseCode())
						&& Objects.equals(result.getInt("week"), halltime.getWeek())
						&& Objects.equals(result.getInt("day"), halltime.getDay())
						&& Objects.equals(LocalTime.parse(result.getString("timeStart")).toString(),
								halltime.getTimeStart().toString())
						&& Objects.equals(LocalTime.parse(result.getString("timeEnd")).toString(),
								halltime.getTimeEnd().toString())) {
					eksisterer = true;
				}
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		// System.out.println(eksisterer);
		return eksisterer;

	}

	// Henter availableplaces
	public static int getAvailablePlaces(Halltime halltime) {

		int availablePlaces = 0;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			String coursecode = halltime.getCourseCode().toUpperCase();
			statement = con.prepareStatement(String.format(
					"SELECT availablePlaces " + " FROM HallTime " + " WHERE Course_courseCode = '%s' "
							+ " AND week = '%s' " + "	AND day = '%s' " + " AND timeStart = '%s' "
							+ " AND timeEnd = '%s' ",
					coursecode, halltime.getWeek(), halltime.getDay(), halltime.getTimeStart(), halltime.getTimeEnd()));

			result = statement.executeQuery();
			result.next();
			availablePlaces = result.getInt("availablePlaces");
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return availablePlaces;
	}

	// Maa skrive inn tiden paa format "00:00:00";
	// Interval in minutes
	public static void supervisorAddHalltime(ArrayList<Halltime> halltimes, int interval) {

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			statement = con.prepareStatement(
					"REPLACE INTO HallTime (Course_courseCode, week, day, timeStart, timeEnd, availablePlaces) VALUES (?,?,?,?,?,?)");
			for (Halltime halltime : halltimes) {
				String coursecode = halltime.getCourseCode().toUpperCase();
				LocalTime bookTime = halltime.getTimeStart();
				LocalTime timeEnd = halltime.getTimeEnd();
				// General statement for inserting halltime if it doesn't already exist

				while (timeEnd.isAfter(bookTime.plusMinutes(interval - 1))) {
					// Specify question marks and add insert to the batch
					statement.setString(1, coursecode);
					statement.setString(2, Integer.toString(halltime.getWeek()));
					statement.setString(3, Integer.toString(halltime.getDay()));
					statement.setString(4, bookTime.toString());
					statement.setString(5, bookTime.plusMinutes(interval).toString());
					statement.setString(6, Integer.toString(halltime.getAvailablePlaces()));
					bookTime.plusMinutes(interval);
					statement.addBatch();
					bookTime = bookTime.plusMinutes(interval);
				}
			}

			statement.executeBatch();
			Logger.getLogger(App.class.getName()).log(Level.INFO, "Entries added");

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}

	public static void addHalltimesTA(ArrayList<Booking> bookings) throws Exception {

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();

			for (Booking booking : bookings) {
				String emailTA = booking.getEmailTA();
				String studentEmail = booking.getEmailStudent();
				String courseCode = booking.getCourseCode();
				LocalTime timeStart = booking.getStartTime();
				int week = booking.getWeek();
				int day = booking.getDay();
				
				statement = con.prepareStatement("SELECT idHallTime FROM HallTime "
						+ "WHERE Course_courseCode = ? AND timeStart = ? AND week = ? AND day = ? AND"
						+ " availablePlaces > 0");
				
				statement.setString(1, courseCode);
				statement.setString(2, timeStart.toString());
				statement.setInt(3, week);
				statement.setInt(4, day);

				result = statement.executeQuery();
				result.next();
				int id = result.getInt("idHallTime");

				result.close();
				statement.close();

				statement = con.prepareStatement(
						"UPDATE HallTime SET availablePlaces = availablePlaces - 1 WHERE idHallTime = ?");

				statement.setInt(1, id);

				statement.execute();

				statement.close();

				statement = con.prepareStatement(
						"INSERT INTO Booking (HallTime_idHallTime, TeachingAssistant_email, Student_email) VALUES (?, ?, ?)");

				statement.setInt(1, id);
				statement.setString(2, emailTA);
				statement.setString(3, studentEmail);

				statement.execute();
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}

	}

	public static void addHalltimeStudent(ArrayList<Booking> bookings) throws Exception {

		try {

			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();

			statement = con.prepareStatement("SELECT idHallTime FROM HallTime INNER JOIN Booking ON "
					+ "HallTime.idHallTime = Booking.HallTime_idHallTime WHERE Course_courseCode = ? AND timeStart = ? "
					+ "AND week = ? AND day = ? AND Student_email IS NULL");

			for (Booking booking : bookings) {
				String studentEmail = booking.getEmailStudent();
				String courseCode = booking.getCourseCode();
				LocalTime timeStart = booking.getStartTime();
				int week = booking.getWeek();
				int day = booking.getDay();

				statement.setString(1, courseCode);
				statement.setString(2, timeStart.toString());
				statement.setInt(3, week);
				statement.setInt(4, day);

				result = statement.executeQuery();
				result.next();
				int id = result.getInt("idHallTime");

				result.close();

				PreparedStatement update = con.prepareStatement(
						// "INSERT INTO Booking (HallTime_idHallTime, TeachingAssistant_email,
						// Student_email) VALUES (?, ?, ?)");
						"UPDATE Booking SET Student_email = ? WHERE HallTime_idHallTime = ?");

				update.setString(1, studentEmail);
				update.setInt(2, id);

				// System.out.println(String.format("%s, %s", Integer.toString(id),
				// studentEmail));

				update.execute();

				result.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
/*
	public static void downloadBookings() throws Exception {

		User user = App.getInstance().getLoggedUser();

		ArrayList<Booking> availableBookingsStudent = new ArrayList<Booking>();
		ArrayList<Booking> availableBookingsTA = new ArrayList<Booking>();
		ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
		ArrayList<Integer> weeksTA = new ArrayList<Integer>();

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();

			if (user.getType() == 1) {
				statement = con.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
						+ "Booking.HallTime_idHallTime WHERE Student_email IS NULL");
				result = statement.executeQuery();
				while (result.next()) {
					String CourseCode = result.getString("Course_CourseCode");
					int week = result.getInt("week");
					int day = result.getInt("day");
					LocalTime timeStart = LocalTime.parse(result.getString("timeStart"));
					LocalTime timeEnd = LocalTime.parse(result.getString("timeEnd"));
					int availablePlaces = result.getInt("availablePlaces");
					String emailTA = result.getString("TeachingAssistant_email");
					if (!weeksStudent.contains(week)) {
						weeksStudent.add(week);
					}
					// System.out.println(CourseCode + " " + week + " " + day + " " + timeStart + "
					// " + timeEnd + " " + availablePlaces);
					Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);

					Booking booking = new Booking(ht, emailTA, user.getEmail());
					availableBookingsStudent.add(booking);
				}
				App.getInstance().setDownloadedBookingsStudent(availableBookingsStudent);
				System.out.println(weeksStudent + "yes");
				App.getInstance().setDownloadedWeeksStudent(weeksStudent);
			} else if (App.getInstance().getLoggedUser().getType() == 2) {
				statement = con.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
						+ "Booking.HallTime_idHallTime WHERE Student_email IS NULL");
				result = statement.executeQuery();
				while (result.next()) {
					String CourseCode = result.getString("Course_CourseCode");
					int week = result.getInt("week");
					int day = result.getInt("day");
					LocalTime timeStart = LocalTime.parse(result.getString("timeStart"));
					LocalTime timeEnd = LocalTime.parse(result.getString("timeEnd"));
					int availablePlaces = result.getInt("availablePlaces");
					String emailTA = result.getString("TeachingAssistant_email");
					if (!weeksStudent.contains(week)) {
						weeksStudent.add(week);
					}
					// System.out.println(CourseCode + " " + week + " " + day + " " + timeStart + "
					// " + timeEnd + " " + availablePlaces);
					Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);

					Booking booking = new Booking(ht, emailTA, user.getEmail());
					availableBookingsStudent.add(booking);
				}
				statement.close();
				result.close();

				statement = con.prepareStatement("SELECT * FROM HallTime WHERE HallTime.idHallTime "
						+ "NOT IN (SELECT HallTime_idHallTime FROM Booking) AND availablePlaces > 0");
				result = statement.executeQuery();
				while (result.next()) {
					String CourseCode = result.getString("Course_CourseCode");
					int week = result.getInt("week");
					int day = result.getInt("day");
					LocalTime timeStart = LocalTime.parse(result.getString("timeStart"));
					LocalTime timeEnd = LocalTime.parse(result.getString("timeEnd"));
					int availablePlaces = result.getInt("availablePlaces");
					if (!weeksTA.contains(week)) {
						weeksTA.add(week);
					}

					Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);
					Booking book = new Booking(ht, user);
					availableBookingsTA.add(book);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		App.getInstance().setDownloadedBookingsTA(availableBookingsTA);
		App.getInstance().setDownloadedBookingsStudent(availableBookingsStudent);

//				App.getInstance().setDownloadedWeeksStudent(weeksStudent);
//				App.getInstance().setDownloadedWeeksTA(weeksTA);

	}
<<<<<<< HEAD
	*/
	
	public static void downloadBookings(User user) {

		if (user.getType() != 1 && user.getType() != 2)
			return;

		ArrayList<Booking> tempBookingsTA = new ArrayList<Booking>();
		ArrayList<Booking> tempAvailableBookingsTA = new ArrayList<Booking>();
		ArrayList<Booking> tempBookingsStudent = new ArrayList<Booking>();
		ArrayList<Booking> tempAvailableBookingsStudent = new ArrayList<Booking>();

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();

			if (user.getType() == 1) {
				statement = con.prepareStatement("SELECT Student_email, TeachingAssistant_email as TA_email, "
						+ "Course_courseCode as course, day, timeEnd, timeStart, week, availablePlaces as places "
						+ "FROM Booking INNER JOIN HallTime ON idHallTime = HallTime_idHallTime "
						+ "WHERE Student_email = ? OR Student_email IS NULL");

				statement.setString(1, user.getEmail());

				result = statement.executeQuery();

				Booking booking;
				while (result.next()) {
					booking = createBookingObjectFromResultSet(result);
					System.out.println(booking.getEmailStudent());
					
					if (booking.getEmailStudent() == null)
						tempAvailableBookingsStudent.add(booking);
					else if (booking.getEmailStudent().equals(user.getEmail()))
						tempBookingsStudent.add(booking);
				}
			} else if (user.getType() == 2) {
				statement = con.prepareStatement("SELECT Student_email, TeachingAssistant_email as TA_email, "
						+ "Course_courseCode as course, day, timeEnd, timeStart, week, availablePlaces as places "
						+ "FROM Booking INNER JOIN HallTime " + "ON idHallTime = HallTime_idHallTime "
						+ "WHERE TeachingAssistant_email = ? "
						+ "OR (Student_email IS NULL AND TeachingAssistant_email <> ?)");

				statement.setString(1, user.getEmail());
				statement.setString(2, user.getEmail());

				result = statement.executeQuery();
				Booking booking;
				while (result.next()) {
					booking = createBookingObjectFromResultSet(result);
					// System.out.println(booking);
					if (booking.getEmailTA() != user.getEmail() && booking.getEmailStudent() == null)
						tempAvailableBookingsStudent.add(booking);
					else if (booking.getEmailStudent() != null && booking.getEmailStudent().equals(user.getEmail()))
						tempBookingsStudent.add(booking);

					if (booking.getEmailTA().equals(user.getEmail()))
						tempBookingsTA.add(booking);
				}

				statement.close();
				result.close();

				statement = con.prepareStatement("SELECT * FROM HallTime WHERE HallTime.idHallTime "
						+ "NOT IN (SELECT HallTime_idHallTime FROM Booking) AND availablePlaces > 0");

				result = statement.executeQuery();

				while (result.next()) {
					booking = createEmptyBookingObjectFromResultSet(result);
					tempAvailableBookingsTA.add(booking);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (Exception e) {
			}
			;
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
			;
		}

		bookingsTA = tempBookingsTA;
		availableBookingsTA = tempAvailableBookingsTA;
		bookingsStudent = tempBookingsStudent;
		availableBookingsStudent = tempAvailableBookingsStudent;

		Logger.getLogger(App.class.getName()).log(Level.INFO, "Bookings downloaded");
	}

	private static Booking createBookingObjectFromResultSet(ResultSet result) throws SQLException {
		String emailTA = result.getString("TA_email");
		String course = result.getString("course");
		int week = result.getInt("week");
		int day = result.getInt("day");
		int places = result.getInt("places");
		LocalTime startTime = LocalTime.parse(result.getString("timeStart"));
		LocalTime endTime = LocalTime.parse(result.getString("timeEnd"));
		String emailStudent = result.getString("Student_email");

		Halltime ht = new Halltime(course, week, day, startTime, endTime, places);
		return new Booking(ht, emailTA, emailStudent);
	}

	private static Booking createEmptyBookingObjectFromResultSet(ResultSet result) throws SQLException {
		String course = result.getString("Course_courseCode");
		int week = result.getInt("week");
		int day = result.getInt("day");
		int places = result.getInt("availablePlaces");
		LocalTime startTime = LocalTime.parse(result.getString("timeStart"));
		LocalTime endTime = LocalTime.parse(result.getString("timeEnd"));

		Halltime ht = new Halltime(course, week, day, startTime, endTime, places);
		return new Booking(ht);
	}

	public static void refreshBookingWeeks(User user, String courseCode) {

		ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
		ArrayList<Integer> availableWeeksStudent = new ArrayList<Integer>();

		if (getAvailableBookingsStudent() != null) {
			for (Booking booking : getAvailableBookingsStudent()) {
				if (!availableWeeksStudent.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode)) {
					availableWeeksStudent.add(booking.getWeek());
				}
			}
			setAvailableWeeksStudent(availableWeeksStudent);
		}

		if (getBookingsStudent() != null) {
			for (Booking booking : getBookingsStudent()) {
				if (!weeksStudent.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode)) {
					weeksStudent.add(booking.getWeek());
				}
			}
			setWeeksStudent(weeksStudent);
		}

		if (user.getType() == 2) {
			ArrayList<Integer> weeksTA = new ArrayList<Integer>();
			ArrayList<Integer> availableWeeksTA = new ArrayList<Integer>();

			if (getAvailableBookingsTA() != null) {
				for (Booking booking : getAvailableBookingsTA()) {
					if (!availableWeeksTA.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode)) {
						availableWeeksTA.add(booking.getWeek());
					}
				}
				setAvailableWeeksTA(availableWeeksTA);
			}

			if (getBookingsTA() != null) {
				for (Booking booking : getAvailableBookingsTA()) {
					if (!weeksTA.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode)) {
						weeksTA.add(booking.getWeek());
					}
				}
				setWeeksTA(weeksTA);
			}
		}
	}
	
	//Used by calendar
	public static void refreshBookingWeeks(User user) {

		ArrayList<Integer> weeksStudent = new ArrayList<Integer>();


		if (getBookingsStudent() != null) {
			for (Booking booking : getBookingsStudent()) {
				if (!weeksStudent.contains(booking.getWeek())) {
					weeksStudent.add(booking.getWeek());
				}
			}
			setWeeksStudent(weeksStudent);
		}

		if (user.getType() == 2) {
			ArrayList<Integer> weeksTA = new ArrayList<Integer>();


			if (getBookingsTA() != null) {
				for (Booking booking : getAvailableBookingsTA()) {
					if (!weeksTA.contains(booking.getWeek())) {
						weeksTA.add(booking.getWeek());
					}
				}
				setWeeksTA(weeksTA);
			}
		}
	}
	

	public static ArrayList<Booking> getBookingsTA() {
		return bookingsTA;
	}

	public static void setBookingsTA(ArrayList<Booking> bookingsTA) {
		DBBooking.bookingsTA = bookingsTA;
	}

	public static ArrayList<Booking> getAvailableBookingsTA() {
		return availableBookingsTA;
	}

	public static void setAvailableBookingsTA(ArrayList<Booking> availableBookingsTA) {
		DBBooking.availableBookingsTA = availableBookingsTA;
	}

	public static ArrayList<Integer> getWeeksTA() {
		return weeksTA;
	}

	public static void setWeeksTA(ArrayList<Integer> weeksTA) {
		DBBooking.weeksTA = weeksTA;
	}

	public static ArrayList<Booking> getBookingsStudent() {
		return bookingsStudent;
	}

	public static void setBookingsStudent(ArrayList<Booking> bookingsStudent) {
		DBBooking.bookingsStudent = bookingsStudent;
	}

	public static ArrayList<Integer> getWeeksStudent() {
		return weeksStudent;
	}

	public static void setWeeksStudent(ArrayList<Integer> weeksStudent) {
		DBBooking.weeksStudent = weeksStudent;
	}

	public static ArrayList<Integer> getAvailableWeeksTA() {
		return availableWeeksTA;
	}

	public static void setAvailableWeeksTA(ArrayList<Integer> availableWeeksTA) {
		DBBooking.availableWeeksTA = availableWeeksTA;
	}

	public static ArrayList<Booking> getAvailableBookingsStudent() {
		return availableBookingsStudent;
	}

	public static void setAvailableBookingsStudent(ArrayList<Booking> availableBookingsStudent) {
		DBBooking.availableBookingsStudent = availableBookingsStudent;
	}

	public static ArrayList<Integer> getAvailableWeeksStudent() {
		return availableWeeksStudent;
	}

	public static void setAvailableWeeksStudent(ArrayList<Integer> availableWeeksStudent) {
		DBBooking.availableWeeksStudent = availableWeeksStudent;
	}
	
	/*
	public static Integer numberOfBookingsInWeek(Integer week, User user) {
		Integer number = null;
		try {
			
			    BasicDataSource bds = DataSource.getInstance().getBds();
			    con = bds.getConnection();
			
			    statement = con.prepareStatement("SELECT COUNT(*) as bookings " + 
								"FROM Booking " + 
								"INNER JOIN HallTime " + 
								"ON HallTime_idHallTime = idHallTime " + 
								"WHERE week = ? and Student_email = ?");
			    statement.setInt(1, week);
			    statement.setString(2, user.getEmail());
			    
			    result = statement.executeQuery();
			    result.next();
			    number = result.getInt("bookings");
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
		} finally {
				try { if (result != null) result.close(); } catch (Exception e) {};
			    try { if (statement != null) statement.close(); } catch (Exception e) {};
			    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		
		return number;
	}*/
/*
	public static void downloadMyBookings() throws Exception{
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
        ArrayList<Booking> myBookingsStudent = new ArrayList<Booking>();
		ArrayList<Booking> myBookingsTA = new ArrayList<Booking>();
		
		User user = App.getInstance().getLoggedUser();
		String myEmail= user.getEmail();
		
		BasicDataSource bds = DataSource.getInstance().getBds();
        con = bds.getConnection();
		statement = con
				.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
						+ "Booking.HallTime_idHallTime WHERE Student_email = '"+myEmail+"'");
		result = statement.executeQuery();
		while (result.next()) {
			String CourseCode = result.getString("Course_CourseCode");
			int week = result.getInt("week");
			int day = result.getInt("day");
			LocalTime timeStart = LocalTime.parse(result.getString("timeStart"));
			LocalTime timeEnd = LocalTime.parse(result.getString("timeEnd"));
			int availablePlaces = result.getInt("availablePlaces");
			String emailTA = result.getString("TeachingAssistant_email");
			// System.out.println(CourseCode + " " + week + " " + day + " " + timeStart + "
			// " + timeEnd + " " + availablePlaces);
			Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);
			Booking booking = new Booking(ht, emailTA, user.getEmail());
			myBookingsStudent.add(booking);
		}
		statement = con
				.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
						+ "Booking.HallTime_idHallTime WHERE TeachingAssistant_email = '"+myEmail+"'");
		result =statement.executeQuery();
		while (result.next()) {
			String CourseCode = result.getString("Course_CourseCode");
			int week = result.getInt("week");
			int day = result.getInt("day");
			LocalTime timeStart = LocalTime.parse(result.getString("timeStart"));
			LocalTime timeEnd = LocalTime.parse(result.getString("timeEnd"));
			int availablePlaces = result.getInt("availablePlaces");
			String studentEmail = result.getString("Student_email");
			// System.out.println(CourseCode + " " + week + " " + day + " " + timeStart + "
			// " + timeEnd + " " + availablePlaces);
			Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);
			Booking booking = new Booking(ht, user.getEmail(), studentEmail);
			myBookingsTA.add(booking);
		}
		
		setMyBookingsStudent(myBookingsStudent);
		setMyBookingsTA(myBookingsTA);
		
		
	}*/
		
	public static void deleteBooking(Booking booking) throws Exception {
		
        
try {
	BasicDataSource bds = DataSource.getInstance().getBds();
	con = bds.getConnection();
	statement = con.prepareStatement(String.format(
			"SELECT idHallTime FROM HallTime WHERE Course_courseCode = '%s' "
					+ "AND week = '%s' AND day = '%s'AND timeStart = '%s' AND timeEnd = '%s' ",
			booking.getCourseCode(), booking.getWeek(), booking.getDay(), booking.getStartTime(),
			booking.getEndTime()));

	result = statement.executeQuery();
	result.next();
	int idHallTime = result.getInt("idHallTime");
	PreparedStatement deleteBooking = con.prepareStatement(
			"DELETE FROM Booking WHERE Halltime_idHalltime ='" + idHallTime + "' " + "AND Student_email ='"
					+ booking.getEmailStudent() + "' AND TeachingAssistant_email = '" + booking.getEmailTA() + "'");

	deleteBooking.execute();
	con.close();
} catch (Exception e) {
	// TODO: handle exception
} finally {
	try { if (result != null) result.close(); } catch (Exception e) {};
    try { if (statement != null) statement.close(); } catch (Exception e) {};
    try { if (con != null) con.close(); } catch (Exception e) {};
}

		
		
		
	}

	/*
	public static ArrayList<Booking> getMyBookingsTA() {
		return myBookingsTA;
	}

	public static void setMyBookingsTA(ArrayList<Booking> bookingsTA) {
		myBookingsTA = bookingsTA;
	}
	public static ArrayList<Booking> getMyBookingsStudent() {
		return myBookingsStudent;
	}

	public static void setMyBookingsStudent(ArrayList<Booking> bookingsStudent) {
		myBookingsStudent = bookingsStudent;
	}
*/
	public static void removeBookingStudent(Booking booking) {
		bookingsStudent.remove(booking);
	}
	public static void removeBookingTA(Booking booking) {
		bookingsTA.remove(booking);
	}
	
	public static int getCurrentWeek() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());
	}
}

	/*
	 * if (user.getType() == 1) { PreparedStatement hallTimesStudent = con
	 * .prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
	 * + "Booking.HallTime_idHallTime WHERE Student_email IS NULL"); ResultSet rs =
	 * hallTimesStudent.executeQuery(); while (rs.next()) { String CourseCode =
	 * rs.getString("Course_CourseCode"); int week = rs.getInt("week"); int day =
	 * rs.getInt("day"); LocalTime timeStart =
	 * LocalTime.parse(rs.getString("timeStart")); LocalTime timeEnd =
	 * LocalTime.parse(rs.getString("timeEnd")); int availablePlaces =
	 * rs.getInt("availablePlaces"); String emailTA =
	 * rs.getString("TeachingAssistant_email"); if (!weeksStudent.contains(week)) {
	 * weeksStudent.add(week); } // System.out.println(CourseCode + " " + week + " "
	 * + day + " " + timeStart + " // " + timeEnd + " " + availablePlaces); Halltime
	 * ht = new Halltime(CourseCode, week, day, timeStart, timeEnd,
	 * availablePlaces);
	 * 
	 * Booking booking = new Booking(ht, emailTA, user.getEmail());
	 * availableBookingsStudent.add(booking); }
	 * App.getInstance().setDownloadedBookings(availableBookingsStudent);
	 * App.getInstance().setDownloadedWeeks(weeksStudent); } else if
	 * (App.getInstance().getLoggedUser().getType() == 2) { PreparedStatement
	 * hallTimesTA =
	 * con.prepareStatement("SELECT * FROM HallTime WHERE HallTime.idHallTime " +
	 * "NOT IN (SELECT HallTime_idHallTime FROM Booking) AND availablePlaces > 0");
	 * ResultSet rs = hallTimesTA.executeQuery(); while (rs.next()) { String
	 * CourseCode = rs.getString("Course_CourseCode"); int week = rs.getInt("week");
	 * int day = rs.getInt("day"); LocalTime timeStart =
	 * LocalTime.parse(rs.getString("timeStart")); LocalTime timeEnd =
	 * LocalTime.parse(rs.getString("timeEnd")); int availablePlaces =
	 * rs.getInt("availablePlaces"); if (!weeksTA.contains(week)) {
	 * weeksTA.add(week); }
	 * 
	 * Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd,
	 * availablePlaces); Booking book = new Booking(ht, user);
	 * availableBookingsTA.add(book); }
	 * App.getInstance().setDownloadedBookings(availableBookingsTA);
	 * App.getInstance().setDownloadedWeeks(weeksTA);
	 * 
	 * }
	 */

