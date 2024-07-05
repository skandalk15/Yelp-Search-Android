package com.example.helloworld.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.Callbacks.CallbackVoid;
import com.example.helloworld.MainActivity;
import com.example.helloworld.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews extends Fragment {


    static String business_id;
    public RequestQueue rQueue;
    JSONArray reviews;
    String name1,name2,name3;
    String rating1,rating2,rating3;
    String text1,text2,text3;
    String date1,date2,date3;
    View view;


    public static Reviews newInstance(String id) {
        Reviews fragmentThird = new Reviews();
        business_id=id;
        return fragmentThird;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String url="https://backend-7al7heoaqa-wm.a.run.app/reviews?id="+business_id;
        rQueue = Volley.newRequestQueue(getContext());
        view =  inflater.inflate(R.layout.fragment_reviews, container, false);
        performRequest(url,new CallbackVoid() {
                    @Override
                    public void onSuccess() {
                        System.out.println("kbk vef vefj ");
                        TextView name_1 = view.findViewById(R.id.name_1);
                        name_1.setText(name1);
                        TextView name_2 = view.findViewById(R.id.name_2);
                        name_2.setText(name2);
                        TextView name_3 = view.findViewById(R.id.name_3);
                        name_3.setText(name3);

                        TextView rating_1 = view.findViewById(R.id.rating_1);
                        rating_1.setText("Rating :"+rating1+"/"+5);
                        TextView rating_2 = view.findViewById(R.id.rating_2);
                        rating_2.setText("Rating :"+rating2+"/"+5);
                        TextView rating_3 = view.findViewById(R.id.rating_3);
                        rating_3.setText("Rating :"+rating3+"/"+5);

                        TextView text_1 = view.findViewById(R.id.review_1);
                        text_1.setText(text1);
                        TextView text_2 = view.findViewById(R.id.review_2);
                        text_2.setText(text2);
                        TextView text_3 = view.findViewById(R.id.review_3);
                        text_3.setText(text2);

                        TextView date_1 = view.findViewById(R.id.date_1);
                        date_1.setText(date1);
                        TextView date_2 = view.findViewById(R.id.date_2);
                        date_2.setText(date2);
                        TextView date_3 = view.findViewById(R.id.date_3);
                        date_3.setText(date3);
                    }
                }
        );
        return view;
    }

    public void performRequest(String url, CallbackVoid handler){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            reviews = data.getJSONArray("reviews");

                            text1 = reviews.getJSONObject(0).getString("text");
                            text2 = reviews.getJSONObject(1).getString("text");
                            text3 = reviews.getJSONObject(2).getString("text");

                            System.out.println(text1);

                            name1 = reviews.getJSONObject(0).getJSONObject("user").getString("name");
                            name2 = reviews.getJSONObject(1).getJSONObject("user").getString("name");
                            name3 = reviews.getJSONObject(2).getJSONObject("user").getString("name");

                            rating1 = reviews.getJSONObject(0).getString("rating");
                            rating2 = reviews.getJSONObject(1).getString("rating");
                            rating3 = reviews.getJSONObject(2).getString("rating");

                            date1 = reviews.getJSONObject(0).getString("time_created").substring(0,10);
                            date2 = reviews.getJSONObject(1).getString("time_created").substring(0,10);
                            date3 = reviews.getJSONObject(2).getString("time_created").substring(0,10);
                            handler.onSuccess();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        rQueue.add(request);
    }
}