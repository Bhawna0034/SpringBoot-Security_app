package com.bhawna.SpringBoot.Security_app;

import com.bhawna.SpringBoot.Security_app.entities.User;
import com.bhawna.SpringBoot.Security_app.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {

		User user = new User(4L, "bhawna@gmail.com", "1234");
	    String token = jwtService.generateAccessToken(user);
		System.out.println(token);

		Long userId = jwtService.getUserIdFromToken(token);
		System.out.println(userId);
	}

}
