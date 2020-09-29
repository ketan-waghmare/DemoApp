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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * created by ketan 26-9-2020
 */
public class EditCategoriesFragment extends Fragment {

    //region variables
    private Spinner spnStatus;
    private String categoryId;
    private EditText edtCategoryName;
    private Button btnUpdateCategory;
    private ArrayList<String> statusList;
    private DataBaseHelper dataBaseHelper;
    //endregion

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

        initDB();
        setupUI(rootView);
        setSpinnerAdapter();
        getReceivedBundle();
        setupAllClickEvents();
        return rootView;
    }

    /**
     * set spinner adapter of status
     */
    private void setSpinnerAdapter() {
        statusList = new ArrayList<>();
        statusList.add("Select Status");
        statusList.add("Active");
        statusList.add("Inactive");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.simple_item_selected, statusList);
        dataAdapter.setDropDownViewResource(R.layout.simple_item);
        spnStatus.setAdapter(dataAdapter);
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }

    /**
     * get data received from bundle from previous fragment
     * set the data of category by using received id
     */
    private void getReceivedBundle() {
        categoryId = getArguments().getString("id");

        setCategoryData();
    }

    /**
     * set category data by id
     * id received from bundle
     */
    private void setCategoryData() {
        try {
            JSONArray categoryArray = dataBaseHelper.getCategoryByID(categoryId);
            edtCategoryName.setText(categoryArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_CATEGORIES.NAME));
            spnStatus.setSelection(statusList.indexOf(categoryArray.getJSONObject(0).getString(DataBaseConstants.Constants_TBL_CATEGORIES.STATUS)));

        }catch (Exception e){

        }
    }

    /**
     * set up all the UI elements of the screen
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Edit Categories");
        spnStatus = rootView.findViewById(R.id.spn_status_category_edit);
        edtCategoryName = rootView.findViewById(R.id.edit_category_name);
        btnUpdateCategory = rootView.findViewById(R.id.btn_update_category);
    }

    /**
     * set up all the click events of the screen
     */
    private void setupAllClickEvents() {
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                updateCategory();
            }
        });
    }

    /**
     * validate user input
     * @return
     */
    private boolean validate() {
        if(edtCategoryName.getText().toString().length() <= 0){
            edtCategoryName.setFocusable(true);
            edtCategoryName.setError("Please Enter Category Name");
            return false;
        }else if(spnStatus.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), "Please Select Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * update the category for the received id
     * show category list screen
     */
    private void updateCategory() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.NAME,edtCategoryName.getText().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.STATUS,spnStatus.getSelectedItem().toString().trim());

        dataBaseHelper.updateTableData(DataBaseConstants.TableNames.TBL_CATEGORIES,contentValues,categoryId);
        showCategoryList();
    }

    /**
     * move to category list screen
     */
    private void showCategoryList() {
        Utils.replaceFragment(getActivity(),new CategoryFragment());
    }

}