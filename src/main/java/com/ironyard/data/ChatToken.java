package com.ironyard.data;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wailm.yousif on 2/21/17.
 */

@Entity
public class ChatToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence")
    @SequenceGenerator(name="token_sequence", sequenceName = "token_sequence", allocationSize = 1, initialValue = 1)
    private long id;

    private String tokenString;
    private Date creationTime;
    private long numOfCalls;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId=true)
    private ChatUser chatUser;

    public ChatToken() { }

    public ChatToken(String tokenString, Date creationTime, long numOfCalls, ChatUser chatUser) {
        this.tokenString = tokenString;
        this.creationTime = creationTime;
        this.numOfCalls = numOfCalls;
        this.chatUser = chatUser;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getNumOfCalls() {
        return numOfCalls;
    }

    public void setNumOfCalls(long numOfCalls) {
        this.numOfCalls = numOfCalls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public void setChatUser(ChatUser chatUser) {
        this.chatUser = chatUser;
    }
}
