package com.example.helloworld;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class BusinessDetails extends Fragment {
    public BusinessDetails() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Soham");
        return inflater.inflate(R.layout.business_details, container, false);
    }
}

