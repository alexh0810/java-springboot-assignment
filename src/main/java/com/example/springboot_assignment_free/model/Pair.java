package com.example.springboot_assignment_free.model;

public class Pair {
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
