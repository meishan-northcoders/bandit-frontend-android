package com.northcoders.banditandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.window.SplashScreen;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;
import com.northcoders.banditandroid.ui.createprofile.CreateProfileActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
                } catch (NullPointerException e) {
                    Log.e(TAG, "Response body is null ", e);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = new Intent(this, CreateProfileActivity.class);

        this.startActivity(intent);

    }
}