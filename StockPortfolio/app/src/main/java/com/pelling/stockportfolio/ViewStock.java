package com.pelling.stockportfolio;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewStock extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock);
        String stockTicker = getIntent().getStringExtra("ticker");
        Database db = new Database(this, null, null, 1);
        Stock stock = db.getStock(stockTicker);

        TextView stockNameText = (TextView) findViewById(R.id.stockNameText);
        TextView stockTickerText = (TextView) findViewById(R.id.stockTickerText);
        TextView priceText = (TextView) findViewById(R.id.priceText);
        TextView changeText = (TextView) findViewById(R.id.changeText);
        TextView volumeText = (TextView) findViewById(R.id.volumeText);
        TextView dayHighText = (TextView) findViewById(R.id.dayHighText);
        TextView dayLowText = (TextView) findViewById(R.id.dayLowText);
        TextView yearHighText = (TextView) findViewById(R.id.yearHighText);
        TextView yearLowText = (TextView) findViewById(R.id.yearLowText);
        TextView marketCapText = (TextView) findViewById(R.id.marketCapText);
        TextView epsText = (TextView) findViewById(R.id.epsText);
        TextView dividendText = (TextView) findViewById(R.id.dividendText);
        TextView yieldText = (TextView) findViewById(R.id.yieldText);
        TextView peRatioText = (TextView) findViewById(R.id.peRatioText);
        TextView psRatioText = (TextView) findViewById(R.id.psRatioText);
        TextView pbRatioText = (TextView) findViewById(R.id.pbRatioText);

        stockNameText.setText(replaceNull(stock.get_name()));
        stockTickerText.setText(replaceNull(stock.get_symbol()));
        priceText.setText(replaceNull(stock.get_price()));
        changeText.setText(replaceNull(stock.get_change()));
        volumeText.setText(replaceNull(stock.get_volume()));
        dayHighText.setText(replaceNull(stock.get_daysHigh()));
        dayLowText.setText(replaceNull(stock.get_daysLow()));
        yearHighText.setText(replaceNull(stock.get_yearHigh()));
        yearLowText.setText(replaceNull(stock.get_yearLow()));
        marketCapText.setText(replaceNull(stock.get_marketCapitalization()));
        epsText.setText(replaceNull(stock.get_earningsShare()));
        dividendText.setText(replaceNull(stock.get_dividendShare()));
        yieldText.setText(replaceNull(stock.get_dividendYield()));
        peRatioText.setText(replaceNull(stock.get_peRatio()));
        psRatioText.setText(replaceNull(stock.get_priceSales()));
        pbRatioText.setText(replaceNull(stock.get_priceBook()));
    }

    //new stock query
    public void newButtonClicked(View view) {
        Intent i = new Intent(ViewStock.this, NewStock.class);
        startActivity(i);
    }

    //open portfolio
    public void openButtonClicked(View view) {
        Intent i = new Intent(ViewStock.this, Portfolio.class);
        startActivity(i);
    }

    //view stock analysis
    public void analysisButtonClicked(View view) {
        String ticker = getIntent().getStringExtra("ticker");
        Intent i = new Intent(ViewStock.this, Analysis.class);
        i.putExtra("ticker", ticker);
        startActivity(i);
    }

    //update stock and redirect to portfolio
    public void updateButtonClicked(View view) {
        String stockTicker = getIntent().getStringExtra("ticker");
        deleteStockFromPortfolio(stockTicker);
        new ConnectionTask(stockTicker).execute();
    }

    private String replaceNull(String value) {
        String returnValue = value;
        if (value == null) {
            returnValue = "";
        }
        return returnValue;
    }

    private void addStockToPortfolio(Stock stock) {
        Database db = new Database(this, null, null, 1);
        db.addStock(stock);
        Toast.makeText(this, "Updated stock in portfolio", Toast.LENGTH_LONG).show();
    }

    private void deleteStockFromPortfolio(String ticker) {
        Database db = new Database(this, null, null, 1);
        db.deleteStock(ticker);
    }

    @Override
    public void run() {

    }

    private class ConnectionTask extends AsyncTask<Void, Integer, Void> {

        private Stock _stock = new Stock();
        private String _stockTicker;

        public ConnectionTask(String stockTicker) {
            this._stockTicker = stockTicker;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String strUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22" + _stockTicker + "%22)&format=json&env=http://datatables.org/alltables.env";
                URL url = new URL(strUrl);
                HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
                urlConn.connect();
                StringBuilder result = new StringBuilder();
                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                rd.close();

                try {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    JSONObject queryObject = jsonObject.getJSONObject("query");
                    JSONObject resultsObject = queryObject.getJSONObject("results");
                    JSONObject quoteObject = resultsObject.getJSONObject("quote");

                    _stock.set_name(replaceNull(quoteObject.getString("Name")));
                    _stock.set_symbol(replaceNull(quoteObject.getString("symbol")));
                    _stock.set_price(replaceNull(quoteObject.getString("LastTradePriceOnly")));
                    _stock.set_change(replaceNull(quoteObject.getString("Change")));
                    _stock.set_volume(replaceNull(quoteObject.getString("Volume")));
                    _stock.set_daysHigh(replaceNull(quoteObject.getString("DaysHigh")));
                    _stock.set_daysLow(replaceNull(quoteObject.getString("DaysLow")));
                    _stock.set_yearHigh(replaceNull(quoteObject.getString("YearHigh")));
                    _stock.set_yearLow(replaceNull(quoteObject.getString("YearLow")));
                    _stock.set_marketCapitalization(replaceNull(quoteObject.getString("MarketCapitalization")));
                    _stock.set_earningsShare(replaceNull(quoteObject.getString("EarningsShare")));
                    _stock.set_dividendShare(replaceNull(quoteObject.getString("DividendShare")));
                    _stock.set_dividendYield(replaceNull(quoteObject.getString("DividendYield")));
                    _stock.set_peRatio(replaceNull(quoteObject.getString("PERatio")));
                    _stock.set_priceSales(replaceNull(quoteObject.getString("PriceSales")));
                    _stock.set_priceBook(replaceNull(quoteObject.getString("PriceBook")));
                }
                catch (Exception e) {

                }
            }
            catch (IOException e) {

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void result) {
            addStockToPortfolio(_stock);

            TextView stockNameText = (TextView) findViewById(R.id.stockNameText);
            TextView stockTickerText = (TextView) findViewById(R.id.stockTickerText);
            TextView priceText = (TextView) findViewById(R.id.priceText);
            TextView changeText = (TextView) findViewById(R.id.changeText);
            TextView volumeText = (TextView) findViewById(R.id.volumeText);
            TextView dayHighText = (TextView) findViewById(R.id.dayHighText);
            TextView dayLowText = (TextView) findViewById(R.id.dayLowText);
            TextView yearHighText = (TextView) findViewById(R.id.yearHighText);
            TextView yearLowText = (TextView) findViewById(R.id.yearLowText);
            TextView marketCapText = (TextView) findViewById(R.id.marketCapText);
            TextView epsText = (TextView) findViewById(R.id.epsText);
            TextView dividendText = (TextView) findViewById(R.id.dividendText);
            TextView yieldText = (TextView) findViewById(R.id.yieldText);
            TextView peRatioText = (TextView) findViewById(R.id.peRatioText);
            TextView psRatioText = (TextView) findViewById(R.id.psRatioText);
            TextView pbRatioText = (TextView) findViewById(R.id.pbRatioText);

            stockNameText.setText(_stock.get_name());
            stockTickerText.setText(_stock.get_symbol());
            priceText.setText(_stock.get_price());
            changeText.setText(_stock.get_change());
            volumeText.setText(_stock.get_volume());
            dayHighText.setText(_stock.get_daysHigh());
            dayLowText.setText(_stock.get_daysLow());
            yearHighText.setText(_stock.get_yearHigh());
            yearLowText.setText(_stock.get_yearLow());
            marketCapText.setText(_stock.get_marketCapitalization());
            epsText.setText(_stock.get_earningsShare());
            dividendText.setText(_stock.get_dividendShare());
            yieldText.setText(_stock.get_dividendYield());
            peRatioText.setText(_stock.get_peRatio());
            psRatioText.setText(_stock.get_priceSales());
            pbRatioText.setText(_stock.get_priceBook());


        }

        private String replaceNull(String value) {
            String returnValue = value;
            if (value == null) {
                returnValue = "";
            }
            return returnValue;
        }

    }
}
