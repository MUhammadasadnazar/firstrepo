package com.zcorridor.expensetracker;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;

public class CategoryaddActivity extends AppCompatActivity {
        Spinner spinner;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
     EditText edtcatname;
    EditText edtdescription, edtamount;
    TextView tvaddexpense;
    boolean iscategory =false;
    boolean isadd = false;
    Sqliteopenhelper mydb;
    Sqliteopenhelpere db2;
    Sqlitehelperclass incomedb;
    String[] values ;
    Categoriesmodalclass categoriesmodalclass;
     String youspent;
    ArrayList<Categoriesmodalclass> listobject;
    ArrayList<Incomemodalclass> incomeobject;
    AdView adView7;
    int totalincome=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
         adView7=findViewById(R.id.adView7);
         AdRequest adRequest=new AdRequest.Builder().build();
         adView7.loadAd(adRequest);
        spinner = findViewById(R.id.spinerselectcatagory);
        edtdescription = findViewById(R.id.edtexpensesdescription);
        edtcatname = findViewById(R.id.edtcatname);
        edtamount = findViewById(R.id.edtexpensesprice);
        tvaddexpense = findViewById(R.id.tvaddexpense);
        sharedPreferences = getSharedPreferences("mysf",MODE_PRIVATE);
        mydb = new Sqliteopenhelper(CategoryaddActivity.this,Sqliteopenhelper.DATABASE_NAME,null,1);
        incomedb = new Sqlitehelperclass(CategoryaddActivity.this,Sqlitehelperclass.DATABASE_NAME,null,1);
        db2 = new Sqliteopenhelpere(CategoryaddActivity.this,Sqliteopenhelpere.DATABASE_NAME,null,1);
         listobject= mydb.getAllData();
        values=new String[listobject.size()];
         for (int i =0; i<listobject.size();  i++){
             values[i]=listobject.get(i).getCategory();
          }

         incomeobject=incomedb.getAllIncome();
         for (int i=0;i<incomeobject.size();i++){
             int x = incomeobject.get(i).incomeamount;
             totalincome=totalincome+x;
         }
//        Toast.makeText(this, ">>"+totalincome, Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, values);
        spinner.setAdapter(spinnerArrayAdapter);
          Boolean name =  getIntent().getBooleanExtra("Name",false);
//        add category
        if (name) {
            edtdescription.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            tvaddexpense.setText("Expense Category");
            edtamount.setHint("Estimated Amount");
            iscategory = true;
        }
        else {                // add expense
            edtcatname.setVisibility(View.GONE);
            isadd= true;
        }
      }
    public void saveexpense(View view) {
         String temp=0+"";
        if (iscategory)
        {
            if (!(edtcatname.getText().toString().isEmpty()) && !(edtamount.getText().toString().isEmpty())) {
                if (totalincome == 0) {
                    Toast.makeText(this, "Your Account is 0", Toast.LENGTH_LONG).show();
                } else if (totalincome < Integer.parseInt(edtamount.getText().toString())) {

                    Toast.makeText(this, "Your Account is less than your Expense Amount", Toast.LENGTH_LONG).show();
                }
                else {
                    String catname = edtcatname.getText().toString();
                    String amnt = edtamount.getText().toString();
                    Boolean result = mydb.insertDAta(catname, amnt, temp);
                    if (result == true) {
                        Toast.makeText(this, "DAta Inserted Successfully", Toast.LENGTH_SHORT).show();
                        edtcatname.setText("");
                        edtamount.setText("");
                    } else
                        Toast.makeText(this, "DAta Inserted Failed...", Toast.LENGTH_SHORT).show();
                }
            }
            else Toast.makeText(this, "All Fields Must Be Filled...", Toast.LENGTH_SHORT).show();

        }
        else if (isadd)
        {
            int t = 0;
            if (!(spinner.getSelectedItem().toString().isEmpty()) &&
                    !(edtdescription.getText().toString().isEmpty())
                    &&!(edtamount.getText().toString().isEmpty())) {
                String spinercat = spinner.getSelectedItem().toString();
                String des = edtdescription.getText().toString();
                String amnt1 = edtamount.getText().toString();
                String time = Calendar.getInstance().getTime().toString();
                int x = listobject.get(spinner.getSelectedItemPosition()).getBudgetspent() + Integer.parseInt(amnt1);
                int plnamnt = listobject.get(spinner.getSelectedItemPosition()).getBudgetplanned();
                if (Integer.parseInt(amnt1)>plnamnt) {
                    Toast.makeText(this, "planed amnt is less than this amnt", Toast.LENGTH_LONG).show();
                } else {
                    mydb.getspent(spinercat, x);
                    Boolean result = db2.insertData(spinercat, des, amnt1, time);
                    if (result == true) {
                        Toast.makeText(this, "DAta Inserted Successfully", Toast.LENGTH_SHORT).show();
                        edtdescription.setText("");
                        edtamount.setText("");
                    } else
                        Toast.makeText(this, "DAta Inserted Failed...", Toast.LENGTH_SHORT).show();
                }
            }
            else Toast.makeText(this, "All Fields Must be Filled...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView7!=null){
            adView7.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView7!=null){
            adView7.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView7!=null){
            adView7.destroy();
        }
    }
}