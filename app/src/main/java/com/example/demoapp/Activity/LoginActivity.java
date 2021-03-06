package com.example.demoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Constants;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

/**
 * created by ketan 26-9-2020
 */
public class LoginActivity extends AppCompatActivity {

    //region variables
    private EditText edtPin;
    private Button btnLogin;
    private JSONArray verifyJsonArray;

    private TextView tvCreatePin;
    private EditText edtMobileNumber;
    private DataBaseHelper dataBaseHelper;
    private SharedPreferences preferenceUser;
    private Context context = LoginActivity.this;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initDB();
        setupUI();
        setupEvents();
    }

    /**
     * initialize database helper object and shared preference
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(this);

//        initialize preference
        preferenceUser = getSharedPreferences(Utils.PREFERENCE_USER, MODE_PRIVATE);
    }

    /**
     * set up UI elements here of the screen
     */
    private void setupUI() {
        edtPin = findViewById(R.id.edt_pin);
        btnLogin = findViewById(R.id.btn_login);
        tvCreatePin = findViewById(R.id.tv_create_pin);
        edtMobileNumber = findViewById(R.id.edt_mobile_number);
    }

    /**
     * set all the click events
     */
    private void setupEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    verifyUser();
            }
        });

        tvCreatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPin();
            }
        });
    }

    /**
     * validate user input
     * @return
     */
    private boolean validate() {
        if(edtMobileNumber.getText().toString().length() <= 0){
            edtMobileNumber.setFocusable(true);
            edtMobileNumber.setError(Constants.ERR_MSG_MOBILE);
            return false;
        }else if(edtPin.getText().toString().length() <= 0){
            edtPin.setFocusable(true);
            edtPin.setError(Constants.ERR_MSG_PIN);
            return false;
        }
        return true;
    }

    /**
     * check for the user is present in database or not
     * if present then move to next screen
     * else show the message user is not register
     */
    private void verifyUser() {
        try {
            verifyJsonArray = dataBaseHelper.verifyPin(edtMobileNumber.getText().toString(), edtPin.getText().toString());

            if (verifyJsonArray != null && verifyJsonArray.length() > 0) {
                context.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                writeToSharedPreference(Utils.PREFERENCE_USER, DataBaseConstants.Constants_TBL_PATIENTS.ID, verifyJsonArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.ID));
            } else {
                Toast.makeText(context, Constants.ERR_MSG_REGISTER, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * create a new pin for the user
     */
    private void createNewPin() {
        context.startActivity(new Intent(this, CreatePinActivity.class));
        finish();
    }

    /**
     * store the logged in patient id
     */
    private void writeToSharedPreference(String preferenceName, String key, String value) {
        SharedPreferences sharedPreference = getSharedPreferences(preferenceName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

}