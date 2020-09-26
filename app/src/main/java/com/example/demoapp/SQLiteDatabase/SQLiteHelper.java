package com.example.demoapp.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteQueries.QUERY_TBl_PATIENTS);
        db.execSQL(SQLiteQueries.QUERY_TBL_CATEGORIES);
        db.execSQL(SQLiteQueries.QUERY_TBl_PATIENT_PROBLEM);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA automatic_index = off;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS);

        onCreate(db);
    }
}