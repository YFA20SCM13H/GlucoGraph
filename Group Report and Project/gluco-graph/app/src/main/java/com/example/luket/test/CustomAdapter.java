package com.example.luket.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DictObjectModel> dataSet;
    Boolean check=false;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView FoodName, Fat, Energy, TotalSugar, Glucose;

        RelativeLayout expandable;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.expandable= (RelativeLayout)itemView.findViewById(R.id.expandableLayout);
            this.FoodName= (TextView)itemView.findViewById(R.id.wordtext);
            this.Fat = (TextView) itemView.findViewById(R.id.fat);
            this.Energy = (TextView) itemView.findViewById(R.id.energy);
            this.TotalSugar = (TextView) itemView.findViewById(R.id.totalsugars);
            this.Glucose = (TextView) itemView.findViewById(R.id.glucose);
        }
    }

    public CustomAdapter(ArrayList<DictObjectModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check)
                {
                    myViewHolder.expandable.animate()
                            .alpha(0.0f)
                            .setDuration(1000);


                    myViewHolder.expandable.setVisibility(View.GONE);
                    check=true;
                    //  myViewHolder.schedule.setVisibility(View.VISIBLE);

                }
                else {
                    myViewHolder.expandable.setVisibility(View.VISIBLE);
                    myViewHolder.expandable.animate()
                            .alpha(1.0f)
                            .setDuration(1000);

                    check=false;

                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView FoodName1= holder.FoodName;
        TextView Fat1 = holder.Fat;
        TextView Energy1 = holder.Energy;
        TextView Totalsugars = holder.TotalSugar;
        TextView Glucose = holder.Glucose;

        FoodName1.setText(dataSet.get(listPosition).getFoodName());
        Fat1.setText(dataSet.get(listPosition).getFat());
        Energy1.setText(dataSet.get(listPosition).getEnergy());
        Totalsugars.setText(dataSet.get(listPosition).getTotalSugar());
        Glucose.setText(dataSet.get(listPosition).getGlucose());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}