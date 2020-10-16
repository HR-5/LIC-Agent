package com.example.licagent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.licagent.AddClient.AddDetailFragment;
import com.example.licagent.Model.ClientClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DisplayFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference notebookRef;
    ClientAdapter adapter;
    String userId;
    ArrayList<ClientClass> client = new ArrayList<>();

    public DisplayFragment() {
        // Required empty public constructor
    }


    public static DisplayFragment newInstance(ArrayList<ClientClass> clientClasses) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable("clients",clientClasses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("flag",2);
        editor.apply();
        userId = user.getUid();
        notebookRef = db.collection(userId);
        client = new ArrayList<>();
        if(getArguments()!=null){
            client = (ArrayList<ClientClass>) getArguments().getSerializable("clients");
        }

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.attrecycler);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new ClientAdapter(getActivity(),fragmentManager);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDetailFragment fragment = new AddDetailFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.conta,fragment)
                        .addToBackStack("add")
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getdata();
    }
    private void getdata(){
        Query query = db.collection(userId).document("My Clients").collection("Client Data");
        client.clear();
        notebookRef.document("My clients").collection("Client Data")
                .get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    int oldIndex = dc.getOldIndex();
                    int newIndex = dc.getNewIndex();
                    ClientClass clientClass = documentSnapshot.toObject(ClientClass.class);
                    switch (dc.getType()){
                        case ADDED:
                            client.add(clientClass);
                            break;
                        case MODIFIED:
                            client.set(oldIndex,clientClass);
                            break;
                        case REMOVED:
                            client.remove(oldIndex);
                            break;
                    }
                }
                adapter.display(client);
            }
        });

    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            final String docPolyNo = String.valueOf(client.get(pos).getPolyno());
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do you want delete the Client?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            notebookRef.document("My clients").collection("Client Data").document(docPolyNo).delete();
                            Toast.makeText(getContext(),"Client Deleted",Toast.LENGTH_SHORT).show();
                            getdata();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            adapter.display(client);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    };
}
