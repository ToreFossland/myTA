package evaluation;

import java.util.ArrayList;

import database.DBEvaluation;
import gui.App;

public class AssignmentInbox {
	
	public ArrayList<Assignment> getAssignments(String subject){
		return DBEvaluation.getAssignments(subject);
	}
	
	
}
