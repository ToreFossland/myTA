package evaluation;

import java.util.ArrayList;
import database.DBEvaluation;

public class EvaluationInbox{
	private String courseCode;
	private ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
	
	public EvaluationInbox(String course) {
		this.courseCode = course;
		refresh();
	}
	
	public void addEval(Evaluation evaluation) {
		DBEvaluation.insertEvaluation(evaluation);
	}

	public ArrayList<Evaluation> getEvaluations() {
		return evaluations;
	}
	
	public void refresh() {
		this.evaluations = DBEvaluation.getEvaluations(courseCode);
	}
}