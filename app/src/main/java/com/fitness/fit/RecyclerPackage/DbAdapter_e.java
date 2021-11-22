package com.fitness.fit.RecyclerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitness.fit.R;

import java.util.ArrayList;

public class DbAdapter_e extends RecyclerView.Adapter<DbAdapter_e.DbViewHolder> {

    ArrayList<DbModelClass_e> objDbmodelClassArrayList;

    public DbAdapter_e(ArrayList<DbModelClass_e> objDbmodelClassArrayList) {
        this.objDbmodelClassArrayList = objDbmodelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View singleRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_row_exercise,viewGroup,false);
        return new DbViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolder dbViewHolder, int i) {
        DbModelClass_e objDbModelClass = objDbmodelClassArrayList.get(i);
        dbViewHolder.plan_e_TV.setText(objDbModelClass.getPlane());
        dbViewHolder.E_forTV.setText(objDbModelClass.getE_for());
        dbViewHolder.E_durationTV.setText(objDbModelClass.getE_duration());
    }

    @Override
    public int getItemCount() {
        return objDbmodelClassArrayList.size();
    }

    public static class DbViewHolder extends RecyclerView.ViewHolder{
        TextView plan_e_TV;
        TextView E_forTV;
        TextView E_durationTV;
        public DbViewHolder(@NonNull View itemView) {
            super(itemView);
            plan_e_TV = itemView.findViewById(R.id.sr_p_no);
            E_forTV = itemView.findViewById(R.id.sr_p_type);
            E_durationTV = itemView.findViewById(R.id.sr_duration);
        }
    }
}
