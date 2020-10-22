package com.example.licagent.AddClient;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licagent.R;


public class Page2 extends Fragment {

    ConstraintLayout dobframe, docframe, lpdframe, domframe, dueframe;
    protected static int dateVal = 0;
    protected static TextView dobset, domset, lpdset, docset, dueset;

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
        View v = inflater.inflate(R.layout.page_2, container, false);
        dobframe = v.findViewById(R.id.dobframe);
        docframe = v.findViewById(R.id.docframe);
        lpdframe = v.findViewById(R.id.lpdframe);
        domframe = v.findViewById(R.id.domframe);
        dueframe = v.findViewById(R.id.dueframe);
        dobset = v.findViewById(R.id.dobset);
        domset = v.findViewById(R.id.domset);
        lpdset = v.findViewById(R.id.lastpremset);
        docset = v.findViewById(R.id.docset);
        dueset = v.findViewById(R.id.duedateset);
        ;
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dueframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateVal = 4;
                openDatePickerDialog();
            }
        });
        domframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateVal = 2;
                openDatePickerDialog();
            }
        });
        dobframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateVal = 0;
                openDatePickerDialog();
            }
        });
        docframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateVal = 1;
                openDatePickerDialog();
            }
        });
        lpdframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateVal = 3;
                openDatePickerDialog();
            }
        });
    }

    private void openDatePickerDialog() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getChildFragmentManager(), "date picker");
    }


}
