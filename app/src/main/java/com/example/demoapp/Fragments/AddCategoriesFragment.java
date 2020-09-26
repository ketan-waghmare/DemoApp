package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.demoapp.Model.CategoryModel;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * created by ketan 23-9-2020
 */
public class AddCategoriesFragment extends Fragment {

    private Spinner spnStatus;
    private Button btnAddCategory;
    private EditText edtCategoryName;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<CategoryModel> dataList;

    public static AddCategoriesFragment newInstance() {
        AddCategoriesFragment fragment = new AddCategoriesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_categories, container, false);

        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    private void setupUI(View rootView) {
        dataBaseHelper = new DataBaseHelper(getActivity()); // initalization of database helper object
        spnStatus = rootView.findViewById(R.id.spn_status_category);
        btnAddCategory = rootView.findViewById(R.id.btn_add_category);
        edtCategoryName = rootView.findViewById(R.id.edt_category_name);
    }

    private void setupClickEvents() {
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory();
            }
        });
    }

    private void addNewCategory() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.NAME,edtCategoryName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.STATUS,spnStatus.getSelectedItem().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.MM_DD_YYY_HH_MM));

        dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_CATEGORIES,contentValues);

        /*
            after adding the data to the database we have to clear the existing fields
            so for that we are doing the following things
         */
        spnStatus.setSelection(0);
        edtCategoryName.setText("");
        gotoCategoriesListPage(getCategoriesFromDB());

    }

    private JSONArray getCategoriesFromDB() {
        JSONArray jsonArray = dataBaseHelper.getCategoriesFromDB();
        return jsonArray;
    }

    private void gotoCategoriesListPage(JSONArray categoriesFromDB) {
        Fragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("category_list", categoriesFromDB.toString());
        fragment.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();

    }

}