package com.example.helloworld.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Business;
import com.example.helloworld.R;
import com.example.helloworld.ResultView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder>{

    public ArrayList<ResultView> resultList;
    public String b_id;
    private Context context;

    public AdapterRecycler(ArrayList<ResultView> resultList,Context context) {
        this.resultList = resultList;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Business.class);
                intent.putExtra("id",b_id);
                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String sno = resultList.get(position).getSno();
        holder.sno.setText(sno);
        String b_name = resultList.get(position).getBusiness_name();
        holder.b_name.setText(b_name);
        String b_image = resultList.get(position).getImage();
        Picasso.get().load(b_image).into(holder.bimage);
        String b_dist = resultList.get(position).getDistance();
        holder.b_dist.setText(b_dist);
        String b_rating = resultList.get(position).getRatings();
        holder.b_rating.setText(b_rating);
        b_id = resultList.get(position).getBusiness_id();
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sno;
        public TextView b_name;
        public ImageView bimage;
        public TextView b_rating;
        public TextView b_dist;
        public MyViewHolder(final View view){
            super(view);
            sno = view.findViewById(R.id.srNo);
            b_name=view.findViewById(R.id.businessName);
            bimage=view.findViewById(R.id.business_image);
            b_rating=view.findViewById(R.id.rating);
            b_dist=view.findViewById(R.id.resultDistance);
        }
    }
}
