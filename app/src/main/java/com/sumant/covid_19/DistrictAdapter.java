package com.sumant.covid_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> {

    private Context context;
    private ArrayList<States> districtItemArrayList;

    public DistrictAdapter(Context context, ArrayList<States> districtItemArrayList) {
        this.context = context;
        this.districtItemArrayList = districtItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false);
        return new DistrictAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        States coronaItem = districtItemArrayList.get(position);
        String state = coronaItem.getStates();
        String confirm = coronaItem.getConfirm();
        String active = coronaItem.getActive();
        String recover = coronaItem.getRecover();
        String death = coronaItem.getDeath();

        holder.state.setText(state);
        holder.confirm.setText(confirm);
        holder.active.setText(active);
        holder.recovered.setText(recover);
        holder.death.setText(death);
    }

    @Override
    public int getItemCount() {
        return districtItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView state, confirm, active, recovered, death;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.stateName);
            confirm = itemView.findViewById(R.id.confirmNo);
            active = itemView.findViewById(R.id.activeNo);
            recovered = itemView.findViewById(R.id.recoveredNo);
            death = itemView.findViewById(R.id.deathNo);
        }
    }
}
