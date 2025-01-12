package com.northcoders.banditandroid.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CancellationSignal;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.exceptions.ClearCredentialException;

import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.banditandroid.MainActivity;

public class LogoutHandler {
    private static LogoutHandler logoutHandler;

    private LogoutHandler(){}
    public static LogoutHandler getInstance(){
        if(logoutHandler == null){
            logoutHandler = new LogoutHandler();
        }
        return logoutHandler;
    }
    public void logout(Activity activity){
        FirebaseAuth.getInstance().signOut();
        CredentialManager credentialManager = CredentialManager.create(activity);
        ClearCredentialStateRequest req = new ClearCredentialStateRequest();
        credentialManager.clearCredentialStateAsync(req, new CancellationSignal(),
                activity.getMainExecutor(), new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(Void unused) {
                        Intent mainIntent = new Intent(activity, MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(mainIntent);
                        activity.finish();
                    }
                    @Override
                    public void onError(@NonNull ClearCredentialException e) {

                    }
                });
    }
}
