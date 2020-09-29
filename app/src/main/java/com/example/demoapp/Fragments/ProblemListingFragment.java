package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.Activity.MainActivity;
import com.example.demoapp.Adapters.CategoryListAdapter;
import com.example.demoapp.Adapters.ProblemListAdapter;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.R;
import com.example.demoapp.SQLiteDatabase.DataBaseHelper;
import com.example.demoapp.Utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * created by ketan 24-9-2020
 */
public class ProblemListingFragment extends Fragment{

    //region variables
    private JSONArray problemArray;
    private ProblemListAdapter adapter;
    private RecyclerView rvProblemsList;
    private ImageView ivAddPatientProblem;
    private DataBaseHelper dataBaseHelper;
    //endregion

    public static ProblemListingFragment newInstance(String param1, String param2) {
        ProblemListingFragment fragment = new ProblemListingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_problem_listing, container, false);

        initDB();
        setupUI(rootView);
        setupClickEvents();
        getProblemListFromDB();
        return rootView;
    }

    /**
     * initialize database helper object
     */
    private void initDB() {
        dataBaseHelper = new DataBaseHelper(getActivity());
    }

    private void getProblemListFromDB() {
        problemArray = dataBaseHelper.getProblemFromDB();
        if (problemArray != null && problemArray.length() > 0) {
            rvProblemsList.setVisibility(View.VISIBLE);
            setProblemListAdapter(problemArray);
        }else {
            rvProblemsList.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Categories found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set up all the UI elements of the screen
     * @param rootView
     */
    private void setupUI(View rootView) {
        MainActivity.tvHeader.setText("Problems");
        rvProblemsList = rootView.findViewById(R.id.rv_problem);
        ivAddPatientProblem = rootView.findViewById(R.id.iv_add_problem);
    }

    /**
     * set all the click events of the screen
     */
    private void setupClickEvents() {
        ivAddPatientProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddProblemFragment();
            }
        });
    }

    /**
     * set the list of the problems added to the database
     * @param dataList
     */
    private void setProblemListAdapter(JSONArray dataList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvProblemsList.setLayoutManager(gridLayoutManager);
        adapter = new ProblemListAdapter(getActivity(), dataList);
        rvProblemsList.setAdapter(adapter);
    }

    /**
     * go to the add problem fragment for adding new problem
     */
    private void gotoAddProblemFragment() {
        Utils.replaceFragment(getActivity(),new AddPatientProblemFragment());
    }
}