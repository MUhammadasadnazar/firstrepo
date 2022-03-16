 package com.zcorridor.expensetracker;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.karan.churi.PermissionManager.PermissionManager;

import java.security.Permission;
import java.text.DecimalFormat;
import java.util.ArrayList;

import me.tankery.lib.circularseekbar.CircularSeekBar;

 public class MainActivity extends AppCompatActivity {
    TextView tvtotalExpense,tvtotalspentexpenses,tvexpensespercent
            ,tvtotalincome, tvincomepercent,tvincomenugted,
            tvgoalspercent,tvgoalstotal,tvgoalssaved;
     Sqliteopenhelper mydb;
    ArrayList<Categoriesmodalclass> list;
    Sqlitehelperclass db2;
    Sqlitetarget sqlitetargetmydb;
    ArrayList<Incomemodalclass> list2;
    ArrayList<Targetmodalclass> listgoals;
     CircularSeekBar seekBarincome,seekBarexpenses,seekBarsavinggoals;
     SeekBar seekBarincomee,seekBarexpensess,seekBargoals;
     AdView adView;
     PermissionManager manager;
     InterstitialAd interstitialAd;

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         manager.checkResult(requestCode, permissions, grantResults);
     }

     @Override
     protected void onPause() {
         super.onPause();
         if (adView!=null){
             adView.pause();
         }
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
         if (adView!=null){
             adView.destroy();
         }
     }

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new PermissionManager() {};
        manager.checkAndRequestPermissions(this);
         adView=findViewById(R.id.adView);
         AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
         initAndLoadInterstitialAds();
//        interstitialAd=new InterstitialAd(this);
//         AdRequest adRequest1=new AdRequest.Builder().build();
//         interstitialAd.loadAd(adRequest1);
          tvtotalincome=findViewById(R.id.tvtotalincome);
        tvincomenugted=findViewById(R.id.tvincomebugted);
        tvtotalspentexpenses = findViewById(R.id.tvExpensestotal);
         tvtotalExpense = findViewById(R.id.tvtotalexpenes);
         tvincomepercent=findViewById(R.id.tvincomepercent);
         tvgoalstotal=findViewById(R.id.tvgolastotal);
         tvgoalssaved=findViewById(R.id.tvgoalssaved);
        tvexpensespercent = findViewById(R.id.tvexpensespercent);
        tvgoalspercent = findViewById(R.id.tvgoalspercent);
         seekBarincome = findViewById(R.id.seekbarincome);
         seekBarexpenses=findViewById(R.id.seekbarexpenses);
         seekBarsavinggoals=findViewById(R.id.seekbarsavinggoals);
         seekBarincomee=findViewById(R.id.seekbarincomee);
         seekBarexpensess=findViewById(R.id.seekbarexpensess);
         seekBargoals=findViewById(R.id.seekbargoals);
     }
     private void initAndLoadInterstitialAds() {
         interstitialAd = new InterstitialAd(this);

         // set the ad unit ID
         interstitialAd.setAdUnitId(getString(R.string.inter));

         AdRequest adRequest = new AdRequest.Builder()
                 .build();

         // Load ads into Interstitial Ads
         interstitialAd.loadAd(adRequest);

     }

     private void showInterstitial(final Intent intent) {
         if (interstitialAd.isLoaded()) {
             interstitialAd.show();
             interstitialAd.setAdListener(new AdListener() {
                 public void onAdLoaded() {
                 }

                 @Override
                 public void onAdFailedToLoad(int i) {
                     super.onAdFailedToLoad(i);
                     initAndLoadInterstitialAds();
                 }

                 @Override
                 public void onAdClosed() {
                     super.onAdClosed();
                  startActivity(intent);
                  initAndLoadInterstitialAds();
                 }
             });
         }
         else {
             startActivity(intent);
             initAndLoadInterstitialAds();

         }
     }
     @Override
    protected void onResume() {
        super.onResume();
        int income=0;
        int goalspentamnt = 0;
        int tempincome = 0;
        int spentt =0;
        if (adView!=null){
        adView.resume();}
        DecimalFormat df = new DecimalFormat("#,###.##");
        list = new ArrayList<Categoriesmodalclass>();
        list2 = new ArrayList<Incomemodalclass>();
        sqlitetargetmydb = new Sqlitetarget(this,Sqlitetarget.DATABASE_NAME,null,1);
        listgoals= new ArrayList<Targetmodalclass>();
        listgoals=sqlitetargetmydb.getTargetData();
        int i2;
        int goaltotalamnt = 0;
        for (i2=0 ; i2<listgoals.size() ;i2++){
            int x= listgoals.get(i2).getTargetamount();
            int y= listgoals.get(i2).getAmountspent();
            goaltotalamnt = goaltotalamnt+x;
            goalspentamnt = goalspentamnt+y;
        }
        tvgoalstotal.setText(df.format(goaltotalamnt)+"");
        tvgoalssaved.setText(df.format(goalspentamnt)+"");
        seekBargoals.setMax(goaltotalamnt);
        seekBargoals.setProgress(goalspentamnt);
        seekBarsavinggoals.setMax(goaltotalamnt);
        seekBarsavinggoals.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekBarsavinggoals.setProgress(goalspentamnt);
        seekBargoals.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        double goal = (seekBarsavinggoals.getProgress()/goaltotalamnt)*100;
        int gooal = (int) goal;
        tvgoalspercent.setText(gooal+"%");
         mydb = new Sqliteopenhelper(this,Sqliteopenhelper.DATABASE_NAME,null,1);
        list= mydb.getAllData();
        int i;
        int plandbgt =0;
        for (i=0;i<list.size();i++){
            int spent = list.get(i).getBudgetspent();
            int temp = list.get(i).getBudgetplanned();
            plandbgt  =plandbgt+temp;
            spentt = spentt+spent;
        }
//        income
        db2 = new Sqlitehelperclass(this,Sqlitehelperclass.DATABASE_NAME,null,1);
        list2 = db2.getAllIncome();
        int ii;
        for (ii=0;ii<list2.size();ii++){
            int x= list2.get(ii).getIncomeamount();
            tempincome = tempincome+x;
        }
        seekBarincome.setMax(tempincome);
        tvtotalincome.setText(df.format(tempincome)+"");
        seekBarincomee.setMax(tempincome);
        seekBarincomee.setProgress(plandbgt);
        seekBarincomee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        seekBarincome.setProgress(plandbgt);
        seekBarincome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        double incme = (seekBarincome.getProgress()/tempincome)*100;
        income = (int) incme;
        if (tempincome<plandbgt){
            tvincomenugted.setText(0+"");
            tvincomepercent.setText(0+"%");

        }
        else {
            tvincomenugted.setText(df.format(plandbgt) + "");
            tvincomepercent.setText(income + "%");
        }
//        income
//        expenses
        seekBarexpenses.setMax(plandbgt);
        seekBarexpenses.setProgress(spentt);
        tvtotalspentexpenses.setText(df.format(plandbgt)+"");
        tvtotalExpense.setText(df.format(spentt)+"");
        seekBarexpenses.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        double expnse = (double) (seekBarexpenses.getProgress()/plandbgt)*100;
        int expens = (int) expnse;
        tvexpensespercent.setText(expens+"%");
        seekBarexpensess.setMax(plandbgt);
        seekBarexpensess.setProgress(spentt);
        seekBarexpensess.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
//        expenses
    }
    public void showIncome(View view) {


                 Intent intent = new Intent(MainActivity.this,Incomelist.class);
        showInterstitial(intent);
     }

    public void target(View view) {


                Intent intent = new Intent(MainActivity.this,Targetlist.class);
             showInterstitial(intent);

    }

     public void showExpensesList(View view) {

                 Intent intent = new Intent(MainActivity.this, Categorieslist.class);
                 showInterstitial(intent);


     }
 }
