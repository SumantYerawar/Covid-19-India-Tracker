package com.sumant.covid_19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<States> coronaItemArrayList;

    RecyclerViewAdapter(Context context, ArrayList<States> coronaItemArrayList) {
        this.context = context;
        this.coronaItemArrayList = coronaItemArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        States coronaItem = coronaItemArrayList.get(position);
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
        return coronaItemArrayList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView state, confirm, active, recovered, death;
        RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.stateName);
            confirm = itemView.findViewById(R.id.confirmNo);
            active = itemView.findViewById(R.id.activeNo);
            recovered = itemView.findViewById(R.id.recoveredNo);
            death = itemView.findViewById(R.id.deathNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),StatesActivity.class);
                    intent.putExtra("state",state.getText().toString());
                    intent.putExtra("confirm",confirm.getText().toString());
                    intent.putExtra("recovered",recovered.getText().toString());
                    intent.putExtra("death",death.getText().toString());
                    itemView.getContext().startActivity(intent);

                }
            });
        }
    }
}