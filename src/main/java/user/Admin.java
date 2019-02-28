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

	private static void addSupervisorPrivileges(String email, String coursecode) {
		int role = 3;
		email = email.toLowerCase();
		String courseCode = coursecode.toUpperCase();
		if (DBConnection.brukerHarCourseEksisterer(email,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(email,courseCode,role);
		}
		
		
	}
	

}
