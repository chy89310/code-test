package cn.kevinsum.codetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class CodeTestApplicationTests {

	@Test
		// generate multiple input for each currency to test if the record calculate correctly
	void recordTest() {
		String [] currencies = {"MOP", "HKD", "CNY"};
		Random random = new Random();
		for (String currency : currencies) {
			double amount = 0;
			for (int i = 0; i < 10; i++) {
				// generate random number between 100.00 ~ -20.00
				double randomAmount = ((double)(random.nextInt(12000)) - 2000) / 100;
				CodeTestApplication.addNewPayment(String.format("%s %f", currency, randomAmount));
				amount += randomAmount;
			}
			Assertions.assertEquals(amount, CodeTestApplication.records.get(currency));
		}
	}
}
