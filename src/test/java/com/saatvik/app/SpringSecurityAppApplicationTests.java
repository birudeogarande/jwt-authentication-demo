package com.saatvik.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityAppApplicationTests {


	private final MockMvc mvc;

	@Autowired
    SpringSecurityAppApplicationTests(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
	void contextLoads() {
	}

	@Test
	@WithMockUser
	void sayHello() throws Exception {
		var expectedContent = "Hello, There!";
		var uri = "/";
		mvc.perform(MockMvcRequestBuilders.get(uri))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedContent));
	}
}
