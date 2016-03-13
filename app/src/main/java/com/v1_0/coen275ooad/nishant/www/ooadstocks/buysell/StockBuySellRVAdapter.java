package com.v1_0.coen275ooad.nishant.www.ooadstocks.buysell;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.v1_0.coen275ooad.nishant.www.ooadstocks.R;
import com.v1_0.coen275ooad.nishant.www.ooadstocks.user.Stock;

import java.util.List;

/**
 * Created by nishant on 12/3/16.
 */
public class StockBuySellRVAdapter extends RecyclerView.Adapter<StockBuySellRVAdapter.StockBuySellViewHolder> {


    List<StockBuySell> stockBuySellList;


    StockBuySellRVAdapter(List<StockBuySell> StockBuySell){
        System.out.println("Inside Constructor");
        this.stockBuySellList = StockBuySell;

    }

    @Override
    public StockBuySellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        System.out.println("Inside Getting stockcarditem");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stockcarditem, parent, false);
        StockBuySellViewHolder svh = new StockBuySellViewHolder(v);

        return svh;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final StockBuySellViewHolder holder, int position) {

        System.out.println("Inside onBindView");

        holder.stockNameTV.setText(stockBuySellList.get(position).getStockName());
        //holder.stockPriceTV.setText(stockBuySellList.get(position).getStockPrice());
        holder.unlockButton.setEnabled(false);

        final String Stock = stockBuySellList.get(position).getStockName();

        holder.lockButton.setOnClickListener(new View.OnClickListener() {
            String ProposedPrice;
            String Quantity;
            String BuySell;
            @Override
            public void onClick(View v) {
                /// button click event
                ProposedPrice = holder.proposedPriceET.getText().toString();
                Quantity = holder.quantityET.getText().toString();
                if(holder.buyRB.isChecked()) {
                    BuySell = "buy";
                }
                else {
                    BuySell = "sell";
                }

                if(ProposedPrice.equals("") || Quantity.equals("")) {
                    holder.statusUpdateTV.setText("Enter Price and Quantity");
                }
                else {
                    LockedBuySellReq.AddRequest(Stock,Quantity,ProposedPrice,BuySell);
                    holder.unlockButton.setEnabled(true);
                    holder.lockButton.setEnabled(false);

                    holder.buyRB.setEnabled(false);
                    holder.sellRB.setEnabled(false);
                    holder.proposedPriceET.setEnabled(false);
                    holder.quantityET.setEnabled(false);

                    holder.statusUpdateTV.setText("Your Request is Locked");
                }
            }
        });

        holder.unlockButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /// button click event
                LockedBuySellReq.RemoveRequest(Stock);
                holder.lockButton.setEnabled(true);
                holder.unlockButton.setEnabled(false);

                holder.buyRB.setEnabled(true);
                holder.sellRB.setEnabled(true);
                holder.proposedPriceET.setEnabled(true);
                holder.quantityET.setEnabled(true);

                holder.statusUpdateTV.setText("Press Lock after editing..");
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockBuySellList.size();
    }

    public static class StockBuySellViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView stockNameTV;
        TextView stockPriceTV;
        TextView statusUpdateTV;
        RadioButton buyRB;
        RadioButton sellRB;
        RadioGroup buySellRG;
        Button lockButton;
        Button unlockButton;
        EditText proposedPriceET;
        EditText quantityET;

        StockBuySellViewHolder(View itemView) {
            super(itemView);

            System.out.println("Inside Getting all views");

            cv = (CardView)itemView.findViewById(R.id.cvStockBuySell);
            stockNameTV = (TextView)itemView.findViewById(R.id.stock_name);
            stockPriceTV = (TextView)itemView.findViewById(R.id.stock_price);
            statusUpdateTV = (TextView)itemView.findViewById(R.id.statusUpdate);
            buyRB = (RadioButton)itemView.findViewById(R.id.buyRB);
            sellRB = (RadioButton)itemView.findViewById(R.id.sellRB);
            buySellRG = (RadioGroup)itemView.findViewById(R.id.buysellRadioGroup);
            lockButton = (Button)itemView.findViewById(R.id.lockButton);
            unlockButton = (Button)itemView.findViewById(R.id.unlockButton);
            proposedPriceET = (EditText)itemView.findViewById(R.id.proposedPrice);
            quantityET = (EditText)itemView.findViewById(R.id.proposedQuantity);

        }
    }

}



