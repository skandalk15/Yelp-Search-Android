package com.example.helloworld.Models;

public class Reservation {
    String email;
    String date;
    String time;
    String bname;
    int sr_no;

    public Reservation(String bname,String email,String date,String time,int sr_no){
        this.email=email;
        this.date=date;
        this.time=time;
        this.bname=bname;
        this.sr_no=sr_no;
    }

    public String getEmail(){
        return this.email;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){ return this.time; }

    public int getSrNo(){ return this.sr_no; }

    public String getBName(){ return this.bname; }

}
