package com.example.licagent.AddClient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licagent.R;

public class Page3 extends Fragment {

    protected  static EditText premamtset,paytermset, assSumset, addressSet;
    public Page3() {
        // Required empty public constructor
    }

    public static Page3 newInstance() {
        Page3 fragment = new Page3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.page_3, container, false);
        premamtset = (EditText) v.findViewById(R.id.totpremset);
        paytermset = (EditText) v.findViewById(R.id.paytermset);
        assSumset = (EditText) v.findViewById(R.id.asssumset);
        addressSet = (EditText) v.findViewById(R.id.addset);

        return v;
    }
}
