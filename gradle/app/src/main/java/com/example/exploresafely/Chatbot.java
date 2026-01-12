package com.example.exploresafely;


public class Chatbot {
    public static String getReply(String userMessage) {
        return ResponseData.getResponse(userMessage);
    }
}
