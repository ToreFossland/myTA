package timeshedule;

import java.util.ArrayList;

import halltimes.Booking;
import user.User;

public class TimeShedule {
	
	
	private ArrayList<Booking> whereStudent; // listen over bookinger hvor han er student
	private ArrayList<Booking> whereTA;
	private User user;
	
	public TimeShedule(User user, ArrayList<Booking> bookings) {
		setUser(user);
		addBookings(bookings);
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void addBookings(ArrayList<Booking> bookings) {
		for (Booking booking:bookings) {
			if (booking.getEmailStudent() == user.getEmail()) {
				whereStudent.add(booking);
			}
			else { whereTA.add(booking);	
			}
		}
	}
	
}
