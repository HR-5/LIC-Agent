package com.example.licagent;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.licagent.DisplayFragment.disFrame;
import static com.example.licagent.DisplayFragment.disRecyclerView;
import static com.example.licagent.NotificationClient.notFrame;
import static com.example.licagent.NotificationClient.notRecyclerview;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TabLayout tabLayout;
    public static ViewPager viewPagerMain;
    public static ViewPagerAdapter viewPagerAdapter;
    public static int pos = 0;
    public static int[] flag = new int[2];
    private CollectionReference notebookRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    AgentClass agentClass = new AgentClass();
    private TypeWriter typeWriter;
    public static ArrayList<ClientClass> clientData = new ArrayList<>();
    public static DisplayFragment displayFragment = new DisplayFragment();
    public static ArrayList<ClientClass> premClient = new ArrayList<>();
    private String name;
    public static final String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        viewPagerMain = findViewById(R.id.viewpager);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPagerMain, true);
        typeWriter = (TypeWriter) findViewById(R.id.type);
        typeWriter.setCharacterDelay(200);
        flag[0] = 0;
        flag[1] = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("Agent Name", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("Name", "");
        if (name.equals(""))
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
        getPermissions();
    }

    private void getAgent() {
        String userId = user.getUid();
        notebookRef = db.collection(userId);
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            DocumentSnapshot documentSnapshot = dc.getDocument();
                            agentClass = documentSnapshot.toObject(AgentClass.class);
                            name = agentClass.getName();
                            typeWriter.animateText("Welcome, " + agentClass.getName());
                            SharedPreferences sharedPreferences = getSharedPreferences("Agent Name", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Name", name).apply();
                            break;
                        }
                    }
                });
    }

    private void getPermissions() {
        if (!(ContextCompat.checkSelfPermission(MainActivity.this,
                PERMISSION[0]) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                PERMISSION[1]) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity.this,
                PERMISSION[2]) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    PERMISSION, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onBackPressed() {
        if (pos == 0 && flag[0] == 1) {
            flag[0] = 0;
            disFrame.animate()
                    .setDuration(200)
                    .translationY(viewPagerMain.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            disFrame.setVisibility(View.GONE);
                            disRecyclerView.setVisibility(View.VISIBLE);
                            disFrame.setTranslationY(-disRecyclerView.getHeight());
                            disRecyclerView.animate()
                                    .setDuration(400)
                                    .translationY(0)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });
                        }
                    });
        } else if (pos == 1 && flag[1] == 1) {
            flag[1] = 0;
            notFrame.animate()
                    .setDuration(200)
                    .translationY(viewPagerMain.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            notFrame.setVisibility(View.GONE);
                            notRecyclerview.setVisibility(View.VISIBLE);
                            notFrame.setTranslationY(-notRecyclerview.getHeight());
                            notRecyclerview.animate()
                                    .setDuration(400)
                                    .translationY(0)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });
                        }
                    });
        } else
            finishAffinity();
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
                if (position == 1) {
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
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                signoutacc();
                break;
            case R.id.export:
                try {
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(name + "'s Clients");
                    HSSFRow rowhead = sheet.createRow(0);
                    rowhead.createCell(0).setCellValue(new HSSFRichTextString("Name"));
                    rowhead.createCell(1).setCellValue(new HSSFRichTextString("Policy No"));
                    rowhead.createCell(2).setCellValue(new HSSFRichTextString("Policy Commencement Date"));
                    rowhead.createCell(3).setCellValue(new HSSFRichTextString("Plan & Policy Term"));
                    rowhead.createCell(4).setCellValue(new HSSFRichTextString("Premium Paying Term"));
                    rowhead.createCell(5).setCellValue(new HSSFRichTextString("Basic Sum Assured"));
                    rowhead.createCell(6).setCellValue(new HSSFRichTextString("Total Instalment Premium"));
                    rowhead.createCell(7).setCellValue(new HSSFRichTextString("Mode of Payment"));
                    rowhead.createCell(8).setCellValue(new HSSFRichTextString("Date of Maturity"));
                    rowhead.createCell(9).setCellValue(new HSSFRichTextString("Due Date of Last Premium"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    for (int i = 0; i < clientData.size(); i++) {
                        ClientClass client = clientData.get(i);
                        HSSFRow row = sheet.createRow(i+1);
                        row.createCell(0).setCellValue(client.getName());
                        row.createCell(1).setCellValue(client.getPolyno());
                        row.createCell(2).setCellValue(simpleDateFormat.format(client.getDatecomm()));
                        row.createCell(3).setCellValue(client.getPlan());
                        row.createCell(4).setCellValue(client.getPayterm());
                        row.createCell(5).setCellValue(client.getAssSum());
                        row.createCell(6).setCellValue(client.getTotPrem());
                        row.createCell(7).setCellValue(client.getPolyterm());
                        row.createCell(8).setCellValue(simpleDateFormat.format(client.getDatemat()));
                        row.createCell(9).setCellValue(simpleDateFormat.format(client.getLastDate()));
                    }
                    Date date = new Date();
                    String fileN = name + "'s Clients " + simpleDateFormat.format(date) + ".xls";
                    String filePath= getExternalFilesDir(null) + File.separator + fileN;
                    String[] propFilePath = filePath.split("Android",2);
                    filePath = propFilePath[0];
                    filePath+="Download"+File.separator+fileN;
                    FileOutputStream fileOut = new FileOutputStream(filePath);
                    workbook.write(fileOut);
                    Toast.makeText(getApplicationContext(),"File Created",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
