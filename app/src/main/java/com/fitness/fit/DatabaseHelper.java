package com.fitness.fit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="user.db";
    public static final String TABLE_NAME="user_table";
    public static final String COL_1 = "weight";
    public static final String COL_2 = "height";
    public static final String COL_3 = "day";
    public static final String COL_4 = "date";
    public static final String COL_5 = "water";
    public static final String COL_6 = "exc";

    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE table " + TABLE_NAME + " (" + COL_1 + " TEXT, " + COL_2 + " TEXT, " + COL_3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_4 + " TEXT, " + COL_5 + " TEXT," + COL_6 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
