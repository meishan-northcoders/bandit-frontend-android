package com.northcoders.banditandroid.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class MessageRequestDTO extends BaseObservable {

    //Sender id taken from context i.e. is the current user
    private String receiverId;
    private String messageBody;

    public MessageRequestDTO() {
    }

    public MessageRequestDTO(String receiverId, String messageBody) {
        this.receiverId = receiverId;
        this.messageBody = messageBody;
    }
    @Bindable
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        notifyPropertyChanged(BR.receiverId);
    }

    @Bindable
    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
        notifyPropertyChanged(BR.messageBody);
    }

    @Override
    public String toString() {
        return "MessageRequestDTO{" +
                "receiverId='" + receiverId + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
