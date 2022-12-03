package com.openclassrooms.moneytransfer;

import com.openclassrooms.moneytransfer.model.Transaction;
import com.openclassrooms.moneytransfer.repository.TransactionRepository;
import com.openclassrooms.moneytransfer.service.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void transactionRepositoryTest() {
		Transaction transaction = new Transaction();
		transaction.setAmount(20);
		transaction.setDescription("descriptionTest");

		// Save
		transaction = transactionRepository.save(transaction);
		Assert.assertNotNull(transaction.getId());
		Assert.assertEquals(20,transaction.getAmount(),0.01);
		Assert.assertEquals("descriptionTest", transaction.getDescription());


		// Update
		transaction.setAmount(30);
		transaction = transactionRepository.save(transaction);
		Assert.assertEquals(30,transaction.getAmount(),0.01);

		// Find
		Transaction transaction1 = transactionRepository.findById(transaction.getId()).get();
		Assert.assertEquals(30,transaction.getAmount(),0.01);
		Assert.assertEquals("descriptionTest", transaction.getDescription());

		// Delete
		Integer id = transaction.getId();
		transactionRepository.delete(transaction1);
		Optional<Transaction> transaction2 = transactionRepository.findById(id);
		Assert.assertFalse(transaction2.isPresent());
	}
}
