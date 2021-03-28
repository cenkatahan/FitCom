package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FitCom.fitcomapplication.R;


public class AllProgramsFragment extends Fragment {


    public AllProgramsFragment() {
        // Required empty public constructor
    }

//    public static AllProgramsFragment newInstance() {
//        return new AllProgramsFragment();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_programs, container, false);
    }
}