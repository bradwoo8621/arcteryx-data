/**
 * 
 */
package com.github.nnest.arcteryx.data.diff;

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

/**
 * @author brad.wu
 */
public class DiffTest {
	@SuppressWarnings("resource")
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml", getClass());

		UserRepository userRepo = context.getBean(UserRepository.class);
		OrderRepository orderRepo = context.getBean(OrderRepository.class);
		try {
			PlatformTransactionManager tmUser = context.getBean("TransactionManagerUser",
					PlatformTransactionManager.class);
			PlatformTransactionManager tmOrder = context.getBean("TransactionManagerOrder",
					PlatformTransactionManager.class);
			DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
			// this must after switching data source
			TransactionStatus statusUser = tmUser.getTransaction(paramTransactionDefinition);
			TransactionStatus statusOrder = tmUser.getTransaction(paramTransactionDefinition);

			User user = new User();
			user.setName("TestTwo");
			userRepo.save(user);
			user = new User();
			user.setName("TestOne");
			userRepo.save(user);

			Order order = new Order();
			order.setName("TestOrder");
			orderRepo.save(order);
			tmUser.commit(statusUser);
			tmOrder.commit(statusOrder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}

		Iterable<User> allUsers = userRepo.findAll();
		assertTrue(allUsers.iterator().hasNext());

		User user = userRepo.findOne(1l);
		assertNotNull(user);
		assertEquals("TestTwo", user.getName());

		Order order = orderRepo.findOne(1l);
		assertNotNull(order);
		assertEquals("TestOrder", order.getName());
		this.printUserData();
		this.printOrderData();
	}

	protected void printUserData() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:user", "username", "password");
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

	protected void printOrderData() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:order", "username", "password");
			PreparedStatement ps = conn.prepareStatement("select * from t_order");
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				System.out.println(
						"Order [id=" + rst.getString("order_id") + ", name=" + rst.getString("order_name") + "]");
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}
}
