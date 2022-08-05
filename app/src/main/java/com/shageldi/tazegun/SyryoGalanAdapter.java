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

public class SyryoGalanAdapter extends RecyclerView.Adapter<SyryoGalanAdapter.ViewHolder> {
    ArrayList<GalanList> arrayList=new ArrayList<>();
    Context context;

    public SyryoGalanAdapter(ArrayList<GalanList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.syryo_galan_design,parent,false);
        return new SyryoGalanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GalanList galanList=arrayList.get(position);
        holder.galan_mocberi.setText(galanList.getGalan_mocberi());
        holder.name.setText(galanList.getName());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Galan.class);
                intent.putExtra("id",galanList.getId());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(galanList.getIcon()).placeholder(R.drawable.slider4).error(R.drawable.slider4).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        CardView card;
        TextView galan_mocberi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            name=itemView.findViewById(R.id.name);
            card=itemView.findViewById(R.id.card);
            galan_mocberi=itemView.findViewById(R.id.galan);
        }
    }
}
