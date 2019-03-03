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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DBBooking;
import database.DBConnection;
import halltimes.Booking;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.*;



/**
 * Main Application. This class handles navigation and user session.
 */
public class App extends Application {
    private Stage stage;
    private User loggedUser;
    
    private ArrayList<Booking> downloadedBookingsTA; //Fjerne booking fra denne on confirm
    private ArrayList<Booking> downloadedBookingsStudent;
    
    //Fag og uke med mulige bookinger
    private HashMap<String,ArrayList<Integer>> weeksDerivedFromBookingsTA;
    private HashMap<String,ArrayList<Integer>> weeksDerivedFromBookingsStudent;

    private static App instance;

    public App() {
        instance = this;
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

    @Override public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoLogin();
            primaryStage.show();
            
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }
    

    public boolean userLogin(String email, String password) throws Exception{

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
			if(loggedUser.getType() == 1 | loggedUser.getType() == 2)
			{
				DBBooking.downloadBookings();
				System.out.println(App.getInstance().getWeeksDerivedFromBookingsTA());
			}
			return true;
		} else {
			return false;
		}
    }
    
    //Overfloedig?
    /*
	public void userRegister(String email, String password, String userName, String firstName, String lastName) {
		userRegister(email, password, firstName, lastName, false);
		// doSomething
	}
	*/
	// skipCheck = true skips checking for user existence in database. Useful for
	// perfomance if this has already been checked.
	public void userRegister(String email, String password, String firstName, String lastName,
			Boolean skipCheck) {
		Map<String, Integer> courses = new HashMap<String, Integer>();
		password = toMD5(password);
		DBConnection.registerUser(email, password, firstName, lastName, skipCheck);
		loggedUser = User.generateUserObject(email, firstName, lastName, courses);
		System.out.println("Registration complete");
		// doSomething
	}

    public void userLogout(){
        loggedUser = null;
        gotoLogin();
    }

    public void gotoProfile() {
        try {
            replaceSceneContent("pages/GenericPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoAdminPage() {
        try {
            replaceSceneContent("pages/AdminPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoSupervisorPage() {
        try {
            replaceSceneContent("pages/SupervisorPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void gotoAssistantPage() {
    	try {
            replaceSceneContent("pages/AssistantPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoStudentPage() {
        try {
            replaceSceneContent("pages/StudentPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoBookingForStudent() {
        try {
            replaceSceneContent("pages/BookingForStudent.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void gotoSupervisorAddsAssistants(){
    	try {
            replaceSceneContent("pages/SupervisorAddsAssistantsToSubjects.fxml");
    		
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoSupervisorCreatesTimes() {
    	try {
            replaceSceneContent("pages/SupervisorCreatesTimes.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoAssistantChooseTime() {
        try {
            replaceSceneContent("pages/AssistantChooseTime.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    	
    public void gotoAddStudentSubject() {
    	try {
            replaceSceneContent("pages/SubjectsPageForStudent.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public boolean isRole(String Email, int Role){
		boolean match = false;
		try {
			Connection con = DBConnection.getConnection();
			String email = Email.toLowerCase();
			PreparedStatement findEmailRole = con.prepareStatement("SELECT User_email, role FROM User_has_Course "
					+ " WHERE User_email = '"+email+"' AND role = '"+Role+"'");
			ResultSet rs = findEmailRole.executeQuery();
			
			//Hvis det eksisterer noen objekter
			if (rs.next() == true) {
				match=true;
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return match;
	}
    
    

    public void gotoLogin() {
        try {
            replaceSceneContent("pages/LoginPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoRegistration() {
        try {
            replaceSceneContent("pages/RegisterPage.fxml");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(App.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            //scene = new Scene(page, 700, 450);
            //scene.getStylesheets().add(App.class.getResource("demo.css").toExternalForm());
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

	
	public void setDummyUser() {
		Map<String, Integer> dummyCourse = new HashMap<String, Integer>();
		dummyCourse.put("TDT4140", 2);
		User dummy = User.generateUserObject("abc@ntnu.no", "abc", "def", dummyCourse);
		loggedUser = dummy;
	}

	public ArrayList<Booking> getDownloadedBookingsStudent() {
		return downloadedBookingsStudent;
	}

	public void setDownloadedBookingsStudent(ArrayList<Booking> downloadedBookingsStudent) {
		this.downloadedBookingsStudent = downloadedBookingsStudent;
	}

	public ArrayList<Booking> getDownloadedBookingsTA() {
		return downloadedBookingsTA;
	}

	public void setDownloadedBookingsTA(ArrayList<Booking> downloadedBookingsTA) {
		this.downloadedBookingsTA = downloadedBookingsTA;
	}

	public HashMap<String,ArrayList<Integer>> getWeeksDerivedFromBookingsTA() {
		return weeksDerivedFromBookingsTA;
	}

	public void setWeeksDerivedFromBookingsTA(HashMap<String,ArrayList<Integer>> weeksDerivedFromBookingsTA) {
		this.weeksDerivedFromBookingsTA = weeksDerivedFromBookingsTA;
	}

	public HashMap<String,ArrayList<Integer>> getWeeksDerivedFromBookingsStudent() {
		return weeksDerivedFromBookingsStudent;
	}

	public void setWeeksDerivedFromBookingsStudent(HashMap<String,ArrayList<Integer>> weeksDerivedFromBookingsStudent) {
		this.weeksDerivedFromBookingsStudent = weeksDerivedFromBookingsStudent;
	}

}

