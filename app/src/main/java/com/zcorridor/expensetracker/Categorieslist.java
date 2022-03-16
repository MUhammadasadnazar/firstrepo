package com.zcorridor.expensetracker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Categorieslist extends AppCompatActivity {
    RecyclerView recyclerViewexpenses;
    TextView tvtotalexpenses;
    TextView textViewcattitle;
    Sqliteopenhelper mydb;
    int size = 0;
    Categoriesadapter adapter;
    ArrayList<Categoriesmodalclass> expensesmodalclasses;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenseslist);
        adView=findViewById(R.id.adView2);
         AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        recyclerViewexpenses = findViewById(R.id.rvexpenses);
        tvtotalexpenses= findViewById(R.id.totalexpenses);
//        textViewcattitle=findViewById(R.id.tvcatgrytitle);
//        textViewcattitle.setText("Categories");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(adView!=null){
            adView.pause();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView!=null){
            adView.destroy();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView!=null){
            adView.resume();

        }
        expensesmodalclasses  = new ArrayList<Categoriesmodalclass>();
        mydb = new Sqliteopenhelper(this,Sqliteopenhelper.DATABASE_NAME,null,1);
//        tvtotalexpenses.setText(getIntent().getIntExtra("totalexpenses",0)+"");
        expensesmodalclasses = mydb.getAllData();
        int i;
        int expenses=0;
        for (i=0;i<expensesmodalclasses.size();i++){
            int x= expensesmodalclasses.get(i).getBudgetspent();
            expenses=expenses+x;
        }
        tvtotalexpenses.setText(expenses+"");
        adapter = new Categoriesadapter(expensesmodalclasses, Categorieslist.this);
        recyclerViewexpenses.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewexpenses.setLayoutManager(new GridLayoutManager(this,1));
    }
    public void addnewWExpense(View view) {
        Intent intent = new Intent(Categorieslist.this, CategoryaddActivity.class);
        intent.putExtra("Name",false);
        startActivity(intent);
    }

    public void addCategory(View view) {
        Intent intent=new Intent(Categorieslist.this, CategoryaddActivity.class);
        intent.putExtra("Name",true);
        startActivity(intent);
////        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
////        SharedPreferences.Editor editor = preferences.edit();
////        editor.putBoolean("Name",true);
////        editor.apply();
    }
}