

//  Homework 04
//  McKeown_HW04
//  Adrianna McKeown

package com.example.mckeown_hw04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements UsersFragment.UserFragmentListener, FilterByStateFragment.FilterByStateFragmentListener,
        SortFragment.SortFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new UsersFragment(), "user_fragment")
                .commit();
    }

    @Override
    public void filter() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new FilterByStateFragment(), "filter_fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sort() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SortFragment(), "sort_fragment")
                .addToBackStack(null)
                .commit();
    }

    //SortFragmentListener
    @Override
    public void stateSelected(String state) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("user_fragment");
        if (usersFragment != null) {
            getSupportFragmentManager()
                    .popBackStack();
            usersFragment.filterUsers(state);
        }
    }

    //FilterByStateFragmentListener
    @Override
    public void sortCriteriaSelected(int position, String sortOrder) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("user_fragment");
        if (usersFragment != null) {
            getSupportFragmentManager()
                    .popBackStack();
            usersFragment.sortUsers(position, sortOrder);
        }
    }
    //find fragment by tag --> update
}