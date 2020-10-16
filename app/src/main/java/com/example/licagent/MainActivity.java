package com.example.licagent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayFragment displayFragment = new DisplayFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.conta,displayFragment)
                        .commitNow();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int flag = preferences.getInt("flag",0);
        if(flag == 0)
            finishAffinity();
        else {
            DisplayFragment displayFragment = new DisplayFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conta, displayFragment)
                    .commit();
        }
    }
}
