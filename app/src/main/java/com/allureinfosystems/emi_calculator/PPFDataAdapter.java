package com.allureinfosystems.emi_calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class PPFDataAdapter extends RecyclerView.Adapter<PPFDataAdapter.MyViewHolder> {
    private ArrayList<? extends HashMap<String, String>> rdDataStat;

    public PPFDataAdapter(ArrayList<? extends HashMap<String, String>> rdData) {
        this.rdDataStat = rdData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ppf_data, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        HashMap<String, String> StateDetails = rdDataStat.get(position);
        holder.ppfDate.setText(StateDetails.get("Date"));
        holder.ppfinterestAmount.setText(StateDetails.get("interest Amount"));
        holder.ppfCapitalizedInterest.setText(StateDetails.get("captilzedInterest"));
        holder.ppfBalance.setText(StateDetails.get("RdBalance"));
    }


    @Override

    public int getItemCount() {
        return (rdDataStat == null) ? 0 : rdDataStat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ppfDate;
        TextView ppfinterestAmount;
        TextView ppfBalance;
        TextView ppfCapitalizedInterest;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ppfDate = (TextView) itemView.findViewById(R.id.ppfRecyclerInvestDate);
            ppfinterestAmount = (TextView) itemView.findViewById(R.id.ppfRecyclerInterestAmount);
            ppfCapitalizedInterest = (TextView) itemView.findViewById(R.id.ppfRecyclerCapitlizedInterest);
            ppfBalance = (TextView) itemView.findViewById(R.id.ppfRecyclerInterestBalance);
        }
    }
}

