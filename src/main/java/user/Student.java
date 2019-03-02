/*
File: Student.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

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
		String username = getEmail();
		if (DBConnection.brukerHarCourseEksisterer(username,courseCode,role)==false) {
			DBConnection.leggTilUserHarCourse(username,courseCode,role);
		}
		
	}

}
