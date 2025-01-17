package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.time.Instant;

public class MessageResponseDTO extends BaseObservable {
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
    @Bindable
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    @Bindable
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Bindable
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Bindable
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
