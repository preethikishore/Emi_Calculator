package com.allureinfosystems.emi_calculator;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RdDataAdapter extends RecyclerView.Adapter<RdDataAdapter.MyViewHolder> {
    private ArrayList<? extends HashMap<String, String>> rdDataStat;

    public RdDataAdapter(ArrayList<? extends HashMap<String, String>> rdData) {
        this.rdDataStat = rdData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rd_data, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        HashMap<String, String> StateDetails = rdDataStat.get(position);
        holder.rdDate.setText(StateDetails.get("Date"));
        holder.interestAmount.setText(StateDetails.get("interest Amount"));
        holder.rdCapitalizedInterest.setText(StateDetails.get("captilzedInterest"));
        holder.RdBalance.setText(StateDetails.get("RdBalance"));
    }


    @Override

    public int getItemCount() {
        return (rdDataStat == null) ? 0 : rdDataStat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rdDate;
        public TextView interestAmount;
        public TextView RdBalance;
        public TextView rdCapitalizedInterest;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rdDate = (TextView) itemView.findViewById(R.id.rdRecyclerInputAmount);
            interestAmount = (TextView) itemView.findViewById(R.id.rdRecyclerInterestAmount);
            rdCapitalizedInterest = (TextView) itemView.findViewById(R.id.rdRecyclerCapitlizedInterest);
            RdBalance = (TextView) itemView.findViewById(R.id.rdRecyclerInterestBalance);
        }
    }
}






















