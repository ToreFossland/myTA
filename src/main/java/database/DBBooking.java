package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import halltimes.Halltime;

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
	public static boolean halltimeExists(Halltime halltime) {
		boolean eksisterer = false;
		try {
			Connection con = getConnection();
			String coursecode = halltime.getCourseCode().toUpperCase();
			PreparedStatement findHalltime = con.prepareStatement("SELECT Course_courseCode, week, day, timeStart, timeEnd " 
					+ "FROM HallTime");
			//PreparedStatement findCourseCode = con.prepareStatement("SELECT Course_courseCode FROM User_has_Course");
			//PreparedStatement findRolle = con.prepareStatement("SELECT role FROM User_has_Course");
			ResultSet rs = findHalltime.executeQuery();
			
			// ResultSet idCourse = finnIdCourse.executeQuery();
			// ResultSet role = finnRolle.executeQuery();
			

			while (rs.next() && eksisterer == false) {
				if (Objects.equals(rs.getString("Course_courseCode"), halltime.getCourseCode()) && Objects.equals(rs.getInt("week"), halltime.getWeek()) 
						&& Objects.equals(rs.getInt("day"), halltime.getDay()) && Objects.equals(rs.getString("timeStart"), halltime.getTimeStart()) 
						&& Objects.equals(rs.getString("timeEnd"), halltime.getTimeEnd())) {
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
	public static int getAvailablePlaces(Halltime halltime) {
		int availablePlaces = 0;
		try {
			Connection con = getConnection();
			String coursecode = halltime.getCourseCode().toUpperCase();
			PreparedStatement getAvailablePlaces = con.prepareStatement(String.format("SELECT availablePlaces " 
					+ " FROM HallTime "
					+ " WHERE Course_courseCode = '%s' "
					+ " AND week = '%s' "
					+ "	AND day = '%s' "
					+ " AND timeStart = '%s' " 
					+ " AND timeEnd = '%s' ", coursecode, halltime.getWeek(), halltime.getDay(), halltime.getTimeStart(), halltime.getTimeEnd()));
			
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
