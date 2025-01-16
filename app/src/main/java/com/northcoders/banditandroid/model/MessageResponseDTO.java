package com.northcoders.banditandroid.model;

import java.time.Instant;

public class MessageResponseDTO {
    private String senderId;
    private String receiverId;
    private String messageBody;
    private String createdAt;

    public MessageResponseDTO() {
    }

    public MessageResponseDTO(String senderId, String receiverId, String messageBody, String createdAt) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageBody = messageBody;
        this.createdAt = createdAt;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MessageResponseDTO{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
