package com.northcoders.banditandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.exceptions.ClearCredentialException;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityProfile extends AppCompatActivity {
    private ImageView ivImage;
    private TextView uName;
    private TextView uemail;
    private Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_profile);
        ivImage = findViewById(R.id.user_image);
        uName = findViewById(R.id.user_name);
        uemail = findViewById(R.id.user_email);
        btLogout = findViewById(R.id.bt_logout);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userPhotoUrl = intent.getStringExtra("userPhotoUrl");
        uName.setText(userName != null ? userName : "No Name");
        uemail.setText(userEmail != null ? userEmail : "No email");
        if (userPhotoUrl != null) {
            Uri photoUri = Uri.parse(userPhotoUrl);
            Glide.with(this).load(photoUri).into(ivImage);
        }
        // Handle Logout
        btLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            CredentialManager credentialManager = CredentialManager.create(this);
            ClearCredentialStateRequest req = new ClearCredentialStateRequest();
            credentialManager.clearCredentialStateAsync(req, new CancellationSignal(),
                    getApplicationContext().getMainExecutor(), new CredentialManagerCallback<>() {
                        @Override
                        public void onResult(Void unused) {
                            Intent mainIntent = new Intent(ActivityProfile.this, MainActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }

                        @Override
                        public void onError(@NonNull ClearCredentialException e) {

                        }
                    });

        });



    }
}