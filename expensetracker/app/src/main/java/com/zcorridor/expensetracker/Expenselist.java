package com.zcorridor.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Expenselist extends AppCompatActivity {
    RecyclerView recyclerViewexpenseadd;
    TextView tvexname;
    EditText edtcatname,edtdescription,edtamount;
    Sqliteopenhelpere mydb;
    Expenseadapter adapter;
    TextView tvremaining,tvyouspent,tvtotalbugted;
    SeekBar seekBarexpense;
    String catname;
    ArrayList<Expensemodalclass> exaddmodalclassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenseadd);
        AdView adView =(AdView) findViewById(R.id.adView5);
        tvexname=findViewById(R.id.tvexpenname);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tvremaining = findViewById(R.id.tvremainingbgt);
        tvtotalbugted = findViewById(R.id.tvtotalbudgted);
        tvyouspent = findViewById(R.id.tvspent);
        seekBarexpense = findViewById(R.id.seekbarexpense);
        recyclerViewexpenseadd = findViewById(R.id.recyclerviewexpenseadd);
    }
    @Override
    protected void onResume() {
        super.onResume();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        mydb = new Sqliteopenhelpere(this,Sqliteopenhelpere.DATABASE_NAME,null,1);
        catname = getIntent().getStringExtra("catname");
        tvexname.setText(catname+"");
        int planned= getIntent().getIntExtra("bgtplaned",00);
        seekBarexpense.setMax(planned);
        seekBarexpense.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        exaddmodalclassArrayList = new ArrayList<Expensemodalclass>();

        exaddmodalclassArrayList = mydb.getalldata(catname);
        int i;
        int youspent =0;
        for ( i = 0;i<exaddmodalclassArrayList.size();i++){
            youspent= youspent+exaddmodalclassArrayList.get(i).getAmount();
        }
        tvyouspent.setText(""+decimalFormat.format(youspent));
        int Remaining = planned-youspent;
         tvremaining.setText(""+decimalFormat.format(Remaining));
        tvtotalbugted.setText(""+decimalFormat.format(planned));
         if (Remaining<0){
             tvremaining.setTextColor(this.getResources().getColor(R.color.colorAccent));
         }
         seekBarexpense.setProgress(youspent);
        adapter = new Expenseadapter(exaddmodalclassArrayList, Expenselist.this);
        recyclerViewexpenseadd.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewexpenseadd.setLayoutManager(new GridLayoutManager(this,1));
    }

    public void addDAta(View view) {
        String catname =  edtcatname.getText().toString();
        String description =  edtdescription.getText().toString();
        String amount = edtamount.getText().toString();
        String date = Calendar.getInstance().getTime().toString();
        boolean res =  mydb.insertData(catname,description,amount,date);
        if(res){
            Toast.makeText(this, "data inserted successfull", Toast.LENGTH_SHORT).show();
            edtcatname.setText("");
            edtdescription.setText("");
            edtamount.setText("");
        }
        else
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
    }
}