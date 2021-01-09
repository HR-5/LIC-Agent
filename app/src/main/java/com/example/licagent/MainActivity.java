package com.example.licagent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.licagent.AddClient.AddDetailFragment;
import com.example.licagent.Login.LoginActivity;
import com.example.licagent.Model.AgentClass;
import com.example.licagent.Model.ClientClass;
import com.example.licagent.Profile.ProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TabLayout tabLayout;
    public static ViewPager viewPagerMain;
    public static ViewPagerAdapter viewPagerAdapter;
    public static int pos = 0;
    private CollectionReference notebookRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    AgentClass agentClass = new AgentClass();
    private TypeWriter typeWriter;
    public static DisplayFragment displayFragment = new DisplayFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        viewPagerMain = findViewById(R.id.viewpager);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPagerMain,true);
        typeWriter = (TypeWriter) findViewById(R.id.type);
        typeWriter.setCharacterDelay(200);
        SharedPreferences sharedPreferences = getSharedPreferences("Agent Name", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name","");
        if(name.equals(""))
            getAgent();
        else {
            typeWriter.animateText("Welcome, " + name);
        }
        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        ImageView menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.menu_items);
                popup.show();
            }
        });
    }

    private void getAgent(){
        String userId = user.getUid();
        notebookRef = db.collection(userId);
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            DocumentSnapshot documentSnapshot = dc.getDocument();
                            agentClass = documentSnapshot.toObject(AgentClass.class);
                            String name = agentClass.getName();
                            typeWriter.animateText("Welcome, " + agentClass.getName());
                            SharedPreferences sharedPreferences = getSharedPreferences("Agent Name", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Name",name).apply();
                            break;
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int flag = preferences.getInt("flag", 0);
        if (flag == 0)
            finishAffinity();
        else {
            DisplayFragment displayFragment = new DisplayFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conta, displayFragment)
                    .commit();
        }
    }
    public void setupViewPager() {
        final NotificationClient notificationClient = new NotificationClient();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(displayFragment, "Clients");
        viewPagerAdapter.addFragment(notificationClient, "Premium");
        viewPagerAdapter.addFragment(new AddDetailFragment(), "Add Client");
        viewPagerMain.setAdapter(viewPagerAdapter);
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    notificationClient.setData();
                }
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                signoutacc();
                break;
        }
        return false;
    }

    private void signoutacc() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to SignOut?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Signed Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
