package com.example.earthquake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import  android.webkit.WebView;
import android.widget.TextView;

public class Browser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView web=findViewById(R.id.web);
        TextView textView=findViewById(R.id.heading);

        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Bundle bundle=getIntent().getExtras();
        web.loadUrl(bundle.getString("url"));
        textView.setText(bundle.getString("url"));

    }
}
