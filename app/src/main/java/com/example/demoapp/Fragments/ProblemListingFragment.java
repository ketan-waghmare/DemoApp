package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.demoapp.Adapters.CategoryListAdapter;
import com.example.demoapp.Adapters.ProblemListAdapter;
import com.example.demoapp.Interfaces.RvClickListener;
import com.example.demoapp.R;

import java.util.ArrayList;

/**
 * created by ketan 24-9-2020
 */
public class ProblemListingFragment extends Fragment implements RvClickListener {

    private RecyclerView rvProblemsList;
    private ImageView ivAddPatientProblem;

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

        setupUI(rootView);
        setupClickEvents();
        setProblemListAdapter();
        return rootView;
    }

    private void setupUI(View rootView) {
        rvProblemsList = rootView.findViewById(R.id.rv_problem);
        ivAddPatientProblem = rootView.findViewById(R.id.iv_add_problem);
    }

    private void setupClickEvents() {
        ivAddPatientProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddProblemFragment();
            }
        });
    }

    private void setProblemListAdapter() {
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("");
        dataList.add("");
        dataList.add("");
        dataList.add("");
        dataList.add("");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        rvProblemsList.setLayoutManager(gridLayoutManager);
        ProblemListAdapter adapter = new ProblemListAdapter(getActivity(), dataList);
        rvProblemsList.setAdapter(adapter);
        adapter.setRvClickListener(this);
    }

    /**
     * go to the add problem fragment for adding new problem
     */
    private void gotoAddProblemFragment() {
        Fragment fragment = new AddPatientProblemFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    @Override
    public void rv_click(int position, int value, String key) {

    }
}