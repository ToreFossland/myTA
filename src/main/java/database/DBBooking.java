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

//Database communication related to bookings
public class DBBooking extends DBConnection {
	
	private static ArrayList<Booking> bookingsTA = new ArrayList<Booking>(); // Fjerne booking fra denne on confirm
	private static ArrayList<Integer> weeksTA = new ArrayList<Integer>();
	private static ArrayList<Booking> availableBookingsTA = new ArrayList<Booking>();
	private static ArrayList<Integer> availableWeeksTA = new ArrayList<Integer>();

	private static ArrayList<Booking> bookingsStudent = new ArrayList<Booking>();
	private static ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
	private static ArrayList<Booking> availableBookingsStudent = new ArrayList<Booking>();
	private static ArrayList<Integer> availableWeeksStudent = new ArrayList<Integer>();

	static Connection con = null;
	static PreparedStatement statement = null;
	static ResultSet result = null;

	// Checks if halltime exists
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
					
					if (booking.getEmailStudent() == null)
						tempAvailableBookingsStudent.add(booking);
					else if (booking.getEmailStudent().equals(user.getEmail()))
						tempBookingsStudent.add(booking);
				}
			} else if (user.getType() == 2) {
				statement = con.prepareStatement("SELECT Student_email, TeachingAssistant_email as TA_email, "
						+ "Course_courseCode as course, day, timeEnd, timeStart, week, availablePlaces as places "
						+ "FROM Booking INNER JOIN HallTime ON idHallTime = HallTime_idHallTime "
						+ "WHERE TeachingAssistant_email = ? OR Student_email = ? "
						+ "OR (Student_email IS NULL AND TeachingAssistant_email <> ?)");

				statement.setString(1, user.getEmail());
				statement.setString(2, user.getEmail());
				statement.setString(3, user.getEmail());

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
				if (!availableWeeksStudent.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode) && booking.getWeek() >= getCurrentWeek()) {
					availableWeeksStudent.add(booking.getWeek());
				}
			}
			setAvailableWeeksStudent(availableWeeksStudent);
		}

		if (getBookingsStudent() != null) {
			for (Booking booking : getBookingsStudent()) {
				if (!weeksStudent.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode) && booking.getWeek() >= getCurrentWeek()) {
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
					if (!availableWeeksTA.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode) && booking.getWeek() >= getCurrentWeek()) {
						availableWeeksTA.add(booking.getWeek());
					}
				}
				setAvailableWeeksTA(availableWeeksTA);
			}

			if (getBookingsTA() != null) {
				for (Booking booking : getAvailableBookingsTA()) {
					if (!weeksTA.contains(booking.getWeek()) && booking.getCourseCode().equals(courseCode) && booking.getWeek() >= getCurrentWeek()) {
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
				if (!weeksStudent.contains(booking.getWeek()) && booking.getWeek() >= getCurrentWeek()) {
					weeksStudent.add(booking.getWeek());
				}
			}
			setWeeksStudent(weeksStudent);
		}

		if (user.getType() == 2) {
			ArrayList<Integer> weeksTA = new ArrayList<Integer>();


			if (getBookingsTA() != null) {
				for (Booking booking : getAvailableBookingsTA()) {
					if (!weeksTA.contains(booking.getWeek()) && booking.getWeek() >= getCurrentWeek()) {
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
	
	public static int getCurrentWeek() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());
	}
	
	public static void removeBookingStudent(Booking booking) {
		bookingsStudent.remove(booking);
	}
	public static void removeBookingTA(Booking booking) {
		bookingsTA.remove(booking);
	}
}
