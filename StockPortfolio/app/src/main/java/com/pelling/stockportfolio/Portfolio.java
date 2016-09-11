package com.pelling.stockportfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Portfolio extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        db = new Database(this, null, null, 1);
        List<String> stocks = db.getAllStocks();
        final ListView stocksListView = (ListView) findViewById(R.id.stocksList);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
        stocksListView.setAdapter(listAdapter);

        stocksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStock = (String) stocksListView.getItemAtPosition(position);
                Intent i = new Intent(Portfolio.this, ViewStock.class);
                i.putExtra("ticker", selectedStock);
                startActivity(i);
            }
        });
    }

    public void newButtonClicked(View view){
        Intent i = new Intent(Portfolio.this, NewStock.class);
        startActivity(i);
    }
}
