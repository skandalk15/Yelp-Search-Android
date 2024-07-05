package com.example.helloworld.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.helloworld.Business;
//import com.example.helloworld.CarouselAdapter;
import com.example.helloworld.Models.BookingID;
import com.example.helloworld.Models.Reservation;
import com.example.helloworld.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


public class Business_details extends Fragment {

    String address;
    String price;
    String contact;
    String status;
    String categories;
    ArrayList<String> images;
    String bname;
    String b_url;
    String b_id;
    int sr_no=0;



    public static Business_details newInstance(String price, String contact, String address, Boolean is_closed, String categories, ArrayList<String> images_url, String name, String url, String id) {
        Business_details fragmentFirst = new Business_details();
        Bundle args = new Bundle();
        args.putStringArrayList("images",images_url);
        args.putString("price", price);
        args.putString("contact", contact);
        args.putString("name", name);
        args.putString("Business Link",url);
        if(is_closed){
            args.putString("status","Closed");
        }
        else{
            args.putString("status","Open Now");
        }
        args.putString("address",address);
        args.putString("categories",categories);
        args.putString("id",id);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

//    public static Business_details newInstance(){
//        Business_details fragmentSecond = new Business_details();
//        return fragmentSecond;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        price = getArguments().getString("price");
        contact = getArguments().getString("contact");
        status = getArguments().getString("status");
        address = getArguments().getString("address");
        categories = getArguments().getString("categories");
        images=getArguments().getStringArrayList("images");
        bname=getArguments().getString("name");
        b_url=getArguments().getString("Business Link");
        b_id=getArguments().getString("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_business_details, container, false);

        TextView price = view.findViewById(R.id.price);
        price.setText(getArguments().getString("price"));

        TextView address = view.findViewById(R.id.address);
        address.setText(getArguments().getString("address"));

        TextView phone_number = view.findViewById(R.id.contact);
        phone_number.setText(getArguments().getString("contact"));

        TextView categories = view.findViewById(R.id.categories);
        categories.setText(getArguments().getString("categories"));

        TextView textView  = view.findViewById(R.id.yelp_href);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href="+b_url+"> Business Link </a>";
        textView.setText(Html.fromHtml(text));

        Button reserve_button = view.findViewById(R.id.reserve_now);
        reserve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                View promptview = inflater.inflate(R.layout.prompt_view, container, false);
                alertDialogBuilder.setView(promptview);
                TextView title = promptview.findViewById(R.id.title);
                title.setText(bname);
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                EditText date = (EditText)promptview.findViewById(R.id.date_input);
                TextView time = promptview.findViewById(R.id.time_input);
                EditText email = promptview.findViewById(R.id.email);
                date.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Calendar myCalander = Calendar.getInstance();
                        int year = myCalander.get(Calendar.YEAR);
                        int month = myCalander.get(Calendar.MONTH);
                        int day = myCalander.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(String.valueOf(monthOfYear+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year));
                            }
                        }, year,month,day).show();

                    }
                });
                time.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                time.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute,true);
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });
                alertDialogBuilder.setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                System.out.println();
                                String email_id = email.getText().toString();
                                System.out.println(date.getText());
                                String rdate=date.getText().toString();
                                String rtime = time.getText().toString();
                                int hrs = Integer.parseInt(rtime.split(":")[0]);
                                System.out.println(hrs);
                                System.out.println(time.getText());
                                if(!email_id.matches(emailPattern)) {
                                    Toast.makeText(getContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                                }
                                else if(hrs<10 || hrs>17){
                                    Toast.makeText(getContext(), "Time should be between 10AM AND 5PM", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    SharedPreferences sP = getActivity().getSharedPreferences("Reservations",0);
                                    SharedPreferences.Editor editor = sP.edit();
                                    sr_no++;
                                    Reservation r = new Reservation(bname,email_id,rdate,rtime,sr_no);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(r);
//                                    BookingID bid = new BookingID(b_id);
                                    editor.putString("Reservation",json);
                                    editor.apply();
                                    Toast.makeText(getContext(), "Reservation Booked", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        TextView status = view.findViewById(R.id.status);
        if(getArguments().getString("status").equals("Open Now")){
            status.setTextColor(Color.parseColor("#22d822"));
        }
        else{
            status.setTextColor(Color.parseColor("#ff0000"));
        }
        status.setText(getArguments().getString("status"));
        HorizontalScrollView hs = view.findViewById(R.id.scroll_view1);
        System.out.println(images.get(0));

        LinearLayout layout = view.findViewById(R.id.linear);
        for (int i = 0; i <= images.size()-1; i++) {
            ImageView imageView = new ImageView(this.getContext());
            Picasso.get().load(images.get(i)).into(imageView);
            layout.addView(imageView);
        }
        return view;
    }
}