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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.dbcp2.BasicDataSource;

import gui.App;
import halltimes.Booking;
import halltimes.Halltime;
import user.User;

public class DBBooking extends DBConnection {

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

	// Sjekker om halltid ligger inne
	public static boolean halltimeExists(Halltime halltime) {
		Connection con = null;
        PreparedStatement findHalltime = null;
        ResultSet rs = null;
		
        
        boolean eksisterer = false;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			findHalltime = con
					.prepareStatement("SELECT Course_courseCode, week, day, timeStart, timeEnd FROM HallTime");
			rs = findHalltime.executeQuery();

	
			while (rs.next() && eksisterer == false) {
				if (Objects.equals(rs.getString("Course_courseCode"), halltime.getCourseCode())
						&& Objects.equals(rs.getInt("week"), halltime.getWeek())
						&& Objects.equals(rs.getInt("day"), halltime.getDay())
						&& Objects.equals(LocalTime.parse(rs.getString("timeStart")).toString(),
								halltime.getTimeStart().toString())
						&& Objects.equals(LocalTime.parse(rs.getString("timeEnd")).toString(),
								halltime.getTimeEnd().toString())) {
					eksisterer = true;
				}
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		} finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (findHalltime != null) findHalltime.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		// System.out.println(eksisterer);
		return eksisterer;

	}

	// Henter availableplaces
	public static int getAvailablePlaces(Halltime halltime) {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
		
        	
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

			rs = statement.executeQuery();
			rs.next();
			availablePlaces = rs.getInt("availablePlaces");
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		} finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return availablePlaces;
	}

	// Maa skrive inn tiden paa format "00:00:00";
	// Interval in minutes
	public static void supervisorAddHalltime(ArrayList<Halltime> halltimes, int interval) {
		
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
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
			System.out.println("Entries added");

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}

	public static void addHalltimesTA(ArrayList<Booking> bookings) throws Exception {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
		try {
	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			statement = con.prepareStatement("SELECT idHallTime FROM HallTime "
					+ "WHERE Course_courseCode = ? AND timeStart = ? AND week = ? AND day = ? AND"
					+ " availablePlaces > 0");

			for (Booking booking : bookings) {
				String emailTA = booking.getEmailTA();
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

				System.out.println(String.format("%s, %s, %s", Integer.toString(id), emailTA, studentEmail));
				statement.execute();
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
		
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
        try {

	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();

			statement = con
					.prepareStatement("SELECT idHallTime FROM HallTime INNER JOIN Booking ON "
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
				
				//System.out.println(String.format("%s, %s", Integer.toString(id), studentEmail));

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

	public static void downloadBookings() throws Exception {
		
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
		User user = App.getInstance().getLoggedUser();

		ArrayList<Booking> availableBookingsStudent = new ArrayList<Booking>();
		ArrayList<Booking> availableBookingsTA = new ArrayList<Booking>();
		ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
		ArrayList<Integer> weeksTA = new ArrayList<Integer>();

		try {
	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			
			if (user.getType() == 1) {
				statement = con
						.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
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
				App.getInstance().setDownloadedWeeksStudent(weeksStudent);
			} else if (App.getInstance().getLoggedUser().getType() == 2) {
				statement = con
						.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
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
				App.getInstance().setDownloadedBookingsTA(availableBookingsTA);
				App.getInstance().setDownloadedBookingsStudent(availableBookingsStudent);

				App.getInstance().setDownloadedWeeksStudent(weeksStudent);
				App.getInstance().setDownloadedWeeksTA(weeksTA);

			}
			
			/*
			if (user.getType() == 1) {
				PreparedStatement hallTimesStudent = con
						.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
								+ "Booking.HallTime_idHallTime WHERE Student_email IS NULL");
				ResultSet rs = hallTimesStudent.executeQuery();
				while (rs.next()) {
					String CourseCode = rs.getString("Course_CourseCode");
					int week = rs.getInt("week");
					int day = rs.getInt("day");
					LocalTime timeStart = LocalTime.parse(rs.getString("timeStart"));
					LocalTime timeEnd = LocalTime.parse(rs.getString("timeEnd"));
					int availablePlaces = rs.getInt("availablePlaces");
					String emailTA = rs.getString("TeachingAssistant_email");
					if (!weeksStudent.contains(week)) {
						weeksStudent.add(week);
					}
					// System.out.println(CourseCode + " " + week + " " + day + " " + timeStart + "
					// " + timeEnd + " " + availablePlaces);
					Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);

					Booking booking = new Booking(ht, emailTA, user.getEmail());
					availableBookingsStudent.add(booking);
				}
				App.getInstance().setDownloadedBookings(availableBookingsStudent);
				App.getInstance().setDownloadedWeeks(weeksStudent);
			} else if (App.getInstance().getLoggedUser().getType() == 2) {
				PreparedStatement hallTimesTA = con.prepareStatement("SELECT * FROM HallTime WHERE HallTime.idHallTime "
						+ "NOT IN (SELECT HallTime_idHallTime FROM Booking) AND availablePlaces > 0");
				ResultSet rs = hallTimesTA.executeQuery();
				while (rs.next()) {
					String CourseCode = rs.getString("Course_CourseCode");
					int week = rs.getInt("week");
					int day = rs.getInt("day");
					LocalTime timeStart = LocalTime.parse(rs.getString("timeStart"));
					LocalTime timeEnd = LocalTime.parse(rs.getString("timeEnd"));
					int availablePlaces = rs.getInt("availablePlaces");
					if (!weeksTA.contains(week)) {
						weeksTA.add(week);
					}

					Halltime ht = new Halltime(CourseCode, week, day, timeStart, timeEnd, availablePlaces);
					Booking book = new Booking(ht, user);
					availableBookingsTA.add(book);
				}
				App.getInstance().setDownloadedBookings(availableBookingsTA);
				App.getInstance().setDownloadedWeeks(weeksTA);

			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
	

	public static void main(String[] args) {
		Map<String, Integer> coursesAndRoles = new HashMap<String, Integer>();
		coursesAndRoles.put("TDT4140", 2);
		/*
		 * LocalTime timeStart = LocalTime.of(13, 0, 0); LocalTime timeEnd =
		 * LocalTime.of(15, 0, 0); Halltime ht = new Halltime("TDT4140", 5, 3,
		 * timeStart, timeEnd, 15);
		 */
		User TA = User.generateUserObject("abc@ntnu.no", "abc", "def", coursesAndRoles);
		/*
		 * User Student = User.generateUserObject("abc@ntnu.no", "ghi", "jkl",
		 * coursesAndRoles); Booking bookTA = new Booking(ht, TA); Booking bookStudent =
		 * new Booking(ht, TA, Student);
		 */
		try {
			// addHalltimeTA(bookTA);
			// addHalltimeStudent(bookStudent);
			downloadBookings();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
