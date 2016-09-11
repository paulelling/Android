package com.pelling.stockportfolio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "portfolioDB.db";
    public static final String TABLE_STOCK = "stock";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CHANGE = "change";
    public static final String COLUMN_VOLUME = "volume";
    public static final String COLUMN_DAYSHIGH = "dayshigh";
    public static final String COLUMN_DAYSLOW = "dayslow";
    public static final String COLUMN_YEARHIGH = "yearhigh";
    public static final String COLUMN_YEARLOW = "yearlow";
    public static final String COLUMN_MARKETCAPITALIZATION = "marketcapitalization";
    public static final String COLUMN_EARNINGSSHARE = "earningsshare";
    public static final String COLUMN_DIVIDENDSHARE = "dividendshare";
    public static final String COLUMN_DIVIDENDYIELD = "dividendyield";
    public static final String COLUMN_PERATIO = "peratio";
    public static final String COLUMN_PSRATIO = "psratio";
    public static final String COLUMN_PBRATIO = "pbratio";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_STOCK + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(50), " +
                COLUMN_SYMBOL + " VARCHAR(10), " +
                COLUMN_PRICE + " VARCHAR(15), " +
                COLUMN_CHANGE + " VARCHAR(15), " +
                COLUMN_VOLUME + " VARCHAR(15), " +
                COLUMN_DAYSHIGH + " VARCHAR(15), " +
                COLUMN_DAYSLOW + " VARCHAR(15), " +
                COLUMN_YEARHIGH + " VARCHAR(15), " +
                COLUMN_YEARLOW + " VARCHAR(15), " +
                COLUMN_MARKETCAPITALIZATION + " VARCHAR(15), " +
                COLUMN_EARNINGSSHARE + " VARCHAR(15), " +
                COLUMN_DIVIDENDSHARE + " VARCHAR(15), " +
                COLUMN_DIVIDENDYIELD + " VARCHAR(15), " +
                COLUMN_PERATIO + " VARCHAR(15), " +
                COLUMN_PSRATIO + " VARCHAR(15), " +
                COLUMN_PBRATIO + " VARCHAR(15) " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
        onCreate(db);
    }

    //Add a stock to the database
    public void addStock(Stock stock){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, stock.get_name());
        values.put(COLUMN_SYMBOL, stock.get_symbol());
        values.put(COLUMN_PRICE, stock.get_price());
        values.put(COLUMN_CHANGE, stock.get_change());
        values.put(COLUMN_VOLUME, stock.get_volume());
        values.put(COLUMN_DAYSHIGH, stock.get_daysHigh());
        values.put(COLUMN_DAYSLOW, stock.get_daysLow());
        values.put(COLUMN_YEARHIGH, stock.get_yearHigh());
        values.put(COLUMN_YEARLOW, stock.get_yearLow());
        values.put(COLUMN_MARKETCAPITALIZATION, stock.get_marketCapitalization());
        values.put(COLUMN_EARNINGSSHARE, stock.get_earningsShare());
        values.put(COLUMN_DIVIDENDSHARE, stock.get_dividendShare());
        values.put(COLUMN_DIVIDENDYIELD, stock.get_dividendYield());
        values.put(COLUMN_PERATIO, stock.get_peRatio());
        values.put(COLUMN_PSRATIO, stock.get_priceSales());
        values.put(COLUMN_PBRATIO, stock.get_priceBook());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_STOCK, null, values);
        db.close();
    }

    //Get a list of all stocks
    public ArrayList<String> getAllStocks() {
        ArrayList<String> stockList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STOCK + " ORDER BY " + COLUMN_SYMBOL + " ASC";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            String stock = c.getString(c.getColumnIndex("symbol"));

            if (stock != null) {
                stockList.add(stock);
            }

            c.moveToNext();
        }
        db.close();

        return stockList;
    }

    //Get a stock by ticker
    public Stock getStock(String stockTicker) {
        Stock stock = new Stock();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STOCK + " WHERE " + COLUMN_SYMBOL + "  = '" + stockTicker + "'";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("symbol")) != null) {
                stock.set_name(c.getString(c.getColumnIndex("name")));
                stock.set_symbol(c.getString(c.getColumnIndex("symbol")));
                stock.set_name(c.getString(c.getColumnIndex("name")));
                stock.set_price(c.getString(c.getColumnIndex("price")));
                stock.set_change(c.getString(c.getColumnIndex("change")));
                stock.set_volume(c.getString(c.getColumnIndex("volume")));
                stock.set_daysHigh(c.getString(c.getColumnIndex("dayshigh")));
                stock.set_daysLow(c.getString(c.getColumnIndex("dayslow")));
                stock.set_yearHigh(c.getString(c.getColumnIndex("yearhigh")));
                stock.set_yearLow(c.getString(c.getColumnIndex("yearlow")));
                stock.set_marketCapitalization(c.getString(c.getColumnIndex("marketcapitalization")));
                stock.set_earningsShare(c.getString(c.getColumnIndex("earningsshare")));
                stock.set_dividendShare(c.getString(c.getColumnIndex("dividendshare")));
                stock.set_dividendYield(c.getString(c.getColumnIndex("dividendyield")));
                stock.set_peRatio(c.getString(c.getColumnIndex("peratio")));
                stock.set_priceSales(c.getString(c.getColumnIndex("psratio")));
                stock.set_priceBook(c.getString(c.getColumnIndex("pbratio")));
            }

            c.moveToNext();
        }
        db.close();

        return stock;
    }

    //Delete a stock by ticker
    public void deleteStock(String stockTicker) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_STOCK, COLUMN_SYMBOL + " = '" + stockTicker + "'", null);
        db.close();
    }
}
