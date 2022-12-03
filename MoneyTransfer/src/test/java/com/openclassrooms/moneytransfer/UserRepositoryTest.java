package com.openclassrooms.moneytransfer;

import com.openclassrooms.moneytransfer.model.User;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void userRepositoryTest() {
		User user = new User();
		user.setFirstName("firstNameTest");
		user.setLastName("lastNameTest");
		user.setEmail("emailTest@gmail.com");

		// Save
		user = userRepository.save(user);
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user.getEmail(), "emailTest@gmail.com");
		Assert.assertEquals(user.getFirstName(), "firstNameTest");
		Assert.assertEquals(user.getLastName(), "lastNameTest");

		// Update
		user.setFirstName("user2");
		user = userRepository.save(user);
		Assert.assertEquals(user.getFirstName(), "user2");

		// Find
		User user1 = userRepository.findByEmail("emailTest@gmail.com").get();
		Assert.assertEquals(user1.getFirstName(), "user2");
		Assert.assertEquals(user1.getLastName(), "lastNameTest");

		// Delete
		Integer id = user.getId();
		userRepository.delete(user);
		Optional<User> user2 = userRepository.findById(id);
		Assert.assertFalse(user2.isPresent());
	}
}
