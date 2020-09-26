package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.demoapp.Adapters.CategoryListAdapter;
import com.example.demoapp.Adapters.PatientListAdapter;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.R;

import java.util.ArrayList;

/**
 * created by ketan 24-9-2020
 */
public class PatientListingFragment extends Fragment implements RvClickListener {

    private Button btnFilter;
    private EditText edtFirstName;
    private EditText edtDateOfBirth;
    private RecyclerView rvPatients;
    private EditText edtCreatedDate;
    private ImageView ivAddNewPatient;

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

        setupUI(rootView);
        setupClickEvents();
        setPatientListAdapter();
        return rootView;
    }

    private void setupUI(View rootView) {
        btnFilter = rootView.findViewById(R.id.btn_filter);
        rvPatients = rootView.findViewById(R.id.rv_patients);
        edtCreatedDate = rootView.findViewById(R.id.edt_created_date);
        edtDateOfBirth = rootView.findViewById(R.id.edt_date_of_birth);
        ivAddNewPatient = rootView.findViewById(R.id.iv_add_new_patient);
    }

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
    }

    private void filterPatients() {

    }

    private void addNewPatient() {
        Fragment fragment = new AddPatientFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    private void setPatientListAdapter() {
        ArrayList<String> dataList = new ArrayList<>();

        dataList.add("");
        dataList.add("");
        dataList.add("");
        dataList.add("");
        dataList.add("");
        dataList.add("");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvPatients.setLayoutManager(gridLayoutManager);
        PatientListAdapter adapter = new PatientListAdapter(getActivity(), dataList);
        rvPatients.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    @Override
    public void rv_click(int position, int value, String key) {

    }
}