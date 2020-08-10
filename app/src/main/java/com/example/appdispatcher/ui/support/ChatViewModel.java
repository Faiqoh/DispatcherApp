package com.example.appdispatcher.ui.support;

public class ChatViewModel {

    private String from;
    private String message;
    private Integer time;

    public ChatViewModel(String from, Integer time, String message) {
        this.from = from;
        this.time = time;
        this.message = message;
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
