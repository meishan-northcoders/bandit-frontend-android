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
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;
import com.northcoders.banditandroid.ui.ProfileAdapter;
import com.northcoders.banditandroid.ui.createprofile.CreateProfileActivity;
import com.northcoders.banditandroid.ui.testmaps.TestMapActivity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

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
        btnbyId = findViewById(R.id.btnGoogleSignIn);
        btnbyId.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                createProfile(currentUser);
            } else {
                authenticateWithGoogle();
            }
        });
    }
    private void connectToBackend() {
        BandMateApiService bandMateApiService = RetrofitInstance.getService();
        //get token from firebase synchronously
        String token = SharedPreferenceHelper.getInstance(getApplicationContext()).getString("token", null);

        Call<ResponseBody> greeting = bandMateApiService.getGreeting("Bearer "+token);
        greeting.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.body() != null){
                        String responseContent = response.body().string();
                        Log.d(TAG, "onSuccess: " + responseContent);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error reading response body", e);
                } catch (NullPointerException e) {
                    Log.e(TAG, "Response body is null ", e);
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
        GetGoogleIdOption signInWithGoogleOption = new GetGoogleIdOption
                .Builder()
                .setServerClientId(getString(R.string.web_client_id))
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

                    if (Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser()) {
                        //New user registration
                        Log.i(TAG, "new user registration flow");  // CREATE NEW PROFILE IN BACKEND
                        createProfile(user);
                    } else {
                        Log.i(TAG, "existing user flow, firebase id = "); // RETRIEVE PROFILE AND DISPLAY
                        //TODO add Sandhya's screen
                        createProfile(user);
                    }
                } else {
                    Log.e(TAG, "Firebase login failed", task.getException());
                }
            }
        });
    }

    private void createProfile(FirebaseUser user) {
        user.getIdToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "Token retrieved successfully");
                // Handle UI updates for the signed-in user.

                //TODO move this to on created profile activity
                connectToBackend();
                SharedPreferenceHelper.getInstance(getApplicationContext()).putString("token", task.getResult().getToken());

                //TODO callback operation to prevent null check from executing before object is returned.
                ProfileRepository profileRepository = new ProfileRepository(this.getApplication());
                MutableLiveData<Profile> mutable = profileRepository.getUserProfile();
                Profile profile = mutable.getValue();

                //This is part of maps api test.
//                Intent testMapIntent = new Intent(this, TestMapActivity.class);
//                this.startActivity(testMapIntent);

                if(profile == null){
                    System.out.println("profile null, create new profile screen starting");
                    Intent createProfile = new Intent(this, CreateProfileActivity.class);
                    createProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    this.startActivity(createProfile);
                }
                else{
                    System.out.println("profile already exists");
                }

            } else {
                Log.e(TAG, "Token retrieval failed", task.getException());
            }

        });
    }

}