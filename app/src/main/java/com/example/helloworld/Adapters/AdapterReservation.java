package com.example.helloworld.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Models.Reservation;
import com.example.helloworld.R;

import java.util.ArrayList;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.MyViewHolder>{

    ArrayList<Reservation> reservations;

    public AdapterReservation(ArrayList<Reservation> res){
        this.reservations=res;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView bname, rdate, rtime, remail,sr_no;

        public MyViewHolder(final View view){
            super(view);
            sr_no = view.findViewById(R.id.rnumber);
            bname = view.findViewById(R.id.reserve_bname);
            rdate = view.findViewById(R.id.rdate);
            rtime = view.findViewById(R.id.rtime);
            remail = view.findViewById(R.id.remail);
        }
    }

    @NonNull
    @Override
    public AdapterReservation.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reservationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_contents, parent, false);
        return new MyViewHolder(reservationView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReservation.MyViewHolder holder, int position) {
        int number = reservations.get(position).getSrNo();
        holder.sr_no.setText(Integer.toString(number));
        String r_bname = reservations.get(position).getBName();
        holder.bname.setText(r_bname);
        String date = reservations.get(position).getDate();
        holder.rdate.setText(date);
        String time = reservations.get(position).getTime();
        holder.rtime.setText(time);
        String email = reservations.get(position).getEmail();
        holder.remail.setText(email);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
