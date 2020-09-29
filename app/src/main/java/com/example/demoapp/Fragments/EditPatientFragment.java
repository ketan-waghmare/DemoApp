package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPatientFragment extends Fragment {

    private EditText edtDOB;
    private EditText edtPin;
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private EditText edtLastName;
    private RadioButton rbFemale;
    private EditText edtFirstName;
    private Spinner spnBloodGroup;
    private Button btnUpdatePatient;
    private EditText edtMobileNumber;

    private String patientID;
    private JSONArray patientArray;
    private ArrayList<String> statusList;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<String> bloodGroupList;

    public static EditPatientFragment newInstance(String param1, String param2) {
        EditPatientFragment fragment = new EditPatientFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_edit_patient, container, false);

        initDB();
        setupUI(rootView);
        setSpinnerAdapter();
        getReceivedBundle();
        setupAllClickEvents();
        return rootView;
    }

    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }


    private void getReceivedBundle() {
        patientID = getArguments().getString(DataBaseConstants.Constants_TBL_PATIENTS.ID);

        setPatientData();
    }

    private void setPatientData() {
        try {
            patientArray = dataBaseHelper.getPatientByID(patientID);
            Log.e("patientArray_log"," = "+patientArray);
            edtPin.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.PIN));
            edtDOB.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.DATE_OF_BIRTH));
            edtLastName.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME));
            edtFirstName.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME));
            edtMobileNumber.setText(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER));
            spnStatus.setSelection(statusList.indexOf(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.STATUS)));
            spnBloodGroup.setSelection(bloodGroupList.indexOf(patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP)));
            if (patientArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_PATIENTS.GENDER).equals("male")) {
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
            } else {
                rbFemale.setChecked(true);
                rbMale.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerAdapter() {
        statusList = new ArrayList<>();
        statusList.add("Select Status");
        statusList.add("Active");
        statusList.add("Inactive");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.simple_item_selected, statusList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnStatus.setAdapter(dataAdapter);

        bloodGroupList = new ArrayList<>();
        bloodGroupList.add("Select Blood Group");
        bloodGroupList.add("O+");
        bloodGroupList.add("O-");
        bloodGroupList.add("A+");
        bloodGroupList.add("A-");
        bloodGroupList.add("B+");
        bloodGroupList.add("B-");
        bloodGroupList.add("AB+");
        bloodGroupList.add("AB-");

        ArrayAdapter<String> bloodGroupAdapter;
        bloodGroupAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.simple_item_selected, bloodGroupList);
        bloodGroupAdapter.setDropDownViewResource(R.layout.simple_item);
        spnBloodGroup.setAdapter(bloodGroupAdapter);


    }

    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Edit Patient");
        rbMale = rootView.findViewById(R.id.rb_male);
        edtPin = rootView.findViewById(R.id.edit_pin);
        rbFemale = rootView.findViewById(R.id.rb_female);
        rgGender = rootView.findViewById(R.id.rg_gender);
        edtDOB = rootView.findViewById(R.id.edit_date_of_birth);
        spnStatus = rootView.findViewById(R.id.spn_status_edit);
        edtLastName = rootView.findViewById(R.id.edit_last_name);
        edtFirstName = rootView.findViewById(R.id.edit_first_name);
        spnBloodGroup = rootView.findViewById(R.id.spn_blood_group_edit);
        edtMobileNumber = rootView.findViewById(R.id.edit_mobile_number);
        btnUpdatePatient = rootView.findViewById(R.id.btn_update_patient);
    }

    private void setupAllClickEvents() {
        btnUpdatePatient.setOnClickListener(view -> {
            updatePatientDetails();
        });
    }

    private void updatePatientDetails() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.IS_DELETED,"N");
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.GENDER,getSelectedGender());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.PIN,edtPin.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.LAST_NAME,edtLastName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.MOBILE_NUMBER,edtMobileNumber.getText().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.FIRST_NAME,edtFirstName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.STATUS,spnStatus.getSelectedItem().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_PATIENTS.BLOOD_GROUP,spnBloodGroup.getSelectedItem().toString().trim());


        dataBaseHelper.updateTableData(DataBaseConstants.TableNames.TBL_PATIENTS,contentValues,patientID);
        showPatientList();
    }

    private String getSelectedGender() {
        int radioButtonID = rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = rgGender.findViewById(radioButtonID);
        return radioButton.getText().toString();
    }

    private void showPatientList() {
        Utils.replaceFragment(getActivity(),new PatientListingFragment());
    }
}