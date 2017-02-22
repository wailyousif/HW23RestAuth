package com.ironyard.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/20/17.
 */

public class RestChatMessage
{
    @Id
    private long id;

    @Column(length = 512)
    private String messageText;

    private Date postTime;

    private long chatUserId;

    private long messageBoardId;

    private String picFileName;

    public RestChatMessage() { }

    public RestChatMessage(String messageText, Date postTime) {
        this.messageText = messageText;
        this.postTime = postTime;
    }

    public RestChatMessage(String messageText, Date postTime, String picFileName) {
        this.messageText = messageText;
        this.postTime = postTime;
        this.picFileName = picFileName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getPicFileName() {
        return picFileName;
    }

    public void setPicFileName(String picFileName) {
        this.picFileName = picFileName;
    }

    public long getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(long chatUserId) {
        this.chatUserId = chatUserId;
    }

    public long getMessageBoardId() {
        return messageBoardId;
    }

    public void setMessageBoardId(long messageBoardId) {
        this.messageBoardId = messageBoardId;
    }
}
