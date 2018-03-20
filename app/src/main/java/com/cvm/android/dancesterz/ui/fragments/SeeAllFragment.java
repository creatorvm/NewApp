package com.cvm.android.dancesterz.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.Paginator;
import com.cvm.android.dancesterz.utilities.URLs;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

public class SeeAllFragment extends Fragment {
    List<ChallengeDto> SeeAllList;
    String url = "";
    String heading = "";
    PullToLoadView pullToLoadView;
    RecyclerView seeAllRecyclerView;

    public SeeAllFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SeeAllFragment newInstance(String url, String heading) {
        SeeAllFragment fragment = new SeeAllFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.URL, url);
        args.putString(AppConstants.HEADING, heading);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_all, container, false);
        if (getArguments() != null) {
            url = getArguments().getString(AppConstants.URL);
            heading = getArguments().getString(AppConstants.HEADING);
            if (heading != null && !heading.equals("")) {
                if (heading.equals(AppConstants.HOT_CHALLENGES)) {
                    url = URLs.URL_GET_SEE_ALL_HOTCHALLENGE;
                } else if (heading.equals(AppConstants.TRENDING_NOW)) {
                    url = URLs.URL_GET_SEE_ALL_TRENDINGNOW;
                } else if (heading.equals(AppConstants.NEWS_FEEDS)) {
                    url = URLs.URL_GET_SEE_ALL_NEWSFEEDS;
                } else if (heading.equals(AppConstants.ACTIVE_CHALLENGES)) {
                    url = "";
                } else if (heading.equals(AppConstants.RECENT_ACTIVITIES)) {
                    url = "";
                } else if (heading.equals(AppConstants.LAST_WEEK)) {
                    url = "";
                }
            }
        }
        SeeAllList = new ArrayList<>();
        pullToLoadView = view.findViewById(R.id.seeAllrecyclerview);
        new Paginator(getActivity(), pullToLoadView, url);
//        seeAllRecyclerView = view.findViewById(R.id.seeAllrecyclerview);
//        seeAllRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) seeAllRecyclerView.getLayoutManager();
//                int visibleThreshold = 10;
//                int totalItemCount = SeeAllList.size();
//                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
////                if (!is)
//            }
//        });
        return view;
    }
}
