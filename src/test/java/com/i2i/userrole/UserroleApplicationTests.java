package com.i2i.userrole;

import org.assertj.core.api.Assert;
import org.assertj.core.error.AssertionErrorMessagesAggregrator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserroleApplicationTests {

	@Test
	void simpletest() {
		Assertions.assertEquals(3, 3);
	}

}
