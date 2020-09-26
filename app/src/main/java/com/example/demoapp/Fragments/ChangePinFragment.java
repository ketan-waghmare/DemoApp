package com.example.demoapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demoapp.R;

/**
 *created by ketan 24-9-2020
 */
public class ChangePinFragment extends Fragment {


    public ChangePinFragment() {
        // Required empty public constructor
    }

    public static ChangePinFragment newInstance(String param1, String param2) {
        ChangePinFragment fragment = new ChangePinFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_change_pin, container, false);
        return rootView;
    }
}