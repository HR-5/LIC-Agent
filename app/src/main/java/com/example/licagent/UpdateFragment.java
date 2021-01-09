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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.licagent.AddClient.AddDetailFragment.*;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.licagent.DisplayFragment.disFrame;
import static com.example.licagent.DisplayFragment.disRecyclerView;
import static com.example.licagent.MainActivity.viewPagerAdapter;
import static com.example.licagent.MainActivity.viewPagerMain;
import static com.example.licagent.NotificationClient.notFrame;
import static com.example.licagent.NotificationClient.notRecyclerview;


public class UpdateFragment extends Fragment implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    EditText nameset, phnumset, polynoset, datecommset, planset, polytermset, datematset,
            premamtset, dobset, paytermset, assSumset, lastedateset, dueDateset, addressSet;
    Button addBtn, backbtn, editbtn;
    boolean edit;
    String name, dob, datecomm, datemat, dueDate, lastDate, address;
    long phnum, plan, polyno, polyterm, payterm, assSum, totPrem;
    ClientClass clientClass,clientClass2 = new ClientClass();
    long oldId;
    CollectionReference notebookRef;
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
        editor.putInt("flag", 0);
        editor.apply();
        String userId = user.getUid();
        notebookRef = db.collection(userId);
        clientClass = new ClientClass();
        if (getArguments() != null) {
            clientClass = (ClientClass) getArguments().getSerializable("client");
            oldId = clientClass.getPolyno();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.client_detail, container, false);
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
        addBtn = v.findViewById(R.id.update);
        backbtn = v.findViewById(R.id.back);
        editbtn = v.findViewById(R.id.edit);
        edit = false;
        setEdit();
        setVar();
        setText();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        editbtn.setOnClickListener(this);
    }

    private void setEdit() {
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

    private void update() throws ParseException {
        if (TextUtils.isEmpty(nameset.getText()) || TextUtils.isEmpty(phnumset.getText())
                || TextUtils.isEmpty(polynoset.getText()) || TextUtils.isEmpty(datecommset.getText())
                || TextUtils.isEmpty(planset.getText()) || TextUtils.isEmpty(polytermset.getText())
                || TextUtils.isEmpty(datematset.getText()) || TextUtils.isEmpty(premamtset.getText())
                || TextUtils.isEmpty(dobset.getText()) || TextUtils.isEmpty(paytermset.getText())
                || TextUtils.isEmpty(assSumset.getText()) || TextUtils.isEmpty(lastedateset.getText())
                || TextUtils.isEmpty(dueDateset.getText()) || TextUtils.isEmpty(addressSet.getText())) {
            Toast.makeText(getContext(), "Enter Valid Data", Toast.LENGTH_SHORT).show();
        } else {
            addData();
        }

    }

    public void addData() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        clientClass2.setName(nameset.getText().toString());
        clientClass2.setPhnum(Long.parseLong(phnumset.getText().toString()));
        clientClass2.setPlan(Long.parseLong(planset.getText().toString()));
        clientClass2.setPolyno(Long.parseLong(polynoset.getText().toString()));
        clientClass2.setPolyterm(Long.parseLong(polytermset.getText().toString()));
        dob = dobset.getText().toString();
        clientClass2.setDob(simpleDateFormat.parse(dob));
        datecomm = datecommset.getText().toString();
        clientClass2.setDatecomm(simpleDateFormat.parse(datecomm));
        datemat = datematset.getText().toString();
        clientClass2.setDatemat(simpleDateFormat.parse(datemat));
        dueDate = dueDateset.getText().toString();
        clientClass2.setDueDate(simpleDateFormat.parse(dueDate));
        lastDate = lastedateset.getText().toString();
        clientClass2.setLastDate(simpleDateFormat.parse(lastDate));
        clientClass2.setAddress(addressSet.getText().toString());
        clientClass2.setPayterm(Long.parseLong(paytermset.getText().toString()));
        clientClass2.setTotPrem(Long.parseLong(premamtset.getText().toString()));
        clientClass2.setAssSum(Long.parseLong(assSumset.getText().toString()));
        if(clientClass.getPolyterm() == clientClass2.getPolyterm() && clientClass.getPayterm() == clientClass2.getPayterm()){
            clientClass2.setPremDates((ArrayList<Date>) clientClass.getPremDates().clone());
        }
        else
            calculatePremDates();
        if (oldId != clientClass2.getPolyno())
            notebookRef.document("My clients").collection("Client Data").document(String.valueOf(oldId)).delete();
        backbtn.performClick();
        notebookRef.document("My clients").collection("Client Data").document(String.valueOf(clientClass2.getPolyno())).set(clientClass2)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Process Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Client Updated", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).setupViewPager();
                    }
                });
    }

    private void calculatePremDates() {
        ArrayList<Date> premDates = new ArrayList<>();
        for (int i = 1; i * clientClass2.getPolyterm() < clientClass2.getPayterm() * 12; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(clientClass2.getDatecomm());
            int m = (int) (i * clientClass2.getPolyterm());
            calendar.add(Calendar.MONTH, m);
            premDates.add(calendar.getTime());
        }
        clientClass2.setPremDates(premDates);
    }


    private void setVar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        name = clientClass.getName();
        dob = simpleDateFormat.format(clientClass.getDob());
        datecomm = simpleDateFormat.format(clientClass.getDatecomm());
        datemat = simpleDateFormat.format(clientClass.getDatemat());
        dueDate = simpleDateFormat.format(clientClass.getDueDate());
        lastDate = simpleDateFormat.format(clientClass.getLastDate());
        address = clientClass.getAddress();
        phnum = clientClass.getPhnum();
        plan = clientClass.getPlan();
        polyno = clientClass.getPolyno();
        polyterm = clientClass.getPolyterm();
        payterm = clientClass.getPayterm();
        assSum = clientClass.getAssSum();
        totPrem = clientClass.getTotPrem();
    }

    private void setText() {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit:
                edit = true;
                editbtn.setEnabled(false);
                addBtn.setVisibility(View.VISIBLE);
                setEdit();
                break;
            case R.id.back:
                if (MainActivity.pos == 0) {
                    disRecyclerView.setVisibility(View.VISIBLE);
                    disFrame.setVisibility(View.GONE);
                } else if (MainActivity.pos == 1) {
                    notRecyclerview.setVisibility(View.VISIBLE);
                    notFrame.setVisibility(View.GONE);
                }
                break;
            case R.id.update:
                editbtn.setEnabled(true);
                addBtn.setVisibility(View.GONE);
                edit = false;
                setEdit();
                try {
                    update();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }
    }
}
