package messageTests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

import communication.Message;
import user.User;

class MessageTests {

	@Test
	void testCompareTo() {
		LocalDateTime time1 = LocalDateTime.of(2000, 12, 12, 12, 10);
		LocalDateTime time2 = LocalDateTime.of(2000, 12, 12, 12, 12);
		LocalDateTime time3 = LocalDateTime.of(2000, 12, 13, 00, 00);
		
		User user = User.generateUserObject("abc@ntnu.no");
		
		Message message1 = new Message(user, user, "subject", "message", time1);
		Message message2 = new Message(user, user, "subject", "message", time2);
		Message message3 = new Message(user, user, "subject", "message", time3);
		
		List<Message> target = new ArrayList<Message>();
		target.add(message1);
		target.add(message2);
		target.add(message3);
		
		List<Message> toSort = new ArrayList<Message>();
		toSort.add(message2);
		toSort.add(message1);
		toSort.add(message3);
		
		Collections.sort(toSort);
		
		assertEquals(toSort, target);
	}

}
