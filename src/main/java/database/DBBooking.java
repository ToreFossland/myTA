package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class DBBooking extends DBConnection {
	
	
	//ny HallTimeID
	public static int newHtId() throws Exception {

		int htID = 0;
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM HallTime ORDER BY idHallTime DESC LIMIT 1");
			ResultSet forrigeId = statement.executeQuery();
			forrigeId.next();
			htID = forrigeId.getInt("idHallTime") + 1;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return htID;
	}
	
	//Sjekker om halltid ligger inne
	public static boolean halltimeExists(String courseCode, int week, int day, String timeStart, String timeEnd) {
		boolean eksisterer = false;
		try {
			Connection con = getConnection();
			String coursecode = courseCode.toUpperCase();
			PreparedStatement findHalltime = con.prepareStatement("SELECT Course_courseCode, week, day, timeStart, timeEnd " 
					+ "FROM HallTime");
			//PreparedStatement findCourseCode = con.prepareStatement("SELECT Course_courseCode FROM User_has_Course");
			//PreparedStatement findRolle = con.prepareStatement("SELECT role FROM User_has_Course");
			ResultSet rs = findHalltime.executeQuery();
			
			// ResultSet idCourse = finnIdCourse.executeQuery();
			// ResultSet role = finnRolle.executeQuery();
			

			while (rs.next() && eksisterer == false) {
				if (Objects.equals(rs.getString("Course_courseCode"), coursecode) && Objects.equals(rs.getInt("week"), week) 
						&& Objects.equals(rs.getInt("day"), day) && Objects.equals(rs.getString("timeStart"), timeStart) 
						&& Objects.equals(rs.getString("timeEnd"), timeEnd)) {
					eksisterer = true;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);

		}
		//System.out.println(eksisterer);
		return eksisterer;
		

	}
	
	//Henter availableplaces
	public static int getAvailablePlaces(String courseCode, int week, int day, String timeStart, String timeEnd) {
		int availablePlaces = 0;
		try {
			Connection con = getConnection();
			String coursecode = courseCode.toUpperCase();
			PreparedStatement getAvailablePlaces = con.prepareStatement(String.format("SELECT availablePlaces " 
					+ " FROM HallTime "
					+ " WHERE Course_courseCode = '%s' "
					+ " AND week = '%s' "
					+ "	AND day = '%s' "
					+ " AND timeStart = '%s' " 
					+ " AND timeEnd = '%s' ", coursecode, week, day, timeStart, timeEnd));
			
			ResultSet rs = getAvailablePlaces.executeQuery();
			rs.next();
			availablePlaces = rs.getInt("availablePlaces");
			
			
			
		} catch (Exception e) {
			System.out.println(e);

		}
		return availablePlaces;
	}
	
	//Maa skrive inn tiden paa format "00:00:00";
	public static void addHalltimeBookings(String courseCode, int week, int day, String timeStart, String timeEnd,
			int numberOfBookings) {
		
		try {
			Connection con = getConnection();
			String coursecode = courseCode.toUpperCase();
			if (halltimeExists(courseCode, week, day, timeStart, timeEnd)==false) {
				
				//Legger til ny, unik ID
				int htID = newHtId();
				
				PreparedStatement bookingToDB = con.prepareStatement(String.format(
						"INSERT INTO HallTime (idHallTime, Course_courseCode, week, day, timeStart, timeEnd, availablePlaces) "
						+ "VALUES('%s', '%s', '%s','%s','%s', '%s','%s')", htID, coursecode, week, day, timeStart, timeEnd, numberOfBookings));
	
				bookingToDB.executeUpdate();
				
			}
			
			//Legger til plasser paa gammelID om det eksisterer. Sparer plass
			else {
				int availablePlaces = getAvailablePlaces(courseCode,week,day,timeStart,timeEnd);
				
				PreparedStatement addNumberOfBookings = con.prepareStatement(String.format(
						"UPDATE HallTime"
						+ " SET availablePlaces = "+(numberOfBookings+availablePlaces)+" "
						+ " WHERE Course_courseCode = '%s' "
						+ " AND week = '%s' "
						+ " AND day = '%s' "
						+ " AND timeStart = '%s' "
						+ " AND timeEnd = '%s'",coursecode, week, day, timeStart, timeEnd));
				
				addNumberOfBookings.executeUpdate();
				
						
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		
		//addHalltimeBookings("TDT4140", 3, 1, "08:00:00", "08:15:00", 7);
		addHalltimeBookings("TDT4140", 3, 2, "08:15:00", "08:30:00",3);
	}
	

	
}
