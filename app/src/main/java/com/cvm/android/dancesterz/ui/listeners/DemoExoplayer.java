package com.cvm.android.dancesterz.ui.listeners;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeViewDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.fragments.ChallegeAcceptersFragment;
import com.cvm.android.dancesterz.ui.fragments.Exoplayer_Fragment;
import com.cvm.android.dancesterz.ui.fragments.VotingFragment;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.github.slashrootv200.exoplayerfragment.BaseExoPlayerActivity;
import com.github.slashrootv200.exoplayerfragment.ExoErrorEvent;
import com.github.slashrootv200.exoplayerfragment.ExoPlayerFragment;
import com.github.slashrootv200.exoplayerfragment.ExoVideoEndedEvent;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DemoExoplayer extends BaseExoPlayerActivity {
    ChallengeViewDao chalengeViewDao;
    public static String TAG="Exoplayer";
    String Challenge_videoPath,audiopath;
    Long ChallengeId,ChallengeVideoId,chaudioId,totalvotes;
    public  Long candidateId;
    LinearLayout challengeVoteLinearLayout;
    LinearLayout challengeAccepterLinearLayout;
    List<AcceptChallengeDto> alAcceptList = null;
    List<ChallengeDto> challengeList = null;

    public DemoExoplayer() {
        super(R.layout.activity_demo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_exoplayer);

        challengeVoteLinearLayout     = findViewById(R.id.challengeVoteLayout);
        challengeAccepterLinearLayout = findViewById(R.id.challengeResponseLinearLayout);

        Challenge_videoPath = getIntent().getExtras().getString(AppConstants.PLAY_VIDEO);
        ChallengeId         = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
        ChallengeVideoId    = getIntent().getExtras().getLong(AppConstants.CHALLENGE_VIDEOID);
        audiopath           = getIntent().getExtras().getString(AppConstants.CHALLENGE_AUDIOPATH);
        totalvotes          = getIntent().getExtras().getLong(AppConstants.TOTAL_VOTES);
        candidateId         = getIntent().getExtras().getLong(AppConstants.OWNER_USERID);
        chaudioId           = getIntent().getExtras().getLong(AppConstants.CHAUDIO_ID);

        alAcceptList        = new ArrayList<AcceptChallengeDto>();
        challengeList        = new ArrayList<ChallengeDto>();
        if (savedInstanceState == null)
        {

            new ChallengeVoteAsyncTask().execute();

        }

    }
    public  void playVideo(String Challenge_videoPath)
    {

        Uri videoUri = Uri.parse(Challenge_videoPath);
        String videoTitle = "Sample Video";
//        mExoPlayerFragment = ExoPlayerFragment.newInstance(videoUri, videoTitle);
        if(mExoPlayerFragment!=null)
        {
            mExoPlayerFragment=null;
            mExoPlayerFragment = ExoPlayerFragment.newInstance(videoUri, videoTitle);
        }
        else {

            mExoPlayerFragment = ExoPlayerFragment.newInstance(videoUri, videoTitle);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, mExoPlayerFragment, ExoPlayerFragment.TAG)
                .commit();
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
                    playVideo(Challenge_videoPath);

                } else {
                    playVideo(alAcceptList.get(0).getVideo().getSourcePath());
                    loadVotingFragment();
                    loadResponseFragment();

                }

                Log.i(TAG, "End of onTaskCompleted Method on GetAcceptChallenges");
            }
        });
        chalengeViewDao.getAcceptChallenges(alAcceptList, challengeList);
        Log.i(TAG, "  Method GetAcceptChallenges finished");

    }
    public void loadResponseFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.challengeResponseLinearLayout,
                ChallegeAcceptersFragment.newInstance(candidateId, (Serializable) challengeList, (Serializable) alAcceptList)).commit();
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
            VotingFragment.getInstance().setChallengeResponseId(alAcceptList.get(0).getResponseId());
            VotingFragment.getInstance().setChallengeID(alAcceptList.get(0).getChallengeId());
            VotingFragment.getInstance().setAccepterId(alAcceptList.get(0).getAccepter().getId());

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

//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.challengeVoteLayout, VotingFragment.getInstance())
//                    .commit();

        }
        if (challengeList != null) {
            if (challengeList.get(0).getOwner().getFirstName() != null) {
                VotingFragment.getInstance().setOwnerName(challengeList.get(0).getOwner().getFirstName());
            }
            else
            {
                VotingFragment.getInstance().setOwnerName("Challenger");
            }
            VotingFragment.getInstance().setOwnerProfilePic(challengeList.get(0).getOwner().getProfileImage());
            ChallengeDto dto=new ChallengeDto();
            if(doesObjectContainField(dto,"firstName"))
            {
                Log.e(TAG,"firstname is present");
                Log.e(TAG,"firstname"+alAcceptList.get(0).getAccepter().getFirstName());
                if (alAcceptList.get(0).getAccepter().getFirstName() != null ) {
                    VotingFragment.getInstance().setAccepterName(alAcceptList.get(0).getAccepter().getFirstName());
                } else {
                    VotingFragment.getInstance().setAccepterName("Accepter");
                }
            }
            else
            {
                Log.e(TAG,"firstname not present");
                VotingFragment.getInstance().setAccepterName("Accepter");
            }
            AcceptChallengeDto acceptChallengeDto=new AcceptChallengeDto();
            if(doesObjectContainField(acceptChallengeDto,"profileImage"))
            {
                VotingFragment.getInstance().setAccepterProfilePic(alAcceptList.get(0).getAccepter().getProfileImage());

            }
        }
    }
    public boolean doesObjectContainField(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onPortrait() {

    }

    @Override
    protected void onLandscape() {

    }

    @Override
    public void onExoVideoEndedEventReceived(ExoVideoEndedEvent e) {

    }

    @Override
    public void onExoErrorReceived(ExoErrorEvent e) {

    }

}
