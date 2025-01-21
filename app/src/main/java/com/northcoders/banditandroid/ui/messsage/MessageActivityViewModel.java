package com.northcoders.banditandroid.ui.messsage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.banditandroid.model.CorrespondentRequestDTO;
import com.northcoders.banditandroid.model.MessageRepository;
import com.northcoders.banditandroid.model.MessageRequestDTO;
import com.northcoders.banditandroid.model.MessageResponseDTO;

import java.util.List;

public class MessageActivityViewModel extends AndroidViewModel {
    private MessageRepository messageRepository;


    public MessageActivityViewModel(@NonNull Application application) {
        super(application);
        this.messageRepository = new MessageRepository(application);
    }

    public LiveData<List<MessageResponseDTO>> getAllMessages(CorrespondentRequestDTO correspondentRequestDTO) {
        return messageRepository.getMutableLiveData(correspondentRequestDTO);
    }

    public void saveMessage(MessageRequestDTO messageRequestDTO) {
        messageRepository.saveMessage(messageRequestDTO);
    }
}
