package com.northcoders.banditandroid.ui.createprofile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityCreateProfileBinding;
import com.northcoders.banditandroid.model.Genre;
import com.northcoders.banditandroid.model.Instrument;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.ArrayList;
import java.util.HashSet;

public class CreateProfileActivity extends AppCompatActivity {



/*
* TODO
*  image:
*  figure out https://medium.com/@mr.yuvraj99/android-take-photo-from-camera-or-pick-image-from-gallery-c2601e364790 how to take photo
*  from cam or pick image from gallery
*  genres/instruments:
*  figure out how to use https://github.com/Cutta/TagView */
    ActivityCreateProfileBinding binding;
    CreateProfileViewModel viewModel;
    CreateProfileClickHandler clickHandler;
    ProfileRepository profileRepository;
    Profile userProfile = new Profile();
    public static final String TAG = "CreateProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createProfileView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(CreateProfileViewModel.class);

        clickHandler = new CreateProfileClickHandler(userProfile, this, viewModel);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_create_profile);

        binding.setUserProfile(userProfile);

        binding.setClickHandler(clickHandler);

        profileRepository = new ProfileRepository(this.getApplication());

        MutableLiveData<ArrayList<Profile>> mutableProfiles =
                profileRepository.getMutableAllProfiles();


        Spinner spinner = findViewById(R.id.editProfileTypeSpinner);

        spinner.setAdapter(new ArrayAdapter<ProfileType>(this, android.R.layout.simple_spinner_item, ProfileType.values()));

        spinner.setOnItemSelectedListener(new ProfileTypeSpinnerListener(this));
        Log.i(TAG, mutableProfiles.toString());
    }
}