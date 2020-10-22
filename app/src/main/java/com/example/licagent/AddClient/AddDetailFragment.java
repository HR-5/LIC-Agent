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
    protected static ClientClass clientClass = new ClientClass();

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
                return true;
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
      addBtn.setOnClickListener(this);
    }

    public void addData() {
        notebookRef.document("My clients").collection("Client Data").document(String.valueOf(clientClass.getPolyno()))
                .set(clientClass)
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
                    if(clientClass.getDatecomm()!=null||clientClass.getDatemat()!=null||clientClass.getDob()!=null||clientClass.getDueDate()!=null
                            ||clientClass.getLastDate()!=null){
                        pos++;
                    }
                    else {
                        Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                    }
                    viewPager.setCurrentItem(pos, true);
                } else if (pos == 2) {
                    if(TextUtils.isEmpty(premamtset.getText())||TextUtils.isEmpty(paytermset.getText())
                            ||TextUtils.isEmpty(assSumset.getText())||TextUtils.isEmpty(addressSet.getText())){
                        Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        clientClass.setAddress(addressSet.getText().toString());
                        clientClass.setPayterm(Long.parseLong(paytermset.getText().toString()));
                        clientClass.setTotPrem(Long.parseLong(premamtset.getText().toString()));
                        clientClass.setAssSum(Long.parseLong(assSumset.getText().toString()));
                        addData();
                    }

                }
        }

    }
}
