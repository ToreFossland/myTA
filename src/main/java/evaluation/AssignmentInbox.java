package evaluation;

import java.util.ArrayList;

import database.DBEvaluation;

//Used by TAAddEvaluationPageController and TAViewEvaluationsPageController
public class AssignmentInbox {
	private static Assignment selectedAssignment;
	
	public static Assignment getSelectedAssignment() {
		return selectedAssignment;
	}
	public static void setSelectedAssignment(Assignment assignment) {
		selectedAssignment = assignment;
		
	}
	
	public static ArrayList<Assignment> getAssignments(String subject){
		return DBEvaluation.getAssignments(subject);
	}
}
