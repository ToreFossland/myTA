package database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import evaluation.Assignment;
import gui.App;

public class DBFile {

	// Requires that assignment object has ID
	public static File downloadFile(Assignment assignment) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			statement = con.prepareStatement("SELECT file, fileName FROM Assignment where idAssignment = ?");
			statement.setInt(1, assignment.getId());
			
			result = statement.executeQuery();
			String path = System.getProperty("java.io.tmpdir").toString() + "myTA\\" + assignment.getFileName();
			// write binary stream into file
            File file = new File(path);
            file.getParentFile().mkdirs();
            FileOutputStream output = new FileOutputStream(file);
 
            System.out.println("Writing to file " + file.getAbsolutePath());
            while (result.next()) {
                InputStream input = result.getBinaryStream("file");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }
            output.close();
            Logger.getLogger(App.class.getName()).log(Level.INFO, "File downloaded");
            return file;

		} catch (Exception e) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Could not connect");
			return null;
		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
}
