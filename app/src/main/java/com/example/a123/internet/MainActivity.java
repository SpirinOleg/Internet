package com.example.a123.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
    }

    public void check(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (checkInternet()){
                    runOnUiThread(run1);
                    Log.d("my_tag", "Есть подключение");
                } else {
                    runOnUiThread(run2);
                    Log.d("my_tag", "Нет подключения");
                }
            }
        }).start();
    }

    public boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            try {
                URL url = new URL("https://ya.ru/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection","close");
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200){
                    return true;
                }
                return false;
            } catch (IOException e) {
                Log.d("my_tag", "Ошибка проверки подключения к интернету", e);
                return false;
            }
        }
        return false;
    }

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
           tvInfo.setText("Коннект");
        }
    };

    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            tvInfo.setText("Без инета");

        }
    };
}
