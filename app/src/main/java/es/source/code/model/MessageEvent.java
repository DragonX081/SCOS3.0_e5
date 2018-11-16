package es.source.code.model;

import java.util.ArrayList;

public class MessageEvent {
    private String Message;
    private ArrayList<String> paramList;
    public MessageEvent(String Message){
        this.Message = Message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getParamList() {
        return paramList;
    }

    public void setParamList(ArrayList<String> paramList) {
        this.paramList = paramList;
    }
}
