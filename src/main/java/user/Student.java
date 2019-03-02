

package user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DBConnection;
import halltimes.Halltime;

public class Student extends User{

	
	public Student(String email, String firstName, String lastName, Map<String, Integer> coursesAndRoles) {
		super(email, firstName, lastName, coursesAndRoles);

		// TODO Auto-generated constructor stub
	}

	private void addCourse(String coursecode) {
		int role = 1;
		
		String courseCode = coursecode.toUpperCase();
		String email = getEmail();
		if (DBConnection.brukerHarCourseEksisterer(email,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(email,courseCode,role);
		}
		
	}

}
