package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.Adapters.CategoryListAdapter;
import com.example.demoapp.Adapters.PatientListAdapter;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.DTU;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * created by ketan 29-9-2020
 */
public class PatientListingFragment extends Fragment implements RvClickListener {

    //region variables
    private Button btnFilter;
    private EditText edtFirstName;
    private JSONArray patientArray;
    private EditText edtDateOfBirth;
    private RecyclerView rvPatients;
    private EditText edtCreatedDate;
    private ImageView ivAddNewPatient;

    private Fragment fragment = null;
    private PatientListAdapter adapter;
    private DataBaseHelper dataBaseHelper;
    //endregion

    public static PatientListingFragment newInstance(String param1, String param2) {
        PatientListingFragment fragment = new PatientListingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_patient_listing, container, false);

        initDB();
        setupUI(rootView);
        setupClickEvents();
        getPatientListFromDB();
        return rootView;
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }

    /**
     * get patient list from the database
     * set the patient list to recyclerview
     * if list is empty show no patient found message to user
     */
    private void getPatientListFromDB() {
        patientArray = dataBaseHelper.getPatientListFromDB();
        Log.e("patient_array_log"," = "+patientArray);
        if(patientArray != null && patientArray.length() > 0){
            rvPatients.setVisibility(View.VISIBLE);
            setPatientListAdapter(patientArray);
        }else{
            rvPatients.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Patients Found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set all the UI elements of the screen here
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Patients");
        btnFilter = rootView.findViewById(R.id.btn_filter);
        rvPatients = rootView.findViewById(R.id.rv_patients);
        edtCreatedDate = rootView.findViewById(R.id.edt_created_date);
        edtDateOfBirth = rootView.findViewById(R.id.edt_date_of_birth);
        edtFirstName = rootView.findViewById(R.id.edt_first_name_filter);
        ivAddNewPatient = rootView.findViewById(R.id.iv_add_new_patient);
    }

    /**
     * set up all the click events of the screen
     */
    private void setupClickEvents() {
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterPatients();
            }
        });
        ivAddNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPatient();
            }
        });

        edtDateOfBirth.setOnClickListener(view -> {
            showDatePicker(edtDateOfBirth);
        });

        edtCreatedDate.setOnClickListener(view -> {
            showDatePicker(edtCreatedDate);
        });
    }

    /**
     * show date picker dialog to user for selecting the date
     */
    private void showDatePicker(EditText edtTextDate) {
        DTU.showDatePickerDialog(getActivity(), DTU.FLAG_OLD_AND_NEW, edtTextDate);
    }

    /**
     * filter the list of patients as per user input
     */
    private void filterPatients() {
        try {
            patientArray = dataBaseHelper.getFilteredList(edtFirstName.getText().toString(),edtCreatedDate.getText().toString(),edtDateOfBirth.getText().toString());
            Log.e("filterArray_log"," = "+patientArray);
            setPatientListAdapter(patientArray);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * move to add new patient screen
     */
    private void addNewPatient() {
        Utils.replaceFragment(getActivity(),new AddPatientFragment());
    }

    /**
     * set list of the patient received from database
     * set the list click listener
     * @param dataList
     */
    private void setPatientListAdapter(JSONArray dataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvPatients.setLayoutManager(gridLayoutManager);
        adapter = new PatientListAdapter(getActivity(), dataList);
        rvPatients.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    /**
     * handle the list item click listener
     * edit and remove
     * if user click edit icon move to edit patient screen
     * if user click delete icon delete the patient and update list
     */
    @Override
    public void rv_click(int position, int value, String key) {
        if(key.equals("edit")){
            editPatient(position);
        }else if(key.equals("remove")){
            removePatient(position);
        }
    }

    /**
     * move to edit patient screen
     * set args id in bundle to send to edit patient screen
     * @param position
     */
    private void editPatient(int position) {
        try{
            fragment = new EditPatientFragment();
            Bundle args = new Bundle();
            args.putString("id",patientArray.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENTS.ID));
            fragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                    R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * remove the patient from database and update the patient list
     * here we are not deleting patient directly
     * we are soft deleting the patients using is_deleted column
     * if is_delete contains Y then it is deleted
     * if is_delete contains N it is present in database
     * @param position
     */
    private void removePatient(int position) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_deleted","Y");
            dataBaseHelper.updateTableData(DataBaseConstants.TableNames.TBL_PATIENTS,contentValues,patientArray.getJSONObject(position).getString(DataBaseConstants.Constants_TBL_PATIENTS.ID));
            getPatientListFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}