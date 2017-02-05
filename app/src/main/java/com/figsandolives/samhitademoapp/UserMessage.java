package com.figsandolives.samhitademoapp;

/**
 * Created by rahim on 5/2/17.
 */

public class UserMessage {

    private String message;
    private String sender;

    public UserMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
