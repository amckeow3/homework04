

//  Homework 04
//  McKeown_HW04
//  Adrianna McKeown

package com.example.mckeown_hw04;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // ----------------------------------------------------------------------------
    private static final String TAG = "Sort Fragment";
    SortFragment.SortFragmentListener mListener;
    ArrayList<String> attributes = new ArrayList<>();
    LinearLayoutManager layoutManager;
    UserRecyclerViewAdapter adapter;
    String sortOrder;


    RecyclerView recyclerView;

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance(String param1, String param2) {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        attributes.add("Age");
        attributes.add("Name");
        attributes.add("State");
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserRecyclerViewAdapter(attributes);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Sort");
    }

    public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {
        ArrayList<String> attributes;

        public UserRecyclerViewAdapter(ArrayList<String> data) {
            this.attributes = data;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_row_item, parent, false);
            UserViewHolder userViewHolder = new UserViewHolder(view);

            return userViewHolder;
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            String attribute = attributes.get(position);
            holder.textViewSortAttribute.setText(attribute);
            holder.position = position;
        }

        @Override
        public int getItemCount() {
            return this.attributes.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            TextView textViewSortAttribute;
            View rootView;
            int position;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                textViewSortAttribute = itemView.findViewById(R.id.textViewSortAttribute);
                //textViewSortAttribute.setText(attribute);

                // Gets which row was clicked on
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                itemView.findViewById(R.id.buttonSortAscending).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortOrder = "ascending";
                        Log.d(TAG, "onClick: attribute clicked " + position + " sort by " + sortOrder);
                        mListener.sortCriteriaSelected(position, sortOrder);
                    }
                });

                itemView.findViewById(R.id.buttonSortDescending).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortOrder = "descending";
                        Log.d(TAG, "onClick: attribute clicked " + position + " sort by " + sortOrder);
                        mListener.sortCriteriaSelected(position, sortOrder);
                    }
                });
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SortFragment.SortFragmentListener) {
            mListener = (SortFragment.SortFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SortFragmentListener");
        }
    }

    public interface SortFragmentListener {
        void sortCriteriaSelected(int position, String sortOrder);
    }
}