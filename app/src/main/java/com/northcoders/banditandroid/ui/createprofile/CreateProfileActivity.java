package com.northcoders.banditandroid.ui.createprofile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityCreateProfileBinding;
import com.northcoders.banditandroid.model.Profile;

public class CreateProfileActivity extends AppCompatActivity {

    ActivityCreateProfileBinding binding;

    CreateProfileViewModel viewModel;

    CreateProfileClickHandler clickHandler;

    Profile userProfile = new Profile();

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

        binding = DataBindingUtil.setContentView(this,
                R.id.createProfileView);

        viewModel = new CreateProfileViewModel(this.getApplication());

        binding.setUserProfile(userProfile);

        binding.setClickHandler(clickHandler);




    }
}