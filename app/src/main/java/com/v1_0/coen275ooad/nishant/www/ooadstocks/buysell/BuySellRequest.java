package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

/**
 * Created by nishant on 12/3/16.
 */
public class BuySellRequest {

    private String StockName;
    private String Quantity;
    private String Price;
    private String BuySell;

    BuySellRequest (String name, String quantity, String price, String buySell) {
        this.StockName = name;
        this.Quantity = quantity;
        this.Price = price;
        this.BuySell = buySell;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBuySell() {
        return BuySell;
    }

    public void setBuySell(String buySell) {
        BuySell = buySell;
    }
}
