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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.licagent.AddClient.AddDetailFragment;
import com.example.licagent.Model.ClientClass;
import com.facebook.shimmer.ShimmerFrameLayout;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static com.example.licagent.MainActivity.clientData;
import static com.example.licagent.MainActivity.premClient;

public class DisplayFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference notebookRef;
    ClientAdapter adapter;
    String userId;
    double monthInsec = 30*24*3600;
    public static ArrayList<ClientClass> client = new ArrayList<>();
    public static FrameLayout disFrame;
    public static RecyclerView disRecyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;

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
        disRecyclerView = (RecyclerView) v.findViewById(R.id.attrecycler);
        disFrame = v.findViewById(R.id.detail_frame);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new ClientAdapter(getActivity(),fragmentManager);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(disRecyclerView);
        disRecyclerView.setAdapter(adapter);
        mShimmerViewContainer = v.findViewById(R.id.shimmerFrameLayout);
        disRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        disRecyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        getdata();
    }
    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void getdata(){
        client.clear();
        premClient.clear();
        premClient = new ArrayList<>();
        notebookRef.document("My clients").collection("Client Data")
                .get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    DocumentSnapshot documentSnapshot = dc.getDocument();
                    int oldIndex = dc.getOldIndex();
                    int newIndex = dc.getNewIndex();
                    ClientClass clientClass = documentSnapshot.toObject(ClientClass.class);
                    checkPrem(clientClass);
                    switch (dc.getType()){
                        case ADDED:
                            client.add(clientClass);
                            break;
                        case MODIFIED:
                            client.set(oldIndex,clientClass);
                            break;
                    }
                }
                clientData.addAll((Collection<? extends ClientClass>) client.clone());
                adapter.display(client);
            }
        });

    }
    private void checkPrem(ClientClass clientClass){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        Date policyDate = clientClass.getPremDates().get(0);
        long time = (policyDate.getTime() - today.getTime())/1000;
        if(time<monthInsec && time>=0) {
            premClient.add(clientClass);
        }
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
