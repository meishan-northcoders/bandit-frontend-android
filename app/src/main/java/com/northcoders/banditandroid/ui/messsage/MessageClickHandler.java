package com.northcoders.banditandroid.ui.messsage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.northcoders.banditandroid.ActivityProfile;
import com.northcoders.banditandroid.model.MessageRequestDTO;

public class MessageClickHandler {
    MessageRequestDTO messageRequestDTO;
    Context context;
    MessageActivityViewModel messageActivityViewModel;
    String TAG = "MessageClickHandler";

    public MessageClickHandler(MessageRequestDTO messageRequestDTO, Context context, MessageActivityViewModel messageActivityViewModel) {
        this.messageRequestDTO = messageRequestDTO;
        this.context = context;
        this.messageActivityViewModel = messageActivityViewModel;
    }

    public void onSendButtonClicked(View view) {
        //WHERE TO ADD CORRESPONDENT ID?
        Log.i(TAG, "onSendButtonClicked: messageRequestDTO: " + messageRequestDTO);
        if(messageRequestDTO.getReceiverId() == null || messageRequestDTO.getMessageBody() == null) {
            Toast.makeText(context, "Message fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            messageActivityViewModel.saveMessage(messageRequestDTO);

            Log.i(TAG, "onSubmitBtnClicked: valid message submitted");

            //This shoudl refresh the page?
            Intent intent = new Intent(context, MessageActivity.class);
            context.startActivity(intent);
        }
    }

    public void onBackButtonClicked(View view) {
        Intent intent = new Intent(context, ActivityProfile.class);
        context.startActivity(intent);
    }
}
