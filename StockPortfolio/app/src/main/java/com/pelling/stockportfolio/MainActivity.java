package com.pelling.stockportfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //new stock query
    public void newButtonClicked(View view) {
        Intent i = new Intent(MainActivity.this, NewStock.class);
        startActivity(i);
    }

    //open portfolio
    public void openButtonClicked(View view) {
        Intent i = new Intent(MainActivity.this, Portfolio.class);
        startActivity(i);
    }
}
