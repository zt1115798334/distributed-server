package com.example.distributedauthentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class DistributedAuthenticationApplicationTests {

//	private final UserService userService;


	@Test
	void initUser() {

		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			String account = "account" + random.nextInt();
			String password = "123456";
			String userName = "userName" + random.nextInt();
			String phone = "phone" + random.nextInt();
			String email = "email" + random.nextInt() + "@qq.com";
//			userService.createdUser(account, password, userName, phone, email);
		}
	}

}
