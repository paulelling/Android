package com.pelling.stockportfolio;

public class Stock {
    int _id;
    private String _price;
    private String _change;
    private String _daysHigh;
    private String _daysLow;
    private String _dividendShare;
    private String _dividendYield;
    private String _earningsShare;
    private String _marketCapitalization;
    private String _name;
    private String _peRatio;
    private String _priceBook;
    private String _priceSales;
    private String _symbol;
    private String _volume;
    private String _yearHigh;
    private String _yearLow;
    private String _analysis;

    public Stock() {
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_price(String _price) { this._price = _price; }

    public void set_change(String _change) { this._change = _change; }

    public void set_daysHigh(String _daysHigh) {
        this._daysHigh = _daysHigh;
    }

    public void set_daysLow(String _daysLow) {
        this._daysLow = _daysLow;
    }

    public void set_dividendShare(String _dividendShare) { this._dividendShare = _dividendShare; }

    public void set_dividendYield(String _dividendYield) { this._dividendYield = _dividendYield; }

    public void set_earningsShare(String _earningsShare) { this._earningsShare = _earningsShare; }

    public void set_marketCapitalization(String _marketCapitalization) { this._marketCapitalization = _marketCapitalization; }

    public void set_name(String _name) { this._name = _name; }

    public void set_peRatio(String _peRatio) { this._peRatio = _peRatio; }

    public void set_priceBook(String _priceBook) { this._priceBook = _priceBook; }

    public void set_priceSales(String _priceSales) { this._priceSales = _priceSales; }

    public void set_symbol(String _symbol) { this._symbol = _symbol; }

    public void set_volume(String _volume) { this._volume = _volume; }

    public void set_yearHigh(String _yearHigh) { this._yearHigh = _yearHigh; }

    public void set_yearLow(String _yearLow) { this._yearLow = _yearLow; }

    public void set_analysis(String _analysis) { this._analysis = _analysis; }

    public int get_id() {
        return _id;
    }

    public String get_price() { return _price; }

    public String get_change() { return _change; }

    public String get_daysHigh() { return _daysHigh; }

    public String get_daysLow() { return _daysLow; }

    public String get_dividendShare() { return _dividendShare; }

    public String get_dividendYield() { return _dividendYield; }

    public String get_earningsShare() { return _earningsShare; }

    public String get_marketCapitalization() { return _marketCapitalization; }

    public String get_name() { return _name; }

    public String get_peRatio() { return _peRatio; }

    public String get_priceBook() { return _priceBook; }

    public String get_priceSales() { return _priceSales; }

    public String get_symbol() { return _symbol; }

    public String get_volume() { return _volume; }

    public String get_yearHigh() { return _yearHigh; }

    public String get_yearLow() { return _yearLow; }

    public String get_analysis() { return _analysis; }
}
