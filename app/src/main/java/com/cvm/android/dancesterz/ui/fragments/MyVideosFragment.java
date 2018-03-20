package com.cvm.android.dancesterz.ui.fragments;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeDao;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.MainActivity;
import com.cvm.android.dancesterz.ui.adapters.HotChallengesAdapter;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MyVideosFragment extends Fragment implements OnTaskCompleted {

    public RecyclerView myVideosRecyclerView;
    ChallengeDao challengeDao;
    List<ChallengeDto> myVideosDtoList;
    RecyclerView.Adapter hotchallengeAdapter;
    ProgressDialog mProgressDialog;
    private static MyVideosFragment myVideosFragment = new MyVideosFragment();
    String url;
    String heading;
    TextView headingTextView;
    TextView seeAllTextView;

    public static MyVideosFragment getInstance() {
        return myVideosFragment;
    }

    public static MyVideosFragment newInstance(String url, String heading) {
        Bundle args = new Bundle();
        args.putString(AppConstants.URL, url);
        args.putString(AppConstants.HEADING, heading);
        MyVideosFragment fragment = new MyVideosFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_videos, container, false);
        if (getArguments() != null) {
            url = getArguments().getString(AppConstants.URL);
            heading = getArguments().getString(AppConstants.HEADING);
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        myVideosDtoList = new ArrayList<>();
        challengeDao = new ChallengeDao(url);
        udpateHotChallenge();
        myVideosRecyclerView = view.findViewById(R.id.myVideosAndExpiredChallengesRecyclerView);
        headingTextView = view.findViewById(R.id.headingTextView);
        seeAllTextView = view.findViewById(R.id.seeall_hc);
        myVideosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        myVideosRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myVideosRecyclerView.setHasFixedSize(true);
        hotchallengeAdapter = new HotChallengesAdapter(myVideosDtoList, getActivity(), heading);
        myVideosRecyclerView.setAdapter(hotchallengeAdapter);
        headingTextView.setText(heading);
        seeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, SeeAllFragment.newInstance(url, heading)).commit();
                ((HomeScreenActivity) getActivity()).changeFragments(SeeAllFragment.newInstance(url, heading));
            }
        });
        return view;
    }

    private void udpateHotChallenge() {
        challengeDao.getHotChallenges(myVideosDtoList, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                mProgressDialog.dismiss();
                hotchallengeAdapter.notifyDataSetChanged();
            }
        }, 0);
    }


    @Override
    public void onTaskCompleted() {
        mProgressDialog.dismiss();
        hotchallengeAdapter.notifyDataSetChanged();
    }

}
