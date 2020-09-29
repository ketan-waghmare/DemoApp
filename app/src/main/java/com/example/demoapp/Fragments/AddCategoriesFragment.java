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
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

/**
 * created by ketan 26-9-2020
 */
public class AddCategoriesFragment extends Fragment {

    private Spinner spnStatus;
    private Button btnAddCategory;
    private EditText edtCategoryName;
    private DataBaseHelper dataBaseHelper;

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

        initDB();
        setupUI(rootView);
        setupClickEvents();
        return rootView;
    }

    /**
     * initialize the database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity()); // initalization of database helper object
    }

    /**
     * set all the UI elements of the screen
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Add Categories");
        spnStatus = rootView.findViewById(R.id.spn_status_category);
        btnAddCategory = rootView.findViewById(R.id.btn_add_category);
        edtCategoryName = rootView.findViewById(R.id.edt_category_name);
    }

    /**
     * set click events of the screen
     */
    private void setupClickEvents() {
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                addNewCategory();
            }
        });
    }

    /**
     * set validation for user input
     * @return
     */
    private boolean validate() {
        if(edtCategoryName.getText().toString().length() <= 0){
            edtCategoryName.setFocusable(true);
            edtCategoryName.setError("Please Enter the Category Name");
            return false;
        }else if(spnStatus.getSelectedItemPosition() == 0){
            Toast.makeText(getActivity(), "Please Select Status", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * add new category to the database
     */
    private void addNewCategory() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.IS_DELETED,"N");
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.STATUS,spnStatus.getSelectedItem().toString());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.NAME,edtCategoryName.getText().toString().trim());
        contentValues.put(DataBaseConstants.Constants_TBL_CATEGORIES.CREATE_DATE, Utils.getCurrentDateTime(Utils.MM_DD_YYY_HH_MM));

        dataBaseHelper.saveToLocalTable(DataBaseConstants.TableNames.TBL_CATEGORIES,contentValues);

        gotoCategoriesListPage();

    }

    private void gotoCategoriesListPage() {
        Fragment fragment = new CategoryFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();

    }

}