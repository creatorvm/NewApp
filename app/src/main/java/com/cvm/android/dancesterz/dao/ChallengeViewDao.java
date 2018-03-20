package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallegeResponse;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Devalopment-1 on 12-01-2018.
 */

public class ChallengeViewDao extends BaseDao {
    static final String TAG = "ChallengeViewDao";
    //    String token;
    Long ChallengeId;
    List<AcceptChallengeDto> challenges = null;
    List<ChallengeDto> challenge = null;
    OnTaskCompleted parent;

    public ChallengeViewDao(Long ChallengeId, List<AcceptChallengeDto> list, List<ChallengeDto> challengeList, OnTaskCompleted callback) {
        Log.v(TAG, "ChallengeViewDao constructor");
//        this.token = LoginDao.TOKEN;
        this.parent = callback;
        this.ChallengeId = ChallengeId;
        this.challenges = list;
        this.challenge = challengeList;
        Log.v(TAG, " ChallengeId  " + ChallengeId);
        new AcceptListTask().execute();
    }

    public void getAcceptChallenges(List<AcceptChallengeDto> list, List<ChallengeDto> challengeList) {
        this.challenges = list;
        this.challenge = challengeList;
    }

    private class AcceptListTask extends AsyncTask<Void, Void, ChallegeResponse> {
        ResponseEntity<ChallegeResponse> challegeResponse;
        ChallegeResponse challegeRes = null;

        @Override
        protected ChallegeResponse doInBackground(Void... params) {
            try {
                Log.v(TAG, "doInBackground");
                challenges.clear();
//                HttpHeaders requestHeaders = new HttpHeaders();
//                requestHeaders.add("X-Authorization", token);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                HttpEntity<String> request = new HttpEntity<String>(getHeaders());
                Log.v(TAG, URLs.URL_CREATECHALLENGE + ChallengeId);
                challegeResponse = restTemplate.exchange(URLs.URL_CREATECHALLENGE + ChallengeId, HttpMethod.GET, request, ChallegeResponse.class);
                challegeRes = challegeResponse.getBody();
                Log.v(TAG, "Challenge view response");
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
            return challegeRes;
        }

        @Override
        protected void onPostExecute(ChallegeResponse challegeResponse) {
            super.onPostExecute(challegeResponse);
            challenges.clear();
            if (challegeResponse != null) {
                challenges.addAll(challegeResponse.getChallenges().get(0).getResponses());
                challenge.addAll(challegeResponse.getChallenges());
//                for (AcceptChallengeDto acceptChallengeDto : challenges) {
//                    Log.d(TAG, acceptChallengeDto.getVideo().getSourcePath());
//                }
            }
            parent.onTaskCompleted();
        }
    }
}
