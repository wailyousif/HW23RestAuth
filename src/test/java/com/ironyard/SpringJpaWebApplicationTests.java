package com.ironyard;

import com.ironyard.data.ChatUser;
import com.ironyard.repo.ChatUserRepo;
import com.ironyard.security.TokenMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJpaWebApplicationTests
{

	@Autowired
	ChatUserRepo chatUserRepo;

	@Test
	public void testToken()
	{
		TokenMaster tm = new TokenMaster();
		long userId = 1;
		ChatUser chatUser = chatUserRepo.findOne(userId);

		if (chatUser != null)
		{
			String token = tm.genreateToken(chatUser);
			System.out.println("token=" + token + "#");

			Long decryptedTokenUserId = tm.validateTokenAndGetUserId(token);
			System.out.println("userId=" + decryptedTokenUserId + "#");
		}
		else
			System.out.println("This is an invalid user");
	}

	@Test
	public void contextLoads()
	{

	}

}
