package evaluation;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluationInbox{
	
	private HashMap<String, ArrayList<Evaluation>> evaluations = new HashMap<String, ArrayList<Evaluation>>();
	
	public void addEval(Evaluation evaluation) {
		
	}

	public HashMap<String, ArrayList<Evaluation>> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(HashMap<String, ArrayList<Evaluation>> evaluations) {
		this.evaluations = evaluations;
	}
}