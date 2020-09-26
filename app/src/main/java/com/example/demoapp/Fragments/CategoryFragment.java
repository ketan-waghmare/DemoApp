package com.example.demoapp.Fragments;

import android.util.Log;
import android.view.View;
import android.os.Bundle;

import com.example.demoapp.R;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.Adapters.CategoryListAdapter;
import com.google.gson.JsonArray;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import org.json.JSONArray;

/**
 * created by ketan 23-9-2020
 */
public class CategoryFragment extends Fragment implements RvClickListener {

    private JSONArray jsonArray;
    private String categoryList = "";
    private RecyclerView rvCategories;
    private ImageView ivAddNewCategory;

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

        setupUI(rootView);
        setupClickEvents();
        getBundleData();
        return rootView;
    }

    private void getBundleData() {
        if (!categoryList.equals("")){
            categoryList = getArguments().getString("category_list");
            Log.e("category_received"," = "+categoryList.length());
            try {
                jsonArray = new JSONArray(categoryList);
            }catch (Exception e){
                e.printStackTrace();
            }
            setupCategoryListAdapter(jsonArray);
        }else{
            Log.e("category_log"," = "+categoryList.length());
            Toast.makeText(getActivity(), "Empty list received", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupUI(View rootView) {
        rvCategories = rootView.findViewById(R.id.rv_categories);
        ivAddNewCategory = rootView.findViewById(R.id.iv_add_new_category);
    }

    private void setupClickEvents() {
        ivAddNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCategory();
            }
        });
    }

    private void setupCategoryListAdapter(JSONArray dataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvCategories.setLayoutManager(gridLayoutManager);
        CategoryListAdapter adapter = new CategoryListAdapter(getActivity(), dataList);
        rvCategories.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    private void addNewCategory() {
        Fragment fragment = new AddCategoriesFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    @Override
    public void rv_click(int position, int value, String key) {

    }
}