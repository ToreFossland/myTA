/*
File: Supervisor.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

package user;

import java.util.Map;

import database.DBConnection;

public class Supervisor extends TeachingAssistant {

	public Supervisor(String username, String firstName, String lastName, String email, String password,
			Map<String, Character> coursesAndRoles) {
		super(username, firstName, lastName, email, password, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}
	
	/* 1 = student
	 * 2 = studass
	 * 3 = emneveileder
	 * 4 = admin
	*/
	
	protected static void addTAPrivileges(String userName, String coursecode) {
		int role = 2;
		String username = userName.toLowerCase();
		String courseCode = coursecode.toUpperCase();
		if (DBConnection.brukerHarCourseEksisterer(username,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(username,courseCode,role);
		}
	}

}
