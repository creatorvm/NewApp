package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.ChallegeResponse;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

/**
 * Created by Devalopment-1 on 12-01-2018.
 */

public class ChallengeDao extends BaseDao {
    static final String TAG = "ChallengeDao";
    List<ChallengeDto> challenges = null;
    String url;
    int pageNumber;
    private String tmpToken;
    static int count = 0;

    public ChallengeDao(String url) {
        Log.v(TAG, "In constructor");
        this.url = url;
    }

    public void getHotChallenges(List<ChallengeDto> list, OnTaskCompleted callback, int pageNumber) {
        Log.d(TAG, "In getHotChallenges");
        challenges = list;
        this.pageNumber = pageNumber;
        new LoadHotTrailsTask(callback).execute();
    }

    private class LoadHotTrailsTask extends AsyncTask {
        ResponseEntity<ChallegeResponse> challegeResponse;
        ChallegeResponse challege = null;
        OnTaskCompleted callback;

        LoadHotTrailsTask() {
        }

        LoadHotTrailsTask(OnTaskCompleted callback) {
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Log.d(TAG, "In doInBackground" + count);

                RestTemplate restTemplate = getRestTemplate();
                HttpEntity<String> request = new HttpEntity<String>(getHeaders());
                challegeResponse = restTemplate.exchange(url + File.separator + pageNumber, HttpMethod.GET, request, ChallegeResponse.class);
                challege = challegeResponse.getBody();

                if (challegeResponse != null && challege.getChallenges() != null) {
                    Log.v(TAG, "Total Challenges " + challege.getChallenges().size());
//                    challenges.clear();
                    challenges.addAll(challege.getChallenges());
                }
                Log.v(TAG, "Challenge response");
            } catch (HttpStatusCodeException e) {
                Log.d(TAG, "In HttpStatusCodeException " + e.getResponseBodyAsString());
                ErrorResponse result = getErrorResponse(e.getResponseBodyAsString(), new LoadHotTrailsTask(callback));
            } catch (Exception e) {
                Log.d(TAG, "In Exception" + challegeResponse);
                Log.e("Exception", e.getMessage(), e);
//                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (callback != null) {
                callback.onTaskCompleted();
            }
        }
    }
}

