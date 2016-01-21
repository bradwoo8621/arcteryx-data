/**
 * 
 */
package com.github.nnest.arcteryx.data.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.github.nnest.arcteryx.data.routing.IDataSourceRouter;

/**
 * @author brad.wu
 */
public class RoutingTest {
	@SuppressWarnings("resource")
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml", getClass());

		IDataSourceRouter router = context.getBean(IDataSourceRouter.class);

		UserRepository repo = context.getBean(UserRepository.class);
		try {
			PlatformTransactionManager tm = context.getBean(PlatformTransactionManager.class);
			DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
			router.switchTo("two");
			// this must after switching data source
			TransactionStatus statusTwo = tm.getTransaction(paramTransactionDefinition);

			User user = new User();
			user.setName("TestTwo");

			repo.save(user);
			tm.commit(statusTwo);
			router.release();
			
			router.switchTo("one");
			TransactionStatus statusOne = tm.getTransaction(paramTransactionDefinition);
			
			user = new User();
			user.setName("TestOne");
			
			repo.save(user);
			tm.commit(statusOne);
			
			System.out.println("Data printing after creating...");
			printData("jdbc:hsqldb:mem:one");
			printData("jdbc:hsqldb:mem:two");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			router.release();
		}

		router.switchTo("two");
		Iterable<User> allUsers = repo.findAll();
		assertTrue(allUsers.iterator().hasNext());

		User user = repo.findOne(1l);
		assertNotNull(user);
		assertEquals("TestTwo", user.getName());
		router.release();

		router.switchTo("one");
		allUsers = repo.findAll();
		assertTrue(allUsers.iterator().hasNext());
		router.release();

		System.out.println("Final data printing...");
		printData("jdbc:hsqldb:mem:one");
		printData("jdbc:hsqldb:mem:two");
	}

	protected void printData(String url) {
		System.out.println("Data from [" + url + "]");
		try {
			Connection conn = DriverManager.getConnection(url, "username", "password");
			PreparedStatement ps = conn.prepareStatement("select * from t_user");
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				System.out
						.println("User [id=" + rst.getString("user_id") + ", name=" + rst.getString("user_name") + "]");
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
