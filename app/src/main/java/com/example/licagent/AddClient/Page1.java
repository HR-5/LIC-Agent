package com.example.licagent.AddClient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licagent.R;

public class Page1 extends Fragment {
    protected  static EditText nameset, phnumset, polynoset,planset, polytermset;
    public Page1() {
        // Required empty public constructor
    }

    public static Page1 newInstance(String param1, String param2) {
        Page1 fragment = new Page1();
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
        View v =  inflater.inflate(R.layout.page_1, container, false);
        nameset = (EditText) v.findViewById(R.id.nameset);
        phnumset = (EditText) v.findViewById(R.id.phnumset);
        polynoset = (EditText) v.findViewById(R.id.polynoset);
        planset = (EditText) v.findViewById(R.id.planset);
        polytermset = (EditText) v.findViewById(R.id.polytermset);
        return v;
    }
}
