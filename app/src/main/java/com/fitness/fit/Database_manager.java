package com.fitness.fit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Database_manager {
    private DatabaseHelper dbhelper;
    private Context context;
    private SQLiteDatabase database;

    public Database_manager(Context context) {
        this.context = context;
    }

    public Database_manager open() throws SQLDataException {
        dbhelper = new DatabaseHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public void insert(String weight, String height){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c.getTime());
        String water = "0";
        String exc = "0";

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_1, weight);
        contentValues.put(DatabaseHelper.COL_2,height);
        contentValues.put(DatabaseHelper.COL_4,formattedDate);
        contentValues.put(DatabaseHelper.COL_5,water);
        contentValues.put(DatabaseHelper.COL_6,exc);

        Log.d("Weight",weight);
        Log.d("height",height);
        Log.d("Fetch", String.valueOf(fetch()));
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }
    public Cursor fetch(){
        String [] columns = new String[] {DatabaseHelper.COL_1,DatabaseHelper.COL_2,DatabaseHelper.COL_3,DatabaseHelper.COL_4};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,columns,null,null,null,null,null);
        if(cursor != null)
            cursor.moveToNext();
        return cursor;
    }
    public ArrayList<String> fetch_date(){
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = database.rawQuery( "select  max(day), date, water, exc  from "+DatabaseHelper.TABLE_NAME, null );
        res.moveToFirst();
        array_list.add(res.getString(0));
        array_list.add(res.getString(1));
        array_list.add(res.getString(2));
        array_list.add(res.getString(3));
        Log.d("Log_max date", String.valueOf(array_list));
        return array_list;
    }

    public int update(String weight, String height, long count){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_1, weight);
        contentValues.put(DatabaseHelper.COL_2,height);
        int ret = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.COL_3 + "=" + count, null);
        return ret;
    }

    public int update_real_water(String water,String count){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_5, water);
        int ret = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.COL_3 + "=" + count, null);
        return ret;
    }
    public int update_real_exc(String water, String exc,String count){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_5, water);
        contentValues.put(DatabaseHelper.COL_6,exc);
        int ret = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.COL_3 + "=" + count, null);
        return ret;
    }

    public void delete (long count)
    {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_3 + "=" + count, null );
    }
    public void drop()
    {
        database.execSQL("DROP TABLE IF EXISTS "+ DatabaseHelper.TABLE_NAME);
    }
}
