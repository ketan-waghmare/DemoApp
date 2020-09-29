package com.example.demoapp.SQLiteDatabase;

/**
 * created by ketan 29-9-2020
 */
public class SQLiteQueries {

    /**
     * create category table query
     */
    public static final String QUERY_TBL_CATEGORIES = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_CATEGORIES + "("
            + DataBaseConstants.Constants_TBL_CATEGORIES.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.IS_DELETED + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE + " VARCHAR" + ");";

    /**
     * create patient table query
     */
    public static final String QUERY_TBl_PATIENTS = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_PATIENTS + "("
            + DataBaseConstants.Constants_TBL_PATIENTS.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.GENDER + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.PIN + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENTS.CREATE_DATE + " VARCHAR" + ");";

    /**
     * create patient problem table query
     */
    public static final String QUERY_TBl_PATIENT_PROBLEM = "create table IF NOT EXISTS "
            + DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS + "("
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.ID + " INTEGER primary key AUTOINCREMENT,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PATIENT_ID + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PROBLEM + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.IS_DELETED + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.STATUS + " VARCHAR,"
            + DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.CREATE_DATE + " VARCHAR" + ");";

}
