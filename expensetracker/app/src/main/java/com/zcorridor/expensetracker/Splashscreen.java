package com.zcorridor.expensetracker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Splashscreen extends Activity {
    InterstitialAd mInterstitialAd;
    boolean firsttime =true;
    SharedPreferences editor;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        editor= getSharedPreferences("myspref",MODE_PRIVATE);
        firsttime=editor.getBoolean("isfirst",true);
//        editor.putBoolean("firsttime",true);
        initAndLoadInterstitialAds();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              showInterstitial();
            }
        },5000);
    }
    private void initAndLoadInterstitialAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter));

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if (firsttime){
                        Intent intent =new Intent(Splashscreen.this,Privacypolicy.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent =new Intent(Splashscreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }
        else {
            if (firsttime){
                Intent intent =new Intent(Splashscreen.this,Privacypolicy.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent =new Intent(Splashscreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}