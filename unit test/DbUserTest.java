import static org.junit.Assert.*;

import org.junit.Test;

import customTools.DbUser;
import model.BhUser;

public class DbUserTest {

	@Test
	public void test() {
		BhUser user = null;
		user = DbUser.getUserByEmail("user1@domain.com");
		assertTrue (user.getUseremail().equals("user1@domain.com"));
	}
	@Test 
	public void Test2() {
	DbUser.isValidUser("user1@domain.com","password");
	}
	@Test
	public void Test3() {
		session.setAttribute("user", user);
	}
	
	
}
