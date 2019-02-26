/*
File: TeachingAssistant.java 
Date 	 	Author 	Changes
--------------------------------------------
Feb 26 19 	David 	Created

*/

package user;

import java.util.Map;

public class TeachingAssistant extends Student{

	public TeachingAssistant(String username, String firstName, String lastName, String email, String password,
			Map<String, Character> coursesAndRoles) {
		super(username, firstName, lastName, email, password, coursesAndRoles);
		// TODO Auto-generated constructor stub
	}
	/* 1 = student
	 * 2 = studass
	 * 3 = emneveileder
	 * 4 = admin
	*/

}
