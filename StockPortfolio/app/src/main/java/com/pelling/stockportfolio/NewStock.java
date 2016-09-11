package com.pelling.stockportfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewStock extends AppCompatActivity {

    EditText stockTickerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock);
        stockTickerText = (EditText) findViewById(R.id.stockTickerText);
    }

    //query stock ticker
    public void goButtonClicked(View view) {
        String ticker = stockTickerText.getText().toString();

        if (ticker.equals("")) {
            Toast.makeText(this, "Enter a stock ticker", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Getting stock information", Toast.LENGTH_LONG).show();
            Intent i = new Intent(NewStock.this, StockResult.class);
            i.putExtra("ticker", ticker);
            startActivity(i);
        }

    }
}
