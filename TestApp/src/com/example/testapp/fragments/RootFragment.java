package com.example.testapp.fragments;

import com.example.testapp2.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_root, container, false);
        
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.root_frame, new LoadFragment());
		transaction.commit();
	

		
        return rootView;
    }
}
