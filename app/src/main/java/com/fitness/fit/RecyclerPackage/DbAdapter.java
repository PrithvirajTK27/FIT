package com.fitness.fit.RecyclerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.fit.PlanView_r;
import com.fitness.fit.R;

import java.util.ArrayList;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.DbViewHolder> {

    ArrayList<DbModelClass> objDbmodelClassArrayList;

    public DbAdapter(ArrayList<DbModelClass> objDbmodelClassArrayList) {
        this.objDbmodelClassArrayList = objDbmodelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View singleRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_row,viewGroup,false);
        return new DbViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolder dbViewHolder, int i) {
        DbModelClass objDbModelClass = objDbmodelClassArrayList.get(i);
        dbViewHolder.planDesTV.setText(objDbModelClass.getPlanDes());
        dbViewHolder.D_forTV.setText(objDbModelClass.getD_for());
        dbViewHolder.D_typeTV.setText(objDbModelClass.getD_type());
    }

    @Override
    public int getItemCount() {
        return objDbmodelClassArrayList.size();
    }

    public static class DbViewHolder extends RecyclerView.ViewHolder{
        TextView planDesTV;
        TextView D_forTV;
        TextView D_typeTV;
        public DbViewHolder(@NonNull View itemView) {
            super(itemView);
            planDesTV = itemView.findViewById(R.id.sr_des);
            D_forTV = itemView.findViewById(R.id.sr_type);
            D_typeTV = itemView.findViewById(R.id.sr_v_nv);
        }
    }
}
