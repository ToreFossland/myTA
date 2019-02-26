/*
File: Student.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

package user;

import java.util.Map;

public class Student extends User{

	public Student(String username, String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		super(username, firstName, lastName, email, password, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}

}
