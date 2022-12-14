package evaluationTests;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import org.junit.jupiter.api.Test;
import evaluation.Assignment;
import evaluation.Evaluation;
import user.Student;
import user.User;


class AssignmentTest {
	@Test
	void testConstructorAssignment() {
		Map<String, Integer> coursesAndRoles = null;
		Student u = new Student("user@ntnu.no", "Tore", "Fossland", coursesAndRoles);
		Assignment assign = new Assignment(u, "TDT4140", "Assignment", LocalDateTime.of(2017,Month.FEBRUARY,3,6,30,40,50000), " ");
		assertTrue(assign.getCourseCode() == "TDT4140" && assign.getDeliveredBy() == u && assign.getTimestamp().equals(
				LocalDateTime.of(2017,Month.FEBRUARY,3,6,30,40,50000)));
	}
	
	@Test
	void testConstructorEvaluation() {
		User user1 = User.generateUserObject("hei@ntnu.no");
		User user2 = User.generateUserObject("hallo@ntnu.no");
		Assignment assign = new Assignment(user1, "TDT4140", "Assignment", LocalDateTime.of(2017,Month.FEBRUARY,3,6,30,40,50000), " ");
		Evaluation eval = new Evaluation(80, user2, assign, "hei");
		assertTrue(eval.getAssignment() == assign && eval.getAssignmentName() == assign.getAssignmentName() && 
				eval.getCourseCode() == "TDT4140" && eval.getNote() == "hei" && eval.getScore() == 80);
		
	}
	
	@Test
	void testCompareTo() {
		User user = User.generateUserObject("abc@ntnu.no");
		LocalDateTime time1 = LocalDateTime.of(2000, 12, 12, 12, 10);
		LocalDateTime time2 = LocalDateTime.of(2000, 12, 12, 12, 12);
		LocalDateTime time3 = LocalDateTime.of(2000, 12, 13, 00, 00);
		Assignment assign1 = new Assignment(user, "TDT4140", "Assignment", time1, " ");
		Assignment assign2 = new Assignment(user, "TDT4140", "Assignment", time2, " ");
		Assignment assign3 = new Assignment(user, "TDT4140", "Assignment", time3, " ");
		Evaluation eval1 = new Evaluation(80, user, assign1, "hei");
		Evaluation eval2 = new Evaluation(80, user, assign2, "hei");
		Evaluation eval3 = new Evaluation(80, user, assign3, "hei");
		eval1.setTimestamp(time1);
		eval2.setTimestamp(time2);
		eval3.setTimestamp(time3);
		
	
		int x = eval1.compareTo(eval2);
		int y = eval3.compareTo(eval2);
		int z = eval2.compareTo(eval2);
		assertTrue(x == 1 && y == -1 && z == 0);
	}
}





