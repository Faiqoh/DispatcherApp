package com.example.appdispatcher.ui.support;

public class ChatViewModel {

    private String from;
    private String message;
    private Integer time;
    private String timeFormated;

    public ChatViewModel(String from, Integer time, String message, String timeFormated) {
        this.from = from;
        this.time = time;
        this.message = message;
        this.timeFormated = timeFormated;

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

    public String getTimeFormated() {
        return timeFormated;
    }

    public void setTimeFormated(String timeFormated) {
        this.timeFormated = timeFormated;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
