/*
File: Admin.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

package user;

import java.util.Map;

import database.DBConnection;

public class Admin extends Supervisor {


	
	/* 1 = student
	 * 2 = studass
	 * 3 = emneveileder
	 * 4 = admin
	*/
	

	public Admin(String email, String firstName, String lastName, Map<String, Integer> coursesAndRoles) {
		super(email, firstName, lastName, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}


	// Never used. Can be removed
	private static void addSupervisorPrivileges(String Email, String coursecode) {

		int role = 3;

		String email = Email.toLowerCase();
		String courseCode = coursecode.toUpperCase();
		if (DBConnection.brukerHarCourseEksisterer(email,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(email,courseCode,role);
		}
		
		
	}
	

}
