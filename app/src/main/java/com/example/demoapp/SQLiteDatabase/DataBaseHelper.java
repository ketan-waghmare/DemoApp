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
    public Context context;
    private SQLiteHelper sqLiteHelper;
    public static SQLiteDatabase sqLiteDatabase;

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
            long count1 = sqLiteDatabase.update(table, contentValues, "id ='" + patient_id + "' ", null);

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

    public void deleteData(String tbl_name, String ids) {
        sqLiteDatabase.execSQL("delete from " + tbl_name + " WHERE  id =" + ids);
        Log.e("deleteQuery"," = "+"delete from " + tbl_name + " WHERE  id =" + ids);
    }

    public JSONArray getCategoriesFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N'", null);
        Log.e("query_log","SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N'", null);
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

    public JSONArray getPatientListFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
        Log.e("query_log","SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
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

    public JSONArray verifyPin(String mobile_number, String pin) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER + " ='" + mobile_number + "' AND pin = '" + pin + "' ORDER BY id ASC LIMIT 1", null);
        Log.e("verifypin_query", "SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER + " ='" + mobile_number + "' AND pin = '" + pin + "' ORDER BY id ACS LIMIT 1");

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

    public long updateTableData(String table, ContentValues contentValues, String id) {
        long count1 = 0;
        try {
            count1 = sqLiteDatabase.update(table, contentValues, "id ='" + id + "' ", null);

            if (count1 != -1) {
                Log.v("DataHelp", "Update " + table + " Details Successfully");
            } else {
                Log.v("DataHelp", "Update " + table + " Details Fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count1;
    }


    public JSONArray getCategoryByID(String id){
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " ='" + id + "'", null);
        Log.e("getCategoryToEdit_query", "SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.ID + " ='" + id + "'");

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

    public JSONArray getPatientByID(String id){
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " ='" + id + "'", null);
        Log.e("getCategoryToEdit_query", "SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.ID + " ='" + id + "'");

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

    public JSONArray getProblemFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS + "` Where is_deleted = 'N'", null);
        Log.e("query_log","SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS + "` Where is_deleted = 'N'", null);
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