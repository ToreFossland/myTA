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
		boolean eksisterer = false;
		try {
			Connection con = getConnection();
			String courseCode = halltime.getCourseCode().toUpperCase();
			PreparedStatement findHalltime = con
					.prepareStatement("SELECT Course_courseCode, week, day, timeStart, timeEnd FROM HallTime");
			// PreparedStatement findCourseCode = con.prepareStatement("SELECT
			// Course_courseCode FROM User_has_Course");
			// PreparedStatement findRolle = con.prepareStatement("SELECT role FROM
			// User_has_Course");
			ResultSet rs = findHalltime.executeQuery();

			// ResultSet idCourse = finnIdCourse.executeQuery();
			// ResultSet role = finnRolle.executeQuery();

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

		}
		// System.out.println(eksisterer);
		return eksisterer;

	}

	// Henter availableplaces
	public static int getAvailablePlaces(Halltime halltime) {
		int availablePlaces = 0;
		try {
			Connection con = getConnection();
			String coursecode = halltime.getCourseCode().toUpperCase();
			PreparedStatement getAvailablePlaces = con.prepareStatement(String.format(
					"SELECT availablePlaces " + " FROM HallTime " + " WHERE Course_courseCode = '%s' "
							+ " AND week = '%s' " + "	AND day = '%s' " + " AND timeStart = '%s' "
							+ " AND timeEnd = '%s' ",
					coursecode, halltime.getWeek(), halltime.getDay(), halltime.getTimeStart(), halltime.getTimeEnd()));

			ResultSet rs = getAvailablePlaces.executeQuery();
			rs.next();
			availablePlaces = rs.getInt("availablePlaces");
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
		return availablePlaces;
	}

	// Maa skrive inn tiden paa format "00:00:00";
	// Interval in minutes
	public static void supervisorAddHalltime(ArrayList<Halltime> halltimes, int interval) {

		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement(
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

					/*
					 * Halltime newHT = new Halltime(halltime.getCourseCode(), halltime.getWeek(),
					 * halltime.getDay(), bookTime, bookTime.plusMinutes(interval), interval); if
					 * (!halltimeExists(newHT)) {
					 * 
					 * query += String.format("(%s), ", newHT.toString());
					 *
					 * 
					 * PreparedStatement bookingToDB = con.prepareStatement(String.format(
					 * "INSERT INTO HallTime (idHallTime, Course_courseCode, week, day, timeStart, timeEnd, availablePlaces) "
					 * + "VALUES(%s)", newHT.toString()));
					 *
					 * 
					 * }
					 * 
					 * // Legger til plasser paa gammelID om det eksisterer. Sparer plass else { int
					 * availablePlaces = getAvailablePlaces(halltime);
					 * 
					 * PreparedStatement addNumberOfBookings = con.prepareStatement(String.format(
					 * "UPDATE HallTime" + " SET availablePlaces = " +
					 * (halltime.getAvailablePlaces() + availablePlaces) + " " +
					 * " WHERE Course_courseCode = '%s' " + " AND week = '%s' " + " AND day = '%s' "
					 * + " AND timeStart = '%s' " + " AND timeEnd = '%s'", halltime.getCourseCode(),
					 * Integer.toString(halltime.getWeek()), Integer.toString(halltime.getDay()),
					 * halltime.getTimeStart().toString(), halltime.getTimeEnd().toString()));
					 * 
					 * addNumberOfBookings.executeUpdate(); } query = query.substring(0,
					 * query.length() - 2); query += ";";
					 */
				}
			}

			statement.executeBatch();
			con.close();
			System.out.println("Entries added");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void addHalltimeTA(ArrayList<Booking> bookings) throws Exception {

		try {
			Connection con = getConnection();
			PreparedStatement getidHallTime = con.prepareStatement("SELECT idHallTime FROM HallTime "
					+ "WHERE Course_courseCode = ? AND timeStart = ? AND week = ? AND day = ? AND"
					+ " availablePlaces > 0");

			for (Booking booking : bookings) {
				String emailTA = booking.getEmailTA();
				String studentEmail = booking.getEmailStudent();
				String courseCode = booking.getCourseCode();
				LocalTime timeStart = booking.getStartTime();
				int week = booking.getWeek();
				int day = booking.getDay();

				getidHallTime.setString(1, courseCode);
				getidHallTime.setString(2, timeStart.toString());
				getidHallTime.setInt(3, week);
				getidHallTime.setInt(4, day);

				ResultSet rs = getidHallTime.executeQuery();
				rs.next();
				int id = rs.getInt("idHallTime");

				PreparedStatement subAvailPlaces = con.prepareStatement(
						"UPDATE HallTime SET availablePlaces = " + "availablePlaces - 1 WHERE idHallTime = ?");

				subAvailPlaces.setInt(1, id);

				PreparedStatement statement = con.prepareStatement(
						"INSERT INTO Booking (HallTime_idHallTime, TeachingAssistant_email, Student_email) VALUES (?, ?, ?)");

				statement.setInt(1, id);
				statement.setString(2, emailTA);
				statement.setString(3, studentEmail);

				System.out.println(String.format("%s, %s, %s", Integer.toString(id), emailTA, studentEmail));

				subAvailPlaces.execute();
				statement.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addHalltimeStudent(ArrayList<Booking> bookings) throws Exception {

		try {

			Connection con = getConnection();

			PreparedStatement getidHallTime = con
					.prepareStatement("SELECT idHallTime FROM HallTime INNER JOIN Booking ON "
							+ " HallTime.idHallTime = Booking.HallTime_idHallTime WHERE Course_courseCode = ? AND timeStart = ? "
							+ "AND week = ? AND day = ? AND Student_email IS NULL");

			for (Booking booking : bookings) {
				String emailTA = booking.getEmailTA();
				String studentEmail = booking.getEmailStudent();
				String courseCode = booking.getCourseCode();
				LocalTime timeStart = booking.getStartTime();
				int week = booking.getWeek();
				int day = booking.getDay();

				getidHallTime.setString(1, courseCode);
				getidHallTime.setString(2, timeStart.toString());
				getidHallTime.setInt(3, week);
				getidHallTime.setInt(4, day);

				ResultSet rs = getidHallTime.executeQuery();
				rs.next();
				int id = rs.getInt("idHallTime");
				System.out.println(Integer.toString(id));

				PreparedStatement statement = con.prepareStatement(
						// "INSERT INTO Booking (HallTime_idHallTime, TeachingAssistant_email,
						// Student_email) VALUES (?, ?, ?)");
						"UPDATE Booking SET Student_email = ? WHERE HallTime_idHallTime = ?");

				statement.setString(1, studentEmail);
				statement.setInt(2, id);

				System.out.println(String.format("%s, %s", Integer.toString(id), studentEmail));

				statement.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void downloadBookings() throws Exception {

		User user = App.getInstance().getLoggedUser();

		ArrayList<Booking> availableBookingsStudent = new ArrayList<Booking>();
		ArrayList<Booking> availableBookingsTA = new ArrayList<Booking>();
		ArrayList<Integer> weeksStudent = new ArrayList<Integer>();
		ArrayList<Integer> weeksTA = new ArrayList<Integer>();

		try {
			Connection con = getConnection();
			if (user.getType() == 1) {
				PreparedStatement hallTimesStudent = con
						.prepareStatement("SELECT * FROM HallTime INNER JOIN Booking ON HallTime.idHallTime = "
								+ "Booking.HallTime_idHallTime WHERE Student_email IS NULL");
				ResultSet rs = hallTimesStudent.executeQuery();
				while (rs.next()) {
					int id = rs.getInt("idHallTime");
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
					int id = rs.getInt("idHallTime");
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

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
