package com.cvm.android.dancesterz.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ChallengeDao;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.adapters.NewsAdapter;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedsFragment extends Fragment {

    public RecyclerView Newsfeeds;
    ChallengeDao challengeDao;
    List<ChallengeDto> myVideosDtoList;
    NewsAdapter newsAdapter;
    private static NewsFeedsFragment newsFeedFragment = new NewsFeedsFragment();
    String url;
    String heading;
    TextView headingTextView;
    ProgressBar loadingProgressBar;
    LinearLayoutManager linearLayoutManager;
    int visibleThreshold = 10;


    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;

    public NewsFeedsFragment() {
        // Required empty public constructor
    }

    public static NewsFeedsFragment getInstance() {
        return newsFeedFragment;
    }

    public static NewsFeedsFragment newInstance(String url, String heading) {
        NewsFeedsFragment fragment = new NewsFeedsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_feeds, container, false);
        if (getArguments() != null) {
            url = getArguments().getString(AppConstants.URL);
            heading = getArguments().getString(AppConstants.HEADING);
        }

        myVideosDtoList = new ArrayList<>();
        Newsfeeds = view.findViewById(R.id.newsFeedRecyclerView);
        headingTextView = view.findViewById(R.id.headingTextView);
        loadingProgressBar = view.findViewById(R.id.mainProgressBar);
        challengeDao = new ChallengeDao(url);
        udpateHotChallenge();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Newsfeeds.setLayoutManager(linearLayoutManager);
        Newsfeeds.setItemAnimator(new DefaultItemAnimator());
        Newsfeeds.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(getActivity(), heading, myVideosDtoList);
        Newsfeeds.setAdapter(newsAdapter);
        headingTextView.setText(heading);

        Newsfeeds.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) Newsfeeds.getLayoutManager();
                int totalItemCount = myVideosDtoList.size();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    loadMore();
                } else {
                    isLoading = false;
                }
            }
        });

//        Newsfeeds.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1; //Increment page index to load the next one
//                loadNextPage();
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
//// mocking 1 second network delay
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadFirstPage();
//            }
//        }, 1000);
        return view;
    }

    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        newsAdapter.addAll(myVideosDtoList);
        newsAdapter.notifyDataSetChanged();
            }
        }, 0);
        currentPage = currentPage + 1;
        isLoading = true;
        udpateHotChallenge();
//setValues();
    }

//    private void loadFirstPage() {
//        // fetching dummy data
//        loadingProgressBar.setVisibility(View.GONE);
//        challengeDao = new ChallengeDao(url);
//        udpateHotChallenge();
//        newsAdapter.addAll(myVideosDtoList);
//
////        if (currentPage <= TOTAL_PAGES) {
////            newsAdapter.addLoadingFooter();
////        } else {
////            isLastPage = true;
////        }
//    }
//
//    private void loadNextPage() {
//        newsAdapter.removeLoadingFooter();  // 2
//        isLoading = false;   // 3
//        challengeDao = new ChallengeDao(url + File.separator + currentPage);
//        udpateHotChallenge();
//        newsAdapter.addAll(myVideosDtoList);   // 4
//
////        if (currentPage != TOTAL_PAGES) {
////            newsAdapter.addLoadingFooter();  // 5
////        } else {
////            isLastPage = true;
////        }
//    }

    private void udpateHotChallenge() {
        challengeDao.getHotChallenges(myVideosDtoList, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {
                Log.v("ChallengeDao", "in challange");
                newsAdapter.notifyDataSetChanged();
            }
        }, currentPage);
    }
}
