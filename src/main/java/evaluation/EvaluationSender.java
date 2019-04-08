package evaluation;

import java.util.ArrayList;

import database.DBEvaluation;

/*
 * Quite basic. 
 * Might be used in the future for sending multiple evaluations at the same time etc.
 */

public class EvaluationSender{
	ArrayList<Evaluation> Evaluations;
	
	public static void sendEvaluation(Evaluation evaluation) {
		DBEvaluation.insertEvaluation(evaluation);
	}
}