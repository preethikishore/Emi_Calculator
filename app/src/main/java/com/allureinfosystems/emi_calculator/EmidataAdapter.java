package com.allureinfosystems.emi_calculator;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class EmidataAdapter extends RecyclerView.Adapter<EmidataAdapter.MyViewHolder> {


    private ArrayList<? extends HashMap<String, String>> emidataDataset;

    public EmidataAdapter(ArrayList<? extends HashMap<String, String>> emidataDataset) {

        this.emidataDataset = emidataDataset;

    }

    @NonNull
    @Override
    public EmidataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emidata, parent, false);
        EmidataAdapter.MyViewHolder vh = new EmidataAdapter.MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final EmidataAdapter.MyViewHolder holder, int position) {
        HashMap<String, String> emiDetails = emidataDataset.get(position);

        holder.period.setText(emiDetails.get("period"));
        holder.principlePaid.setText(emiDetails.get("principlePaid"));
        holder.interestCalc.setText(emiDetails.get("interestCalc"));
        holder.principleRemains.setText(emiDetails.get("principleRemain"));

    }


    @Override
    public int getItemCount() {
        return (emidataDataset == null) ? 0 : emidataDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView period;
        public TextView principlePaid;
        public TextView interestCalc;
        public TextView principleRemains;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            period = (TextView) itemView.findViewById(R.id.recyclerPeriod);
            principlePaid = (TextView) itemView.findViewById(R.id.recyclerPrinciplePaid);
            interestCalc = (TextView) itemView.findViewById(R.id.recyclerinterestCalc);
            principleRemains = (TextView) itemView.findViewById(R.id.recyclerprincipleRemains);

        }


    }
}





