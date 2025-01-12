package com.northcoders.banditandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    GoogleIdTokenCredential googleIdTokenCredential;
    View btnbyId;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        connectToBackend();
        btnbyId = findViewById(R.id.btnGoogleSignIn);

        btnbyId.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                setProfile(currentUser);
            } else {
                authenticateWithGoogle();
            }

        });
    }

    private void connectToBackend() {
        BandMateApiService bandMateApiService = RetrofitInstance.getService();
        Call<ResponseBody> greeting = bandMateApiService.getGreeting();
        greeting.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseContent = response.body().string();
                    Log.d(TAG, "onSuccess: " + responseContent);
                } catch (IOException e) {
                    Log.e(TAG, "Error reading response body", e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void authenticateWithGoogle() {
//        GetSignInWithGoogleOption signInWithGoogleOption = new GetSignInWithGoogleOption
//                .Builder("259890167411-i7sb2bfq6uov0imbqei9gkssj25r136o.apps.googleusercontent.com")
//                //.setServerClientId()
//                //.setFilterByAuthorizedAccounts(true)// even if account is not registered with my app
//                //.setAutoSelectEnabled(false)
//                .setNonce("bandMateNonce2587436985147590008743658745896")  // can be any string, used to prevent replay attack
//                .build();

        GetGoogleIdOption signInWithGoogleOption = new GetGoogleIdOption
                .Builder()
                .setServerClientId("259890167411-i7sb2bfq6uov0imbqei9gkssj25r136o.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)// even if account is not registered with my app
                .setAutoSelectEnabled(false)
                .setNonce("bandMateNonce2587436985147590008743658745896")  // can be any string, used to prevent replay attack
                .build();
        //CredentialRequest with google sign in options
        GetCredentialRequest request = new GetCredentialRequest
                .Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build();

        CredentialManager credentialManager = CredentialManager.create(this);


        credentialManager.getCredentialAsync(this,
                request,
                new CancellationSignal(),
                getApplicationContext().getMainExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse getCredentialResponse) {
                        handleSignIn(getCredentialResponse);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e(TAG, "failed to authenticate", e);
                        //registerUser(credentialManager);
                    }
                }
        );
    }



    private void handleSignIn(GetCredentialResponse credentialResponse) {
        Credential credential = credentialResponse.getCredential();
        googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
        String idToken = googleIdTokenCredential.getIdToken();
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(firebaseCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Firebase login success");
                    FirebaseUser user = task.getResult().getUser();
                    assert user != null;
                    if(Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser()){
                        //New user registration
                        Log.i(TAG, "new user registration flow");
                        setProfile(user);
                    }else {
                        Log.i(TAG, "existing user flow");
                        setProfile(user);
                    }
                } else {
                    Log.e(TAG, "Firebase login failed", task.getException());
                }
            }
        });
    }

    private void setProfile(FirebaseUser user) {
        // Handle UI updates for the signed-in user.
//        startActivity(new Intent(MainActivity.this, ActivityProfile.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        Intent profileIntent = new Intent(MainActivity.this, ActivityProfile.class);
        profileIntent.putExtra("userName", user.getDisplayName());
        profileIntent.putExtra("userEmail", user.getEmail());
        profileIntent.putExtra("userPhotoUrl", user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null);
        profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(profileIntent);
        Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
    }

}