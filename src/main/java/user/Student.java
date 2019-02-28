/*
File: Student.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

package user;

import java.util.Map;

import database.DBConnection;

public class Student extends User{

	public Student(String username, String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		super(username, firstName, lastName, email, password, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}
	
	private void addCourse(String coursecode) {
		int role = 1;
		
		String courseCode = coursecode.toUpperCase();
		String username = getUsername();
		if (DBConnection.brukerHarCourseEksisterer(username,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(username,courseCode,role);
		}
		
	}

}
