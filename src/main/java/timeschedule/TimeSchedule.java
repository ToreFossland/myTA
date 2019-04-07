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
		if(bookingsStudent != null)
			this.whereStudent = bookingsStudent;
		else
			this.whereStudent = new ArrayList<Booking>();
		
		if(bookingsTA != null)
			this.whereTA = bookingsStudent;
		else
			this.whereTA = new ArrayList<Booking>();
		
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
	
	public ArrayList<Booking> getWhereStudent() {
		return whereStudent;
	}
	
	public ArrayList<Booking> getWhereTA() {
		return whereTA;
	}
	
}
