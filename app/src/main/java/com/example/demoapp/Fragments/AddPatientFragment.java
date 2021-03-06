package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Constants;
import com.example.demoapp.Utils.DTU;
import com.example.demoapp.Utils.Utils;

/**
 * created by ketan 26-09-2020
 */
public class AddPatientFragment extends Fragment {

    //region varialbles

    private EditText edtDOB;
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private RadioButton rbGender;
    private EditText edtLastName;
    private RadioButton rbFemale;
    private EditText edtFirstName;
    private EditText edtPinPatient;
    private EditText edtMobileNumber;

    private Button btnAddPatient;
    private int selectedGenderId;
    private Spinner spnBloodGroup;
    private DataBaseHelper dataBaseHelper;

    //endregion

    public AddPatientFragment() {
        // Required empty public constructor
    }

    public static AddPatientFragment newInstance(String param1, String param2) {
        AddPatientFragment fragment = new AddPatientFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_patient, container, false);

        initDB();
        setupUI(rootView);
        setupClickEvents(rootView);
        return rootView;
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }


    /**
     * set up all the UI elements of the screen
     * @param rootView
     */
    private void setupUI(View rootView) {
        //first set header
        MainActivity.tvHeader.setText(Constants.ADD_PATIENT);

        rbMale = rootView.findViewById(R.id.rd_male);
        rgGender = rootView.findViewById(R.id.rd_gender);
        rbFemale = rootView.findViewById(R.id.rd_female);
        spnStatus = rootView.findViewById(R.id.spn_status);
        edtDOB = rootView.findViewById(R.id.edt_date_of_birth);
        edtLastName = rootView.findViewById(R.id.edt_last_name);
        edtFirstName = rootView.findViewById(R.id.edt_first_name);
        spnBloodGroup = rootView.findViewById(R.id.spn_blood_group);
        btnAddPatient = rootView.findViewById(R.id.btn_add_patient);
        edtPinPatient = rootView.findViewById(R.id.edt_pin_patient);
        edtMobileNumber = rootView.findViewById(R.id.edt_mobile_number);
    }

    /**
     * set all click events of the screen
     * @param rootView
     */
    private void setupClickEvents(View rootView) {
        btnAddPatient.setOnClickListener(view -> {
            if(validate())
            addNewPatient();
        });

        edtDOB.setOnClickListener(view -> {
            DTU.showDatePickerDialog(getActivity(), DTU.FLAG_OLD_AND_NEW, edtDOB);
        });

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            selectedGenderId = rgGender.getCheckedRadioButtonId();
            rbGender = rootView.findViewById(selectedGenderId);
        });
    }

    /**
     * validation for user input
     * @return
     */
    private boolean validate() {
        if(edtMobileNumber.getText().toString().length() <= 0){
            edtMobileNumber.setFocusable(true);
            edtMobileNumber.setError(Constants.ERR_MSG_MOBILE);
            return false;
        }else if(edtFirstName.getText().toString().length() <= 0){
            edtFirstName.setFocusable(true);
            edtFirstName.setError(Constants.ERR_MSG_FIRST_NAME);
            return false;
        }else if(edtLastName.getText().toString().length() <= 0){
            edtLastName.setFocusable(true);
            edtLastName.setError(Constants.ERR_MSG_LAST_NAME);
            return false;
        }else if(edtDOB.getText().toString().length() <= 0){
            edtDOB.setFocusable(true);
            edtDOB.setError(Constants.ERR_MSG_DATE_OF_BIRTH);
            return false;
        }else if(edtPinPatient.getText().toString().length() <= 0){
            edtPinPatient.setFocusable(true);
            edtPinPatient.setError(Constants.ERR_MSG_PIN);
            return false;
        }else if(spnStatus.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), Constants.ERR_MSG_STATUS, Toast.LENGTH_SHORT).show();
            return false;
        }else if(spnBloodGroup.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), Constants.ERR_MSG_BLOOD_GROUP, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * add new patient to the database
     */
    private void addNewPatient() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED, "N");
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.GENDER, getSelectedGender());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.PIN,edtPinPatient.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER, edtMobileNumber.getText().toString());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH, edtDOB.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME, edtLastName.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME, edtFirstName.getText().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.STATUS, spnStatus.getSelectedItem().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP, spnBloodGroup.getSelectedItem().toString().trim());
            contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.DD_MM_YYYY));

            dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues);
            gotoPatientListing();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * move to the patient listing screen
     */
    private void gotoPatientListing() {
       Fragment fragment = new PatientListingFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    /**
     * get user selected gender
     * @return
     */
    private String getSelectedGender(){
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

}