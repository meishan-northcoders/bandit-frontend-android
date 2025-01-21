package com.northcoders.banditandroid.ui.messsage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.northcoders.banditandroid.ActivityProfile;
import com.northcoders.banditandroid.model.MessageRequestDTO;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.ui.favourites.FavouritesPageActivity;

public class MessageClickHandler {
    MessageRequestDTO messageRequestDTO;
    Context context;
    MessageActivityViewModel messageActivityViewModel;

    Profile profile;
    String TAG = "MessageClickHandler";

    public MessageClickHandler(MessageRequestDTO messageRequestDTO, Context context, MessageActivityViewModel messageActivityViewModel, Profile profile) {
        this.messageRequestDTO = messageRequestDTO;
        this.context = context;
        this.messageActivityViewModel = messageActivityViewModel;
        this.profile = profile;
    }

    public void onSendButtonClicked(Profile profile) {
        //WHERE TO ADD CORRESPONDENT ID?
        Log.i(TAG, "onSendButtonClicked: messageRequestDTO: " + messageRequestDTO);
        if(messageRequestDTO.getReceiverId() == null || messageRequestDTO.getMessageBody() == null) {
            Toast.makeText(context, "Message fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            messageActivityViewModel.saveMessage(messageRequestDTO);

            Log.i(TAG, "onSubmitBtnClicked: valid message submitted");

            //This shoudl refresh the page?
            Intent intent = new Intent(context, MessageActivity.class);

            Gson converter = new Gson();

            String profileStr = converter.toJson(profile);
            intent.putExtra("PROFILE", profileStr);

            context.startActivity(intent);
        }
    }

    public void onBackButtonClicked(View view) {
        Intent intent = new Intent(context, FavouritesPageActivity.class);
        context.startActivity(intent);
    }
}
