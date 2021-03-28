package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.FitCom.fitcomapplication.R;

public class MyProgramsFragment extends Fragment {


    public MyProgramsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyProgramsFragment newInstance(String param1, String param2) {
        return new MyProgramsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_programs, container, false);
    }
}