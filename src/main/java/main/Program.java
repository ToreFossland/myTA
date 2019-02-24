package main;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.HashMap;
import database.DBConnection;

public class Program {

	private static User user;

	public static void main(String[] args) {
		
	}
	
	public static boolean login(String username, String password) {

		// encrypts the password with MD5
		password = toMD5(password);

		boolean success = false;

		try {
			success = DBConnection.usernamePasswordMatch(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (success) {
			Program.user = DBConnection.returnUserObject(username);
			System.out.println("Logged in");
			return true;
		} else {
			return false;
		}
	}

	public static String toMD5(String cleartext) {
		// Static getInstance method is called with hashing MD5
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// digest() method is called to calculate message digest
		// of an input digest() return array of byte
		byte[] messageDigest = md.digest(cleartext.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String hashtext = no.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}

	public static User getUser() {
		return user;
	}

	public static void register(String email, String password, String userName, String firstName, String lastName) {
		register(email, password, userName, firstName, lastName, false);
		// doSomething
	}

	// skipCheck = true skips checking for user existence in database. Useful for
	// perfomance if this has already been checked.
	public static void register(String email, String password, String userName, String firstName, String lastName,
			Boolean skipCheck) {
		HashMap<String, Character> courses = new HashMap<String, Character>();
		password = toMD5(password);
		DBConnection.registerUser(email, password, userName, firstName, lastName, skipCheck);
		Program.user = new User(userName, firstName, lastName, email, password, courses);
		System.out.println("Registration complete");
		// doSomething
	}
}
