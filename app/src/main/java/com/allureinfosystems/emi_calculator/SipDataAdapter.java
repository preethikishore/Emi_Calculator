package com.allureinfosystems.emi_calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;


public class SipDataAdapter  extends RecyclerView.Adapter<SipDataAdapter.MyViewHolder> {
    private ArrayList<? extends HashMap<String, String>> sipDataStat;

    public SipDataAdapter (ArrayList<? extends HashMap<String, String>> sipData) {
        this.sipDataStat = sipData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sip_data, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        HashMap<String, String> StateDetails = sipDataStat.get(position);
        holder.sipDate.setText(StateDetails.get("sipDate"));
        holder.sipInterestAmount.setText(StateDetails.get("sipInterestAmount"));
        holder.sipCapitalizedInterest.setText(StateDetails.get("sipInvestAmt"));
        holder.sipBalance.setText(StateDetails.get("sipBalance"));
    }


    @Override

    public int getItemCount() {
        return (sipDataStat == null) ? 0 : sipDataStat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sipDate;
        TextView sipInterestAmount;
        TextView sipBalance;
        TextView sipCapitalizedInterest;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sipDate = (TextView) itemView.findViewById(R.id.sipRecyclerInvestDate);
            sipInterestAmount = (TextView) itemView.findViewById(R.id.sipRecyclerInterestAmount);
            sipCapitalizedInterest = (TextView) itemView.findViewById(R.id.sipRecyclerInvestmentAmt);
            sipBalance = (TextView) itemView.findViewById(R.id.sipRecyclersipBalance);
        }
    }
}
