package com.example.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Adapters.AdapterReservation;
import com.example.helloworld.Models.BookingID;
import com.example.helloworld.Models.Reservation;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {

    ArrayList<Reservation> reservations = new ArrayList<>();
    public RecyclerView resAdapter;
    BookingID[] b_id;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        SharedPreferences sP = getSharedPreferences("Reservations",0);
        SharedPreferences.Editor prefsEditor = sP.edit();
        reservations = new ArrayList<>();
        TextView no_booking = findViewById(R.id.no_booking);
        no_booking.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        String json = sP.getString("Reservation","");
        Reservation r = gson.fromJson(json,Reservation.class);
        reservations.add(r);
//        System.out.println(r.getEmail());
        if(reservations.size()==0){
            no_booking.setVisibility(View.VISIBLE);
        }
        else{

            no_booking.setVisibility(View.GONE);
            resAdapter = findViewById(R.id.reservation_recycler);
            set_Adapter();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void set_Adapter() {
        AdapterReservation adapter = new AdapterReservation(reservations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        resAdapter.setLayoutManager(layoutManager);
        resAdapter.setItemAnimator(new DefaultItemAnimator());
        resAdapter.setAdapter(adapter);
    }
}
