package com.cvm.android.dancesterz.ui.fragments;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.SignUpDao;
import com.cvm.android.dancesterz.dto.UserResponse;
import com.cvm.android.dancesterz.ui.adapters.FavouriteDanceStyleAdapter;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;

import java.util.List;

public class FavouriteDanceStyleFragment extends Fragment {
    RecyclerView favouriteDanceRecyclerView = null;
    FavouriteDanceStyleAdapter favouriteDanceStyleAdapter = null;
    Button favStyleSelectedButton = null;
    List<String> userDanceStyleWholeList = null;
    public FavouriteDanceStyleFragment() {
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
        View view = inflater.inflate(R.layout.fragment_favourite_dance_style, container, false);
        favouriteDanceRecyclerView = view.findViewById(R.id.danceFavouriteStyleRecyclerView);
        favStyleSelectedButton = view.findViewById(R.id.favouriteSelectedButton);

        new SignUpDao("", "", "", "", "", null, null, null,
                "", "", "", "", "", "", "", "", AppConstants.WHOLE_DANCE_STYLES, "", new ParameterListener() {
            @Override
            public void OnTaskCompletedWithParameter(String votes) {
                UserResponse userResponse = SignUpDao.getUserResponse();
                userDanceStyleWholeList = userResponse.getUser().get(0).getUserDanceStyleWholeList();
                favouriteDanceStyleAdapter = new FavouriteDanceStyleAdapter(getActivity(), userDanceStyleWholeList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                favouriteDanceRecyclerView.setLayoutManager(mLayoutManager);
                favouriteDanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
                favouriteDanceRecyclerView.setAdapter(favouriteDanceStyleAdapter);
            }
        });

        favStyleSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> favouriteDanceList = favouriteDanceStyleAdapter.getFavouriteDanceList();
                ProfileFragment.favouriteDanceSelected = favouriteDanceList;
                for (String selected : favouriteDanceList) {
                    Log.e("Selected", selected);
                }
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
            }
        });

        return view;
    }
}
