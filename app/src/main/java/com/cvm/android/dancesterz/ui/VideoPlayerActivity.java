package com.cvm.android.dancesterz.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeViewDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.ui.fragments.Exoplayer_Fragment;
import com.cvm.android.dancesterz.ui.fragments.MakeChallengeFragment;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.io.File;
import java.util.List;

public class VideoPlayerActivity extends BaseUIActivity implements OnTaskCompleted {
    //    SurfaceView videoSurface;

    Button btnmakechallenge = null;
    String videoFile;

    TextView challenge_category;
    PreferencesManager preferencesManager;
    private final static String TAG = "VideoPlayerActivity";
    private static final String RECORDED_VIDEO = "myvideo.mp4";
    ChallengeViewDao chalengeViewDao;
    List<AcceptChallengeDto> allist;
    AsyncTask<String, Integer, String> downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recording);

        btnmakechallenge = (Button) findViewById(R.id.btnmakechallenge);
        challenge_category = (TextView) findViewById(R.id.challenge_category);
        new PlayVideoTask().execute();
//        loadVideoRecordingFragment();
    }

//    public void loadVideoRecordingFragment() {
//        DanceRecorder.instance().setFileName(RECORDED_VIDEO);
//        getFragmentManager().beginTransaction()
//                .replace(R.id.videoRecording, DanceRecorder.instance())
//                .commit();
//        String audiopath = getIntent().getExtras().getString(AppConstants.PLAY_AUDIO);
////        DanceRecorder.instance().setAudioUri(Uri.parse(audiopath));
//        downloadTask = new DownloadTask(VideoPlayerActivity.this).execute(audiopath);
//        DanceRecorder.instance().setAudioUri(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "download.m4a"));
//    }

//    private class DownloadTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String audiopath = getIntent().getExtras().getString(AppConstants.PLAY_AUDIO);
//            DanceRecorder.instance().setAudioUri(Uri.parse(audiopath));
//            return null;
//        }
//    }

    @Override
    public void onTaskCompleted() {

        new PlayVideoTask().execute();

    }

    private class PlayVideoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            videoFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "challengevideo.mp4";
            Long audioId = getIntent().getExtras().getLong(AppConstants.AUDIO_ID);
            Log.e(TAG, "audioId" + audioId);
            preferencesManager = new PreferencesManager(getApplicationContext());
            if (videoFile != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.videoPlayerContainer, Exoplayer_Fragment.newInstance(videoFile))
                        .commit();
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.videoRecording, MakeChallengeFragment.getInstance())
                    .commit();



//                        VideoPlayer.getInstance().play();
//                        loadVotingFragment();
//                     VideoPlayer.getInstance().play();

            MakeChallengeFragment.getInstance().setAudioId(audioId);
            MakeChallengeFragment.getInstance().setVideoFile(videoFile);


            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (downloadTask.isCancelled()) {
            downloadTask.cancel(true);
        }

    }
}
