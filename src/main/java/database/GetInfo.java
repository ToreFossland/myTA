package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import user.User;

public class GetInfo extends DBConnection {
	// collects all relevant information about user (used when loggin in).
	// Return Array and Dictionary of courses
	public User returnUserObject(String email) {
		try {
			String query = String.format("SELECT * FROM User WHERE username = '%s'", email);
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			rs.next();

			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");

			query = String.format("SELECT Course_courseCode, role FROM User_has_Course WHERE User_email = '%s'", email);
			st = conn.prepareStatement(query);
			rs = st.executeQuery();

			Map<String, Integer> coursesAndRoles = new HashMap<String, Integer>();
			while (rs.next()) {
				coursesAndRoles.put(rs.getString("Course_courseCode"), rs.getInt("role"));
			}

			return User.generateUserObject(email, firstName, lastName, coursesAndRoles);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int getUserPermission(String username) {
		try {
			PreparedStatement findRole = conn
					.prepareStatement("SELECT DISTINCT role FROM User_has_Course WHERE User_username = %s");
			ResultSet roles = findRole.executeQuery();

			int permission = 1;
			while (roles.next()) {
				permission = roles.getInt("role") > permission ? roles.getInt("role") : permission;
			}
			return permission;
		} catch (SQLException e) { // TODO Auto-generated catch
			e.printStackTrace();
		}

		return 1;
	}
}
