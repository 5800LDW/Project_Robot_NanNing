package com.tecsun.robot.evenbus;

public class EvenbusMessage {
    private String message;

    public  EvenbusMessage(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

