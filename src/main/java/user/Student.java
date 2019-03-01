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

	public Student(String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		super(firstName, lastName, email, password, coursesAndRoles);
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
