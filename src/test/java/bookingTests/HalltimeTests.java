package bookingTests;

import static org.junit.jupiter.api.Assertions.*;  

import java.time.LocalTime;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.MINUTES;

import org.junit.jupiter.api.Test;

import halltimes.Booking;
import halltimes.Halltime;

class HalltimeTests {

	@Test
	void testSplitIntoMultipleBookings() {

		LocalTime timeStart = LocalTime.of(13, 0, 0);
		LocalTime timeEnd = LocalTime.of(15, 0, 0);
		Halltime ht = new Halltime("TDT4140", 5, 3, timeStart, timeEnd, 12);
		Booking booking = new Booking(ht, "abc@ntnu.no");
		
		ArrayList<Booking> expectedResult = new ArrayList<Booking>();
		expectedResult.add(new Booking(new Halltime("TDT4140",5,3,LocalTime.of(13, 0, 0),LocalTime.of(13, 30, 0),12),"abc@ntnu.no"));
		expectedResult.add(new Booking(new Halltime("TDT4140",5,3,LocalTime.of(13, 30, 0),LocalTime.of(14, 00, 0),12),"abc@ntnu.no"));
		expectedResult.add(new Booking(new Halltime("TDT4140",5,3,LocalTime.of(14, 0, 0),LocalTime.of(14, 30, 0),12),"abc@ntnu.no"));
		expectedResult.add(new Booking(new Halltime("TDT4140",5,3,LocalTime.of(14, 30, 0),LocalTime.of(15, 00, 0),12),"abc@ntnu.no"));
		
		for (int i = 0; i < 4; i++) {
			assertTrue(expectedResult.get(i).getStartTime().until(booking.splitIntoMultipleBookings(30).get(i).getStartTime(), MINUTES) == 0);
		}
		
	}

}
