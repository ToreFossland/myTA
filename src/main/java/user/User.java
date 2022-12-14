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

/*
 * Abstract class, so cannot be instantiated.
 * User.generateUserObject(...) returns a user-object of proper type depending on its role in different courses.
 * 
 * Different functions/attributes for different users was intended, but since that never happened 
 * this can be restructured to a single class (with type stored as attribute) without consequenses
 */
public abstract class User {
	private String email;
	private String firstName;
	private String lastName;
	private Map<String, Integer> myCourses; // Inneholder permission og fagkode, fagkode er n�kkelen. Se getPosition for
											// kodeforklaring.

	public User(String email, String firstName, String lastName,

			Map<String, Integer> coursesAndRoles) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.myCourses = coursesAndRoles;
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

	/*
	 * 1 = Student
	 * 2 = Teaching assistant
	 * 3 = Supervisor
	 * 4 = Admin
	 */
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

	/* 
	 * If user is both student and TA, will return TeachingAssistant-object
	 * If user has no courses, will return Student-object
	 */
	public static User generateUserObject(String email, String firstName, String lastName,
			Map<String, Integer> coursesAndRoles) {

		int permission = 1;
		if (coursesAndRoles != null) {
			for (int role : coursesAndRoles.values()) {
				permission = role > permission ? role : permission;
			}
		}

		switch (permission) {
		case 1:
			return new Student(email, firstName, lastName, coursesAndRoles);

		case 2:
			return new TeachingAssistant(email, firstName, lastName, coursesAndRoles);

		case 3:
			return new Supervisor(email, firstName, lastName, coursesAndRoles);

		case 4:
			return new Admin(email, firstName, lastName, coursesAndRoles);

		default:
			return new Student(email, firstName, lastName, coursesAndRoles);
		}
	}

	public static User generateUserObject(String email) {
		return generateUserObject(email, null, null, null);
	}

	public void addCourse(String courseCode, int role) {
		myCourses.put(courseCode, role);
	}
}