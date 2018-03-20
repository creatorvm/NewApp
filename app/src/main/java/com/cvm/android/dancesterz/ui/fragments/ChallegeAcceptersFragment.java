package com.cvm.android.dancesterz.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.adapters.ChallengeAdapter;
import com.cvm.android.dancesterz.utilities.AppConstants;

import java.io.Serializable;
import java.util.List;

public class ChallegeAcceptersFragment extends Fragment {

    RecyclerView challengeAccepterRecyclerView;
    ChallengeAdapter rchallengelistViewadapter;
    List<AcceptChallengeDto> alAcceptList = null;
    List<ChallengeDto> challengeList = null;
    public static Long candidateId;

    public ChallegeAcceptersFragment() {
        // Required empty public constructor
    }

    public static ChallegeAcceptersFragment newInstance(Long candidateId, Serializable challengeList, Serializable alAcceptList) {
        ChallegeAcceptersFragment fragment = new ChallegeAcceptersFragment();
        Bundle args = new Bundle();
        args.putLong(AppConstants.OWNER_USERID, candidateId);
        args.putSerializable(AppConstants.ACCEPTER_LIST, alAcceptList);
        args.putSerializable(AppConstants.CHALLENGE_LIST, challengeList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            candidateId = getArguments().getLong(AppConstants.OWNER_USERID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challege_accepters, container, false);
        challengeAccepterRecyclerView = view.findViewById(R.id.challengeAccepterRecyclerView);
//        alAcceptList        = new ArrayList<AcceptChallengeDto>();
//        challengeList        = new ArrayList<ChallengeDto>();
        if (getArguments() != null) {
            candidateId = getArguments().getLong(AppConstants.OWNER_USERID);
            alAcceptList = (List<AcceptChallengeDto>) getArguments().getSerializable(AppConstants.ACCEPTER_LIST);
            challengeList = (List<ChallengeDto>) getArguments().getSerializable(AppConstants.CHALLENGE_LIST);
        }
        challengeAccepterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rchallengelistViewadapter = new ChallengeAdapter(alAcceptList, getActivity(), candidateId, challengeList);
        challengeAccepterRecyclerView.setHasFixedSize(true);
        challengeAccepterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        challengeAccepterRecyclerView.setAdapter(rchallengelistViewadapter);
        return view;
    }
}
