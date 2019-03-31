/*
 * Copyright (c) 2008, 2011 Oracle and/or its affiliates. 
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gui;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import database.DBBooking;
import database.DBConnection;
import database.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import timeschedule.TimeSchedule;
import user.*;

/**
 * Main Application. This class handles navigation and user session.
 */
public class App extends Application {
	private Stage stage;
	private User loggedUser;

	private Stack<String> pagesHistory;
	
	TimeSchedule myTimeSchedule;

	private static App instance;

	public App() {
		instance = this;
		pagesHistory = new Stack<String>();
	}

	public static App getInstance() {
		return instance;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			gotoFrontPage();
			primaryStage.show();
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public Stage getStage() {
		return this.stage;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public boolean userLogin(String email, String password) throws Exception {

		// encrypts the password with MD5
		password = toMD5(password);

		boolean success = false;

		try {
			success = DBConnection.usernamePasswordMatch(email, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (success) {
			loggedUser = DBConnection.returnUserObject(email);
			if (loggedUser.getType() == 1 | loggedUser.getType() == 2) {
				DBBooking.downloadBookings(getLoggedUser());
				TimeSchedule ts = new TimeSchedule(loggedUser, DBBooking.getBookingsStudent(), DBBooking.getBookingsTA());
				setMyTimeSchedule(ts);
			}
			return true;
		} else {
			return false;
		}
	}

	public void userRegister(String email, String password, String firstName, String lastName, Boolean skipCheck) {
		Map<String, Integer> courses = new HashMap<String, Integer>();
		password = toMD5(password);
		DBConnection.registerUser(email, password, firstName, lastName, skipCheck);
		loggedUser = User.generateUserObject(email, firstName, lastName, courses);
		System.out.println("Registration complete");
		// doSomething
	}

	public void userLogout() {
		loggedUser = null;
		gotoLogin();

	}
	
	public void gotoProfile() {
		try {
			String page = "pages/GenericPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoAdminPage() {
		try {
			String page = "pages/AdminPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoSupervisorPage() {
		try {
			String page = "pages/SupervisorPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoFrontPage() {
		try {
			String page = "pages/FrontPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoAssistantPage() {
		try {
			String page = "pages/AssistantPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoStudentPage() {
		try {
			String page = "pages/StudentPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoBookingForStudent() {
		try {
			String page = "pages/BookingForStudent.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoSupervisorAddsAssistants() {
		try {
			String page = "pages/SupervisorAddsAssistantsToSubjects.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoSupervisorCreatesTimes() {
		try {
			String page = "pages/SupervisorCreatesTimes.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoAssistantChooseTime() {
		try {
			String page = "pages/AssistantChooseTime.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoAddStudentSubject() {
		try {
			String page = "pages/SubjectsPageForStudent.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void gotoRegistration() {
		try {
			String page = "pages/RegisterPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
			Logger.getLogger(App.class.getName()).log(Level.INFO, "Changed to registration (this is just a logger test)");
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoSendMessagePage() {
		try {
			String page = "pages/SendMessagePage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void gotoInboxPage() {
		try {
			String page = "pages/InboxPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void gotoReadMessagePage() {
		try {
			String page = "pages/ReadMessagePage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
    public void gotoTAViewEvaluationsPage() {
    	try {
    		String page = "pages/TAViewEvaluationsPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoTAAddEvaluationPage() {
    	try {
    		String page = "pages/TAAddEvaluationPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void gotoStudentAddOrView() {
    	try {
    		String page = "pages/StudentAddOrViewPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoViewAssignments() {
    	try {
    		String page = "pages/StudentViewAssignmentsPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
    		replaceSceneContent("pages/StudentViewAssignmentsPage.fxml");
    	} catch (Exception ex) {
    		Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }
    public void gotoMyCalendar() {
    	try {
    		String page = "pages/MyCalendar1.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
    		replaceSceneContent("pages/MyCalendar1.fxml");
    	} catch (Exception ex) {
    		Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }
    public void gotoBookingInfoStudent() {
		try {
			String page = "pages/BookingInfoStudent.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    public void gotoBookingInfoTA() {
		try {
			String page = "pages/BookingInfoTA.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
    public void gotoAddAssignment() {
    	try {
    		String page = "pages/StudentAddAssignmentPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
    	} catch (Exception ex) {
    		Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }	
	
	public void gotoLogin() {
		try {
			String page = "pages/LoginPage.fxml";
			pagesHistory.push(page);
			replaceSceneContent(page);
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void gotoPrevious() {
		try {
			if (pagesHistory.size() > 1) {
				pagesHistory.pop();
				replaceSceneContent(pagesHistory.peek());
			} else {
				Logger.getLogger(App.class.getName()).log(Level.INFO, "Cannot go to previous page as page history is empty");
			}
		} catch (Exception ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void clearHistory() {
		pagesHistory.clear();
	}

	// Database methods should not be placed in this class
	public boolean isRole(String Email, int Role) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;

				boolean match = false;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			String email = Email.toLowerCase();
			statement = con.prepareStatement("SELECT User_email, role FROM User_has_Course " + " WHERE User_email = '"
					+ email + "' AND role = '" + Role + "'");
			result = statement.executeQuery();

			// Hvis det eksisterer noen objekter
			if (result.next() == true) {
				match = true;
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (result != null)
					result.close();
			} catch (Exception e) {
			}
			;
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
			;

		}

		return match;
 }

	private Parent replaceSceneContent(String fxml) throws Exception {
		Parent page = (Parent) FXMLLoader.load(App.class.getResource(fxml), null, new JavaFXBuilderFactory());
		Scene scene = stage.getScene();
		if (scene == null) {
			scene = new Scene(page);
			// scene = new Scene(page, 700, 450);
			// scene.getStylesheets().add(App.class.getResource("demo.css").toExternalForm());
			stage.setScene(scene);
		} else {
			stage.getScene().setRoot(page);
		}
		stage.sizeToScene();
		return page;
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

	public TimeSchedule getMyTimeSchedule() {
		return myTimeSchedule;
	}

	public void setMyTimeSchedule(TimeSchedule myTimeSchedule) {
		this.myTimeSchedule = myTimeSchedule;
	}

}
