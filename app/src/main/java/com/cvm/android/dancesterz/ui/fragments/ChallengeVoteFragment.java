package com.cvm.android.dancesterz.ui.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeViewDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.adapters.ChallengeAdapter;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ChallengeVoteFragment extends Fragment {

    String Challenge_videoPath;
    Long ChallengeId;
    Long ChallengeVideoId;
    Long candidateId;
    String audiopath;
    Long chaudioId;
    String challengeName;
    private final static String TAG = "ChallengeVoteActivity";
    List<AcceptChallengeDto> alAcceptList=null;
    List<ChallengeDto> challengeList = null;
    ChallengeViewDao chalengeViewDao;

    RecyclerView rchallengelistView;
    ChallengeAdapter rchallengelistViewadapter;
    public  static PreferencesManager preferencesManager;

    public ChallengeVoteFragment() {
        // Required empty public constructor
    }

    public static ChallengeVoteFragment newInstance(String Challenge_videoPath, Long ChallengeId, Long ChallengeVideoId, Long candidateId, String audiopath, Long chaudioId, String challengeName) {
        ChallengeVoteFragment fragment = new ChallengeVoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_challenge_vote, container, false);
        rchallengelistView = (RecyclerView) view.findViewById(R.id.vchallenges_list);
        if (getArguments() != null) {
            Challenge_videoPath = getArguments().getString(AppConstants.PLAY_VIDEO);
            ChallengeId = getArguments().getLong(AppConstants.CHALLENGE_ID);
            ChallengeVideoId = getArguments().getLong(AppConstants.CHALLENGE_VIDEOID);
            candidateId = getArguments().getLong(AppConstants.OWNER_USERID);
            audiopath = getArguments().getString(AppConstants.CHALLENGE_AUDIOPATH);
            challengeName = getArguments().getString(AppConstants.CHALLENGE_NAME);
            chaudioId = getArguments().getLong(AppConstants.CHAUDIO_ID);
        }
        preferencesManager=new PreferencesManager(getActivity());
        alAcceptList        = new ArrayList<AcceptChallengeDto>();
        new ChallengeVoteAsyncTask().execute();

        rchallengelistView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rchallengelistViewadapter = new ChallengeAdapter(alAcceptList, getActivity(),candidateId,challengeList);
        rchallengelistView.setHasFixedSize(true);
        rchallengelistView.setItemAnimator(new DefaultItemAnimator());
        rchallengelistView.setAdapter(rchallengelistViewadapter);
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
                    VideoPlayer.getInstance().setMediaUrl(Uri.parse(Challenge_videoPath));
                    VideoPlayer.getInstance().play();
                    // Setting Width -
//                    setVideoPlayerFullWidth();
                } else {
                    Log.i(TAG, "url" + alAcceptList.get(0).getVideo().getSourcePath());
                    VideoPlayer.getInstance().setMediaUrl(Uri.parse(alAcceptList.get(0).getVideo().getSourcePath()));
                    VideoPlayer.getInstance().play();
                    // Setting Width -
//                    setVideoPlayerFullWidth();
//                    loadVotingFragment();
                }
                Log.i(TAG, "End of onTaskCompleted Method on GetAcceptChallenges");
            }
        });
        chalengeViewDao.getAcceptChallenges(alAcceptList, challengeList);
        rchallengelistViewadapter.notifyDataSetChanged();
        Log.i(TAG, "  Method GetAcceptChallenges finished");
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

//            getFragmentManager().beginTransaction()
//                    .replace(R.id.votefragment, VotingFragment.getInstance())
//                    .commit();

        }
        if (challengeList != null) {
            if (challengeList.get(0).getOwner().getFirstName() != null && challengeList.get(0).getOwner().getLastName() != null) {
                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName() + " " + challengeList.get(0).getOwner().getLastName());
//            } else if (challengeList.get(0).getOwner().getFirstName() == null && challengeList.get(0).getOwner().getLastName() != null) {
//                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName() + " " + challengeList.get(0).getOwner().getLastName());
//            } else if (challengeList.get(0).getOwner().getFirstName() != null && challengeList.get(0).getOwner().getLastName() == null) {
//                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName() + " " + challengeList.get(0).getOwner().getLastName());
            } else {
                VotingFragment.getInstance().setOwnerName("Challenger Name");
            }
            VotingFragment.getInstance().setOwnerProfilePic(challengeList.get(0).getOwner().getProfileImage());
            if (alAcceptList.get(0).getAccepter().getFirstName() != null && alAcceptList.get(0).getAccepter().getLastName() != null) {
                VotingFragment.getInstance().setAccepterName(alAcceptList.get(0).getAccepter().getFirstName() + " " + alAcceptList.get(0).getAccepter().getLastName());
//            } else if (challengeList.get(0).getOwner().getFirstName() == null && challengeList.get(0).getOwner().getLastName() != null) {
//                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName() + " " + challengeList.get(0).getOwner().getLastName());
//            } else if (challengeList.get(0).getOwner().getFirstName() != null && challengeList.get(0).getOwner().getLastName() == null) {
//                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName() + " " + challengeList.get(0).getOwner().getLastName());
            } else {
                VotingFragment.getInstance().setAccepterName("Accepter Name");
            }
            VotingFragment.getInstance().setAccepterProfilePic(alAcceptList.get(0).getAccepter().getProfileImage());
//            new ChallengeVoteAsyncTask().execute();
        }
    }
}
