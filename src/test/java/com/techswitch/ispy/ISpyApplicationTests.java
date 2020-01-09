package com.techswitch.ispy;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ISpyApplication.class)
class ISpyApplicationTests {
	@Test
	public void mainMethodTest() {
		ISpyApplication.main(new String[]{"--spring.main.web-environment=false"});
	}
}
