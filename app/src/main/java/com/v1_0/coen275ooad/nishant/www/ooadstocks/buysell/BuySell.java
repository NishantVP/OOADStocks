package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

/**
 * Created by nishant on 12/3/16.
 */
public class BuySell {

    private String stockName;
    private int userId;
    private String userName;
    private double unitPrice;
    private int quantity;
    private boolean buySell; //buy = true , sell = false

    public BuySell(String sn,int id, String un, double up, int qty, boolean bS) {
        stockName = sn;
        userId = id;
        userName = un;
        unitPrice = up;
        quantity = qty;
        buySell = bS;
    }

    public String getStockName() {
        return stockName;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public double getUnitPrice() {
        return unitPrice;

    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isBuy() {
        return buySell;
    }

    public boolean isSell() {
        return !buySell;
    }

}
