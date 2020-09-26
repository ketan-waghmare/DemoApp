package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.demoapp.R;

/**
 * created by ketan 25-9-2020
 */
public class AddPatientProblemFragment extends Fragment {

    private Button btnAddProblem;
    private EditText edtPatientProblem;
    private Spinner spnStatusPatientProblem;

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

        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    private void setupUI(View rootView) {
        btnAddProblem = rootView.findViewById(R.id.btn_add_problem);
        edtPatientProblem = rootView.findViewById(R.id.edt_add_patient_problem);
        spnStatusPatientProblem = rootView.findViewById(R.id.spn_patient_problem_status);
    }

    private void setupClickEvents() {
        btnAddProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPatientProblem();
            }
        });
    }

    /**
     * add new problem of patient
     */
    private void addPatientProblem() {

    }
}