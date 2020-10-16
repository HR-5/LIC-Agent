package com.example.licagent.AddClient;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licagent.DisplayFragment;
import com.example.licagent.Model.ClientClass;
import com.example.licagent.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.licagent.AddClient.Page1.nameset;
import static com.example.licagent.AddClient.Page1.phnumset;
import static com.example.licagent.AddClient.Page1.planset;
import static com.example.licagent.AddClient.Page1.polynoset;
import static com.example.licagent.AddClient.Page1.polytermset;
import static com.example.licagent.AddClient.Page2.datecommset;
import static com.example.licagent.AddClient.Page2.datematset;
import static com.example.licagent.AddClient.Page2.dobset;
import static com.example.licagent.AddClient.Page2.dueDateset;
import static com.example.licagent.AddClient.Page2.lastedateset;
import static com.example.licagent.AddClient.Page3.addressSet;
import static com.example.licagent.AddClient.Page3.assSumset;
import static com.example.licagent.AddClient.Page3.paytermset;
import static com.example.licagent.AddClient.Page3.premamtset;

public class AddDetailFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId;
    ViewPager viewPager;
    Button addBtn, back;
    int pos = 0;
    ClientClass clientClass = new ClientClass();

    public AddDetailFragment() {
        // Required empty public constructor
    }


    public static AddDetailFragment newInstance() {
        AddDetailFragment fragment = new AddDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("flag", 1);
        editor.apply();
        userId = user.getUid();
        notebookRef = db.collection(userId);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_client, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.client_viewpager);
        addBtn = (Button) v.findViewById(R.id.next);
        back = (Button) v.findViewById(R.id.back);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        back.setText("Back");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFragment fragment = new DisplayFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.conta, fragment)
                        .commit();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nameset.getText()) || TextUtils.isEmpty(phnumset.getText())
                        || TextUtils.isEmpty(polynoset.getText()) || TextUtils.isEmpty(datecommset.getText())
                        || TextUtils.isEmpty(planset.getText()) || TextUtils.isEmpty(polytermset.getText())
                        || TextUtils.isEmpty(datematset.getText()) || TextUtils.isEmpty(premamtset.getText())
                        || TextUtils.isEmpty(dobset.getText()) || TextUtils.isEmpty(paytermset.getText())
                        || TextUtils.isEmpty(assSumset.getText()) || TextUtils.isEmpty(lastedateset.getText())
                        || TextUtils.isEmpty(dueDateset.getText()) || TextUtils.isEmpty(addressSet.getText())) {
                    Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    addData();
                }
            }
        });
    }

    public void addData() {
        String name, dob, datecomm, datemat, dueDate, lastDate, address;
        long phnum, plan, polyno, polyterm, payterm, assSum, totPrem;

        dob = dobset.getText().toString();
        datecomm = datecommset.getText().toString();
        datemat = datematset.getText().toString();
        dueDate = dueDateset.getText().toString();
        lastDate = lastedateset.getText().toString();
        address = addressSet.getText().toString();
        phnum = Long.parseLong(phnumset.getText().toString());
        plan = Long.parseLong(planset.getText().toString());
        polyno = Long.parseLong(polynoset.getText().toString());
        polyterm = Long.parseLong(polytermset.getText().toString());
        payterm = Long.parseLong(paytermset.getText().toString());
        assSum = Long.parseLong(assSumset.getText().toString());
        totPrem = Long.parseLong(premamtset.getText().toString());
        ClientClass clientClass = new ClientClass("name", dob, datecomm, datemat, dueDate, lastDate, address,
                phnum, plan, polyno, polyterm, payterm, assSum, totPrem);
        notebookRef.document("My clients").collection("Client Data").document(String.valueOf(polyno)).set(clientClass)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                        DisplayFragment fragment = new DisplayFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.conta, fragment)
                                .commit();
                        return;
                    }
                });
        Toast.makeText(getContext(), "Client Added", Toast.LENGTH_SHORT).show();
        DisplayFragment fragment = new DisplayFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.conta, fragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (pos == 0) {
                    if (TextUtils.isEmpty(nameset.getText()) || TextUtils.isEmpty(phnumset.getText())
                            || TextUtils.isEmpty(polynoset.getText()) || TextUtils.isEmpty(planset.getText())
                            || TextUtils.isEmpty(polytermset.getText())) {
                        Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        clientClass.setName(nameset.getText().toString());
                        clientClass.setPhnum(Long.parseLong(phnumset.getText().toString()));
                        clientClass.setPlan(Long.parseLong(planset.getText().toString()));
                        clientClass.setPolyno(Long.parseLong(polynoset.getText().toString()));
                        clientClass.setPolyterm(Long.parseLong(polytermset.getText().toString()));
                        pos++;
                    }
                    viewPager.setCurrentItem(pos, true);
                } else if (pos == 1) {
                    if (TextUtils.isEmpty(datecommset.getText()) || TextUtils.isEmpty(datematset.getText())
                            || TextUtils.isEmpty(dobset.getText()) || TextUtils.isEmpty(lastedateset.getText())
                            || TextUtils.isEmpty(dueDateset.getText())){
                        Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
//                        clientClass.setDatecomm();
//                        clientClass.setDatemat();
//                        clientClass.setDob();
//                        clientClass.setDueDate();
//                        clientClass.setLastDate();
                        pos++;
                    }
                    viewPager.setCurrentItem(pos, true);
                } else if (pos == 2) {

                }
        }

    }
}
