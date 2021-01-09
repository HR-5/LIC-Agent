package com.example.licagent.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.licagent.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileFragment fragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.profile_frame,fragment)
                .commit();
    }
}