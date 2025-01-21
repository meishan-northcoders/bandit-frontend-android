package com.northcoders.banditandroid.ui.updateprofile;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.gson.Gson;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityUpdateProfileBinding;
import com.northcoders.banditandroid.model.Genre;
import com.northcoders.banditandroid.model.Instrument;
import com.northcoders.banditandroid.model.Profile;
import com.northcoders.banditandroid.model.ProfileRepository;
import com.northcoders.banditandroid.model.ProfileType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class UpdateProfileActivity extends AppCompatActivity {

    ActivityUpdateProfileBinding binding;
    UpdateProfileViewModel viewModel;
    UpdateProfileClickHandler clickHandler;
    ProfileRepository profileRepository;
    Profile userProfile;
    public static final String TAG = "CreateProfileActivity";

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);


        viewModel = new ViewModelProvider(this).get(UpdateProfileViewModel.class);
        //get data for the profile
        ProfileRepository profileRepository = new ProfileRepository(this.getApplication());
        MutableLiveData<Profile> mutable = profileRepository.getUserProfile();
        mutable.observe(this, profile -> {
            userProfile = profile;
            //binding.setUserProfile(userProfile);
            userProfileActions();

        });
//        userProfile = mutable.getValue();

//        String userProfileString = getIntent().getStringExtra("USER_PROFILE");
//
//        System.out.println(userProfileString);
//
//        Gson converter = new Gson();
//
//        userProfile = converter.fromJson(userProfileString, Profile.class);
//
//        System.out.println("successfully passed profile into update activity" + userProfile);

    }

    private void userProfileActions() {
        ProfileRepository profileRepository;
        clickHandler = new UpdateProfileClickHandler(userProfile, this, viewModel);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_update_profile);

        CheckBox bandCheck = findViewById(R.id.bandCheckBox);
        CheckBox musicianCheck = findViewById(R.id.musicianCheckBox);
        EditText genreEditText = findViewById(R.id.editAddGenreText);
        EditText instrumentEditText = findViewById(R.id.editAddInstrumentText);
        TagView genreTagView = findViewById(R.id.genreTagView);
        TagView instrumentTagView = findViewById(R.id.instrumentTagView);

        //add genre tags:

        userProfile.getGenres().forEach(genre -> genreTagView.addTag(new Tag(genre.getGenre())));
        userProfile.getInstruments().forEach(instrument -> instrumentTagView.addTag(new Tag(instrument.getInstrument())));
        switch(userProfile.getProfile_type()){
            case BAND: bandCheck.setChecked(true);
            break;
            case MUSICIAN: musicianCheck.setChecked(true);
            break;
        }

        submitBtn = findViewById(R.id.submitProfileBtn);

        ScrollView scrollView = findViewById(R.id.createProfileScrollView);

        //submitBtn.setEnabled(false);

        binding.setUserProfile(userProfile);
        binding.setClickHandler(clickHandler);

        profileRepository = new ProfileRepository(this.getApplication());

        MutableLiveData<ArrayList<Profile>> mutableProfiles =
                profileRepository.getMutableAllProfiles();


        //CHECK BOXES:
        bandCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userProfile.setProfile_type(ProfileType.BAND);
                musicianCheck.setChecked(false);
            }
            else{
                userProfile.setProfile_type(ProfileType.MUSICIAN);
                bandCheck.setChecked(false);
                musicianCheck.setChecked(true);
            }
            updateSubmitBtn();
        });

        musicianCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                userProfile.setProfile_type(ProfileType.MUSICIAN);
                bandCheck.setChecked(false);
            }
            else{
                userProfile.setProfile_type(ProfileType.BAND);
                musicianCheck.setChecked(false);
                bandCheck.setChecked(true);

            }
            updateSubmitBtn();
        });


        //TAGS:
        genreEditText.setOnEditorActionListener((v, actionId, event) -> {

            try{
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    String text = v.getText().toString().trim();

                    List<String> currentTags = new ArrayList<>();

                    genreTagView.getTags().forEach(tag -> currentTags.add(tag.text));

                    if(!currentTags.contains(text) && !text.isEmpty()){
                        Random colour = new Random();

                        Tag tag = new Tag(v.getText().toString());

                        tag.layoutColor = -colour.nextInt(16777216);

                        genreTagView.addTag(tag);

                        userProfile.addGenre(tag.text);

                        genreEditText.setText("");
                    }
                }
            } catch (Exception e) {
                Log.i("EditText OnEditorActionListener exception: ", Objects.requireNonNull(e.getMessage()));
            }
            updateSubmitBtn();
            return false;
        });

        genreTagView.setOnTagClickListener((tag, position) ->{

            genreTagView.remove(position);

            Set<Genre> newGenres = new HashSet<>();
            userProfile.getGenres().forEach(genre -> {
                String genreName = genre.getGenre();
                if(!Objects.equals(genreName, tag.text)){
                    newGenres.add(genre);
                }
            });
            userProfile.setGenres(newGenres);
            updateSubmitBtn();
        });


        instrumentEditText.setOnEditorActionListener((v, actionId, event) -> {

            try{
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    String text = v.getText().toString().trim();

                    List<String> currentTags = new ArrayList<>();

                    instrumentTagView.getTags().forEach(tag -> currentTags.add(tag.text));

                    if(!currentTags.contains(text) && !text.isEmpty()){
                        Random colour = new Random();

                        Tag tag = new Tag(v.getText().toString());

                        tag.layoutColor = -colour.nextInt(16777216);

                        instrumentTagView.addTag(tag);

                        userProfile.addInstrument(text);

                        instrumentEditText.setText("");
                    }
                }
            } catch (Exception e) {
                Log.i("EditText OnEditorActionListener exception: ", Objects.requireNonNull(e.getMessage()));
            }
            updateSubmitBtn();
            return false;

        });

        instrumentTagView.setOnTagClickListener((tag, position) -> {
            instrumentTagView.remove(position);

            Set<Instrument> newInstruments = new HashSet<>();
            userProfile.getInstruments().forEach(instrument -> {
                String genreName = instrument.getInstrument();
                if(!Objects.equals(genreName, tag.text)){
                    newInstruments.add(instrument);
                }
            });
            userProfile.setInstruments(newInstruments);
            updateSubmitBtn();
        });

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                System.out.println("scrollview scrolled!");
                updateSubmitBtn();
            }
        });
    }

    private void updateSubmitBtn(){
        submitBtn.setEnabled(userProfile.checkAttributesNotNull());
    }

}