package com.pelling.stockportfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Analysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        String stockTicker = getIntent().getStringExtra("ticker");
        Database db = new Database(this, null, null, 1);
        Stock stock = db.getStock(stockTicker);

        TextView stockNameText = (TextView) findViewById(R.id.stockNameText);
        TextView stockTickerText = (TextView) findViewById(R.id.stockTickerText);

        stockNameText.setText(stock.get_name());
        stockTickerText.setText(stock.get_symbol());

        analyzeStock(stock);
    }

    //open portfolio
    public void portfolioButtonClicked(View view) {
        Intent i = new Intent(Analysis.this, Portfolio.class);
        startActivity(i);
    }

    private void analyzeStock(Stock stock) {
        String analysis = "";

        Double psRatio = Double.parseDouble(stock.get_priceSales());
        Double pbRatio = Double.parseDouble(stock.get_priceBook());

        analysis += "\n" + analyzePrice(stock) + "\n";
        analysis += "\n" + analyzeEps(stock) + "\n";
        analysis += "\n" + analyzePeRatio(stock) + "\n";
        analysis += "\n" + analyzePsRatio(stock) + "\n";
        analysis += "\n" + analyzePbRatio(stock) + "\n";

        TextView analysisText = (TextView) findViewById(R.id.analysisText);
        analysisText.setText(analysis);
    }

    private String analyzePbRatio(Stock stock) {
        String analysis = "";
        Double pbRatio = Double.parseDouble(stock.get_priceBook());

        analysis += "P/B Ratio is " + pbRatio.toString() + ". ";
        analysis += "A low P/B Ratio could mean that the company is undervalued. ";
        analysis += "It could also mean that there is something fundamentally wrong with the company's ability to create value for shareholders.";

        return analysis;
    }

    private String analyzePsRatio(Stock stock) {
        String analysis = "";
        Double psRatio = Double.parseDouble(stock.get_priceSales());

        analysis += "P/S Ratio is " + psRatio.toString() + ". ";
        analysis += "A low P/S Ratio could mean that the stock provides good value for the price.";

        return analysis;
    }

    private String analyzePeRatio(Stock stock) {
        String analysis = "";
        Double peRatio = Double.parseDouble(stock.get_peRatio());

        analysis += "P/E Ratio is " + peRatio.toString() + ". ";
        analysis += "A high P/E Ratio could mean that the company has significant prospects for future growth. ";
        analysis += "It could also mean that investors are over-paying for the stock.";

        return analysis;
    }

    private String analyzeEps(Stock stock) {
        String analysis = "";
        Double eps = Double.parseDouble(stock.get_earningsShare());

        analysis += "Earnings Per Share is " + eps.toString() + ". EPS is a measure of profitability. ";
        analysis += "A high EPS means the company can pay more earnings to shareholders or reinvest earnings in the business.";

        return analysis;
    }

    private String analyzePrice(Stock stock) {
        String analysis = "";
        Double price = Double.parseDouble(stock.get_price());
        Double yearHigh = Double.parseDouble(stock.get_yearHigh());
        Double yearLow = Double.parseDouble(stock.get_yearLow());

        Double differenceYearHighPrice = yearHigh - price;
        Double differencePriceYearLow = price - yearLow;

        if (differenceYearHighPrice > differencePriceYearLow) {
            analysis += "Price of " + price.toString() + " is closer to 52-Week Low of " + yearLow.toString() + " than 52-Week High of " + yearHigh.toString() + ".";
        }
        else if (differencePriceYearLow > differenceYearHighPrice) {
            analysis += "Price of " + price.toString() + " is closer to 52-Week High of " + yearHigh.toString() + " than 52-Week Low of " + yearLow.toString() + ".";
        }

        return analysis;
    }
}
