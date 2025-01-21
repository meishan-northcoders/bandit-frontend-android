package com.northcoders.banditandroid.ui.messsage;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityMessageBinding;
import com.northcoders.banditandroid.databinding.MessageItemCorrespondentBinding;
import com.northcoders.banditandroid.databinding.MessageItemUserBinding;
import com.northcoders.banditandroid.model.CorrespondentRequestDTO;
import com.northcoders.banditandroid.model.MessageRequestDTO;
import com.northcoders.banditandroid.model.MessageResponseDTO;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileAccurate;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MessageResponseDTO> messages;
    private Profile correspondentProfile;
    private MessageAdapter messageAdapter;
    private MessageItemUserBinding messageItemUserBinding;
    private MessageItemCorrespondentBinding messageItemCorrespondentBinding;
    private ActivityMessageBinding activityMessageBinding;
    private MessageClickHandler messageClickHandler;
    private MessageRequestDTO messageRequestDTO;
    private MessageActivityViewModel viewModel;

    private final String TAG = "MessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        //TODO this will be updated when get CorrespondentProfile extracts the correspondent from the parcel sent from favourites, esp. the Class will change from AccurateProfile...
        Gson converter = new Gson();
        String profileStr = getIntent().getStringExtra("PROFILE");
        correspondentProfile =  converter.fromJson(profileStr, Profile.class);

        //setup and populate recyclerview
        messageItemUserBinding = DataBindingUtil.setContentView(
                this,
                R.layout.message_item_user
        );

        messageItemCorrespondentBinding = DataBindingUtil.setContentView(
                this,
                R.layout.message_item_correspondent
        );

        viewModel = new ViewModelProvider(this).get(MessageActivityViewModel.class);

        getAllMessages();

        //setup binding for sendMessage
        messageRequestDTO = new MessageRequestDTO();

        activityMessageBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_message
        );

        activityMessageBinding.setMessageRequestDTO(messageRequestDTO);

        //may need to change when integrating with other activities if correspondentProfile class changes
        String correspondentId = correspondentProfile.getProfile_id();
        messageRequestDTO.setReceiverId(correspondentId);
        Log.i(TAG, "onCreate: messageRequestDTO: " + messageRequestDTO);

        messageClickHandler = new MessageClickHandler(messageRequestDTO, this, viewModel, correspondentProfile);

        activityMessageBinding.setClickHandler(messageClickHandler);

        //update correspondentusername using binding
        //may need to change when integrating with other activities if correspondentProfile class changes
        activityMessageBinding.setCorrespondentProfile(correspondentProfile);
    }

    private void getAllMessages() {
        String correspondentId = correspondentProfile.getProfile_id();
        CorrespondentRequestDTO correspondentRequestDTO = new CorrespondentRequestDTO(correspondentId);

        viewModel.getAllMessages(correspondentRequestDTO).observe(this, new Observer<List<MessageResponseDTO>>() {
            @Override
            public void onChanged(List<MessageResponseDTO> messageResponseDTOS) {
                messages = (ArrayList<MessageResponseDTO>) messageResponseDTOS;

                displayMessagesInRecyclerView();
            }
        });
    }

    private void displayMessagesInRecyclerView() {
        recyclerView = findViewById(R.id.recycler_gchat); //this is slightly different to Simon
        messageAdapter = new MessageAdapter(messages, correspondentProfile);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        messageAdapter.notifyDataSetChanged();
    }

}