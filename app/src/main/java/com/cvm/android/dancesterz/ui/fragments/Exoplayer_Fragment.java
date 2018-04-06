package com.cvm.android.dancesterz.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;


public class Exoplayer_Fragment extends Fragment {
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private String mediaUrl;
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private BandwidthMeter bandwidthMeter;
    private long mResumePosition;
    private final static String TAG = "Exoplayer_Fragment";
    View view;
    SimpleExoPlayer player=null;
    private DataSource.Factory mediaDataSourceFactory;
    private static Exoplayer_Fragment exoplayer_fragment = new Exoplayer_Fragment();


    public static Exoplayer_Fragment getInstance() {
        return exoplayer_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }
    public static Exoplayer_Fragment newInstance(String videopath) {
        Exoplayer_Fragment fragment = new Exoplayer_Fragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.SOURCEPATH, videopath);
        Log.i(TAG,"new instance"+videopath);
        fragment.setArguments(args);
        return fragment;
    }
//    public String getMediaUrl() {
//        return mediaUrl;
//    }
//
//    public void setMediaUrl(String mediaUrl) {
//        this.mediaUrl = mediaUrl;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_exoplayer, container, false);
        if (mExoPlayerView == null) {
            mExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer);

            if (getArguments() != null) {
                mediaUrl = getArguments().getString(AppConstants.SOURCEPATH);

            }
            Log.i(TAG,"create view "+mediaUrl);
            initFullscreenDialog();
            initFullscreenButton();
//            String streamUrl = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
//            String userAgent = Util.getUserAgent(getActivity(),getActivity().getApplicationInfo().packageName);
//            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), null, httpDataSourceFactory);
//            Uri daUri = Uri.parse(streamUrl);
//            mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaDataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
            mVideoSource = new ExtractorMediaSource(Uri.parse(mediaUrl), mediaDataSourceFactory, extractorsFactory, null, null);
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {

                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }


    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout)view.findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_expand));
    }


    private void initFullscreenButton() {

//        PlaybackControlView controlView = view.findViewById(R.id.exo_controller);
        mFullScreenIcon = view.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = view.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }
    public void stopExoPlayer() {
        player.stop();
    }

    public void initExoPlayer() {


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player= ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);

        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

        mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        player.stop();
    }
    @Override
    public void onResume() {
        super.onResume();
//        if (mExoPlayerView == null) {
//            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
//            initFullscreenDialog();
//            initFullscreenButton();
//
//            String streamUrl = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
//            String userAgent = Util.getUserAgent(getActivity(),getActivity().getApplicationInfo().packageName);
//            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), null, httpDataSourceFactory);
//            Uri daUri = Uri.parse(streamUrl);
//
//            mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
//        }
//
//        initExoPlayer();
//
//        if (mExoPlayerFullscreen) {
//            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
//            mFullScreenDialog.show();
//        }
    }


    @Override
    public void onPause() {

        super.onPause();

//        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
//            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
//            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
//
//            mExoPlayerView.getPlayer().release();
//        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

}
