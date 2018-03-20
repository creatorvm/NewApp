package com.cvm.android.dancesterz.ui.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;


public class Exoplayer_Fragment extends Fragment implements UniversalVideoView.VideoViewCallback {

    private String TAG = "VideoPlayerActivity";
    FrameLayout mVideoLayout;
    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;
    String videoPath = "";

    private int cachedHeight;
    private boolean isFullscreen;
    private int mSeekPosition;

    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";


    View view;
    private static Exoplayer_Fragment exoplayer_fragment = new Exoplayer_Fragment();

    public static Exoplayer_Fragment newInstance(String videopath) {
        Bundle args = new Bundle();
        args.putString(AppConstants.SOURCEPATH, videopath);
        Exoplayer_Fragment fragment = new Exoplayer_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Exoplayer_Fragment getInstance() {
        return exoplayer_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoPath = getArguments().getString(AppConstants.SOURCEPATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fullscreen_video, container, false);
        mVideoLayout = view.findViewById(R.id.video_layout);
        mVideoView = view.findViewById(R.id.videoView);


//        if (mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
//        }
        mMediaController = view.findViewById(R.id.media_controller);
        mMediaController.setTitle("Loading");

        mVideoView.setMediaController(mMediaController);
        if (getArguments() != null) {
            videoPath = getArguments().getString(AppConstants.SOURCEPATH);
        }
        setVideoAreaSize(videoPath);
        mVideoView.setVideoViewCallback(this);

        if (mSeekPosition > 0) {
            mVideoView.seekTo(mSeekPosition);
        }
        mVideoView.start();
        return view;
    }

    private void setVideoAreaSize(final String videoPath) {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(videoPath);
                mVideoView.requestFocus();
                mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
                    @Override
                    public void onScaleChange(boolean isFullscreen) {
                        isFullscreen = isFullscreen;
                        if (isFullscreen) {
                            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                            mVideoLayout.setLayoutParams(layoutParams);
                            //GONE the unconcerned views to leave room for video and controller
                        } else {
                            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                            layoutParams.height = cachedHeight;
                            mVideoLayout.setLayoutParams(layoutParams);
                        }
                    }

                    @Override
                    public void onPause(MediaPlayer mediaPlayer) {

                    }

                    @Override
                    public void onStart(MediaPlayer mediaPlayer) {

                    }

                    @Override
                    public void onBufferingStart(MediaPlayer mediaPlayer) {

                    }

                    @Override
                    public void onBufferingEnd(MediaPlayer mediaPlayer) {

                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        Log.e("Exo", "onScaleChange");
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("Exo", "On resume");
    }
}
