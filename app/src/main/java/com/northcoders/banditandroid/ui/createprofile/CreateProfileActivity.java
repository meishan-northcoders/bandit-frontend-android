package com.northcoders.banditandroid.ui.createprofile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.google.android.gms.common.api.GoogleApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.northcoders.banditandroid.R;
import com.northcoders.banditandroid.databinding.ActivityCreateProfileBinding;
import com.northcoders.banditandroid.helper.LogoutHandler;
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

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profile);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i(TAG, "onCreate: current user: " + currentUser);

        findViewById(R.id.bt_logout).setOnClickListener(v -> {
            Log.i(TAG, "onCreate: logout clicked");
             LogoutHandler.getInstance().logout(this);
             Log.d(TAG, "onCreate: logout clicked");
         });


        viewModel = new ViewModelProvider(this).get(CreateProfileViewModel.class);

        clickHandler = new CreateProfileClickHandler(userProfile, this, viewModel);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_create_profile);

        CheckBox bandCheck = findViewById(R.id.bandCheckBox);
        CheckBox musicianCheck = findViewById(R.id.musicianCheckBox);
        EditText genreEditText = findViewById(R.id.editAddGenreText);
        EditText instrumentEditText = findViewById(R.id.editAddInstrumentText);
        TagView genreTagView = findViewById(R.id.genreTagView);
        TagView instrumentTagView = findViewById(R.id.instrumentTagView);

        submitBtn = findViewById(R.id.submitProfileBtn);

        ScrollView scrollView = findViewById(R.id.createProfileScrollView);

        submitBtn.setEnabled(false);

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