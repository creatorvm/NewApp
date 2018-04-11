package com.cvm.android.dancesterz.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.AcceptChallengeDao;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.PlaceChallengeActivity;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;


public class JoinChallengeFragment extends Fragment implements OnTaskCompleted {

    private static JoinChallengeFragment joinChallenge = new JoinChallengeFragment();
    private final static String TAG = "JoinChallengeFragment";
    private Button placechallenge;
    private Button reshoot;

    Long challengeId      = null;
    Long challengeVideoId = null;
    Long audioId          = null;
    Long userId           = null;

    private AcceptChallengeDao acceptDao = new AcceptChallengeDao();

    public static JoinChallengeFragment getInstance() {
        return joinChallenge;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view   = inflater.inflate(R.layout.join_challenge_fragment, container, false);



        placechallenge  = view.findViewById(R.id.placechallenge);
        reshoot         = view.findViewById(R.id.reshoot);

        addButtonListeners();
        return view;
    }

    OnTaskCompleted saveCallback = new OnTaskCompleted() {
        @Override
        public void onTaskCompleted() {
            getActivity().finish();
                startActivity(new Intent(getActivity(), HomeScreenActivity.class));
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void addButtonListeners(){
        placechallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CLicked", Toast.LENGTH_SHORT).show();
                acceptDao.save(challengeId, userId, challengeVideoId, audioId, saveCallback);
            }
        });

//        reshoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                VideoPlayer.getInstance().stop();
//                getActivity().getFragmentManager()
//                .beginTransaction()
//                .remove(joinChallenge)
//                .commit();
//                ((PlaceChallengeActivity)getActivity()).loadVideoRecordingFragment();
//            }
//        });
    }


    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Long getChallengeVideoId() {
        return challengeVideoId;
    }

    public void setChallengeVideoId(Long challengeVideoId) {
        this.challengeVideoId = challengeVideoId;
    }

    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public void onTaskCompleted() {
        Toast.makeText(getActivity(), "Uploaded....", Toast.LENGTH_SHORT).show();
    }
}
