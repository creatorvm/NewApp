package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.AcceptChallengeFacadeRequest;
import com.cvm.android.dancesterz.dto.VideoDto;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Devalopment-1 on 19-01-2018.
 */

public class AcceptChallengeDao extends BaseDao {

    String token;
    Long   challengeId;
    Long   userId;
    String mixedvideopath;
    Long   saveVideoIdResponse,ChallengeVideoId;
    Long   audioId;
    OnTaskCompleted parent;
    private static final String TAG = "AcceptChallengeDao";


    public void save(Long challengeId, Long userId, Long ChallengeVideoId, Long audioId, OnTaskCompleted parent) {

        Log.v(TAG,"In constructor");
        this.challengeId         =   challengeId;
        this.userId              =   userId;
        this.audioId             =   audioId;
        this.ChallengeVideoId    =   ChallengeVideoId;
        this.parent              =   parent;
        Log.v(TAG,"challengeId   "+challengeId+  "  userid  "+userId+"videopath    "+mixedvideopath+" "+"audid   "+audioId+"challenge owner vid "+ChallengeVideoId);
        new AcceptChallengeTask().execute();
    }

    private class AcceptChallengeTask extends AsyncTask<String, Void, Boolean> {
        Boolean acceptRequest;
        ResponseEntity<Boolean>  response;
        @Override
        protected Boolean doInBackground(String... voids) {
            try {
                // The URL for making the POST request
                Log.v(TAG, "In Accept Challenge Task");
                HttpHeaders headers = getHeaders();
//                headers.add("X-Authorization", token);
                headers.add("Content-Type", "application/json");

                AcceptChallengeFacadeRequest aRequest = new AcceptChallengeFacadeRequest();
                aRequest.setChallenge(new AcceptChallengeDto());
                aRequest.getChallenge().setVideo(new VideoDto());
                aRequest.getChallenge().getVideo().setAudioId(audioId);
                aRequest.getChallenge().getVideo().setId(ChallengeVideoId);
                aRequest.getChallenge().setChallengeId(challengeId);
                aRequest.getChallenge().setDesc("Saved from Android");


                HttpEntity<AcceptChallengeFacadeRequest> request = new HttpEntity<AcceptChallengeFacadeRequest>(aRequest, headers);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                response        = restTemplate.exchange(URLs.URL_ACCEPTCHALLENGE, HttpMethod.PUT, request, Boolean.class);
                acceptRequest   = response.getBody();
                Log.v(TAG, response.toString());
            }
            catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return acceptRequest;
        }
        @Override
        protected void onPostExecute(Boolean response) {
            super.onPostExecute(response);

            parent.onTaskCompleted();

        }
    }

}
