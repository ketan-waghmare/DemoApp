package com.example.demoapp.SQLiteDatabase;

/**
 * Class Name        :  <b>DataBaseConstants.java<b/>
 * Purpose           :  DataBaseConstants is class related of constants.
 * Developed By      :  <b>@ketan waghmare</b>
 * Created Date      :  <b>24-08-2020</b>
 */


public class DataBaseConstants {

    public static final String DATABASE_NAME = "demo_database";
    public static final int DATABASE_VERSION = 1;

    public static class TableNames {
        public static final String TBL_PATIENTS = "patients";
        public static final String TBL_CATEGORIES = "categories";
        public static final String TBL_PATIENT_PROBLEMS = "patient_problems";
    }

    public static class Constants_TBL_PATIENTS {
        public static final String ID = "id";
        public static final String PIN = "pin";
        public static final String GENDER = "gender";
        public static final String STATUS = "status";
        public static final String LAST_NAME = "last_name";
        public static final String MOBILE_NUMBER = "mobile_number";
        public static final String IS_DELETED = "is_deleted";
        public static final String FIRST_NAME = "first_name";
        public static final String BLOOD_GROUP = "blood_group";
        public static final String CREATE_DATE = "create_date";
        public static final String DATE_OF_BIRTH = "date_of_birth";
    }

    public static class Constants_TBL_CATEGORIES {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String IS_DELETED = "is_deleted";
        public static final String CREATE_DATE = "create_date";
    }

    public static class Constants_TBL_PATIENT_PROBLEMS {
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String PROBLEM = "problem";
        public static final String IS_DELETED = "is_deleted";
        public static final String PATIENT_ID = "patient_id";
        public static final String CREATE_DATE = "create_date";
    }

}