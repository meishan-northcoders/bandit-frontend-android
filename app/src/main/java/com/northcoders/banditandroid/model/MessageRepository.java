package com.northcoders.banditandroid.model;

import android.app.Application;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.banditandroid.helper.SharedPreferenceHelper;
import com.northcoders.banditandroid.service.BandMateApiService;
import com.northcoders.banditandroid.service.RetrofitInstance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {
    private MutableLiveData<List<MessageResponseDTO>> mutableLiveData = new MutableLiveData<>();
    private Application application = new Application();
    public MessageRepository(Application application) {
        this.application = application;
    }
    private final String TAG = "MessageRepository";

    public MutableLiveData<List<MessageResponseDTO>> getMutableLiveData(CorrespondentRequestDTO correspondentRequestDTO) {
        BandMateApiService bandMateApiService = RetrofitInstance.getService();

        //get token from firebase synchronously
        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        Call call = bandMateApiService.getMessagesBetweenUsers("Bearer "+token, correspondentRequestDTO);

        call.enqueue(new Callback<List<MessageResponseDTO>>() {
            @Override
            public void onResponse(Call<List<MessageResponseDTO>> call, Response<List<MessageResponseDTO>> response) {
                List<MessageResponseDTO> messages = response.body();
                Log.i(TAG, "getMutableLiveData, onResponse, messages: " + messages);
                mutableLiveData.setValue(messages);
                Log.i(TAG, "getMutableLiveData, onResponse, mutableLiveData: " + mutableLiveData.getValue());
            }

            @Override
            public void onFailure(Call<List<MessageResponseDTO>> call, Throwable t) {
                Log.i(TAG, "GET messages request failed:" + t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public void saveMessage(MessageRequestDTO messageRequestDTO) {
        BandMateApiService bandMateApiService = RetrofitInstance.getService();
        //get token from firebase synchronously
        String token = SharedPreferenceHelper.getInstance(application.getApplicationContext()).getString("token", null);

        Call<MessageResponseDTO> call = bandMateApiService.saveMessage("Bearer " + token, messageRequestDTO);

        call.enqueue(new Callback<MessageResponseDTO>() {
            @Override
            public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                Log.i(TAG, "onResponse: " + response);
                if (response.code() == 200) {
                    Toast.makeText(application.getApplicationContext(),
                            "Message saved successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application.getApplicationContext(),
                            "Message not saved",
                            Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "saveMessage received response other than 200: HTTP code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MessageResponseDTO> call, Throwable t) {
                Toast.makeText(
                        application.getApplicationContext(),
                        "Unable to save message",
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG, "saveMessage failed: " + t.getMessage());
            }
        });
    }
}
