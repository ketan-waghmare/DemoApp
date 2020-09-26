package com.example.demoapp.SQLiteDatabase;

public class SQLiteQueries {

    public static final String QUERY_TBL_CATEGORIES = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_CATEGORIES + "("
            + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE + " VARCHAR" + ");";

    public static final String QUERY_TBl_PATIENTS = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_PATIENTS + "("
            + DataBaseConstants.Constants_TBL_PATIENTS.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.GENDER + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.PIN + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.CREATE_DATE + " VARCHAR" + ");";

    public static final String QUERY_TBl_PATIENT_PROBLEM = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS + "("
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PATIENT_ID + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PROBLEM + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.CREATE_DATE + " VARCHAR" + ");";


    public static final String QUERY_GET_CART_ITEMS_LIST = "SELECT * from `"
            + DataBaseConstants.TableNames.TBL_CATEGORIES + "` Where is_deleted = 'N'";

}
