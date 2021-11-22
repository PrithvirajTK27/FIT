package com.fitness.fit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fitness.fit.RecyclerPackage.DbAdapter;
import com.fitness.fit.RecyclerPackage.DbAdapter_e;
import com.fitness.fit.RecyclerPackage.DbModelClass;
import com.fitness.fit.RecyclerPackage.DbModelClass_e;
import com.fitness.fit.SQLitePackage.MyDbClass;

import java.util.ArrayList;

public class PlanView_r extends AppCompatActivity {

    MyDbClass objMyDbClass;
    MyDbClass objMyDbClass_e;

    ArrayList<DbModelClass> objDbModelClassArrayList;
    ArrayList<DbModelClass_e> objDbModelClassArrayList_e;

    RecyclerView recyclerView;
    RecyclerView recyclerView_e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view_r);

        objMyDbClass = new MyDbClass(this);
        objDbModelClassArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.data_dietRV);

        objMyDbClass_e = new MyDbClass(this);
        objDbModelClassArrayList_e = new ArrayList<>();
        recyclerView_e = findViewById(R.id.data_exerciseRV);
    }
    public void showData(View view){

        try {
            objDbModelClassArrayList = objMyDbClass.getAllData();
            DbAdapter objDbAdapter = new DbAdapter(objDbModelClassArrayList);

            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(objDbAdapter);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"ShowData: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void showData_e(View view){

        try {
            objDbModelClassArrayList_e = objMyDbClass_e.getAllData_e();
            DbAdapter_e objDbAdapter = new DbAdapter_e(objDbModelClassArrayList_e);

            recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(objDbAdapter);
        }
        catch(Exception e)
        {
            Toast.makeText(this,"ShowData: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}