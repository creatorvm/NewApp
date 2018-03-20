package com.cvm.android.dancesterz.ui.fragments;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;

import java.io.File;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * Created by prasadprabhakaran on 2/11/18.
 */

public class VideoPlayer extends Fragment {

    private final static String TAG = "VideoPlayer";
    private static VideoPlayer dancePreview = new VideoPlayer();
    //    DPlayerVideoView videoView = null;
//    SimpleMediaPlayerController playerController = null;
    private Uri mediaUrl;
    private AssetFileDescriptor localFile;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    TextView videoTextView;

    public static VideoPlayer getInstance() {
        return dancePreview;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "In onCreateView Method");
        View view = inflater.inflate(R.layout.video_player_fragment, container, false);
        jzVideoPlayerStandard = view.findViewById(R.id.videoplayer);
        videoTextView = view.findViewById(R.id.videotextview);
//        play();

        //
        Log.i(TAG, "In onCreateView End of Method");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //initVideo( videoView);
        Log.d(TAG, "In onResume End of Method");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate Method");

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "In onViewCreated Method");
        jzVideoPlayerStandard = view.findViewById(R.id.videoplayer);
        //super.onViewCreated(view, savedInstanceState);
//        if (mediaUrl != null) {
//            play();
//        }
        Log.i(TAG, "In onViewCreated End of Method");
    }


    private void setSystemUiVisibility(final boolean visible) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        if (!visible) {
            newVis |= View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
    }

    public Uri getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(Uri mediaUrl) {
        this.mediaUrl = mediaUrl;
        this.localFile = null; // If setting URL, then making local file pram to null
    }


    public void play(Integer delay) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }, delay);

    }

    public void play() {
        Uri mediaUrl = getMediaUrl();
//        videoTextView.setText(mediaUrl.toString());
        if (mediaUrl != null) {
            Log.e(TAG, "Video Url : " + this.getMediaUrl().toString());
            jzVideoPlayerStandard.setUp(getMediaUrl().toString(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            jzVideoPlayerStandard.clearFloatScreen();
            jzVideoPlayerStandard.startProgressTimer();
        } else {
            jzVideoPlayerStandard.dismissProgressDialog();
            jzVideoPlayerStandard.setUp(Uri.parse(Environment.getExternalStorageDirectory().getPath() + File.separator + "myvideo.mp4").toString(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        }
    }

    public void stop() {
        jzVideoPlayerStandard.release();
    }

    public AssetFileDescriptor getLocalFile() {
        return localFile;
    }

    public void setLocalFile(AssetFileDescriptor localFile) {
        this.localFile = localFile;
        this.mediaUrl = null;
    }
}
