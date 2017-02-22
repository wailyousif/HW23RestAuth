package com.ironyard.controller.rest;

import com.ironyard.data.ChatToken;
import com.ironyard.data.ChatUser;
import com.ironyard.repo.ChatTokenRepo;
import com.ironyard.repo.ChatUserRepo;
import com.ironyard.security.TokenMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@RestController
public class RestLogin
{
    @Autowired
    ChatUserRepo chatUserRepo;

    @Autowired
    ChatTokenRepo chatTokenRepo;

    @RequestMapping(path = "/rest/session/login", method = RequestMethod.POST)
    public ChatToken login(@RequestParam(value = "username", required = true) String username,
                           @RequestParam(value = "password", required = true) String password
                        ) throws Exception {
        ChatUser chatUser = chatUserRepo.findByUsernameAndPassword(username, password);
        if (chatUser != null)
        {
            TokenMaster tm = new TokenMaster();
            String tokenString = tm.genreateToken(chatUser);

            ChatToken chatToken = new ChatToken(tokenString, new Date(), 0, chatUser);
            chatTokenRepo.save(chatToken);
            return chatToken;
        }
        else
        {
            throw new Exception("Invalid credentials");
        }
    }
}
