package com.allureinfosystems.emi_calculator;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.ArrayList;
        import java.util.HashMap;


public class FdDataAdapter extends RecyclerView.Adapter<FdDataAdapter.MyViewHolder> {
    private ArrayList<? extends HashMap<String, String>> fdDataStat;

    public FdDataAdapter(ArrayList<? extends HashMap<String, String>> fdData) {
        this.fdDataStat = fdData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fd_data, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        HashMap<String, String> StateDetails = fdDataStat.get(position);
        holder.fdDate.setText(StateDetails.get("fdDate"));
        holder.fdInterestAmount.setText(StateDetails.get("fdInterestAmount"));
        holder.fdCapitalizedInterest.setText(StateDetails.get("fdCaptilzedInterest"));
        holder.fdBalance.setText(StateDetails.get("fdBalance"));
    }


    @Override

    public int getItemCount() {
        return (fdDataStat == null) ? 0 : fdDataStat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fdDate;
        TextView fdInterestAmount;
        TextView fdBalance;
        TextView fdCapitalizedInterest;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fdDate = (TextView) itemView.findViewById(R.id.fdRecyclerInvestDate);
            fdInterestAmount = (TextView) itemView.findViewById(R.id.fdRecyclerInterestAmount);
            fdCapitalizedInterest = (TextView) itemView.findViewById(R.id.fdRecyclerCapitlizedInterest);
            fdBalance = (TextView) itemView.findViewById(R.id.fdRecyclerInterestBalance);
        }
    }
}
