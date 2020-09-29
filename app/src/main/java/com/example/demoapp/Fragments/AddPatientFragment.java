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

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
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

        setupUI(rootView);
        initDatabase();
        setupClickEvents(rootView);
        return rootView;
    }

    private void initDatabase() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }


    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Add Patient");
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
            contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.MM_DD_YYY_HH_MM));

            dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_PATIENTS, contentValues);
            gotoPatientListing();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void gotoPatientListing() {
       Fragment fragment = new PatientListingFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    private String getSelectedGender(){
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

}