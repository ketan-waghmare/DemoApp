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

/**
 * created by ketan 29-9-2020
 */
public class DataBaseHelper {

    public Context context;
    private SQLiteHelper sqLiteHelper;
    public static SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(Context context) {
        this.context = context;
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    /**
     * store the data to the database
     * insertion operation is performed using this function
     */
    public Long saveToLocalTable(String table, ContentValues contentValues) {
        long count = 0;
        try {
            count = sqLiteDatabase.insert(table, null, contentValues);

            if (count != -1) {
                Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Data not stored properly", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * this function is used to get the category list from the database
     * returns json array of category list
     */
    public JSONArray getCategoriesFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N'", null);

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

    /**
     * this function is used to get the patient list from the database
     * returns json array of patient list
     */
    public JSONArray getPatientListFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
        Log.e("query_log", "SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N'", null);
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

    /**
     * this function is used to verify that user is exist in the databse or not
     * if user exists returns json array of user information
     * else returns the empty array of the user information
     */
    public JSONArray verifyPin(String mobile_number, String pin) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER + " ='" + mobile_number + "' AND pin = '" + pin + "' ORDER BY id ASC LIMIT 1", null);

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

    /**
     * this function is used to update the data of database
     * we can update any tables data using this function
     * just pass the parameter tablename values to update and id of the record to update
     */
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

    /**
     * this function is used to get the data of category by passing id of the category
     * returns the single record of the category
     */
    public JSONArray getCategoryByID(String id) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " ='" + id + "'", null);

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

    /**
     * this function returns the single record of patient against the selected id
     */
    public JSONArray getPatientByID(String id) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' ANd " + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " ='" + id + "'", null);

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

    /**
     * this function returns json array of patient problems
     */
    public JSONArray getProblemFromDB() {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS + "` Where is_deleted = 'N'", null);

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

    /**
     * get the filetered list of patients
     */
    public JSONArray getFilteredList(String firstName, String createdDate, String dateOfBirth) {
        Cursor cursor = null;
        JSONArray jArray = new JSONArray();

        cursor = sqLiteDatabase.rawQuery("SELECT * from `" + DataBaseConstants.TableNames.TBL_PATIENTS + "` Where is_deleted = 'N' AND " + DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME + " LIKE '%" + firstName + "%' AND "
                + DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH + " LIKE '%" + dateOfBirth + "%' AND " + DataBaseConstants.Constants_TBL_PATIENTS.CREATE_DATE + " LIKE '%" + createdDate + "%'", null);

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