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
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileAccurate;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private MessageItemUserBinding messageItemUserBinding;
    private MessageItemCorrespondentBinding messageItemCorrespondentBinding;
    private List<MessageResponseDTO> messages;
    private ProfileAccurate correspondentProfile;

    private static final int VIEW_TYPE_USER_MESSAGE = 1;
    private static final int VIEW_TYPE_CORRESPONDENT_MESSAGE = 2;

    private final String TAG = "MessageAdapter";

    public MessageAdapter(List<MessageResponseDTO> messages, ProfileAccurate correspondentProfile) {
        this.messages = messages;
        this.correspondentProfile = correspondentProfile;
    }

        @Override
    public int getItemViewType(int position) {
        MessageResponseDTO message = (MessageResponseDTO) messages.get(position);

        //if getUserProfile doesn't work in a context which isn't MessageActivity then the MessageAdapter can be updated to take a String (activeUserId) or a Profile (activeUserProfile) as a parameter instead
        if (message.getSenderId().equals(correspondentProfile.getProfile_id())) {
            // If the correspondent is the sender of the message
            return VIEW_TYPE_CORRESPONDENT_MESSAGE;
        } else {
            // If the active user (or another user; this case is yet to be handled...) sent the message
            return VIEW_TYPE_USER_MESSAGE;
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

        } else {
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

    //to be updated with real profile class when profile journey is merged, and to extract correspondent profile from parcel passed to MessageActivity when Favourites Activity is merged
    private ProfileAccurate getUserProfile() {
        return new ProfileAccurate("c401bxWARycXXcuxdxkN2nf6H2F2", "testUsername1", "testURL", ProfileType.BAND, "testDescription", 100, 200, 10, null, null);
    }
}