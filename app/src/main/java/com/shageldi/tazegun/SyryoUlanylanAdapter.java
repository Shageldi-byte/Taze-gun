package com.shageldi.tazegun;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SyryoUlanylanAdapter extends RecyclerView.Adapter<SyryoUlanylanAdapter.ViewHolder> {
    ArrayList<Ulanylan> ulanylans=new ArrayList<>();
    Context context;

    public SyryoUlanylanAdapter(ArrayList<Ulanylan> ulanylans, Context context) {
        this.ulanylans = ulanylans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.syryo_ulanylan_design,parent,false);
        return new SyryoUlanylanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Ulanylan ulanylan=ulanylans.get(position);

            holder.name.setText(ulanylan.getName());

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,UlanylanView.class);
                    intent.putExtra("id",ulanylan.getId());
                    context.startActivity(intent);
                }
            });

        Glide.with(context).load(ulanylan.getIcon()).placeholder(R.drawable.slider4).error(R.drawable.slider4).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return ulanylans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
            card=itemView.findViewById(R.id.card);
        }
    }
}
