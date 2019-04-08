

package user;

import java.util.Map;

import database.DBConnection;

public class Student extends User{

	
	public Student(String email, String firstName, String lastName, Map<String, Integer> coursesAndRoles) {
		super(email, firstName, lastName, coursesAndRoles);

		// TODO Auto-generated constructor stub
	}

	// Never used. Can be removed
	private void addCourse(String coursecode) {
		int role = 1;
		
		String courseCode = coursecode.toUpperCase();
		String email = getEmail();
		if (DBConnection.brukerHarCourseEksisterer(email,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(email,courseCode,role);
		}
		
	}

}
