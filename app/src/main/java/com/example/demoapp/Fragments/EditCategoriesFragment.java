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
 * created by ketan 23-9-2020
 */
public class EditCategoriesFragment extends Fragment {

    private Spinner spnStatus;
    private EditText edtCategoryName;
    private Button btnUpdateCategory;

    public static EditCategoriesFragment newInstance(String param1, String param2) {
        EditCategoriesFragment fragment = new EditCategoriesFragment();
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.fragment_edit_categories, container, false);

        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    private void setupUI(View rootView) {
        spnStatus = rootView.findViewById(R.id.spn_status_edit);
        edtCategoryName = rootView.findViewById(R.id.edit_category_name);
        btnUpdateCategory = rootView.findViewById(R.id.btn_update_category);
    }

    private void setupClickEvents() {
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategory();
            }
        });
    }

    private void updateCategory() {

    }

}