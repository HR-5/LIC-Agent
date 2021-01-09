package com.example.licagent;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.licagent.Model.ClientClass;

import java.util.ArrayList;

import static com.example.licagent.DisplayFragment.premClient;

public class NotificationClient extends Fragment {

    ClientAdapter adapter;
    public static FrameLayout notFrame;
    public static RecyclerView notRecyclerview;

    public NotificationClient() {
        // Required empty public constructor
    }

    public static NotificationClient newInstance() {
        NotificationClient fragment = new NotificationClient();
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
        View view = inflater.inflate(R.layout.premium_layout, container, false);
        notRecyclerview = (RecyclerView) view.findViewById(R.id.prem_recy);
        notFrame = view.findViewById(R.id.prem_frame);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new ClientAdapter(getActivity(),fragmentManager);
        notRecyclerview.setAdapter(adapter);
        notRecyclerview.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        notRecyclerview.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.display((ArrayList<ClientClass>) premClient.clone());
    }

    public void setData(){
        adapter.display((ArrayList<ClientClass>) premClient.clone());
    }
}