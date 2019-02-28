/*
File: Example.java    
Date 	 	Author 	Changes
--------------------------------------------
Feb 11 19 	Tore 	Created
Feb 18 19 	Tore 	Added new functionality
Feb 19 19 	Tore 	Added new functionality
Feb 20 19 	David 	Added new bugfixes
*/

package user;


import java.util.Map;

public abstract class User{
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Map<String, Integer> myCourses; //Inneholder permission og fagkode, fagkode er n�kkelen. Se getPosition for kodeforklaring.
	
	
	public User(String username, String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.myCourses = coursesAndRoles;
		this.password = password;
	}
	/*
	public boolean checkIfElementExsists(String username, String courseCode, int role) {
		boolean exists = false;
	}
	*/
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	public Map<String, Integer> getMyCourses() {
		return myCourses;
	}
	
	public Integer getRoleInCourse(String course) {
		return myCourses.get(course);
	}
	

	public int getType() {
		String className = this.getClass().getSimpleName();
		
		switch (className) {
		case "Student":
			return 1;
		case "TeachingAssistant":
			return 2;
		case "Supervisor":
			return 3;
		case "Admin":
			return 4;
		default:
			throw new NullPointerException("Could not determine user type");
		}
	}
	
	public static User generateUserObject(String username, String firstName, String lastName, String email, String password,
			Map<String, Integer> coursesAndRoles) {
		
		int permission = 1;
		for (int role : coursesAndRoles.values()) {
			permission = role > permission ? role : permission;
		}
		
		switch (permission) {
		case 1:
			return new Student(username, firstName, lastName, email, password, coursesAndRoles);
		case 2:
			return new TeachingAssistant(username, firstName, lastName, email, password, coursesAndRoles);
		case 3:
			return new Supervisor(username, firstName, lastName, email, password, coursesAndRoles);
		case 4:
			return new Admin(username, firstName, lastName, email, password, coursesAndRoles);
		default:
			return new Student(username, firstName, lastName, email, password, coursesAndRoles);
		}
	}

}

