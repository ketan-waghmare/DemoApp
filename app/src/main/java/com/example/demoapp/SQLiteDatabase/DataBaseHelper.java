package com.example.demoapp.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBaseHelper {
    public static SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper sqLiteHelper;
    public Context context;

    private JSONArray jArray;
    private JSONObject json_data;
    private boolean isExist = false;
    private String str_column_name;

    public DataBaseHelper(Context context) {
        this.context = context;
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    public Long saveToLocalTable(String table, ContentValues contentValues) {
        long count = 0;
        try {
            count = sqLiteDatabase.insert(table, null, contentValues);

            if (count != -1) {
                Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show();
                Log.v("DataHelp_Log", "Insert " + table + " Details Successfully");
            } else {
                Toast.makeText(context, "Data not stored properly", Toast.LENGTH_SHORT).show();
                Log.v("DataHelp_Log", "Insert " + table + " Details Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updatePatientInfo(String table, ContentValues contentValues, String patient_id) {
        try {
            long count1 = sqLiteDatabase.update(table, contentValues, "patient_id ='" + patient_id + "' ", null);

            if (count1 != -1) {
                Log.v("DataHelp", "Update " + table + " Details Successfully");
            } else {
                Log.v("DataHelp", "Update " + table + " Details Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateParametersInfo(String table, ContentValues contentValues, String parameter_id) {
        try {
            long count1 = sqLiteDatabase.update(table, contentValues, "parameter_id ='" + parameter_id + "' ", null);

            if (count1 != -1) {
                Log.v("DataHelpUpdate", "Update " + table + " Details Successfully");
            } else {
                Log.v("DataHelpUpdate", "Update " + table + " Details Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTable_data(String tbl_name, String key, String ids) {

        sqLiteDatabase.execSQL("delete from " + tbl_name + " WHERE  id =" + ids);

    }

    public JSONArray getCategoriesFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from " + DataBaseConstants.TableNames.TBL_CATEGORIES , null);
        JSONObject json = null;

        if (cursor.getCount() != 0) {
            try {
                while (cursor.moveToNext()) {
                    json = new JSONObject();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        json.put(cursor.getColumnName(i), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(i))));
                    }
                    jArray.put(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jArray;
    }

}