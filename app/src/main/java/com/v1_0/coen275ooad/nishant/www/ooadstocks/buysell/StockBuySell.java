package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

/**
 * Created by nishant on 12/3/16.
 */
public class StockBuySell {

    private String StockName;
    private String StockPrice;


    public StockBuySell (String name, String price) {
        this.StockName = name;
        this.StockPrice = price;

    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(String stockPrice) {
        StockPrice = stockPrice;
    }


}
