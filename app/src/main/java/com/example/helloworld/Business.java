package com.example.helloworld;



import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.fragments.Business_details;
import com.example.helloworld.fragments.MapsFragment;
import com.example.helloworld.fragments.Reviews;
import com.google.android.material.tabs.TabLayout;
import com.example.helloworld.Callbacks.CallbackVoid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class Business extends AppCompatActivity {

    public RequestQueue mQueue;
    String b_name;
    String phone_no;
    String address="";
    String cat_title="";
    String category;
    String price_range="";
    String url;
    String b_url;
    String b_id;
    String lat;
    String lng;
    boolean is_closed;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ArrayList<String> images_url = new ArrayList<>();

    public static class MyAdapter extends FragmentStateAdapter {
        String price_range;
        String phone_number;
        String b_address;
        String b_category;
        String name;
        Boolean is_closed;
        String latitude;
        String longitude;
        ArrayList<String> photos;
        String id;
        String url;


        public MyAdapter(FragmentActivity fa,String price,String contact,String address,String categories,
                         Boolean status, ArrayList<String> images,String bname,String lat, String lng,String b_id, String b_url) {
            super(fa);
            price_range = price;
            photos=images;
            phone_number=contact;
            b_address=address;
            b_category = categories;
            is_closed=status;
            latitude=lat;
            longitude=lng;
            name=bname;
            id=b_id;
            url=b_url;

        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return Business_details.newInstance(price_range,phone_number,b_address,is_closed,b_category,photos,name,url,id);
                case 1:
                    System.out.println("kdnjwc");
                    return MapsFragment.newInstance(latitude,longitude);
                case 2:
                    return Reviews.newInstance(id);
                default:
                    return Business_details.newInstance(price_range,phone_number,b_address,is_closed,b_category,photos,name,url,id);
            }

        }
        @Override
        public int getItemCount() {
            return 3;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        b_id = myIntent.getStringExtra("id");
        setContentView(R.layout.business);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = findViewById(R.id.business_tabs);
        viewPager = findViewById(R.id.viewPager);
        mQueue = Volley.newRequestQueue(this);
        String url = "https://backend-7al7heoaqa-wm.a.run.app/business-details?id="+b_id;
        System.out.println(url);
        performRequest(url, new CallbackVoid() {
            @Override
            public void onSuccess() {
                MyAdapter adapter = new MyAdapter(Business.this,price_range,phone_no,address,cat_title,is_closed,images_url,b_name,lat,lng,b_id,b_url);
                System.out.println(tabLayout.getTabCount());
                viewPager.setAdapter(adapter);
                System.out.println("Hi");
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        System.out.println("Tab Selected");
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
                System.out.println(b_id);
            }
        });
    }

    public void performRequest(String url, CallbackVoid handler){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            System.out.println(data);
                            b_name = data.getString("name").toString();
                            phone_no = data.getString("display_phone").toString();
                            price_range = data.getString("price").toString();
                            is_closed = ((Boolean)data.getBoolean("is_closed")).booleanValue();
                            b_url=data.getString("url");
                            System.out.println(b_url);
                            JSONArray dA = data.getJSONObject("location").getJSONArray("display_address");
                            System.out.println(dA);
                            for(int i = 0; i<=dA.length()-1;i++){
                                address+= dA.get(i)+",";
                            }
                            JSONArray cat = data.getJSONArray("categories");
                            for(int i = 0;i<=cat.length()-1;i++){
                                cat_title += cat.getJSONObject(i).getString("title")+"|";
                            }
                            JSONArray images = data.getJSONArray("photos");
                            for(int i = 0;i<=images.length()-1;i++){
                                images_url.add(images.getString(i));
                            }
                            lat = data.getJSONObject("coordinates").getString("latitude");
                            lng = data.getJSONObject("coordinates").getString("longitude");
                            System.out.println(lat);
                            System.out.println(lng);
                            System.out.println(images_url);
                            System.out.println(address);
                            System.out.println(cat_title);
                            System.out.println(phone_no);
                            System.out.println(price_range);
                            System.out.println(b_name);
                            setTitle(b_name);
                            handler.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "error inside jsonparse", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.business,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.facebook_icon:
                Intent viewIntent1 =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.facebook.com/sharer/sharer.php?u="+b_url));
                startActivity(viewIntent1);
                return true;

            case R.id.twitter_icon:
                Intent viewIntent2 =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://twitter.com/intent/tweet?text=Check%20"+b_name+"%20on%20Yelp!&url="+b_url));
                startActivity(viewIntent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
