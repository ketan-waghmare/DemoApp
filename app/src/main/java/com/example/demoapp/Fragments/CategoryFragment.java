package com.example.demoapp.Fragments;

import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.os.Bundle;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.Adapters.CategoryListAdapter;
import com.example.demoapp.SQLiteDatabase.DataBaseConstants;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import org.json.JSONArray;

/**
 * created by ketan 26-9-2020
 */
public class CategoryFragment extends Fragment implements RvClickListener {

    //region variables
    private JSONArray jsonArray;
    private String categoryList = "";
    private Fragment fragment = null;
    private RecyclerView rvCategories;
    private ImageView ivAddNewCategory;
    private CategoryListAdapter adapter;
    private DataBaseHelper dataBaseHelper;
    //endregion

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        initDB();
        setupUI(rootView);
        getCategoryList();
        setupClickEvents();
        return rootView;
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }

    /**
     * get category list from the database
     */
    private void getCategoryList() {
        jsonArray = dataBaseHelper.getCategoriesFromDB();
        if (jsonArray != null && jsonArray.length() > 0) {
            rvCategories.setVisibility(View.VISIBLE);
            setupCategoryListAdapter(jsonArray);
        }else {
            rvCategories.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Categories found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set all the ui elements here
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Categories");
        rvCategories = rootView.findViewById(R.id.rv_categories);
        ivAddNewCategory = rootView.findViewById(R.id.iv_add_new_category);
    }

    /**
     * set all the click events here
     */
    private void setupClickEvents() {
        ivAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory();
            }
        });
    }

    /**
     * set category list adapter for showing the category list
     * @param dataList
     */
    private void setupCategoryListAdapter(JSONArray dataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvCategories.setLayoutManager(gridLayoutManager);
        adapter = new CategoryListAdapter(getActivity(), dataList);
        rvCategories.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    /**
     * add new category
     * open add category page
     */
    private void addNewCategory() {
        Utils.replaceFragment(getActivity(),new AddCategoriesFragment());
    }

    /**
     * handle the click events of the screen
     * @param position
     * @param value
     * @param key
     */
    @Override
    public void rv_click(int position, int value, String key) {
        if (key.equals("edit")) {
            editCategory(position);
        } else if (key.equals("remove")) {
            removeCategory(position);
        }
    }

    /**
     * edit the category
     * send the category id to the Edit category screen
     * @param position
     */
    private void editCategory(int position) {
        try{
            fragment = new EditCategoriesFragment();
            Bundle args = new Bundle();
            args.putString("id",jsonArray.getJSONObject(position).getString("id"));
            fragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                    R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * remove the category
     * @param position
     */
    private void removeCategory(int position) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_deleted","Y");
            dataBaseHelper.updateTableData(DataBaseConstants.TableNames.TBL_CATEGORIES,contentValues,jsonArray.getJSONObject(position).getString("id"));
            getCategoryList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}