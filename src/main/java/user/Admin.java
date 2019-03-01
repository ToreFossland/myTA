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

	public Admin(String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		super(firstName, lastName, email, password, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}
	
	/* 1 = student
	 * 2 = studass
	 * 3 = emneveileder
	 * 4 = admin
	*/
	
	private static void addSupervisorPrivileges(String Email, String coursecode) {
		int role = 3;
		String email = Email.toLowerCase();
		String courseCode = coursecode.toUpperCase();
		if (DBConnection.brukerHarCourseEksisterer(email,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(email,courseCode,role);
		}
		
		
	}
	

}
