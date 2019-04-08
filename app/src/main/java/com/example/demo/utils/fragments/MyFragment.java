package com.example.demo.utils.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


public class MyFragment extends Fragment {

    /**
     * Static factory method that takes an int parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     *
     * @param index the index
     * @return the my fragment
     */
    public static MyFragment newInstance(int index) {
        MyFragment f = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}