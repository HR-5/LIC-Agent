package com.example.licagent.AddClient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licagent.R;


public class Page2 extends Fragment {

    protected  static EditText datecommset, datematset,dobset, lastedateset, dueDateset;
    public Page2() {
        // Required empty public constructor
    }

    public static Page2 newInstance(String param1, String param2) {
        Page2 fragment = new Page2();
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
        View v =  inflater.inflate(R.layout.page_2, container, false);
        datecommset = (EditText) v.findViewById(R.id.datecomset);
        datematset = (EditText) v.findViewById(R.id.datematset);
        dobset = (EditText) v.findViewById(R.id.dobset);
        lastedateset = (EditText) v.findViewById(R.id.lastdateset);
        dueDateset = (EditText) v.findViewById(R.id.premdateset);
        return v;
    }
}
