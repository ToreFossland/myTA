package userTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import user.Admin;
import user.Student;
import user.Supervisor;
import user.TeachingAssistant;
import user.User;

class UserTests {
	
	private static User student;
	private static User TA;
	private static User supervisor;
	private static User admin;

	/*@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}*/

	@Test
	void testGenerateUserObjectStringStringStringMapOfStringInteger() {
		HashMap<String, Integer> coursesAndRoles1 = new HashMap<String, Integer>();
		HashMap<String, Integer> coursesAndRoles2 = new HashMap<String, Integer>();
		HashMap<String, Integer> coursesAndRoles3 = new HashMap<String, Integer>();
		HashMap<String, Integer> coursesAndRoles4 = new HashMap<String, Integer>();
		
		coursesAndRoles1.put("TDT4140", 1);
		coursesAndRoles2.put("TDT4140", 2);
		coursesAndRoles2.put("TDT2100", 1);
		coursesAndRoles3.put("TDT4140", 3);
		coursesAndRoles3.put("TDT4140", 3);
		coursesAndRoles4.put("TTT0000", 4);
		
		student = User.generateUserObject("student@ntnu.no", "ole", "olsen", coursesAndRoles1);
		TA = User.generateUserObject("studass@ntnu.no", "ole", "olsen", coursesAndRoles2);
		supervisor = User.generateUserObject("emneveileder@ntnu.no", "ole", "olsen", coursesAndRoles3);
		admin = User.generateUserObject("admin@ntnu.no", "ole", "olsen", coursesAndRoles4);
	
		assertTrue(student instanceof Student);
		assertTrue(TA instanceof TeachingAssistant);
		assertTrue(supervisor instanceof Supervisor);
		assertTrue(admin instanceof Admin);
		
		assertEquals(student.getType(), 1);
		assertEquals(TA.getType(), 2);
		assertEquals(supervisor.getType(), 3);
		assertEquals(admin.getType(), 4);
	}
}
