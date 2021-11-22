package com.fitness.fit.SQLitePackage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fitness.fit.HomePackage.DBModelPlan;
import com.fitness.fit.RecyclerPackage.DbModelClass;
import com.fitness.fit.RecyclerPackage.DbModelClass_e;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDbClass extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="fit.db";
    private static final int DATABASE_VERSION=1;
    private static String eid, did;
    public MyDbClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    Context context;
    public ArrayList<DbModelClass_e> getAllData_e()
    {
        try
        {
            ArrayList<DbModelClass_e> objDbModelClassArrayList = new ArrayList<>();
            SQLiteDatabase objSQLiteDatabase = getReadableDatabase();
            if(objSQLiteDatabase!=null)
            {
                Cursor objCursor = objSQLiteDatabase.rawQuery("select * from Exercise",null);
                if (objCursor.getCount()!=0){
                    while(objCursor.moveToNext())
                    {
                        String plan_e= objCursor.getString(1);
                        String E_for = objCursor.getString(0);
                        String E_duration = objCursor.getString(12);
                        objDbModelClassArrayList.add(
                                new DbModelClass_e(
                                        plan_e,E_for,E_duration
                                )
                        );
                    }
                    return objDbModelClassArrayList;
                }
                else{
                    Toast.makeText(context, "No data is retrieved..",Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            else
            {
                Toast.makeText(context, "Database is null",Toast.LENGTH_LONG).show();
                return null;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "GetAllData: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public ArrayList<DbModelClass> getAllData()
    {
        try
        {
            ArrayList<DbModelClass> objDbModelClassArrayList = new ArrayList<>();
            SQLiteDatabase objSQLiteDatabase = getReadableDatabase();
            if(objSQLiteDatabase!=null)
            {
                Cursor objCursor = objSQLiteDatabase.rawQuery("select * from Dietary",null);
                if (objCursor.getCount()!=0){
                    while(objCursor.moveToNext())
                    {
                        String planDes = objCursor.getString(2);
                        String D_for = objCursor.getString(1);
                        String D_type = objCursor.getString(0);
                        objDbModelClassArrayList.add(
                                new DbModelClass(
                                        planDes,D_for,D_type
                                )
                        );
                    }
                    return objDbModelClassArrayList;
                }
                else{
                    Toast.makeText(context, "No data is retrieved..",Toast.LENGTH_LONG).show();
                    return null;
                }
            }
            else
            {
                Toast.makeText(context, "Database is null",Toast.LENGTH_LONG).show();
                return null;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "GetAllData: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public ArrayList<DBModelPlan> getData(String eid, String did, String day_c, int op)
    {
        int food_col=2,n1_col=2,n2_col=2,n3_col=2;
        switch (op){
            case 1:
                food_col+=Integer.parseInt(day_c);
                n1_col+=Integer.parseInt(day_c)+10;
                n2_col+=Integer.parseInt(day_c)+20;
                n3_col+=Integer.parseInt(day_c)+30;
                break;
            case 2:
                food_col+=Integer.parseInt(day_c)+10;
                n1_col+=Integer.parseInt(day_c);
                n2_col+=Integer.parseInt(day_c)+20;
                n3_col+=Integer.parseInt(day_c)+30;
                break;
            case 3:
                food_col+=Integer.parseInt(day_c)+20;
                n1_col+=Integer.parseInt(day_c);
                n2_col+=Integer.parseInt(day_c)+10;
                n3_col+=Integer.parseInt(day_c)+30;
                break;
            case 4:
                food_col+=Integer.parseInt(day_c)+30;
                n1_col+=Integer.parseInt(day_c);
                n2_col+=Integer.parseInt(day_c)+10;
                n3_col+=Integer.parseInt(day_c)+20;
                break;
        }
        try
        {
            ArrayList<DBModelPlan> objDbModelClassArrayList = new ArrayList<>();
            SQLiteDatabase objSQLiteDatabase = getReadableDatabase();
            if(objSQLiteDatabase!=null)
            {
                Log.d("Dbclass Eplan", eid);
                Log.d("Dbclass Dplan", did);
                Cursor objCursor_E = objSQLiteDatabase.rawQuery("select * from Exercise  where Plan_no = ?" , new String[] {eid});
                Cursor objCursor_D = objSQLiteDatabase.rawQuery("select * from Dietary where Plan_no = ?", new String[] {did});
                if (objCursor_E.getCount()!=0){
                    while(objCursor_E.moveToNext() && objCursor_D.moveToNext()) {
                        String planDes = objCursor_D.getString(2);
                        String D_for = objCursor_D.getString(1);
                        String D_type = objCursor_D.getString(0);
                        String d_meal = objCursor_D.getString(food_col);
                        String plan_e = objCursor_E.getString(1);
                        String E_for = objCursor_E.getString(0);
                        String E_duration = objCursor_E.getString(12);
                        ArrayList<String> exec = new ArrayList<>();
                        for(int i=2;i<=11;i++)
                            exec.add(objCursor_E.getString(i));
                        String d_n1 = objCursor_D.getString(n1_col);
                        String d_n2 = objCursor_D.getString(n2_col);
                        String d_n3 = objCursor_D.getString(n3_col);
                        objDbModelClassArrayList.add(
                                new DBModelPlan(
                                        planDes, D_for, D_type, plan_e, E_for, E_duration,d_meal,d_n1,d_n2,d_n3,exec
                                )
                        );
                    }
                    Log.d("MYDBclass", String.valueOf(objDbModelClassArrayList));
                    return objDbModelClassArrayList;
                }
            }
            else
            {
                Toast.makeText(context, "Database is null",Toast.LENGTH_LONG).show();
                return null;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "GetAllData: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }
        return null;
    }
}
