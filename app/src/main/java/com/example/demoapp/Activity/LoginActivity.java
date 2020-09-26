package com.example.demoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPin;
    private Button btnLogin;
    private TextView tvCreatePin;
    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI();
        setupEvents();
    }

    private void setupUI() {
        edtPin = findViewById(R.id.edt_pin);
        btnLogin = findViewById(R.id.btn_login);
        tvCreatePin = findViewById(R.id.tv_create_pin);
    }

    private void setupEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
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
     * handle the login of the application using the pin
     */
    private void doLogin() {
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        context.startActivity(new Intent(this,MainActivity.class));
    }


    /**
     * create a new pin for the user
     */
    private void createNewPin() {
        context.startActivity(new Intent(this,CreatePinActivity.class));
        finish();
    }

}