package evaluation;

import java.util.ArrayList;

import database.DBEvaluation;

public class EvaluationSender{
	ArrayList<Evaluation> Evaluations;
	
	public void sendEvaluation(Evaluation evaluation) {
		DBEvaluation.insertEvaluation(evaluation);
	}
}