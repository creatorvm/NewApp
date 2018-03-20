package com.cvm.android.dancesterz.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;

public class BannerFragment extends Fragment {

    FloatingActionButton btnSelecttrack;

    public BannerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        btnSelecttrack = view.findViewById(R.id.btncreatechallenge);
        btnSelecttrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), MusicPlayActivity.class));
                ((HomeScreenActivity)getActivity()).changeFragments(MusicFragment.getInstance());
            }
        });
        return view;
    }
}
