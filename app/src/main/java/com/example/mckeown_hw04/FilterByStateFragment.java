

//  Homework 04
//  McKeown_HW04
//  Adrianna McKeown

package com.example.mckeown_hw04;

import android.app.AliasActivity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FilterByStateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // ----------------------------------------------------------------------------
    private static final String TAG = "Filter Fragment";
    FilterByStateFragment.FilterByStateFragmentListener mListener;
    ArrayAdapter<String> adapter;
    ArrayList<DataServices.User> users = new ArrayList<>();
    HashSet<String> states = new HashSet<>();
    ArrayList<String> sortedStates = new ArrayList<>();

    public FilterByStateFragment() {
        // Required empty public constructor
    }

    public static FilterByStateFragment newInstance(String param1, String param2) {
        FilterByStateFragment fragment = new FilterByStateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_by_state, container, false);

        // Retrieves the list of unique states from the Data class
        users = DataServices.getAllUsers();
        for (DataServices.User user : users) {
            states.add(user.state);
        }

        //  The sortedStates ArrayList presents the list of unique states in ascending order
        for (String state : states) {
            sortedStates.add(state);
        }
        Collections.sort(sortedStates);
        sortedStates.add(0, "All States");

        ListView listView = view.findViewById(R.id.listViewStates);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, sortedStates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String state = sortedStates.get(position);
                Log.d(TAG, "state clicked: " + state);
                mListener.stateSelected(state);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Filter By State");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FilterByStateFragment.FilterByStateFragmentListener) {
            mListener = (FilterByStateFragment.FilterByStateFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement FilterByStateFragmentListener");
        }
    }

    public interface FilterByStateFragmentListener {
        void stateSelected(String state);
    }
}