package com.example.demoapp.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * created by ketan 29-9-2020
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
    }

    /**
     * create table querys are executed here
     * new tables are created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteQueries.QUERY_TBl_PATIENTS);
        db.execSQL(SQLiteQueries.QUERY_TBL_CATEGORIES);
        db.execSQL(SQLiteQueries.QUERY_TBl_PATIENT_PROBLEM);

    }

    /**
     * opens the databse connection
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA automatic_index = off;");
        }
    }

    /**
     * update the tables
     * if tables are present then delete and create new tables
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS);

        onCreate(db);
    }
}