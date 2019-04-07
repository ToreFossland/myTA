package timeScheduleTests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import halltimes.Booking;
import halltimes.Halltime;
import timeschedule.TimeSchedule;
import user.User;

class TimeScheduleTests {

	@Test
	void testAddBookings() {
		User user = User.generateUserObject("bruker@ntnu.no");
		
		LocalTime timeStart = LocalTime.of(13, 0, 0);
		LocalTime timeEnd = LocalTime.of(15, 0, 0);
		Halltime ht1 = new Halltime("TDT4140", 5, 4, timeStart, timeEnd, 12);
		Halltime ht2 = new Halltime("TDT4140", 5, 5, timeStart, timeEnd, 10);
		Halltime ht3 = new Halltime("TDT4140", 6, 1, timeStart, timeEnd, 10);
		Halltime ht4 = new Halltime("TDT4140", 6, 3, timeStart, timeEnd, 12);
		Booking booking1 = new Booking(ht1, "bruker2@ntnu.no", "bruker@ntnu.no"); //TA, student
		Booking booking2 = new Booking(ht2, "bruker@ntnu.no", "bruker2@ntnu.no");
		Booking booking3 = new Booking(ht3, "bruker2@ntnu.no", "bruker@ntnu.no");
		Booking booking4 = new Booking(ht4, "bruker@ntnu.no", "bruker2@ntnu.no");
		
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		bookings.add(booking1);
		bookings.add(booking2);
		bookings.add(booking3);
		bookings.add(booking4);
		
		TimeSchedule ts = new TimeSchedule(user, null, null);
		
		ts.addBookings(bookings);
		
		ArrayList<Booking> whereTA = ts.getWhereTA();
		ArrayList<Booking> whereStudent = ts.getWhereStudent();
		
		assertTrue(whereTA.contains(booking2));
		assertTrue(whereTA.contains(booking4));
		assertTrue(whereStudent.contains(booking1));
		assertTrue(whereStudent.contains(booking3));
	}

}
