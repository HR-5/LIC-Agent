package com.example.licagent;

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

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cname = clientClasses.get(position).getName();
        String cphnum = String.valueOf(clientClasses.get(position).getPhnum());
        String cpremamt = String.valueOf(clientClasses.get(position).getPayterm());
        holder.name.setText(cname);
        holder.phnum.setText(cphnum);
        holder.premamt.setText(cpremamt);
    }

    @Override
    public int getItemCount() {
        return clientClasses.size();
    }

    public void display(ArrayList<ClientClass> data){
        clientClasses.clear();
        clientClasses.addAll(data);
        this.notifyDataSetChanged();
    }

    public void update(int pos,View view){
        UpdateFragment fragment = UpdateFragment.newInstance(clientClasses.get(pos));
          manager.beginTransaction()
                .replace(R.id.conta,fragment)
                .commit();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,phnum;
        private TextView premamt;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameset);
            phnum = (TextView) itemView.findViewById(R.id.phnum);
            premamt = (TextView) itemView.findViewById(R.id.premamt);
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
