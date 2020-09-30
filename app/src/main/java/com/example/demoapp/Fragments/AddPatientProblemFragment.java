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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

/**
 * created by ketan 26-9-2020
 */
public class AddPatientProblemFragment extends Fragment {

    //region varialble
    private Button btnAddProblem;
    private EditText edtPatientProblem;
    private Spinner spnStatusPatientProblem;

    private DataBaseHelper dataBaseHelper;
    private SharedPreferences preferences;

    //endregion

    public AddPatientProblemFragment() {
        // Required empty public constructor
    }

    public static AddPatientProblemFragment newInstance(String param1, String param2) {
        AddPatientProblemFragment fragment = new AddPatientProblemFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_patient_problem, container, false);

        initDB();
        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());

        //initializing shared preference
        preferences = getActivity().getSharedPreferences(Utils.PREFERENCE_USER, Context.MODE_PRIVATE);
    }

    /**
     * UI elements of the add patient screen
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Add Patient Problem");
        btnAddProblem = rootView.findViewById(R.id.btn_add_problem);
        edtPatientProblem = rootView.findViewById(R.id.edt_add_patient_problem);
        spnStatusPatientProblem = rootView.findViewById(R.id.spn_patient_problem_status);
    }

    /**
     * contains all click events of the screen
     */
    private void setupClickEvents() {
        btnAddProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                addPatientProblem();
            }
        });
    }

    private boolean validate() {

        if(edtPatientProblem.getText().toString().length() <= 0){
            edtPatientProblem.setFocusable(true);
            edtPatientProblem.setError("Please Enter Problem");
            return false;
        }else if(spnStatusPatientProblem.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), "Please Select Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * add new problem of patient
     */
    private void addPatientProblem() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.IS_DELETED, "N");
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PROBLEM, edtPatientProblem.getText().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.CREATE_DATE, Utils.getCurrentDateTime(Utils.DD_MM_YYYY));
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.STATUS, spnStatusPatientProblem.getSelectedItem().toString().trim());
        if (!preferences.getString(DataBaseConstants.Constants_TBL_PATIENTS.ID, "").equals(""))
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENT_PROBLEMS.PATIENT_ID, preferences.getString(DataBaseConstants.Constants_TBL_PATIENTS.ID, ""));

        dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENT_PROBLEMS, contentValues);

        showProblemListing();
    }

    /**
     * move to problem listing screen
     */
    private void showProblemListing() {
      Utils.replaceFragment(getActivity(),new ProblemListingFragment());
    }
}