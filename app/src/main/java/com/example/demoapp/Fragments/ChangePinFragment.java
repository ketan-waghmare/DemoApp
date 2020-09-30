package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Constants;
import com.example.demoapp.Utils.Utils;

/**
 *created by ketan 26-9-2020
 */
public class ChangePinFragment extends Fragment {

    //region variables
    private EditText edtPin;
    private Button btnChagnePin;
    private EditText edtConfirmPin;

    private DataBaseHelper dataBaseHelper;
    private SharedPreferences preferences;
    //endregion

    public ChangePinFragment() {
        // Required empty public constructor
    }

    public static ChangePinFragment newInstance(String param1, String param2) {
        ChangePinFragment fragment = new ChangePinFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_pin, container, false);

        initDB();
        setupUI(rootView);
        setupEvents();
        return rootView;
    }

    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());

        //initialize shared preference for user id
        preferences = getActivity().getSharedPreferences(Utils.PREFERENCE_USER, Context.MODE_PRIVATE);
    }

    /**
     * contains all the UI elements of the screen here
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText(Constants.CHANGE_PIN);
        edtPin = rootView.findViewById(R.id.edt_enter_pin_change);
        btnChagnePin = rootView.findViewById(R.id.btn_change_pin);
        edtConfirmPin = rootView.findViewById(R.id.edt_confirm_pin_change);
    }

    /**
     * set all the click events here
     */
    private void setupEvents() {
        btnChagnePin.setOnClickListener(view -> {
            if(validate())
            changePin();
        });
    }

    /**
     * validate user input
     * return true when user enter all the data
     * return false when user miss to enter data
     */
    private boolean validate() {
        if(edtPin.getText().toString().length() <= 0){
            edtPin.setFocusable(true);
            edtPin.setError(Constants.ERR_MSG_PIN);
            return false;
        }else if(edtConfirmPin.getText().toString().length() <= 0){
            edtConfirmPin.setFocusable(true);
            edtConfirmPin.setError(Constants.ERR_MSG_CONFIRM_PIN);
            return false;
        }
        return true;
    }

    /**
     * change pin of the user or update the existing pin
     */
    private void changePin() {
        if (!preferences.getString(DataBaseConstants.Constants_TBL_PATIENTS.ID, "").equals("")) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.PIN, edtConfirmPin.getText().toString());
            dataBaseHelper.updateTableData(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues, preferences.getString(DataBaseConstants.Constants_TBL_PATIENTS.ID, ""));
            showPatientListing();
        }
    }

    /**
     * move to patient listing screen
     */
    private void showPatientListing() {
      Utils.replaceFragment(getActivity(),new PatientListingFragment());
    }
}