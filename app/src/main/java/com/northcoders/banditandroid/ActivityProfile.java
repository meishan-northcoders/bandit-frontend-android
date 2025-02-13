package com.northcoders.banditandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.northcoders.banditandroid.helper.LogoutHandler;
import com.northcoders.banditandroid.ui.messsage.MessageActivity;

public class ActivityProfile extends AppCompatActivity {
    private ImageView ivImage;
    private TextView uName;
    private TextView uemail;
    private Button btLogout;
    private Button btMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ivImage = findViewById(R.id.user_image);
        uName = findViewById(R.id.user_name);
        uemail = findViewById(R.id.user_email);

//        btLogout = findViewById(R.id.bt_logout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // No user is signed in
            Toast.makeText(getApplicationContext(), "No user signed in", Toast.LENGTH_LONG).show();
            LogoutHandler.getInstance().logout(this);

        }
        uName.setText(user.getDisplayName() != null ? user.getDisplayName()  : "No Name");
        String userPhotoUrl = user.getPhotoUrl().toString();
        if (userPhotoUrl != null) {
            Uri photoUri = Uri.parse(userPhotoUrl);
            Glide.with(this).load(photoUri).into(ivImage);
        }
        uemail.setText(user.getEmail() != null ? user.getEmail() : "No email");
        // Handle Logout


//        btLogout.setOnClickListener(v -> {
//            LogoutHandler.getInstance().logout(this);
//        });

    }

}