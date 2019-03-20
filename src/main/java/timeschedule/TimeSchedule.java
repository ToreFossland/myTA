package timeschedule;

import java.util.ArrayList;

import halltimes.Booking;
import user.User;

public class TimeSchedule {
	
	
	private ArrayList<Booking> whereStudent; // listen over bookinger hvor han er student
	private ArrayList<Booking> whereTA;
	private User user;
	
	public TimeSchedule(User user, ArrayList<Booking> bookingsStudent, ArrayList<Booking> bookingsTA) {
		setUser(user);
		this.whereStudent = bookingsStudent;
		this.whereTA = bookingsTA;
		
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void addBookings(ArrayList<Booking> bookings) {
		for (Booking booking:bookings) {
			if (booking.getEmailStudent() == user.getEmail()) {
				whereStudent.add(booking);
			}
			else {whereTA.add(booking);	
			}
		}
	}
	
}
