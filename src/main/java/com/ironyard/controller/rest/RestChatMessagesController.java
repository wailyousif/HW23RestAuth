package com.ironyard.controller.rest;

import com.ironyard.data.ChatMessage;
import com.ironyard.data.ChatUser;
import com.ironyard.repo.ChatMessageRepo;
import com.ironyard.repo.ChatTokenRepo;
import com.ironyard.repo.ChatUserRepo;
import com.ironyard.repo.MessageBoardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wailm.yousif on 2/20/17.
 */

@RestController
public class RestChatMessagesController
{
    @Autowired
    ChatMessageRepo chatMessageRepo;

    @Autowired
    ChatUserRepo chatUserRepo;

    @Autowired
    MessageBoardRepo messageBoardRepo;

    @Autowired
    ChatTokenRepo chatTokenRepo;


    @RequestMapping(path = "/rest/chats/list", method = RequestMethod.GET)
    public Page<ChatMessage> listMessages(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size,
                                        @RequestParam(value = "dir", required = false) Sort.Direction dir)
    {
        if(page == null){
            page = 0;
        }
        if(size == null){
            size = 2;
        }
        if(dir == null){
            dir = Sort.Direction.DESC;
        }
        Sort s = new Sort(dir, "postTime");
        PageRequest pr = new PageRequest(page, size, s);

        Page<ChatMessage> found = chatMessageRepo.findAll(pr);

        /*
        long tokenId = 2;
        ChatToken dbChatToken = chatTokenRepo.findOne(tokenId);
        System.out.println("ChatToken String=" + dbChatToken.getTokenString() + "#");
        dbChatToken.setNumOfCalls(dbChatToken.getNumOfCalls()+1);
        chatTokenRepo.save(dbChatToken);
        */

        return found;
    }


    @RequestMapping(path = "/rest/chats/create", method = RequestMethod.POST)
    public ChatMessage createMessage(@RequestAttribute Long userId, @RequestBody ChatMessage chatMessage) throws Exception
    {
        //ChatUser chatUser = chatUserRepo.findOne(chatMessage.getChatUserId());
        ChatUser chatUser = chatUserRepo.findOne(userId);
        chatMessage.setChatUser(chatUser);

        /*
        MessageBoard messageBoard = messageBoardRepo.findOne(restChatMessage.getMessageBoardId());
        ChatMessage chatMessage = new ChatMessage(restChatMessage.getMessageText(),
                restChatMessage.getPostTime(),
                restChatMessage.getPicFileName()
                );
        chatMessage.setMessageBoard(messageBoard);
        */

        chatMessageRepo.save(chatMessage);
        return chatMessage;
    }


    @RequestMapping(path = "/rest/chats/delete/{id}",  method = RequestMethod.DELETE)
    public void deleteMessage(@PathVariable Long id) throws Exception
    {
        chatMessageRepo.delete(id);
        return;
    }


    @RequestMapping(path = "/rest/chats/getone/{id}",  method = RequestMethod.GET)
    public ChatMessage getByMessageId(@PathVariable Long id) throws Exception
    {
        ChatMessage found = chatMessageRepo.findOne(id);
        return found;
    }


    @RequestMapping(path = "/rest/chats/update",  method = RequestMethod.PATCH)
    public ChatMessage editMovie(@RequestAttribute Long userId, @RequestBody ChatMessage chatMessage) throws Exception
    {
        /*
        ChatUser chatUser = chatUserRepo.findOne(restChatMessage.getChatUserId());
        if (chatUser == null)
            throw new Exception("Invalid Chat-User Id.");
        */
        ChatUser chatUser = chatUserRepo.findOne(userId);
        chatMessage.setChatUser(chatUser);

        /*
        MessageBoard messageBoard = messageBoardRepo.findOne(restChatMessage.getMessageBoardId());
        if (messageBoard == null)
            throw new Exception("Invalid Message-Board Id.");

        ChatMessage chatMessage = chatMessageRepo.findOne(restChatMessage.getId());
        if (chatMessage == null)
            throw new Exception("Invalid Chat-Message Id.");

        chatMessage.setChatUser(chatUser);
        chatMessage.setMessageBoard(messageBoard);

        if (restChatMessage.getMessageText() != null)
            chatMessage.setMessageText(restChatMessage.getMessageText());

        if (restChatMessage.getPostTime() != null)
            chatMessage.setPostTime(restChatMessage.getPostTime());

        if (restChatMessage.getPicFileName() != null)
            chatMessage.setPicFileName(restChatMessage.getPicFileName());
            */

        chatMessageRepo.save(chatMessage);
        return chatMessage;
    }

}
