package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.Adapters.AdapterRecycler;
import com.example.helloworld.Callbacks.CallbackVoid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText dist,loc;
    AutoCompleteTextView kw;
    Spinner cat;
    Button submit_button, clear_button;
    CheckBox auto_detect;
    MenuItem booking;
    RecyclerView resView;
    boolean f = false;
    public RequestQueue rQueue;
    public ArrayList<ResultView> tablelist;
    String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tablelist = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kw = findViewById(R.id.keyword);
        dist = findViewById(R.id.distance);
        loc = findViewById(R.id.location);
        cat = (Spinner) findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
        clear_button = findViewById(R.id.clear);
        submit_button = findViewById(R.id.submit);
        auto_detect = findViewById(R.id.auto_detect);
        rQueue = Volley.newRequestQueue(this);
        resView = findViewById(R.id.results_view);

        kw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i_1, int i_2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i_1, int i_2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String url= "https://backend-7al7heoaqa-wm.a.run.app/autocomplete?kw="+kw.getText().toString();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray scatArray = response.getJSONObject("data").getJSONArray("categories");
                                    JSONArray stitleArray = response.getJSONObject("data").getJSONArray("terms");
                                    String[] sug = new String[6];
                                    for(int i=0; i< scatArray.length();i++) {
                                        sug[i] = scatArray.getJSONObject(i).getString("alias");
                                    }
                                    for(int i=0; i< stitleArray.length();i++) {
                                        sug[i+3] = stitleArray.getJSONObject(i).getString("text");
                                    }
                                    ArrayAdapter<String> suggestAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, sug);
                                    kw.setAdapter(suggestAdapter);
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
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(kw.getText().toString())) {
                    kw.setError("This field is required");
                }
                else if(TextUtils.isEmpty(dist.getText().toString())){
                    dist.setError("This field is required");
                }
                else if(TextUtils.isEmpty(loc.getText().toString()) && loc.getVisibility() == View.VISIBLE){
                    loc.setError("This field is required");
                }
                else if (TextUtils.isEmpty(loc.getText().toString()) && loc.getVisibility() == View.INVISIBLE) {
                    callIp(new CallbackVoid(){
                        @Override
                        public void onSuccess() {
                            displayTable_autocheck();
                        }
                    });

                }
                else {
                    displayTable_onLoc();
                }
            }
        });

        auto_detect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if (ischecked){
                    loc.setVisibility(View.INVISIBLE);
                }
                if (!ischecked){
                    loc.setVisibility(View.VISIBLE);
                }
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f = true;
                kw.getText().clear();
                dist.getText().clear();
                cat.setAdapter(adapter);
                loc.getText().clear();
                TextView no_result = findViewById(R.id.no_result);
                if(no_result.getVisibility()==(View.VISIBLE)){
                    no_result.setVisibility(View.INVISIBLE);
                }
                auto_detect.setChecked(false);
                setAdapter();

            }
        });

    }

    private void setAdapter(){
        if(f ==false){
            AdapterRecycler adapter = new AdapterRecycler(tablelist,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            resView.setLayoutManager(layoutManager);
            resView.setItemAnimator(new DefaultItemAnimator());
            resView.setAdapter(adapter);
        }
        else{
            f = false;
            AdapterRecycler adapter = new AdapterRecycler(tablelist,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            tablelist.clear();
            resView.setLayoutManager(layoutManager);
            resView.setItemAnimator(new DefaultItemAnimator());
            resView.setAdapter(adapter);
        }
    }

    private void displayTable_onLoc(){
        String url = "https://backend-7al7heoaqa-wm.a.run.app/search?kw="+kw.getText().toString()+"&dist="+dist.getText().toString()+"&cat="+cat.getSelectedItem().toString()+"&loc="+loc.getText().toString();
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0;i<=data.length()-1;i++){
                                JSONObject b = data.getJSONObject(i);
                                System.out.println(b.get("name"));
                                System.out.println(b.get("distance"));
//                                int dist = Integer.parseInt(b.get("distance").toString());
                                int dist = (int)Double.parseDouble(b.get("distance").toString());
                                dist=dist/1609;
                                tablelist.add(new ResultView(Integer.toString(i+1),b.get("name").toString(),b.get("rating").toString(), Integer.toString(dist), b.get("image_url").toString(),b.get("id").toString()));
                            }
                            setAdapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView no_result = findViewById(R.id.no_result);
                no_result.setVisibility(View.VISIBLE);
//                error.printStackTrace();
            }
        });
        rQueue.add(request);
    }


    private void displayTable_autocheck(){
        String url = "https://backend-7al7heoaqa-wm.a.run.app/search?kw="+kw.getText().toString()+"&dist="+dist.getText().toString()+"&cat="+cat.getSelectedItem().toString()+"&lat="+lat+"&lng="+lng;
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for(int i=0;i<=data.length()-1;i++){
                                JSONObject b = data.getJSONObject(i);
                                System.out.println(b.get("name"));
                                System.out.println(b.get("distance"));
//                                int dist = Integer.parseInt(b.get("distance").toString());
                                int dist = (int)Double.parseDouble(b.get("distance").toString());
                                dist=dist/1609;
                                tablelist.add(new ResultView(Integer.toString(i+1),b.get("name").toString(),b.get("rating").toString(), Integer.toString(dist), b.get("image_url").toString(),b.get("id").toString()));
                            }
                            setAdapter();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView no_result = findViewById(R.id.no_result);
                no_result.setVisibility(View.VISIBLE);
              //  error.printStackTrace();
            }
        });
        rQueue.add(request);
    }



    public void callIp(CallbackVoid handler) {
        String url = "https://ipinfo.io/207.151.52.21?token=ecab9bb657c8c0";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String loc = response.getString("loc");
                            String[] coord = loc.split(",");
                            lat=coord[0];
                            lng=coord[1];
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
        rQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        booking=menu.findItem(R.id.my_booking);
        booking.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                openNewActivity();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, Bookings.class);
        startActivity(intent);
    }
}