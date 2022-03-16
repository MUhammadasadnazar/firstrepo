package com.zcorridor.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Transactionrecord extends AppCompatActivity {
    RecyclerView recyclerView;
    Transactionadapter transactionadapter;
    ArrayList<TransactionModalClass> list;
    Sqlitetarget mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        recyclerView = findViewById(R.id.rvshowrecord);
        mydb = new Sqlitetarget(this,Sqlitetarget.DATABASE_NAME,null,1);
        list=new ArrayList<TransactionModalClass>();
        list=mydb.getTrnasactionData();
        transactionadapter=new Transactionadapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transactionadapter);

    }
}