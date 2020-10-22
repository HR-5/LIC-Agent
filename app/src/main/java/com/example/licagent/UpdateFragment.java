package com.example.licagent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licagent.Model.ClientClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;


public class UpdateFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("My Client");
    EditText nameset, phnumset, polynoset, datecommset, planset, polytermset, datematset,
            premamtset, dobset, paytermset, assSumset, lastedateset, dueDateset, addressSet;
    Button addBtn,cancelBtn;
    boolean edit;
    String name, dob, datecomm, datemat, dueDate, lastDate, address;
    long phnum, plan, polyno, polyterm, payterm, assSum, totPrem;
    ClientClass clientClass;
    long oldId;
    TextView title;

    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(ClientClass client) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable("client", (Serializable) client);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("flag",0);
        editor.apply();
        clientClass = new ClientClass();
        if (getArguments() != null) {
            clientClass = (ClientClass) getArguments().getSerializable("client");
            oldId = clientClass.getPolyno();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.client_details, container, false);
        nameset = (EditText) v.findViewById(R.id.nameset);
        phnumset = (EditText) v.findViewById(R.id.phnumset);
        polynoset = (EditText) v.findViewById(R.id.polynoset);
        datecommset = (EditText) v.findViewById(R.id.datecomset);
        planset = (EditText) v.findViewById(R.id.planset);
        polytermset = (EditText) v.findViewById(R.id.polytermset);
        datematset = (EditText) v.findViewById(R.id.datematset);
        premamtset = (EditText) v.findViewById(R.id.totpremset);
        dobset = (EditText) v.findViewById(R.id.dobset);
        paytermset = (EditText) v.findViewById(R.id.paytermset);
        assSumset = (EditText) v.findViewById(R.id.asssumset);
        lastedateset = (EditText) v.findViewById(R.id.lastdateset);
        dueDateset = (EditText) v.findViewById(R.id.premdateset);
        addressSet = (EditText) v.findViewById(R.id.addset);
        title = (TextView) v.findViewById(R.id.title);
        title.setText("Client Details");
        addBtn = (Button) v.findViewById(R.id.add);
        cancelBtn = (Button) v.findViewById(R.id.cancel);
        cancelBtn.setText("Back");
        addBtn.setText("Edit");
        edit = false;
        setEdit();
        setVar();
        setText();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayFragment fragment = new DisplayFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.conta,fragment)
                        .commit();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }
    private void setEdit(){
        nameset.setClickable(edit);
        phnumset.setClickable(edit);
        polynoset.setClickable(edit);
        datecommset.setClickable(edit);
        planset.setClickable(edit);
        polytermset.setClickable(edit);
        datematset.setClickable(edit);
        premamtset.setClickable(edit);
        dobset.setClickable(edit);
        paytermset.setClickable(edit);
        assSumset.setClickable(edit);
        lastedateset.setClickable(edit);
        dueDateset.setClickable(edit);
        addressSet.setClickable(edit);
        nameset.setFocusableInTouchMode(edit);
        phnumset.setFocusableInTouchMode(edit);
        polynoset.setFocusableInTouchMode(edit);
        datecommset.setFocusableInTouchMode(edit);
        planset.setFocusableInTouchMode(edit);
        polytermset.setFocusableInTouchMode(edit);
        datematset.setFocusableInTouchMode(edit);
        premamtset.setFocusableInTouchMode(edit);
        dobset.setFocusableInTouchMode(edit);
        paytermset.setFocusableInTouchMode(edit);
        assSumset.setFocusableInTouchMode(edit);
        lastedateset.setFocusableInTouchMode(edit);
        dueDateset.setFocusableInTouchMode(edit);
        addressSet.setFocusableInTouchMode(edit);
    }
    private void update(){
        edit = true;
        title.setText("Edit Details");
        cancelBtn.setVisibility(View.VISIBLE);
        addBtn.setText("Update");
        setEdit();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nameset.getText())||TextUtils.isEmpty(phnumset.getText())
                        ||TextUtils.isEmpty(polynoset.getText())||TextUtils.isEmpty(datecommset.getText())
                        ||TextUtils.isEmpty(planset.getText())||TextUtils.isEmpty(polytermset.getText())
                        ||TextUtils.isEmpty(datematset.getText())||TextUtils.isEmpty(premamtset.getText())
                        ||TextUtils.isEmpty(dobset.getText())||TextUtils.isEmpty(paytermset.getText())
                        ||TextUtils.isEmpty(assSumset.getText())||TextUtils.isEmpty(lastedateset.getText())
                        ||TextUtils.isEmpty(dueDateset.getText())||TextUtils.isEmpty(addressSet.getText())){
                    Toast.makeText(getContext(),"Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    addData();
                }
            }
        });
    }

    public void addData() {
        name = nameset.getText().toString();
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
        if(oldId != polyno)
            notebookRef.document("My clients").collection("Client Data").document(String.valueOf(oldId)).delete();
        notebookRef.document("My clients").collection("Client Data").document(String.valueOf(polyno)).set(clientClass)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Process Failed",Toast.LENGTH_SHORT).show();
                        DisplayFragment fragment = new DisplayFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.conta,fragment)
                                .commit();
                        return;
                    }
                })
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Client Updated",Toast.LENGTH_SHORT).show();
                DisplayFragment fragment = new DisplayFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.conta,fragment)
                        .commit();
            }
        });
    }

    private void setVar(){
        name = clientClass.getName();
//        dob = clientClass.getDob();
//        datecomm = clientClass.getDatecomm();
//        datemat = clientClass.getDatemat();
//        dueDate = clientClass.getDueDate();
//        lastDate = clientClass.getLastDate();
        address = clientClass.getAddress();
        phnum = clientClass.getPhnum();
        plan = clientClass.getPlan();
        polyno = clientClass.getPolyno();
        polyterm = clientClass.getPolyterm();
        payterm = clientClass.getPayterm();
        assSum = clientClass.getAssSum();
        totPrem = clientClass.getTotPrem();
    }

    private void setText(){
        nameset.setText(name);
        phnumset.setText(String.valueOf(phnum));
        polynoset.setText(String.valueOf(polyno));
        datecommset.setText(datecomm);
        planset.setText(String.valueOf(plan));
        polytermset.setText(String.valueOf(polyterm));
        datematset.setText(datemat);
        premamtset.setText(String.valueOf(totPrem));
        dobset.setText(dob);
        paytermset.setText(String.valueOf(payterm));
        assSumset.setText(String.valueOf(assSum));
        lastedateset.setText(lastDate);
        dueDateset.setText(dueDate);
        addressSet.setText(address);
    }
}
