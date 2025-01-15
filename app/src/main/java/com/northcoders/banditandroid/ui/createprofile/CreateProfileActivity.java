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

    ActivityCreateProfileBinding binding;

    CreateProfileViewModel viewModel;

    CreateProfileClickHandler clickHandler;

    ProfileRepository profileRepository;

    //Static to make it easier to access from the Spinner Listener - could refactor to make the spinner listener a subclass of this
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