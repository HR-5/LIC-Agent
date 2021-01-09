package com.example.licagent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licagent.Model.ClientClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.licagent.DisplayFragment.disFrame;
import static com.example.licagent.DisplayFragment.disRecyclerView;
import static com.example.licagent.NotificationClient.notFrame;
import static com.example.licagent.NotificationClient.notRecyclerview;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {
    ArrayList<ClientClass> clientClasses;
    Activity context;
    FragmentManager manager;

    public ClientAdapter(Activity context,FragmentManager manager) {
        this.context = context;
        this.manager = manager;
        clientClasses = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_details, parent, false);
        return new ClientAdapter.ViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClientClass client = clientClasses.get(position);
        String cname = client.getName();
        String cphnum = String.valueOf(client.getPhnum());
        String cpremamt = "Rs."+ client.getTotPrem();
        String cpolyno = String.valueOf(client.getPolyno());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String nxtDate = simpleDateFormat.format(client.getPremDates().get(0));
        holder.name.setText(cname);
        holder.phnum.setText(cphnum);
        holder.premamt.setText(cpremamt);
        holder.pnoset.setText(cpolyno);
        holder.nxtDate.setText(nxtDate);
    }

    @Override
    public int getItemCount() {
        return clientClasses.size();
    }

    public void display(ArrayList<ClientClass> data){
        clientClasses.clear();
        clientClasses = new ArrayList<>();
        clientClasses.addAll((Collection<? extends ClientClass>) data.clone());
        this.notifyDataSetChanged();
    }

    public void update(int pos,View view){
        UpdateFragment fragment = UpdateFragment.newInstance(clientClasses.get(pos));
        if(MainActivity.pos==0) {
            disRecyclerView.setVisibility(View.GONE);
            disFrame.setVisibility(View.VISIBLE);
            manager.beginTransaction()
                    .replace(R.id.detail_frame,fragment)
                    .commit();
        }
        else if(MainActivity.pos==1){
            notRecyclerview.setVisibility(View.GONE);
            notFrame.setVisibility(View.VISIBLE);
            manager.beginTransaction()
                    .replace(R.id.prem_frame,fragment)
                    .commit();
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,phnum, pnoset,premamt, nxtDate;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameset);
            phnum = (TextView) itemView.findViewById(R.id.phnoset);
            premamt = (TextView) itemView.findViewById(R.id.prmset);
            pnoset = itemView.findViewById(R.id.tv_pnoset);
            nxtDate = itemView.findViewById(R.id.nxtdate);

            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.cons);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    update(pos,view);
                }
            });
        }
    }
}
