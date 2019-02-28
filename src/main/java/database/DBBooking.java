package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.Objects;

import halltimes.Halltime;

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
	public static void main(String[] args) {

		LocalTime timeStart = LocalTime.of(8, 00, 00);
		LocalTime timeEnd = LocalTime.of(16, 00, 00);
		Halltime ht = new Halltime("TDT4140", 5, 3, timeStart, timeEnd, 20);
		supervisorAddHalltime(ht, 20);
	}*/

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
	public static void supervisorAddHalltime(Halltime halltime, int interval) {

		try {
			Connection con = getConnection();
			String coursecode = halltime.getCourseCode().toUpperCase();
			LocalTime bookTime = halltime.getTimeStart();
			LocalTime timeEnd = halltime.getTimeEnd();
			// General statement for inserting halltime if it doesn't already exist
			PreparedStatement statement = con.prepareStatement(
					"REPLACE INTO HallTime (Course_courseCode, week, day, timeStart, timeEnd, availablePlaces) VALUES (?,?,?,?,?,?)");

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

			statement.executeBatch();
			con.close();
			System.out.println("Entries added");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
