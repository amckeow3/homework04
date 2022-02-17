

//  Homework 04
//  McKeown_HW04
//  Adrianna McKeown

package com.example.mckeown_hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UsersFragment extends Fragment {
    private static final String ARG_STATE = "state";
    private static final String ARG_PARAM2 = "param2";
    private String selectedState;
    private String mParam2;
    // ----------------------------------------------------------------------------
    private static final String TAG = "Users Fragment";
    UserFragmentListener mListener;
    private ArrayList<DataServices.User> users = new ArrayList<>();
    private ArrayList<DataServices.User> filteredUsers;
    UserAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    public static UsersFragment newInstance(String state) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedState = getArguments().getString(ARG_STATE);
        }
        users = new ArrayList<>();
        users = DataServices.getAllUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ListView listView = view.findViewById(R.id.linkedListUsers);
        adapter = new UserAdapter(getContext(), R.layout.user_row_item, users);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Users");

        //  Clicking the Filter button shows the Filter by State Fragment and puts the Users
        //  fragment on the back stack.
        view.findViewById(R.id.buttonFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.filter();
            }
        });

        // Clicking the Sort button shows the Sort Fragment and puts the Users fragment
        // on the back stack.
        view.findViewById(R.id.buttonSort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sort();
            }
        });
    }

    static class UserAdapter extends ArrayAdapter<DataServices.User> {
        public UserAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.User> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row_item, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textViewName = convertView.findViewById(R.id.textViewName);
                viewHolder.textViewState = convertView.findViewById(R.id.textViewState);
                viewHolder.textViewAge = convertView.findViewById(R.id.textViewAge);
                viewHolder.textViewGroup = convertView.findViewById(R.id.textViewGroup);
                viewHolder.icon = convertView.findViewById(R.id.imageGenderIcon);
                convertView.setTag(viewHolder);

            }
            DataServices.User user = getItem(position);
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();

            //  User icon is selected based on the user’s gender
            if (user.gender.matches("Female"))
                viewHolder.icon.setImageResource(R.drawable.avatar_female);
            else
                viewHolder.icon.setImageResource(R.drawable.avatar_male);

            viewHolder.textViewName.setText(user.name);
            viewHolder.textViewState.setText(user.state);
            viewHolder.textViewAge.setText(user.age + " Years Old");
            viewHolder.textViewGroup.setText(user.group);

            return convertView;
        }

        private static class ViewHolder {
            TextView textViewName;
            TextView textViewState;
            TextView textViewAge;
            TextView textViewGroup;
            ImageFilterView icon;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserFragmentListener) {
            mListener = (UsersFragment.UserFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement UserFragmentListener");
        }
    }

    public interface UserFragmentListener {
        void filter();
        void sort();
    }

    public void sortUsers(int position, String order) {
        if (position == 0) {
            if (order.equals("ascending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        if(o1.age > o2.age)
                            return 1;
                        else if (o1.age == o2.age)
                            return 0;
                        return -1;
                    }
                });
            } else if (order.equals("descending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        if(o1.age < o2.age)
                            return 1;
                        else if (o1.age == o2.age)
                            return 0 ;
                        return -1;
                    }
                });
            }
        } else if (position == 1) {
            if (order.equals("ascending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        return o1.name.compareTo(o2.name);
                    }
                });
            }
            if (order.equals("descending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        return o2.name.compareTo(o1.name);
                    }
                });
            }
        } else if (position == 2) {
            if (order.equals("ascending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        return o1.state.compareTo(o2.state);
                    }
                });
            } else if (order.equals("descending")) {
                Collections.sort(users, new Comparator<DataServices.User>(){
                    @Override
                    public int compare(DataServices.User o1, DataServices.User o2) {
                        return o2.state.compareTo(o1.state);
                    }
                });
            }
        }
    }

    public void filterUsers(String state) {
        filteredUsers = new ArrayList<>();
        if (state == "All States") {
            users = DataServices.getAllUsers();
        }
        else {
            users = DataServices.getAllUsers();
            for (DataServices.User user : users) {
                if (user.state.matches(state)) {
                    filteredUsers.add(user);
                }
            }
            users.clear();
            users.addAll(filteredUsers);
        }

    }
}