package com.zcorridor.expensetracker;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class Privacypolicy extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);
        webView=findViewById(R.id.webid);
        webView.loadUrl("http://zcorridor.com/privacypolicy.html");
    }
    public void agree(View view){
        getSharedPreferences("myspref",MODE_PRIVATE).edit().putBoolean("isfirst",false).apply();
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}