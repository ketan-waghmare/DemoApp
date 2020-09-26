package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.demoapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPatientFragment extends Fragment {

    private EditText edtDOB;
    private Spinner spnStatus;
    private RadioButton rbMale;
    private RadioGroup rgGender;
    private EditText edtLastName;
    private RadioButton rbFemale;
    private EditText edtFirstName;
    private Spinner spnBloodGroup;

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

        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    private void setupUI(View rootView) {
        rbMale = rootView.findViewById(R.id.rb_male);
        rbFemale = rootView.findViewById(R.id.rb_female);
        rgGender = rootView.findViewById(R.id.rg_gender);
        edtDOB = rootView.findViewById(R.id.edit_date_of_birth);
        spnStatus = rootView.findViewById(R.id.spn_status_edit);
        edtLastName = rootView.findViewById(R.id.edit_last_name);
        edtFirstName = rootView.findViewById(R.id.edit_first_name);
        spnBloodGroup = rootView.findViewById(R.id.spn_blood_group_edit);
    }

    private void setupClickEvents() {

    }
}