package com.cvm.android.dancesterz.ui;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;

import com.cvm.android.dancesterz.dao.PreviewDao;
import com.cvm.android.dancesterz.ui.fragments.DanceRecorder;
import com.cvm.android.dancesterz.ui.fragments.Exoplayer_Fragment;
import com.cvm.android.dancesterz.ui.fragments.JoinChallengeFragment;
import com.cvm.android.dancesterz.ui.fragments.VideoPlayer;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.DownloadTask;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.io.File;

public class PlaceChallengeActivity extends BaseUIActivity {
    private FrameLayout myCameraRecordingFrameLayout;
    private final static String TAG = "PlaceChallengeActivity";
    private static final String RECORDED_VIDEO = "challengeResponse.mp4";
    private PowerManager.WakeLock mWakeLock;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recording);
        placechallenge();
    }

//    public void loadVideoRecordingFragment() {
//
//        DanceRecorder.instance().setFileName(RECORDED_VIDEO);
//
//        getFragmentManager().beginTransaction()
//                .replace(R.id.videoRecording, DanceRecorder.instance())
//                .commit();
//
//        String audiopath = getIntent().getExtras().getString(AppConstants.CHALLENGE_AUDIOPATH);
//
//        new DownloadTask(PlaceChallengeActivity.this).execute(audiopath);
//
//        DanceRecorder.instance().setAudioUri(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "download.m4a"));
//    }


    public void placechallenge() {

        progressDialog = new ProgressDialog(PlaceChallengeActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Preparing Video Preview...");
        progressDialog.show();

       final String videoFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "challengevideo.mp4";
        Log.d(TAG, "Recorded Video Location " + videoFile);
        Long challengeId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_ID);
        Long challengeVideoId = getIntent().getExtras().getLong(AppConstants.CHALLENGE_VIDEOID);
        Long audioId = getIntent().getExtras().getLong(AppConstants.CHAUDIO_ID);

        final PreviewDao previewDao = new PreviewDao(new PreferencesManager(getApplicationContext()));

        previewDao.preview(challengeId, challengeVideoId, audioId, videoFile, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                progressDialog.dismiss();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.videoPlayerContainer,Exoplayer_Fragment.newInstance(videoFile))
                        .commit();
            }
        });

        JoinChallengeFragment.getInstance().setAudioId(audioId);
        JoinChallengeFragment.getInstance().setChallengeId(challengeId);
        JoinChallengeFragment.getInstance().setChallengeVideoId(challengeVideoId);

        getFragmentManager().beginTransaction()
                .replace(R.id.videoRecording, JoinChallengeFragment.getInstance()).remove(DanceRecorder.instance())
                .commit();
        Log.i(TAG, videoFile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
