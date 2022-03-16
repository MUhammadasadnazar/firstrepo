package com.zcorridor.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Incomeadd extends AppCompatActivity {
    EditText edtdes,edtamount;
    Sqlitehelperclass mydb;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomeadd);
        adView=findViewById(R.id.adView8);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        edtdes = findViewById(R.id.incomedescription);
        edtamount = findViewById(R.id.incomerupees);
        mydb = new Sqlitehelperclass(this,Sqlitehelperclass.DATABASE_NAME,null,1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView!=null){
            adView.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(adView!=null){
            adView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView!=null){
            adView.resume();
        }
    }

    public void addincomee(View view) {
        if (edtdes.getText().toString().isEmpty() || edtamount.getText().toString().isEmpty()){
            Toast.makeText(this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
        }
        else {
            Boolean result = mydb.insertIncome(edtdes.getText().toString(), Integer.parseInt(edtamount.getText().toString()));
            if (result == true) {
                Toast.makeText(this, "DAta Inserted Successfully", Toast.LENGTH_SHORT).show();
                edtdes.setText("");
                edtamount.setText("");
            } else
                Toast.makeText(this, "DAta Inserted Failed", Toast.LENGTH_SHORT).show();
        }
    }
}