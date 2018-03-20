package com.cvm.android.dancesterz.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.BaseDao;
import com.cvm.android.dancesterz.dto.ChallegeResponse;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.adapters.SeeAllAdapter;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Development-2 on 07-03-2018.
 */

public class Paginator extends BaseDao {
    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    String url;
    static final String TAG = "BaseDao";
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    SeeAllAdapter adapter;
    int i;
    int flag = 0;
    int listsz;
    int pagenum = 0;
    List<ChallengeDto> Challengelist = new ArrayList<ChallengeDto>();
    List<ChallengeDto> chlist = new ArrayList<ChallengeDto>();

//    public Paginator(final Context c, PullToLoadView pullToLoadView, String url) {
    public Paginator(Activity activity, PullToLoadView pullToLoadView, String url) {
//        this.c = c;
        this.c = activity.getApplicationContext();
        this.pullToLoadView = pullToLoadView;
        this.url = url;
        //RECYCLERVIEW
        RecyclerView rv = pullToLoadView.getRecyclerView();
//        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
        final RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(c, 2);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.findViewById(R.id.seeAllrecyclerview);
        adapter = new SeeAllAdapter(new ArrayList<ChallengeDto>(), c);
        rv.setAdapter(adapter);
        this.pullToLoadView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.cardview_light_background);
        new HttpRequestTask().execute("");
        initializePaginator();
    }


    private class HttpRequestTask extends AsyncTask<String, String, ChallegeResponse> {
        ChallegeResponse challege = null;

        @Override
        protected ChallegeResponse doInBackground(String... params) {
            Log.d(TAG, "seeall doInBackground");
            try {
                ResponseEntity<ChallegeResponse> challegeResponse;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                HttpHeaders requestHeaders = getHeaders();

                HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
                challegeResponse = restTemplate.exchange(url + "/" + params[0], HttpMethod.GET, request, ChallegeResponse.class);
                challege = challegeResponse.getBody();
                Log.e(TAG, challege.getChallenges().size() + "");
            } catch (Exception e) {
                Log.e(TAG, "Error while Login Process", e);
            }
            return challege;
        }

        @Override
        protected void onPostExecute(ChallegeResponse o) {
            super.onPostExecute(o);
            if (o != null && challege.getChallenges() != null) {
                Log.v("Paginator", "Total Challenges " + challege.getChallenges().size());
                Challengelist.clear();
                Challengelist.addAll(challege.getChallenges());
                if (chlist.size() > 0) {
                    chlist.clear();
                }
                chlist.addAll(Challengelist);
            }
            if (flag == 0) {
                listsz = Challengelist.size();
                flag = 1;
            }
            if (listsz != Challengelist.size()) {
                adapter.clear();
                chlist.clear();
                chlist.addAll(Challengelist);
                flag = 1;
            }
            Log.v("Paginator", "Challenge response");
        }
    }


    public void initializePaginator() {

        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {

            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                Log.e("Next Page", nextPage + "");
                new HttpRequestTask().execute(String.valueOf(nextPage));
                loadData(nextPage);
            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                loadData(1);
            }

            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });

        pullToLoadView.initLoad();
    }

    public void loadData(final int page) {
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                int j = i;
                if (i < chlist.size()) {
                    //ADD CURRENT PAGE'S DATA
                    for (int index = 0; index < chlist.size(); index++) {
                        adapter.add(chlist.get(index));
                    }
                }
                //UPDATE PROPETIES
                pullToLoadView.setComplete();
                isLoading = false;
                nextPage = page + 1;
            }
        }, 100);
    }
}
