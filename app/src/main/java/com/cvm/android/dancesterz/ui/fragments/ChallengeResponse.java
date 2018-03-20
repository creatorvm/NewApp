package com.cvm.android.dancesterz.ui.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeViewDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class ChallengeResponse extends Fragment {

    String Challenge_videoPath;
    Long ChallengeId;
    Long ChallengeVideoId;
    Long candidateId;
    String audiopath;
    Long chaudioId;
    String challengeName;
    private final static String TAG = "ChallengeVoteActivity";
    List<AcceptChallengeDto> alAcceptList = null;
    List<ChallengeDto> challengeList = null;
    ChallengeViewDao chalengeViewDao;

    LinearLayout challengeVideoLinearLayout;
    LinearLayout challengeVoteLinearLayout;
    LinearLayout challengeAccepterLinearLayout;

    PreferencesManager preferencesManager;

    public ChallengeResponse() {
        // Required empty public constructor
    }

    public static ChallengeResponse newInstance(String Challenge_videoPath, Long ChallengeId, Long ChallengeVideoId, Long candidateId, String audiopath, Long chaudioId, String challengeName) {
        ChallengeResponse fragment = new ChallengeResponse();
        Bundle args = new Bundle();
        args.putString(AppConstants.PLAY_VIDEO, Challenge_videoPath);
        args.putLong(AppConstants.CHALLENGE_ID, ChallengeId);
        args.putLong(AppConstants.CHALLENGE_VIDEOID, ChallengeVideoId);
        args.putLong(AppConstants.OWNER_USERID, candidateId);
        args.putString(AppConstants.CHALLENGE_AUDIOPATH, audiopath);
        args.putLong(AppConstants.CHAUDIO_ID, chaudioId);
        args.putString(AppConstants.CHALLENGE_NAME, challengeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Challenge_videoPath = getArguments().getString(AppConstants.PLAY_VIDEO);
            ChallengeId = getArguments().getLong(AppConstants.CHALLENGE_ID);
            ChallengeVideoId = getArguments().getLong(AppConstants.CHALLENGE_VIDEOID);
            candidateId = getArguments().getLong(AppConstants.OWNER_USERID);
            audiopath = getArguments().getString(AppConstants.CHALLENGE_AUDIOPATH);
            challengeName = getArguments().getString(AppConstants.CHALLENGE_NAME);
            chaudioId = getArguments().getLong(AppConstants.CHAUDIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        challengeVideoLinearLayout = view.findViewById(R.id.challengeVideoLinearLayout);
        challengeVoteLinearLayout = view.findViewById(R.id.challengeVoteLinearLayout);
        challengeAccepterLinearLayout = view.findViewById(R.id.challengeResponseLinearLayout);
        if (getArguments() != null) {
            Challenge_videoPath = getArguments().getString(AppConstants.PLAY_VIDEO);
            ChallengeId = getArguments().getLong(AppConstants.CHALLENGE_ID);
            ChallengeVideoId = getArguments().getLong(AppConstants.CHALLENGE_VIDEOID);
            candidateId = getArguments().getLong(AppConstants.OWNER_USERID);
            audiopath = getArguments().getString(AppConstants.CHALLENGE_AUDIOPATH);
            challengeName = getArguments().getString(AppConstants.CHALLENGE_NAME);
            chaudioId = getArguments().getLong(AppConstants.CHAUDIO_ID);
        }
        ActionBar supportActionBar = ((HomeScreenActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle(challengeName);
//        supportActionBar.setIcon(R.drawable.signout);
        preferencesManager = new PreferencesManager(getActivity());
        alAcceptList = new ArrayList<AcceptChallengeDto>();
        challengeList = new ArrayList<ChallengeDto>();

        new ChallengeVoteAsyncTask().execute();
//        getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.getInstance()).commit();
        return view;
    }


    private class ChallengeVoteAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            GetAcceptChallenges();
            return null;
        }
    }

    public void GetAcceptChallenges() {
        Log.i(TAG, "In GetAcceptChallenges Method");
        chalengeViewDao = new ChallengeViewDao(ChallengeId, alAcceptList, challengeList, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                Log.i(TAG, "Inside onTaskCompleted Method on GetAcceptChallenges");



                if (alAcceptList.isEmpty()) {
//                    Exoplayer_Fragment.getInstance().setMediaUrl(Challenge_videoPath);
//                    getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.getInstance()).commit();
//                    Exoplayer_Fragment.getInstance().setMediaUrl(Challenge_videoPath);
//                    VideoPlayer.getInstance().play();
                    // Setting Width -
//                    setVideoPlayerFullWidth();
                    getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.newInstance(Challenge_videoPath)).commit();
                } else {
                    Log.i(TAG, "url" + alAcceptList.get(0).getVideo().getSourcePath());
//                    Exoplayer_Fragment.getInstance().setMediaUrl(Challenge_videoPath);
//                    getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.getInstance()).commit();
//
//                    VideoPlayer.getInstance().play();
                    // Setting Width -
//                    setVideoPlayerFullWidth();
                    getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.newInstance(alAcceptList.get(0).getVideo().getSourcePath())).commit();
                    loadVotingFragment();
                }
//                getChildFragmentManager().beginTransaction().replace(R.id.challengeVideoLinearLayout, VideoPlayer.getInstance()).commit();
//                VideoPlayer.getInstance().play();
                Log.i(TAG, "End of onTaskCompleted Method on GetAcceptChallenges");
            }
        });
        chalengeViewDao.getAcceptChallenges(alAcceptList, challengeList);
        Log.i(TAG, "  Method GetAcceptChallenges finished");

//        if (alAcceptList != null) {
//            getChildFragmentManager().beginTransaction().replace(R.id.challengeResponseLinearLayout, ChallegeAcceptersFragment.newInstance(candidateId)).commit();
//        }
    }

    public void loadVotingFragment() {

        Log.i(TAG, "Loading voting fragment");
        if (!alAcceptList.isEmpty()) {

            Log.i(TAG, "getResponseId" + alAcceptList.get(0).getResponseId());
            Log.i(TAG, "getChallengeId" + alAcceptList.get(0).getChallengeId());
            Log.i(TAG, "getAccepterVote" + alAcceptList.get(0).getAccepterVote());
            Log.i(TAG, "getChallengerVote" + alAcceptList.get(0).getChallengerVote());

//            for (AcceptChallengeDto challengeDto : alAcceptList) {
            VotingFragment.getInstance().setCandidateId(candidateId);
            VotingFragment.getInstance().setPreferencesManager(preferencesManager);
            VotingFragment.getInstance().setChallengeResponseId(alAcceptList.get(0).getResponseId());
            VotingFragment.getInstance().setChallengeID(alAcceptList.get(0).getChallengeId());
            if (alAcceptList.get(0).getAccepterVote() != null) {
                VotingFragment.getInstance().setResponseVote(alAcceptList.get(0).getAccepterVote());
            } else {
                VotingFragment.getInstance().setResponseVote(new BigInteger("0"));
            }
            if (alAcceptList.get(0).getChallengerVote() != null) {
                VotingFragment.getInstance().setOwnerVote(alAcceptList.get(0).getChallengerVote());
            } else {
                VotingFragment.getInstance().setOwnerVote(new BigInteger("0"));
            }
//            }

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.challengeVoteLinearLayout, VotingFragment.getInstance())
                    .commit();

        }
        if (challengeList != null) {
            if (challengeList.get(0).getOwner().getFirstName() != null ) {
                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName());
            } else {
                VotingFragment.getInstance().setOwnerName("Challenger Name");
            }
            VotingFragment.getInstance().setOwnerProfilePic(challengeList.get(0).getOwner().getProfileImage());
            if (alAcceptList.get(0).getAccepter().getFirstName() != null) {
                VotingFragment.getInstance().setAccepterName(alAcceptList.get(0).getAccepter().getFirstName());
            } else {
                VotingFragment.getInstance().setAccepterName("Accepter Name");
            }
            VotingFragment.getInstance().setAccepterProfilePic(alAcceptList.get(0).getAccepter().getProfileImage());
        }
        if (alAcceptList != null && challengeList != null) {
            getChildFragmentManager().beginTransaction().replace(R.id.challengeResponseLinearLayout,
                    ChallegeAcceptersFragment.newInstance(candidateId, (Serializable) challengeList, (Serializable) alAcceptList)).commit();
        }
    }
}
