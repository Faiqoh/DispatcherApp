package com.example.appdispatcher.ui.support;

public class ChatViewModel {

    private String from;
    private String message;
    private String time;

    /*public ChatViewModel(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }*/

    public ChatViewModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /*public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/
}
