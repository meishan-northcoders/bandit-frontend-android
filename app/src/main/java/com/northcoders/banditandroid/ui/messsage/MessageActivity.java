package com.northcoders.banditandroid.ui.messsage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.MessageItemCorrespondentBinding;
import com.northcoders.banditandroid.databinding.MessageItemUserBinding;
import com.northcoders.banditandroid.model.CorrespondentRequestDTO;
import com.northcoders.banditandroid.model.MessageResponseDTO;
import com.northcoders.banditandroid.model.ProfileAccurate;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<MessageResponseDTO> messages;
    private MessageAdapter messageAdapter;
    private MessageItemUserBinding messageItemUserBinding;
    private MessageItemCorrespondentBinding messageItemCorrespondentBinding;
    private MessageActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageItemUserBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_message
        );

        messageItemCorrespondentBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_message
        );

        viewModel = new ViewModelProvider(this).get(MessageActivityViewModel.class);

        getAllMessages();

    }

    private void getAllMessages() {
        ProfileAccurate correspondentProfile = getCorrespondentProfile();
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
        messageAdapter = new MessageAdapter(messages);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        messageAdapter.notifyDataSetChanged();
    }

    //to be updated with real profile class when profile journey is merged, and to extract correspondent profile from parcel passed to MessageActivity when Favourites activity is merged
    private ProfileAccurate getCorrespondentProfile() {
        return new ProfileAccurate("testCorrespondentId2", "testUsername1", "testURL", ProfileType.BAND, "testDescription", 100, 200, 10, null, null);
    }
}