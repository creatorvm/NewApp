package com.cvm.android.dancesterz.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TabHost;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.JukeboxDao;
import com.cvm.android.dancesterz.dto.AudioDto;
import com.cvm.android.dancesterz.ui.adapters.MusicAdapter;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment implements OnTaskCompleted {

    MusicAdapter musicAdapter = null;
    public static List<AudioDto> media;
    PreferencesManager preferencesManager;
    JukeboxDao jukeboxDao;
    TabHost tabHost;
    ProgressDialog mProgressDialog;
    private static MusicFragment musicFragment = new MusicFragment();
    SearchView searchMusic;
    public static RecyclerView songsRecyclerView = null, songsRecyclerView1 = null;

    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment getInstance() {
        return musicFragment;
    }

    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        songsRecyclerView = view.findViewById(R.id.OurJukebox);
        songsRecyclerView1 = view.findViewById(R.id.OnYourDevice);
        searchMusic = view.findViewById(R.id.search_Music);
//        tabHost = getTabHost();

//        tabHost.setOnTabChangedListener(this);

        media = new ArrayList<AudioDto>();
        jukeboxDao = new JukeboxDao(getActivity(), media, this);
        loadAudio();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();


        songsRecyclerView.setHasFixedSize(true);
        songsRecyclerView1.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());

        songsRecyclerView.setLayoutManager(mLayoutManager);
        songsRecyclerView1.setLayoutManager(mLayoutManager1);

        songsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        songsRecyclerView1.setItemAnimator(new DefaultItemAnimator());

        musicAdapter = new MusicAdapter(getActivity(), media);
        songsRecyclerView.setHasFixedSize(true);
        songsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        songsRecyclerView.setAdapter(musicAdapter);
        return view;
    }

    public void loadAudio() {
        jukeboxDao.searchAudio("*");
    }

    @Override
    public void onTaskCompleted() {
        mProgressDialog.dismiss();
        musicAdapter.notifyDataSetChanged();
    }
}
