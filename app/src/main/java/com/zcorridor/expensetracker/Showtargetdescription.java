package com.zcorridor.expensetracker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;

public class Showtargetdescription extends AppCompatActivity {
    ArrayList<Targetmodalclass> obj;
    Sqlitetarget mydb;
     AlertDialog dialog;
     TextView tvdesname,tvdessaved,tvdesremaining,tvdestotal;
     ImageView imageView;
     Button btnadd,btndatepicker;
     int y;
     CardView cardViewadddays;
     boolean isadd,iswithdraw;
     long isadded ;
     long days;
     TextView tvdaily,tvweekly,tvmonthly;
    AlertDialog.Builder builder;
    AdView adView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.targetdesrows);
         mydb  =new Sqlitetarget(this,Sqlitetarget.DATABASE_NAME,null,1);
//         tvdesname = findViewById(R.id.tvdesname);
         adView=findViewById(R.id.adView6);
         AdRequest adRequest=new AdRequest.Builder().build();
         adView.loadAd(adRequest);
         tvdessaved = findViewById(R.id.tvdesspntamnt);
         imageView=findViewById(R.id.imageviewdes);
         tvdesname=findViewById(R.id.tvdesgoalname);
         tvdaily = findViewById(R.id.tvdaily);
         tvweekly = findViewById(R.id.tvweekly);
         cardViewadddays=findViewById(R.id.cardviewadddays);
         tvmonthly = findViewById(R.id.tvmonthly);
        tvdesremaining=findViewById(R.id.tvdesrmningamnt);
        tvdestotal = findViewById(R.id.tvdestotalamnt);
        btndatepicker = findViewById(R.id.datepicker);
        btnadd = findViewById(R.id.btnadd);
         tvdessaved.setText(getIntent().getIntExtra("savedamnt",00)+"");
         tvdesname.setText(getIntent().getStringExtra("des")+"");
         imageView.setImageURI(Uri.parse(getIntent().getStringExtra("imgpath").toString()));
         tvdestotal.setText(getIntent().getIntExtra("total",00)+"");
         getIntent().getLongExtra("days",0);
         tvdesremaining.setText(getIntent().getIntExtra("total",00)
                 -Integer.parseInt(tvdessaved.getText().toString())+"");
         btndatepicker.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog datePickerDialog =new DatePickerDialog(Showtargetdescription.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         Calendar calendar= Calendar.getInstance();
                         calendar.set(year,month,dayOfMonth);
                         days = (calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / (1000 * 60 * 60 * 24);
                         long res = mydb.updateDays(getIntent().getStringExtra("des"),days);
                         if (res == 1){
                              isadded =days;
                             btndatepicker.setVisibility(View.GONE);
                             cardViewadddays.setVisibility(View.GONE);
                             refresh();
                             Toast.makeText(Showtargetdescription.this, "Days are Updated..", Toast.LENGTH_SHORT).show();
                         }
                         else
                             Toast.makeText(Showtargetdescription.this, "Days are not update...", Toast.LENGTH_SHORT).show();
                     }
                 },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                 datePickerDialog.show();
             }
         });
         if(getIntent().getLongExtra("days",0)>0){
             btndatepicker.setVisibility(View.GONE);
             cardViewadddays.setVisibility(View.GONE);
             refresh();
         }
     }
    @Override
    protected void onResume() {
        super.onResume();
      }
    public void refresh(){
        if (getIntent().getLongExtra("days",0)>1) {
//            Toast.makeText(this, "days"+getIntent().getLongExtra("days",0), Toast.LENGTH_SHORT).show();
            tvdaily.setText(Integer.parseInt(tvdesremaining.getText().toString())
                    / getIntent().getLongExtra("days", 0) + "");
        }
        else tvdaily.setText("_");
        if (getIntent().getLongExtra("days",0)>7){
            tvweekly.setText(Integer.parseInt(tvdesremaining.getText().toString())
                    / (getIntent().getLongExtra("days",0) / 7) + "");
        }
        else tvweekly.setText("_");
        if (getIntent().getLongExtra("days",0)>30) {
            tvmonthly.setText(Integer.parseInt(tvdesremaining.getText().toString())
                    / (getIntent().getLongExtra("days", 0) / 30) + "");
        }
        else tvmonthly.setText("_");

    }
    public void addSavingOrWithdraw(View view) {
         isadd=true;
        btnadd.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view1= layoutInflater.inflate(R.layout.savingorwithdrawl,null);
        final EditText editText = view1.findViewById(R.id.edtadorwithdraw);
        final Button btnadd = view1.findViewById(R.id.btnaddd);
        final Button btnwithdraw = view1.findViewById(R.id.btnwithdraww);
        TextView tvsave = view1.findViewById(R.id.tvsavee);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isadd =true;
                iswithdraw=false;
                btnadd.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnwithdraw.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
              }
        });
        btnwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isadd=false;
                iswithdraw=true;
                btnwithdraw.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                btnadd.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            }
        });
        tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x= getIntent().getIntExtra("savedamnt",0);
                if (isadd){
                         y = x + Integer.parseInt(editText.getText().toString());
                        long res = mydb.updateData(getIntent().getStringExtra("des"), y);
                        boolean ress = mydb.insertTransactionData(Integer.parseInt(editText.getText().toString()),"Added"
                                ,getIntent().getStringExtra("des"),Calendar.getInstance().getTimeInMillis());
//                        if (res == 1) {
                        if ( res>=1 && ress) {
                            editText.setText("");
                            dialog.dismiss();
                            Toast.makeText(Showtargetdescription.this, "data updated", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Showtargetdescription.this, "Failed.....", Toast.LENGTH_SHORT).show();
                }
                if (iswithdraw){
                        y = x - Integer.parseInt(editText.getText().toString());
                    boolean ress = mydb.insertTransactionData(Integer.parseInt(editText.getText().toString()),"Withdraw"
                            ,getIntent().getStringExtra("des"),Calendar.getInstance().getTimeInMillis());
                        long res = mydb.updateData(getIntent().getStringExtra("des"), y);
//                        if (res == 1) {
                            if ( res>=1 && ress) {
                                editText.setText("");
                            dialog.dismiss();
                            Toast.makeText(Showtargetdescription.this, "data updated", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Showtargetdescription.this, "Failed.....", Toast.LENGTH_SHORT).show();

                }
                else if (!isadd && !iswithdraw &&editText.getText().toString().isEmpty()){
                    Toast.makeText(Showtargetdescription.this, "All Field Must be Filled...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view1);
        dialog =builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void cancelDialoge(View view) {
          dialog.dismiss();
    }
}