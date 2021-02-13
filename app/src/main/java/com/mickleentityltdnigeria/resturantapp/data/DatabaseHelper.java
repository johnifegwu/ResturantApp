package com.mickleentityltdnigeria.resturantapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    static final String TABLE_NAME = "USERS";
    static final String User_ID = "_ID";
    static final String USERNAME = "UserName";
    static final String PASSWORD = "Password";
    private static final String DB_NAME = "BON_APPETIT.DB";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + User_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT NOT NULL, " + PASSWORD + " TEXT);";


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
