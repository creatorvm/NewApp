package com.cvm.android.dancesterz.ui.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.MediaDao;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.MakeChallengePopUp;
import com.cvm.android.dancesterz.ui.VideoPlayerActivity;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Devalopment-1 on 15-02-2018.
 */

public class MakeChallengeFragment extends Fragment {


    Context context;
    Button btnmakechallenge = null;
    EditText challenge_name = null;
    Long audioId = null;
    PreferencesManager preferencesManager = null;
    String videoFile = null;
    ProgressDialog mProgressDialog;

    public static String TAG = "MakeChallengeFragment";

    private static MakeChallengeFragment makeChallenge = new MakeChallengeFragment();

    private MediaDao mediaDao = null;

    public static MakeChallengeFragment getInstance() {

        return makeChallenge;
    }
    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }


    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.make_challenge_fragment, container, false);

        challenge_name = view.findViewById(R.id.challenge_name);

        btnmakechallenge = view.findViewById(R.id.btnmakechallenge);



        Log.d(TAG, "In Makechallenge " + videoFile);
//        if (videoFile != null) {
//
//            ((VideoPlayerActivity) getActivity()).getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.videolayout, Exoplayer_Fragment.newInstance(videoFile))
//                    .commit();
//        }
//        mProgressDialog = new ProgressDialog(getActivity());
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Please Wait...");
//        mProgressDialog.setCanceledOnTouchOutside(false);


//        if (videoFile != null) {
//
//            try {
//                AssetFileDescriptor file = new AssetFileDescriptor(ParcelFileDescriptor.open(new File(videoFile),
//                        ParcelFileDescriptor.MODE_READ_ONLY), 0,
//                        AssetFileDescriptor.UNKNOWN_LENGTH);
//
//
//                VideoPlayer.getInstance().setLocalFile(file);
//                VideoPlayer.getInstance().play(1000);
//                Log.d(TAG, "In invoked Play!! ");
//            } catch (FileNotFoundException e) {
//                Log.e(TAG, e.getMessage());
//            }
//        }

        mediaDao = new MediaDao(new PreferencesManager(getActivity()), getActivity());
        addButtonListeners();

        return view;
    }

    OnTaskCompleted saveCallback = new OnTaskCompleted() {
        @Override
        public void onTaskCompleted() {
            Log.i(TAG, "In Makechallenge Taskcompleted");
//            mProgressDialog.dismiss();
            startActivity(new Intent(getActivity(), MakeChallengePopUp.class));
            getActivity().finish();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void addButtonListeners() {

        btnmakechallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    VideoPlayer.getInstance().stop();
//                    mProgressDialog.show();
                    Log.i(TAG, "In makeChallenge Method");
                    //mediaDao.upload(Long.valueOf(11), saveCallback);
                    mediaDao.createChallenge(challenge_name.getText().toString(), "Test Desc from Andorid", getAudioId(), getVideoFile(), saveCallback);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
