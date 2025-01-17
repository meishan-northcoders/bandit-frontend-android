package com.northcoders.banditandroid.ui.messsage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.MessageItemCorrespondentBinding;
import com.northcoders.banditandroid.databinding.MessageItemUserBinding;
import com.northcoders.banditandroid.model.MessageResponseDTO;
import com.northcoders.banditandroid.model.ProfileAccurate;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private MessageItemUserBinding messageItemUserBinding;
    private MessageItemCorrespondentBinding messageItemCorrespondentBinding;
    private List<MessageResponseDTO> messages;

    private static final int VIEW_TYPE_USER_MESSAGE = 1;
    private static final int VIEW_TYPE_CORRESPONDENT_MESSAGE = 2;

    private final String TAG = "MessageAdapter";

    public MessageAdapter(List<MessageResponseDTO> messages) {
        this.messages = messages;
    }

        @Override
    public int getItemViewType(int position) {
        MessageResponseDTO message = (MessageResponseDTO) messages.get(position);

        if (message.getSenderId().equals(getUserProfile().getProfile_id())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_USER_MESSAGE;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_CORRESPONDENT_MESSAGE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_USER_MESSAGE) {
            MessageItemUserBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.message_item_user,
                    parent,
                    false
            );
            return new UserMessageViewHolder(binding);

        } else if (viewType == VIEW_TYPE_CORRESPONDENT_MESSAGE) {
            MessageItemCorrespondentBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.message_item_correspondent,
                    parent,
                    false
            );
            return new CorrespondentMessageViewHolder(binding);
        } else {
            //Use the correspondent message ViewHolder as default
            MessageItemCorrespondentBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.message_item_correspondent,
                    parent,
                    false
            );
            return new CorrespondentMessageViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageResponseDTO message = messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_USER_MESSAGE:
                UserMessageViewHolder userMessageViewHolder = (MessageAdapter.UserMessageViewHolder) holder;
                userMessageViewHolder.messageItemUserBinding.setMessageResponseDTO(message);
                break;
            case VIEW_TYPE_CORRESPONDENT_MESSAGE:
                CorrespondentMessageViewHolder correspondentMessageViewHolder = (MessageAdapter.CorrespondentMessageViewHolder) holder;
                correspondentMessageViewHolder.messageItemCorrespondentBinding.setMessageResponseDTO(message);
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class UserMessageViewHolder extends RecyclerView.ViewHolder {

        private MessageItemUserBinding messageItemUserBinding;

        public UserMessageViewHolder(MessageItemUserBinding messageItemUserBinding) {
            super(messageItemUserBinding.getRoot());
            this.messageItemUserBinding = messageItemUserBinding;
        }
    }

    public static class CorrespondentMessageViewHolder extends RecyclerView.ViewHolder {

        private MessageItemCorrespondentBinding messageItemCorrespondentBinding;

        public CorrespondentMessageViewHolder(MessageItemCorrespondentBinding messageItemCorrespondentBinding) {
            super(messageItemCorrespondentBinding.getRoot());
            this.messageItemCorrespondentBinding = messageItemCorrespondentBinding;
        }
    }

    private ProfileAccurate getUserProfile() {
        return new ProfileAccurate("activeUserId1", "testUsername1", "testURL", ProfileType.BAND, "testDescription", 100, 200, 10, null, null);
    }
    private ProfileAccurate getCorrespondentProfile() {
        return new ProfileAccurate("testCorrespondentId2", "testUsername1", "testURL", ProfileType.BAND, "testDescription", 100, 200, 10, null, null);
    }
}