package com.cvm.android.dancesterz.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeViewDao;
import com.cvm.android.dancesterz.dao.VotingDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.adapters.ChallengeAdapter;
import com.cvm.android.dancesterz.ui.fragments.VideoPlayer;
import com.cvm.android.dancesterz.ui.fragments.VotingFragment;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ChallengeVoteActivity extends AppCompatActivity {
    Toolbar toolbar;
    ChallengeViewDao chalengeViewDao;
    RecyclerView rchallengelistView;
    ChallengeAdapter rchallengelistViewadapter;
    public  static PreferencesManager preferencesManager;
    List<AcceptChallengeDto> alAcceptList=null;
    List<ChallengeDto> challengeList = null;
    String Challenge_videoPath,audiopath;
    Long ChallengeId,ChallengeVideoId,chaudioId,totalvotes;
    public  Long candidateId;
    BigInteger no_of_votes1,no_of_votes2;
    Button joinChallenge,btnvote1,btnvote2;
    //SurfaceHolder videoHolder;
    LinearLayout linearLayout;
    TextView no_votes_user1,no_votes_user2;
    DrawerLayout drawer;
    Long challengeResponseId;
    private final static String TAG = "ChallengeVoteActivity";
    //MediaPlayer mMediaPlayer;
    //VideoControllerView controller;
    private int mVideoWidth;
    private int mVideoHeight;
    private View mContentView;
    private View mLoadingView;
    private boolean mIsComplete;
    VotingDao votingDao;
    SurfaceHolder holder;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                    return true;
                case R.id.navigation_profile:
//                    startActivity(new Intent(getApplicationContext(), HomescreenActivity.class));
                    return true;
//                case R.id.navigation_notifications:
////                    startActivity(new Intent(getApplicationContext(), HomescreenActivity.class));
//                    return true;
                case R.id.navigation_settings:
//                    startActivity(new Intent(getApplicationContext(), HomescreenActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_vote);
        rchallengelistView = (RecyclerView) findViewById(R.id.vchallenges_list);
//        ChallengevideoSurface=(SurfaceView)findViewById(R.id.videoSurface1);

//        navigationDrawer();
        preferencesManager=new PreferencesManager(getApplicationContext());
        joinChallenge       = (Button) findViewById(R.id.joinChallenge);
        btnvote1            = (Button) findViewById(R.id.btnvoteuser1);
        btnvote2            = (Button) findViewById(R.id.btnvoteuser2);
//        mVideoSurface       = (ResizeSurfaceView) findViewById(R.id.videoSurface1);
//        mContentView        = findViewById(R.id.video_container1);
//        mLoadingView        = findViewById(R.id.loading);

//        linearLayout        = (LinearLayout)findViewById(R.id.votelay) ;

        //        get video details
        Challenge_videoPath = getIntent().getExtras().getString(AppConstants.PLAY_VIDEO);
        ChallengeId         = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
        ChallengeVideoId    = getIntent().getExtras().getLong(AppConstants.CHALLENGE_VIDEOID);
        audiopath           = getIntent().getExtras().getString(AppConstants.CHALLENGE_AUDIOPATH);
        totalvotes          = getIntent().getExtras().getLong(AppConstants.TOTAL_VOTES);
        candidateId         = getIntent().getExtras().getLong(AppConstants.OWNER_USERID);
        chaudioId           = getIntent().getExtras().getLong(AppConstants.CHAUDIO_ID);

        Log.e(TAG,"candidateId  "+candidateId+""+Challenge_videoPath+"chid "+ChallengeId+"chvidid "+ChallengeVideoId+" audioid"+chaudioId+" pathaudio "+audiopath+" challengeResponseId  "+challengeResponseId);

        alAcceptList        = new ArrayList<AcceptChallengeDto>();
        challengeList        = new ArrayList<ChallengeDto>();

        new ChallengeVoteAsyncTask().execute();

        rchallengelistView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rchallengelistViewadapter = new ChallengeAdapter(alAcceptList, getApplicationContext(),candidateId,challengeList);
        rchallengelistView.setHasFixedSize(true);
        rchallengelistView.setItemAnimator(new DefaultItemAnimator());
        rchallengelistView.setAdapter(rchallengelistViewadapter);

        joinChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordvideo();
            }
        });

//        getFragmentManager().beginTransaction()
//                .replace(R.id.videoPlayerContainervote, Exoplayer_Fragment.getInstance())
//                .commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.e(TAG, "Inside onConfigurationChanged " + newConfig.orientation);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            RelativeLayout videoLayout = findViewById(R.id.videoFragment);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoLayout.getLayoutParams();
            params.width = width;
            params.height = height;
            params.setMargins(0, 0, 0, 0);
            videoLayout.bringToFront();

            View view = findViewById(R.id.challengeDataContainer);
            view.setVisibility(View.INVISIBLE);


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoLayout.getLayoutParams();
            RelativeLayout videoLayout = findViewById(R.id.videoFragment);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoLayout.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            params.setMargins(0, 0, 0, 0);
            videoLayout.bringToFront();

            View view = findViewById(R.id.challengeDataContainer);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setVideoPlayerFullWidth() {
        RelativeLayout videoLayout = findViewById(R.id.videoFragment);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoLayout.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = params.MATCH_PARENT;
        params.setMargins(0, 0, 0, 0);
        videoLayout.bringToFront();

        Log.e(TAG, "Inside onConfigurationChanged " + width + "|" + height + "|" + params.height);
    }

    private class ChallengeVoteAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            GetAcceptChallenges();
            return null;
        }
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

//            getFragmentManager().beginTransaction()
//                    .replace(R.id.votefragment, VotingFragment.getInstance())
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
    public void GetAcceptChallenges() {
        try {
            Log.i(TAG, "In GetAcceptChallenges Method");
            chalengeViewDao = new ChallengeViewDao(ChallengeId, alAcceptList, challengeList, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted() {
                    Log.i(TAG, "Inside onTaskCompleted Method on GetAcceptChallenges");

                    if (alAcceptList.isEmpty()) {
                        VideoPlayer.getInstance().setMediaUrl(Uri.parse(Challenge_videoPath));
                        VideoPlayer.getInstance().play();
                    }
                    else {
                        if (alAcceptList.get(0).getVideo().getSourcePath() != null) {
                            Log.i(TAG, "url" + alAcceptList.get(0).getVideo().getSourcePath());
                            VideoPlayer.getInstance().setMediaUrl(Uri.parse(alAcceptList.get(0).getVideo().getSourcePath()));
                            VideoPlayer.getInstance().play();
                            loadVotingFragment();
                            rchallengelistViewadapter.notifyDataSetChanged();
                        }

                    }
                    Log.i(TAG, "End of onTaskCompleted Method on GetAcceptChallenges");
                }
            });
            chalengeViewDao.getAcceptChallenges(alAcceptList, challengeList);

            Log.i(TAG, "  Method GetAcceptChallenges finished");
        }catch (Exception e)
        {
            Log.e(TAG,e.getMessage());
        }
    }

    public void recordvideo(){
//        Log.i(TAG,"In recordvideo Method" );
//        finish();
//        VideoPlayer.getInstance().stop();
//        Intent intent=new Intent(getApplicationContext(),PlaceChallengeActivity.class);
//        intent.putExtra(AppConstants.PLAY_VIDEO,Challenge_videoPath);
//        intent.putExtra(AppConstants.CHALLENGE_ID,ChallengeId);
//        intent.putExtra(AppConstants.CHALLENGE_VIDEOID,ChallengeVideoId);
//        intent.putExtra(AppConstants.CHALLENGE_AUDIOPATH,audiopath);
//        intent.putExtra(AppConstants.CHAUDIO_ID,chaudioId);
//        Log.i(TAG," Calling VideoRecording Activity" );
//        startActivity(intent);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        GetAcceptChallenges();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetAcceptChallenges();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        VideoPlayer.getInstance().stop();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}
