package com.example.demoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.DTU;
import com.example.demoapp.Utils.Utils;

import java.net.Inet4Address;

/**
 * created by ketan 26-9-2020
 */
public class CreatePinActivity extends AppCompatActivity {

    //region variable
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private RadioButton rbGender;
    private RadioButton rbFemale;
    private EditText edtLastName;
    private Button btnCreateUser;
    private EditText edtEnterPin;
    private EditText edtFirstName;
    private Spinner spnBloodGroup;
    private EditText edtConfirmPin;
    private EditText edtDateOfBirth;
    private EditText edtMobileNumber;

    private int selectedGenderId;
    private DataBaseHelper dataBaseHelper;
    private Context context = CreatePinActivity.this;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);

        initDB();
        setupUI();
        setupClickEvents();
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(this);
    }

    /**
     * setting all the ui elements of the activity here
     */
    private void setupUI() {
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        spnStatus = findViewById(R.id.spn_status_create);
        rgGender = findViewById(R.id.rd_gender_register);
        btnCreateUser = findViewById(R.id.btn_create_user);
        edtLastName = findViewById(R.id.edt_last_name_create);
        edtEnterPin = findViewById(R.id.edt_enter_pin_create);
        edtFirstName = findViewById(R.id.edt_first_name_create);
        spnBloodGroup = findViewById(R.id.spn_blood_group_create);
        edtConfirmPin = findViewById(R.id.edt_confirm_pin_create);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth_create);
        edtMobileNumber = findViewById(R.id.edt_mobile_number_create);
    }

    /**
     * setting all the click events of the activity here
     */
    private void setupClickEvents() {
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEnterPin.getText().toString().equals(edtConfirmPin.getText().toString())) {
                    if (validate())
                        createNewUser();
                } else
                    Toast.makeText(CreatePinActivity.this, "pin must be same", Toast.LENGTH_SHORT).show();
            }
        });

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            selectedGenderId = rgGender.getCheckedRadioButtonId();
            rbGender = findViewById(selectedGenderId);
        });

        edtDateOfBirth.setOnClickListener(view -> {
            DTU.showDatePickerDialog(context, DTU.FLAG_OLD_AND_NEW, edtDateOfBirth);
        });
    }

    /**
     * validation for empty text
     * check for entered text by user if text is empty
     * then show error message to user
     * else return true
     * @return
     */
    private boolean validate() {
        if(edtMobileNumber.getText().toString().length() <= 0){
            edtMobileNumber.setFocusable(true);
            edtMobileNumber.setError("Please Enter Mobile Number");
            return false;
        }else if(edtFirstName.getText().toString().length() <= 0){
            edtFirstName.setFocusable(true);
            edtFirstName.setError("Please Enter First Name");
            return false;
        }else if(edtLastName.getText().toString().length() <= 0){
            edtLastName.setFocusable(true);
            edtLastName.setError("Please Enter Last Name");
            return false;
        }else if(edtDateOfBirth.getText().toString().length() <= 0){
            edtDateOfBirth.setFocusable(true);
            edtDateOfBirth.setError("Please Enter Date of Birth");
            return false;
        }else if(edtEnterPin.getText().toString().length() <= 0){
            edtEnterPin.setFocusable(true);
            edtEnterPin.setError("Please Enter Pin");
            return false;
        }else if(edtConfirmPin.getText().toString().length() <= 0){
            edtConfirmPin.setFocusable(true);
            edtConfirmPin.setError("Please Enter Confirm Pin");
            return false;
        }else if(spnStatus.getSelectedItemPosition() == 0){
            Toast.makeText(context, "Please select status", Toast.LENGTH_SHORT).show();
            return false;
        }else if(spnBloodGroup.getSelectedItemPosition() == 0){
            Toast.makeText(context, "Please Select Blood Group", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * get the selected gender
     * @return
     */
    private String getSelectedGender() {
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

    /**
     * create a new user and insert the data into database
     */
    private void createNewUser() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.GENDER, getSelectedGender());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.PIN, edtConfirmPin.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME, edtLastName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME, edtFirstName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.STATUS, spnStatus.getSelectedItem().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH, edtDateOfBirth.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP, spnBloodGroup.getSelectedItem().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED, "N");
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER, edtMobileNumber.getText().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.MM_DD_YYY_HH_MM));

        dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues);
        context.startActivity(new Intent(CreatePinActivity.this, LoginActivity.class));
    }


}