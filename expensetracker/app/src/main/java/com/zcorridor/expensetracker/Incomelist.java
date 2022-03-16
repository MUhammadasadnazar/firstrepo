package com.zcorridor.expensetracker;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Incomelist extends AppCompatActivity {
    RecyclerView recyclerViewincome;
    TextView tvmyincome;
    Sqlitehelperclass mydb;
    ArrayList<Incomemodalclass> list;
    IncomeAdapterclass adapter;
    AdView adView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomelist);
        tvmyincome=findViewById(R.id.tvmyincomeee);
        adView3=findViewById(R.id.adView3);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView3.loadAd(adRequest);
//        tvmyincome.setText(getIntent().getIntExtra("myincome",0)+"");
         recyclerViewincome = findViewById(R.id.recyclerviewincome);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView3!=null){
            adView3.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView3!=null){
            adView3.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView3!=null) {
         adView3.resume();
        }
        mydb = new Sqlitehelperclass(this,Sqlitehelperclass.DATABASE_NAME,null,1);
        DecimalFormat decimalFormat=new DecimalFormat("#,###.##");
        list = new ArrayList<Incomemodalclass>();
        list = mydb.getAllIncome();
        int income=0;
        for (int i=0;i<list.size();i++){
             int x = list.get(i).getIncomeamount();
             income=income+x;
        }
        tvmyincome.setText(decimalFormat.format(income)+"");
        adapter = new IncomeAdapterclass(this,list);
        recyclerViewincome.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewincome.setLayoutManager(linearLayoutManager);
    }

    public void addincomee(View view) {
        Intent intent = new Intent(Incomelist.this,Incomeadd.class);
        startActivity(intent);
    }
}