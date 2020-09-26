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

public class CreatePinActivity extends AppCompatActivity {

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

    private int selectedGenderId;
    private DataBaseHelper dataBaseHelper;
    private Context context = CreatePinActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);

        initDB();
        setupUI();
        setupClickEvents();
    }

    private void initDB() {
        dataBaseHelper = new DataBaseHelper(this);
    }

    /**
     * setting all the ui elements of the activity here
     */
    private void setupUI() {
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rgGender = findViewById(R.id.rd_gender_register);
        btnCreateUser = findViewById(R.id.btn_create_user);
        edtLastName = findViewById(R.id.edt_last_name_create);
        edtEnterPin = findViewById(R.id.edt_enter_pin_create);
        edtFirstName = findViewById(R.id.edt_first_name_create);
        spnBloodGroup = findViewById(R.id.spn_blood_group_create);
        edtConfirmPin = findViewById(R.id.edt_confirm_pin_create);
        edtDateOfBirth = findViewById(R.id.edt_date_of_birth_create);
    }

    /**
     * setting all the click events of the activity here
     */
    private void setupClickEvents() {
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEnterPin.getText().toString().equals(edtConfirmPin.getText().toString()))
                    createNewUser();
                else
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

    private String getSelectedGender() {
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

    private void createNewUser() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.GENDER, getSelectedGender());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.PIN, edtConfirmPin.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME, edtLastName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME, edtFirstName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH, edtDateOfBirth.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP, spnBloodGroup.getSelectedItem().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.MM_DD_YYY_HH_MM));

        dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues);
        context.startActivity(new Intent(CreatePinActivity.this,LoginActivity.class));
    }


}